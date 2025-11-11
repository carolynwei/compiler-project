package com.gemini.compiler.ast;

/**
 * 关系运算符枚举
 */
public enum RelationalOperator {
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_EQUAL("<="),
    GREATER_EQUAL(">=");
    
    private final String symbol;
    
    RelationalOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
