package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;

/**
 * 为多个优化 Pass 提供的通用辅助方法。
 */
final class OptimizerUtils {

    private OptimizerUtils() {}

    static TACInstruction cloneInstruction(
        TACInstruction original,
        TACOpcode opcode,
        String arg1,
        String arg2,
        String result
    ) {
        TACInstruction clone = new TACInstruction(opcode, arg1, arg2, result);
        clone.setResultType(original.getResultType());
        clone.setMetadata(original.getMetadata());
        clone.setLine(original.getLine());
        return clone;
    }

    static boolean isNumericLiteral(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        int start = (value.charAt(0) == '-') ? 1 : 0;
        if (start == value.length()) {
            return false;
        }
        for (int i = start; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static Integer tryParseInt(String value) {
        if (!isNumericLiteral(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}

