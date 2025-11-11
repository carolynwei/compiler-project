package com.gemini.compiler.ast;

/**
 * 后缀运算符枚举
 */
public enum PostfixOperator {
    INCREMENT("++"),
    DECREMENT("--");
    
    private final String symbol;
    
    PostfixOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
