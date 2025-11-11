package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 简单的循环不变式提升（LICM）传递：
 * <p>
 * 针对由 IR 生成器产生的 for/while 结构，在循环体标签直后的常量 alloca/assign
 * 组合进行外提，使常量初始化只执行一次。该实现不处理通用 SSA，仅覆盖
 * 实验用例所涉及的模式。
 */
public final class LoopInvariantHoistPass implements OptimizerPass {

    private static final String FOR_BODY_PREFIX = "for_body";
    private static final String WHILE_BODY_PREFIX = "while_body";

    private final boolean debug;

    public LoopInvariantHoistPass() {
        this(false);
    }

    public LoopInvariantHoistPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        if (instructions.isEmpty()) {
            return instructions;
        }

        List<TACInstruction> result = new ArrayList<>(instructions.size());
        Set<Integer> skipped = new HashSet<>();

        for (int i = 0; i < instructions.size(); i++) {
            if (skipped.contains(i)) {
                continue;
            }

            TACInstruction instruction = instructions.get(i);
            if (isLoopBodyLabel(instruction)) {
                List<TACInstruction> hoisted = collectLoopInvariants(instructions, i, skipped);
                if (!hoisted.isEmpty()) {
                    result.addAll(hoisted);
                }
                result.add(instruction);
            } else {
                result.add(instruction);
            }
        }

        if (debug && !skipped.isEmpty()) {
            System.out.println("LoopInvariantHoistPass: 提升了 " + skipped.size() + " 条指令");
        }

        return result;
    }

    private List<TACInstruction> collectLoopInvariants(List<TACInstruction> instructions,
                                                       int labelIndex,
                                                       Set<Integer> skipped) {
        List<TACInstruction> hoisted = new ArrayList<>();
        int nextLabel = findNextLabel(instructions, labelIndex + 1);

        int cursor = labelIndex + 1;
        while (cursor + 1 < instructions.size() && cursor + 1 < nextLabel) {
            TACInstruction alloc = instructions.get(cursor);
            TACInstruction assign = instructions.get(cursor + 1);

            if (!isHoistablePair(alloc, assign, instructions, cursor + 2, nextLabel)) {
                break;
            }

            hoisted.add(OptimizerUtils.cloneInstruction(alloc, alloc.getOpcode(), alloc.getArg1(), alloc.getArg2(), alloc.getResult()));
            hoisted.add(OptimizerUtils.cloneInstruction(assign, assign.getOpcode(), assign.getArg1(), assign.getArg2(), assign.getResult()));

            skipped.add(cursor);
            skipped.add(cursor + 1);
            cursor += 2;
        }

        return hoisted;
    }

    private int findNextLabel(List<TACInstruction> instructions, int startIndex) {
        for (int i = startIndex; i < instructions.size(); i++) {
            TACInstruction instruction = instructions.get(i);
            if (instruction.getOpcode() == TACOpcode.LABEL) {
                return i;
            }
        }
        return instructions.size();
    }

    private boolean isHoistablePair(TACInstruction alloc,
                                    TACInstruction assign,
                                    List<TACInstruction> instructions,
                                    int bodyStart,
                                    int bodyEnd) {
        if (alloc.getOpcode() != TACOpcode.ALLOC || assign.getOpcode() != TACOpcode.ASSIGN) {
            return false;
        }

        if (alloc.getMetadata() != null) {
            return false;
        }

        String variable = alloc.getResult();
        if (variable == null || !variable.equals(assign.getResult())) {
            return false;
        }

        if (!OptimizerUtils.isNumericLiteral(assign.getArg1())) {
            return false;
        }

        // 确保循环体内没有对该变量的再次赋值，否则外提会改变语义。
        for (int i = bodyStart; i < bodyEnd; i++) {
            TACInstruction instr = instructions.get(i);
            if (variable.equals(instr.getResult()) && definesValue(instr.getOpcode())) {
                return false;
            }
        }

        return true;
    }

    private boolean definesValue(TACOpcode opcode) {
        switch (opcode) {
            case ASSIGN:
            case CAST:
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case EQ:
            case NE:
            case LT:
            case GT:
            case LE:
            case GE:
            case AND:
            case OR:
            case NOT:
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case MUL_ASSIGN:
            case DIV_ASSIGN:
            case MOD_ASSIGN:
            case INCREMENT:
            case DECREMENT:
            case ARRAY_ACCESS:
            case MEMBER_ACCESS:
            case STRUCT_COPY:
            case SELECT:
                return true;
            default:
                return false;
        }
    }

    private boolean isLoopBodyLabel(TACInstruction instruction) {
        if (instruction.getOpcode() != TACOpcode.LABEL || instruction.getResult() == null) {
            return false;
        }
        String label = instruction.getResult();
        return label.contains(FOR_BODY_PREFIX) || label.contains(WHILE_BODY_PREFIX);
    }
}


