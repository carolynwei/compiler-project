package com.gemini.compiler.semantic;

/**
 * 语义错误类型枚举
 */
public enum SemanticErrorType {
    // 类型检查错误
    TYPE_MISMATCH("类型不匹配"),
    INCOMPATIBLE_ASSIGNMENT("赋值类型不兼容"),
    FUNCTION_PARAMETER_MISMATCH("函数参数类型或数量不匹配"),
    CONTROL_EXPRESSION_TYPE_ERROR("控制表达式类型错误"),
    RETURN_TYPE_MISMATCH("返回类型不匹配"),
    
    // 声明与作用域错误
    UNDEFINED_IDENTIFIER("未定义的标识符"),
    REDEFINITION("重定义错误"),
    BREAK_CONTINUE_OUTSIDE_LOOP("break或continue语句不在循环体内"),
    
    // 数组与结构体错误
    ARRAY_INDEX_TYPE_ERROR("数组下标不是整数类型"),
    ARRAY_DIMENSION_ERROR("数组访问时维数错误"),
    NON_STRUCT_MEMBER_ACCESS("对非结构体变量使用成员访问运算符"),
    STRUCT_MEMBER_NOT_FOUND("结构体成员不存在"),
    STRUCT_CIRCULAR_DEPENDENCY("结构体定义中存在循环依赖"),
    
    // 其他错误
    DUPLICATE_INITIALIZATION("变量重复初始化"),
    NON_CALLABLE_IDENTIFIER("函数调用时使用了不可调用的标识符"),
    DIVISION_BY_ZERO("除数为零"),
    INVALID_LVALUE("无效的左值"),
    MAIN_FUNCTION_MISSING("main函数缺少或签名错误"),
    SWITCH_CASE_TYPE_MISMATCH("switch表达式类型与case常量类型不匹配"),
    STRUCT_TYPE_UNDEFINED("结构体类型未定义"),
    
    // 新增错误类型
    ARRAY_SIZE_NEGATIVE("数组大小为负数"),
    FUNCTION_RECURSION_DEPTH("函数递归深度过大"),
    VARIABLE_NOT_INITIALIZED("变量未初始化"),
    CONSTANT_MODIFICATION("修改常量"),
    INVALID_OPERATOR_USAGE("运算符使用错误");

    private final String description;
    
    SemanticErrorType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}