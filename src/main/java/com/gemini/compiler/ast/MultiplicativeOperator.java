package com.gemini.compiler.ast;

/**
 * 乘法运算符枚举
 */
public enum MultiplicativeOperator {
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%");
    
    private final String symbol;
    
    MultiplicativeOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
