package com.gemini.compiler.ast;

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
}
