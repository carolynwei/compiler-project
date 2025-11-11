package com.gemini.compiler.ir;

/**
 * 三地址代码 (TAC) 指令类型
 */
public enum TACOpcode {
    // 算术运算
    ADD, SUB, MUL, DIV, MOD,
    
    // 比较运算
    EQ, NE, LT, GT, LE, GE,
    
    // 逻辑运算
    AND, OR, NOT,
    
    // 赋值
    ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN,
    
    // 自增自减
    INCREMENT, DECREMENT,
    
    // 跳转
    GOTO, IF_TRUE, IF_FALSE, IF_ZERO, IF_NONZERO,
    
    // 标签
    LABEL,
    
    // 函数调用
    CALL, RETURN,
    
    // 数组操作
    ARRAY_ACCESS, ARRAY_ASSIGN,
    
    // 结构体操作
    MEMBER_ACCESS, MEMBER_ASSIGN,
    
    // 选择操作
    SELECT,
    
    // 开关操作
    SWITCH,
    
    // 类型转换
    CAST,
    
    // 结构体复制
    STRUCT_COPY,
    
    // 其他
    PARAM, ARG, ALLOC, LOAD, STORE
}