package com.wei.compiler.semantic;

/**
 * SymbolType 枚举
 * 定义标识符的类型，描述它在程序中的角色。
 */
public enum SymbolType {
    VARIABLE,             // 普通变量 (int a;)
    FUNCTION,             // 函数定义或声明 (int func(int);)
    STRUCT_DEFINITION,    // 结构体定义 (struct MyStruct {})
    PARAMETER,            // 函数参数 (void func(int param) {})
    CONSTANT              // 常量 (const int a = 5;)
    // ... 可以添加 TYPEDEF, ENUMERATOR 等
}