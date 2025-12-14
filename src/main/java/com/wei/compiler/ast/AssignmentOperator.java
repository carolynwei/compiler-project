package com.wei.compiler.ast;

/**
 * 赋值操作符枚举
 */
public enum AssignmentOperator {
    ASSIGN("="),
    PLUS_ASSIGN("+="),
    MINUS_ASSIGN("-="),
    MULTIPLY_ASSIGN("*="),
    DIVIDE_ASSIGN("/="),
    MODULO_ASSIGN("%=");
    
    private final String symbol;
    
    AssignmentOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
    
    // 根据token字符串转换为AssignmentOperator
    public static AssignmentOperator fromToken(String token) {
        if ("=".equals(token)) return ASSIGN;
        if ("+=".equals(token)) return PLUS_ASSIGN;
        if ("-=".equals(token)) return MINUS_ASSIGN;
        if ("*=".equals(token)) return MULTIPLY_ASSIGN;
        if ("/=".equals(token)) return DIVIDE_ASSIGN;
        if ("%=".equals(token)) return MODULO_ASSIGN;
        throw new IllegalArgumentException("Unknown assignment operator: " + token);
    }
}