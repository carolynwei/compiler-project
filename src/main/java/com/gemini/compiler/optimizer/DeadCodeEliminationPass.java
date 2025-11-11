package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 死代码消除（含不可达代码移除 + 逆向活跃变量分析）。
 */
public final class DeadCodeEliminationPass implements OptimizerPass {

    private final boolean debug;

    public DeadCodeEliminationPass() {
        this(false);
    }

    public DeadCodeEliminationPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        if (instructions.isEmpty()) {
            return instructions;
        }

        List<TACInstruction> reachableFiltered = removeUnreachableInstructions(instructions);
        if (reachableFiltered.isEmpty()) {
            return reachableFiltered;
        }

        List<TACInstruction> withoutDeadAssignments = removeDeadAssignments(reachableFiltered);

        if (debug) {
            System.out.println("死代码消除完成");
        }

        return withoutDeadAssignments;
    }

    private List<TACInstruction> removeUnreachableInstructions(List<TACInstruction> instructions) {
        ControlFlowGraph cfg = ControlFlowGraph.fromInstructions(instructions);
        boolean[] reachable = cfg.computeReachable();

        List<TACInstruction> result = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            if (reachable[i]) {
                result.add(instructions.get(i));
            } else if (debug) {
                System.out.println("移除不可达指令: " + instructions.get(i));
            }
        }
        return result;
    }

    private List<TACInstruction> removeDeadAssignments(List<TACInstruction> instructions) {
        Set<String> live = new HashSet<>();
        List<TACInstruction> reversed = new ArrayList<>(instructions.size());

        for (int i = instructions.size() - 1; i >= 0; i--) {
            TACInstruction instr = instructions.get(i);
            TACOpcode opcode = instr.getOpcode();

            addUsedOperandsToLive(instr, live);

            boolean definesVariable = definesVariable(instr);
            String result = instr.getResult();

            if (definesVariable && result != null && !live.contains(result) && isRemovableInstruction(opcode)) {
                if (opcode == TACOpcode.ASSIGN && OptimizerUtils.isNumericLiteral(instr.getArg1())) {
                    // 保留常量折叠结果，便于后续 pass 使用
                } else {
                    if (debug) {
                        System.out.println("删除无用赋值: " + instr);
                    }
                    continue;
                }
            }

            if (definesVariable && result != null) {
                live.remove(result);
            }

            reversed.add(instr);
        }

        Collections.reverse(reversed);
        return reversed;
    }

    private void addUsedOperandsToLive(TACInstruction instruction, Set<String> live) {
        TACOpcode opcode = instruction.getOpcode();
        String arg1 = instruction.getArg1();
        String arg2 = instruction.getArg2();
        String result = instruction.getResult();

        addIfVariable(live, arg1);
        addIfVariable(live, arg2);

        switch (opcode) {
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case MUL_ASSIGN:
            case DIV_ASSIGN:
            case MOD_ASSIGN:
            case INCREMENT:
            case DECREMENT:
                addIfVariable(live, result);
                break;
            case ARRAY_ASSIGN:
            case MEMBER_ASSIGN:
            case STRUCT_COPY:
                addIfVariable(live, result);
                break;
            case SELECT:
                addIfVariable(live, instruction.getMetadata());
                break;
            default:
                break;
        }
    }

    private void addIfVariable(Set<String> live, String operand) {
        if (operand != null && !OptimizerUtils.isNumericLiteral(operand)) {
            live.add(operand);
        }
    }

    private boolean definesVariable(TACInstruction instruction) {
        if (instruction.getResult() == null) {
            return false;
        }
        switch (instruction.getOpcode()) {
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
            case ALLOC:
            case LOAD:
            case CALL:
                return true;
            default:
                return false;
        }
    }

    private boolean isRemovableInstruction(TACOpcode opcode) {
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
            case ARRAY_ACCESS:
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case MUL_ASSIGN:
            case DIV_ASSIGN:
            case MOD_ASSIGN:
            case INCREMENT:
            case DECREMENT:
            case SELECT:
                return true;
            default:
                return false;
        }
    }
}

