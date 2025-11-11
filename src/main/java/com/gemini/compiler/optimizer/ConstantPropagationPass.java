package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量传播（含常量折叠）传递。
 */
public final class ConstantPropagationPass implements OptimizerPass {

    private final boolean debug;

    public ConstantPropagationPass() {
        this(false);
    }

    public ConstantPropagationPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        Map<String, Integer> constants = new HashMap<>();
        List<TACInstruction> optimized = new ArrayList<>(instructions.size());

        for (TACInstruction instruction : instructions) {
            TACOpcode opcode = instruction.getOpcode();

            if (opcode == TACOpcode.LABEL) {
                constants.clear();
                optimized.add(instruction);
                continue;
            }

            String substitutedArg1 = substituteConstant(instruction.getArg1(), constants);
            String substitutedArg2 = substituteConstant(instruction.getArg2(), constants);
            String result = instruction.getResult();
            String substitutedMetadata = substituteConstant(instruction.getMetadata(), constants);

            switch (opcode) {
                case ASSIGN: {
                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                        instruction,
                        TACOpcode.ASSIGN,
                        substitutedArg1,
                        null,
                        result
                    );
                    optimized.add(rewritten);
                    updateConstant(constants, result, substitutedArg1);
                    break;
                }
                case SELECT: {
                    Integer condValue = OptimizerUtils.tryParseInt(substitutedArg1);
                    Integer trueValue = OptimizerUtils.tryParseInt(substitutedArg2);
                    Integer falseValue = OptimizerUtils.tryParseInt(substitutedMetadata);

                    if (condValue != null) {
                        boolean conditionTrue = condValue != 0;
                        String chosen = conditionTrue ? substitutedArg2 : substitutedMetadata;
                        TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                                instruction,
                                TACOpcode.ASSIGN,
                                chosen,
                                null,
                                result
                        );
                        optimized.add(rewritten);
                        updateConstant(constants, result, chosen);
                        break;
                    }

                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                            instruction,
                            opcode,
                            substitutedArg1,
                            substitutedArg2,
                            result
                    );
                    rewritten.setMetadata(substitutedMetadata);
                    optimized.add(rewritten);
                    constants.remove(result);
                    break;
                }
                case CAST: {
                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                        instruction,
                        opcode,
                        substitutedArg1,
                        null,
                        result
                    );
                    optimized.add(rewritten);
                    constants.remove(result);
                    break;
                }
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
                case OR: {
                    String simplified = simplifyBinaryWithIdentities(opcode, substitutedArg1, substitutedArg2);
                    if (simplified != null && result != null) {
                        TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                            instruction,
                            TACOpcode.ASSIGN,
                            simplified,
                            null,
                            result
                        );
                        optimized.add(rewritten);
                        updateConstant(constants, result, simplified);
                        break;
                    }

                    Integer lhs = OptimizerUtils.tryParseInt(substitutedArg1);
                    Integer rhs = OptimizerUtils.tryParseInt(substitutedArg2);
                    if (lhs != null && rhs != null) {
                        Integer folded = foldBinary(opcode, lhs, rhs);
                        if (folded != null) {
                            TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                                instruction,
                                TACOpcode.ASSIGN,
                                String.valueOf(folded),
                                null,
                                result
                            );
                            optimized.add(rewritten);
                            updateConstant(constants, result, String.valueOf(folded));
                            break;
                        }
                    }

                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                        instruction,
                        opcode,
                        substitutedArg1,
                        substitutedArg2,
                        result
                    );
                    optimized.add(rewritten);
                    constants.remove(result);
                    break;
                }
                case NOT: {
                    Integer value = OptimizerUtils.tryParseInt(substitutedArg1);
                    if (value != null) {
                        int folded = value == 0 ? 1 : 0;
                        TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                            instruction,
                            TACOpcode.ASSIGN,
                            String.valueOf(folded),
                            null,
                            result
                        );
                        optimized.add(rewritten);
                        updateConstant(constants, result, String.valueOf(folded));
                    } else {
                        TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                            instruction,
                            opcode,
                            substitutedArg1,
                            null,
                            result
                        );
                        optimized.add(rewritten);
                        constants.remove(result);
                    }
                    break;
                }
                case IF_TRUE:
                case IF_FALSE:
                case IF_ZERO:
                case IF_NONZERO: {
                    Integer value = OptimizerUtils.tryParseInt(substitutedArg1);
                    if (value != null) {
                        boolean shouldBranch = evaluateBranch(opcode, value);
                        if (shouldBranch) {
                            optimized.add(OptimizerUtils.cloneInstruction(
                                instruction,
                                TACOpcode.GOTO,
                                null,
                                null,
                                instruction.getResult()
                            ));
                        }
                        // 条件恒为假时直接删除
                    } else {
                        optimized.add(OptimizerUtils.cloneInstruction(
                            instruction,
                            opcode,
                            substitutedArg1,
                            null,
                            instruction.getResult()
                        ));
                    }
                    constants.clear();
                    break;
                }
                case GOTO: {
                    optimized.add(instruction);
                    constants.clear();
                    break;
                }
                case RETURN: {
                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                        instruction,
                        opcode,
                        substitutedArg1,
                        null,
                        result
                    );
                    optimized.add(rewritten);
                    constants.clear();
                    break;
                }
                default: {
                    TACInstruction rewritten = OptimizerUtils.cloneInstruction(
                        instruction,
                        opcode,
                        substitutedArg1,
                        substitutedArg2,
                        result
                    );
                    rewritten.setMetadata(substitutedMetadata);
                    optimized.add(rewritten);
                    if (result != null) {
                        constants.remove(result);
                    }
                    if (hasSideEffect(opcode)) {
                        constants.clear();
                    }
                    break;
                }
            }
        }

        if (debug) {
            System.out.println("常量传播完成");
        }

        return optimized;
    }

    private String substituteConstant(String operand, Map<String, Integer> constants) {
        if (operand == null) {
            return null;
        }
        if (OptimizerUtils.isNumericLiteral(operand)) {
            return operand;
        }
        Integer value = constants.get(operand);
        return value != null ? String.valueOf(value) : operand;
    }

    private void updateConstant(Map<String, Integer> constants, String variable, String value) {
        if (variable == null) {
            return;
        }
        Integer numeric = OptimizerUtils.tryParseInt(value);
        if (numeric != null) {
            constants.put(variable, numeric);
        } else {
            constants.remove(variable);
        }
    }

    private Integer foldBinary(TACOpcode opcode, int lhs, int rhs) {
        switch (opcode) {
            case ADD: return lhs + rhs;
            case SUB: return lhs - rhs;
            case MUL: return lhs * rhs;
            case DIV: return rhs != 0 ? lhs / rhs : null;
            case MOD: return rhs != 0 ? lhs % rhs : null;
            case EQ:  return lhs == rhs ? 1 : 0;
            case NE:  return lhs != rhs ? 1 : 0;
            case LT:  return lhs < rhs ? 1 : 0;
            case GT:  return lhs > rhs ? 1 : 0;
            case LE:  return lhs <= rhs ? 1 : 0;
            case GE:  return lhs >= rhs ? 1 : 0;
            case AND: return (lhs != 0 && rhs != 0) ? 1 : 0;
            case OR:  return (lhs != 0 || rhs != 0) ? 1 : 0;
            default:  return null;
        }
    }

    private String simplifyBinaryWithIdentities(TACOpcode opcode, String left, String right) {
        switch (opcode) {
            case ADD:
                if (isZero(left)) { return right; }
                if (isZero(right)) { return left; }
                break;
            case SUB:
                if (isZero(right)) { return left; }
                break;
            case MUL:
                if (isZero(left) || isZero(right)) { return "0"; }
                if (isOne(left)) { return right; }
                if (isOne(right)) { return left; }
                break;
            case DIV:
                if (isZero(left)) { return "0"; }
                if (isOne(right)) { return left; }
                break;
            case AND:
                if (isZero(left) || isZero(right)) { return "0"; }
                if (isOne(left)) { return right; }
                if (isOne(right)) { return left; }
                break;
            case OR:
                if (isZero(left)) { return right; }
                if (isZero(right)) { return left; }
                if (isOne(left) || isOne(right)) { return "1"; }
                break;
            default:
                break;
        }
        return null;
    }

    private boolean evaluateBranch(TACOpcode opcode, int value) {
        switch (opcode) {
            case IF_TRUE:
            case IF_NONZERO:
                return value != 0;
            case IF_FALSE:
            case IF_ZERO:
                return value == 0;
            default:
                return false;
        }
    }

    private boolean isZero(String value) {
        return "0".equals(value);
    }

    private boolean isOne(String value) {
        return "1".equals(value);
    }

    private boolean hasSideEffect(TACOpcode opcode) {
        switch (opcode) {
            case STORE:
            case ARRAY_ASSIGN:
            case MEMBER_ASSIGN:
            case STRUCT_COPY:
            case CALL:
            case RETURN:
            case SWITCH:
                return true;
            default:
                return false;
        }
    }
}

