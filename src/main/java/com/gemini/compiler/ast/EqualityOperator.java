package com.gemini.compiler.ast;

/**
 * 相等性运算符枚举
 */
public enum EqualityOperator {
    EQUAL("=="),
    NOT_EQUAL("!=");
    
    private final String symbol;
    
    EqualityOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
