package com.gemini.compiler.ast;

/**
 * 一元运算符枚举
 */
public enum UnaryOperator {
    PLUS("+"),
    MINUS("-"),
    NOT("!"),
    INCREMENT("++"),
    DECREMENT("--");
    
    private final String symbol;
    
    UnaryOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
