package com.wei.compiler.semantic;

/**
 * SymbolKind 枚举
 * 定义符号在作用域中的种类，描述其存储位置或可见性。
 */
public enum SymbolKind {
    GLOBAL,         // 全局作用域中的符号
    LOCAL,          // 局部作用域中的变量
    PARAMETER,      // 函数参数
    STRUCT_MEMBER   // 结构体的字段/成员
}