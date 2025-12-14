package com.wei.compiler.ast;

public enum BitwiseOperator {
    AND, // & (位与)
    OR,// | (位或)
    XOR,// ^ (位异或)
    NOT // ~ (位非) - 通常在一元表达式中处理
}