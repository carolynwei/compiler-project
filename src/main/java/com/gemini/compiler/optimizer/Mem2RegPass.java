package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简化版的 Mem2Reg 传递：
 * <p>
 * 仅推广函数入口块内的标量 alloca（未出现额外 LABEL），
 * 将成对的 STORE/LOAD 替换为直接的寄存器赋值，去掉冗余的栈访问。
 * 该实现为后续完整 SSA 重写打基础。
 */
public final class Mem2RegPass implements OptimizerPass {

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        List<TACInstruction> optimized = new ArrayList<>();

        int index = 0;
        while (index < instructions.size()) {
            TACInstruction instr = instructions.get(index);
            if (isFunctionLabel(instr)) {
                int start = index;
                int end = findNextFunctionLabel(instructions, index + 1);
                optimized.addAll(promoteInFunction(instructions.subList(start, end)));
                index = end;
            } else {
                optimized.add(instr);
                index++;
            }
        }

        return optimized;
    }

    private boolean isFunctionLabel(TACInstruction instruction) {
        return instruction.getOpcode() == TACOpcode.LABEL
            && instruction.getResult() != null
            && instruction.getResult().startsWith("func_");
    }

    private int findNextFunctionLabel(List<TACInstruction> instructions, int start) {
        for (int i = start; i < instructions.size(); i++) {
            TACInstruction instr = instructions.get(i);
            if (isFunctionLabel(instr)) {
                return i;
            }
        }
        return instructions.size();
    }

    private List<TACInstruction> promoteInFunction(List<TACInstruction> functionBody) {
        // 仅在单块（没有额外 LABEL）内尝试推广。
        boolean hasAdditionalLabels = functionBody.stream()
            .skip(1) // 跳过函数标签
            .anyMatch(instr -> instr.getOpcode() == TACOpcode.LABEL);
        if (hasAdditionalLabels) {
            return new ArrayList<>(functionBody);
        }

        Set<String> promotable = findPromotableAllocas(functionBody);
        if (promotable.isEmpty()) {
            return new ArrayList<>(functionBody);
        }

        Map<String, String> currentValue = new HashMap<>();
        List<TACInstruction> rewritten = new ArrayList<>(functionBody.size());

        for (TACInstruction instruction : functionBody) {
            TACOpcode opcode = instruction.getOpcode();

            switch (opcode) {
                case ALLOC: {
                    String var = instruction.getResult();
                    if (promotable.contains(var)) {
                        // 丢弃冗余 alloca
                        break;
                    }
                    rewritten.add(instruction);
                    break;
                }
                case STORE: {
                    String target = instruction.getResult();
                    if (promotable.contains(target)) {
                        currentValue.put(target, instruction.getArg1());
                        break;
                    }
                    rewritten.add(instruction);
                    break;
                }
                case LOAD: {
                    String source = instruction.getArg1();
                    if (promotable.contains(source)) {
                        String value = currentValue.get(source);
                        if (value != null) {
                            TACInstruction assign = OptimizerUtils.cloneInstruction(
                                instruction,
                                TACOpcode.ASSIGN,
                                value,
                                null,
                                instruction.getResult()
                            );
                            rewritten.add(assign);
                            break;
                        }
                        // 没有已知值，回退到原指令
                    }
                    rewritten.add(instruction);
                    break;
                }
                default:
                    rewritten.add(instruction);
                    break;
            }
        }

        return rewritten;
    }

    private Set<String> findPromotableAllocas(List<TACInstruction> functionBody) {
        Map<String, Candidate> candidates = new HashMap<>();

        for (TACInstruction instruction : functionBody) {
            TACOpcode opcode = instruction.getOpcode();
            switch (opcode) {
                case ALLOC: {
                    String var = instruction.getResult();
                    if (var != null && instruction.getMetadata() == null) {
                        candidates.put(var, new Candidate());
                    }
                    break;
                }
                case STORE: {
                    String target = instruction.getResult();
                    Candidate candidate = candidates.get(target);
                    if (candidate != null) {
                        candidate.seenStore = true;
                    }
                    break;
                }
                case LOAD: {
                    String source = instruction.getArg1();
                    Candidate candidate = candidates.get(source);
                    if (candidate != null && !candidate.seenStore) {
                        candidate.promotable = false;
                    }
                    break;
                }
                default: {
                    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
                        String var = entry.getKey();
                        if (usesVariable(instruction, var) && !isAllowedUse(opcode)) {
                            entry.getValue().promotable = false;
                        }
                    }
                    break;
                }
            }
        }

        Set<String> promotable = new HashSet<>();
        for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
            Candidate candidate = entry.getValue();
            if (candidate.promotable && candidate.seenStore) {
                promotable.add(entry.getKey());
            }
        }
        return promotable;
    }

    private boolean usesVariable(TACInstruction instruction, String variable) {
        return variable.equals(instruction.getArg1())
            || variable.equals(instruction.getArg2())
            || variable.equals(instruction.getResult());
    }

    private boolean isAllowedUse(TACOpcode opcode) {
        return opcode == TACOpcode.STORE || opcode == TACOpcode.LOAD || opcode == TACOpcode.ALLOC;
    }

    private static final class Candidate {
        boolean promotable = true;
        boolean seenStore = false;
    }
}

