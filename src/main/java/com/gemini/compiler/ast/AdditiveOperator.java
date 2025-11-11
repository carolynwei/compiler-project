package com.gemini.compiler.ast;

/**
 * 加法运算符枚举
 */
public enum AdditiveOperator {
    PLUS("+"),
    MINUS("-");
    
    private final String symbol;
    
    AdditiveOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
