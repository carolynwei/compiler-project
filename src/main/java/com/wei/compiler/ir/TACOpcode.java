package com.wei.compiler.ir;

/**
 * 三地址代码 (TAC) 指令类型
 */
public enum TACOpcode {
    // 算术运算
    ADD, SUB, MUL, DIV, MOD,
    
    // 浮点算术运算
    FADD, FSUB, FMUL, FDIV, FNEG,
    
    // 比较运算
    EQ, NE, LT, GT, LE, GE,
    // 浮点比较运算
    FEQ, FNE, FLT, FGT, FLE, FGE,
    
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
    ARRAY_INDEX,  // 计算数组元素地址
    
    // 结构体操作
    MEMBER_ACCESS, MEMBER_ASSIGN,
    GET_FIELD_ADDR,  // 获取结构体字段地址
    
    // 地址操作
    GET_ADDR,     // 获取变量地址
    LOAD,         // 从地址加载值
    STORE,        // 存储值到地址
    
    // 指针操作
    DEREFERENCE,  // 指针解引用
    REFERENCE,    // 取地址
    
    // 类型转换
    CAST,
    SITOFP,       // 有符号整数转浮点
    FPTOSI,       // 浮点转有符号整数
    
    // 选择操作
    SELECT,
    
    // 开关操作
    SWITCH,
    
    // 结构体复制
    STRUCT_COPY,
    
    // 其他
    PARAM, ARG, ALLOC
}