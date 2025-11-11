package com.gemini.compiler.ast;

/**
 * 主表达式类型枚举
 */
public enum PrimaryType {
    IDENTIFIER,
    INT_LITERAL,
    FLOAT_LITERAL,
    CHAR_LITERAL,
    STRING_LITERAL,
    PARENTHESIZED_EXPRESSION
}