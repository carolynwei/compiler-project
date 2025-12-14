package com.wei.compiler.semantic;

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
    INVALID_OPERATOR_USAGE("运算符使用错误"),
    EXPECTED_CONSTANT_EXPRESSION("期望常量表达式"),
    REPEATED_CASE_LABEL("重复的case标签"),
    REPEATED_DEFAULT_LABEL("重复的default标签"),
    INVALID_ARRAY_DIMENSION("无效的数组维度"),
    
    // 扩展的新错误检测 (11种)
    FLOAT_USED_AS_ARRAY_INDEX("浮点数不能作为数组下标"),
    IMPLICIT_FLOAT_TO_INT_CONVERSION("隐式浮点转整数可能丧失精度"),
    UNREACHABLE_CODE("不可达代码"),
    FUNCTION_CALLED_BEFORE_DECLARATION("函数在声明前被调用"),
    SHADOWED_VARIABLE("局部变量遮蔽了外层变量"),
    UNUSED_VARIABLE("变量已声明但未使用"),
    STRUCT_SIZE_ZERO("结构体大小为零"),
    VOID_PARAMETER_TYPE("参数类型不能为void"),
    MULTIPLE_DEFAULTS_IN_SWITCH("switch中有多个default分支"),
    VOID_FUNCTION_RETURN_VALUE("void函数不应该返回值"),
    POTENTIAL_NULL_POINTER("可能的空指针访问");

    private final String description;
    
    SemanticErrorType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}