package com.wei.compiler.ast;

/**
 * 一元运算符枚举
 */
public enum UnaryOperator {
    PLUS("+"),
    MINUS("-"),
    NOT("!"),
    BITWISE_NOT("~"),
    DEREFERENCE("*"),
    ADDRESS_OF("&"),
    SIZEOF("sizeof"),
    PRE_INCREMENT("++"),
    PRE_DECREMENT("--"),
    POST_INCREMENT("++"),
    POST_DECREMENT("--");
    
    private final String symbol;
    
    UnaryOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
    
    // 根据token字符串转换为UnaryOperator
    public static UnaryOperator fromToken(String token) {
        if ("+".equals(token)) return PLUS;
        if ("-".equals(token)) return MINUS;
        if ("!".equals(token)) return NOT;
        if ("~".equals(token)) return BITWISE_NOT;
        if ("*".equals(token)) return DEREFERENCE;
        if ("&".equals(token)) return ADDRESS_OF;
        if ("sizeof".equals(token)) return SIZEOF;
        if ("++".equals(token)) return PRE_INCREMENT;
        if ("--".equals(token)) return PRE_DECREMENT;
        throw new IllegalArgumentException("Unknown unary operator: " + token);
    }
}