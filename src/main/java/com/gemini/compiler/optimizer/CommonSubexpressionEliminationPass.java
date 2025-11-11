package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 公共子表达式消除传递。
 */
public final class CommonSubexpressionEliminationPass implements OptimizerPass {

    private final boolean debug;

    public CommonSubexpressionEliminationPass() {
        this(false);
    }

    public CommonSubexpressionEliminationPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        Map<ExpressionKey, String> expressionTable = new HashMap<>();
        List<TACInstruction> optimized = new ArrayList<>(instructions.size());

        int exprCounter = 0;

        for (TACInstruction instruction : instructions) {
            TACOpcode opcode = instruction.getOpcode();

            if (isCSECandidate(opcode) && instruction.getResult() != null) {
                String resultName = instruction.getResult();

                if (resultName == null || resultName.isEmpty()) {
                    resultName = "cse_" + (++exprCounter);
                    instruction = OptimizerUtils.cloneInstruction(
                        instruction,
                        opcode,
                        instruction.getArg1(),
                        instruction.getArg2(),
                        resultName
                    );
                }

                ExpressionKey key = ExpressionKey.of(opcode, instruction.getArg1(), instruction.getArg2());
                String existing = expressionTable.get(key);
                if (existing != null) {
                    // ✅ 修复：找到公共子表达式，创建 ASSIGN 指令重用结果
                    TACInstruction replacement = OptimizerUtils.cloneInstruction(
                        instruction,
                        TACOpcode.ASSIGN,
                        existing,
                        null,
                        instruction.getResult()
                    );
                    optimized.add(replacement);
                    // ✅ 修复：不更新表达式表，保持原始计算的映射 (MUL, x, y) -> t1, not -> t2
                    // This ensures if we later see t3 = x * y, it will still map to t1
                    // expressionTable.put(key, instruction.getResult());  // Remove this line
                    // Now call invalidate and availability checks with the replacement
                    invalidateExpressions(expressionTable, replacement);
                    if (breaksAvailability(replacement.getOpcode())) {
                        expressionTable.clear();
                    }
                    continue; // Skip adding original and the old invalidation/availability check
                }
                expressionTable.put(key, instruction.getResult());
                optimized.add(instruction);
            } else {
                optimized.add(instruction);
            }

            // Perform invalidation and availability checks for non-CSE instructions
            // (For CSE instructions, we skip this when continuing above)
            invalidateExpressions(expressionTable, instruction);

            if (breaksAvailability(opcode)) {
                expressionTable.clear();
            }
        }

        if (debug) {
            System.out.println("公共子表达式消除完成");
        }

        return optimized;
    }

    private boolean isCSECandidate(TACOpcode opcode) {
        switch (opcode) {
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
                return true;
            default:
                return false;
        }
    }

    private void invalidateExpressions(Map<ExpressionKey, String> table, TACInstruction instruction) {
        String definedVar = instruction.getResult();
        if (definedVar == null) {
            return;
        }

        table.entrySet().removeIf(entry ->
            entry.getValue().equals(definedVar) || entry.getKey().usesVariable(definedVar)
        );
    }

    private boolean breaksAvailability(TACOpcode opcode) {
        switch (opcode) {
            case CALL:
            case RETURN:
            case GOTO:
            case IF_TRUE:
            case IF_FALSE:
            case IF_ZERO:
            case IF_NONZERO:
            case LABEL:
            case SWITCH:
            case STORE:
            case ARRAY_ASSIGN:
            case MEMBER_ASSIGN:
                return true;
            default:
                return false;
        }
    }

    private static final class ExpressionKey {
        private final TACOpcode opcode;
        private final String left;
        private final String right;

        private ExpressionKey(TACOpcode opcode, String left, String right) {
            this.opcode = opcode;
            this.left = left;
            this.right = right;
        }

        static ExpressionKey of(TACOpcode opcode, String arg1, String arg2) {
            if (isCommutative(opcode) && arg1 != null && arg2 != null) {
                if (arg1.compareTo(arg2) > 0) {
                    return new ExpressionKey(opcode, arg2, arg1);
                }
            }
            return new ExpressionKey(opcode, arg1, arg2);
        }

        boolean usesVariable(String variable) {
            return variable.equals(left) || variable.equals(right);
        }

        private static boolean isCommutative(TACOpcode opcode) {
            switch (opcode) {
                case ADD:
                case MUL:
                case EQ:
                case NE:
                case AND:
                case OR:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ExpressionKey)) {
                return false;
            }
            ExpressionKey other = (ExpressionKey) obj;
            return opcode == other.opcode && Objects.equals(left, other.left) && Objects.equals(right, other.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(opcode, left, right);
        }
    }
}

