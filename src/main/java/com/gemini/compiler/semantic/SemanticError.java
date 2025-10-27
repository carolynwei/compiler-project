package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.*;
import java.util.*;

/**
 * 语义错误类型枚举
 */
enum SemanticErrorType {
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

/**
 * 语义错误类
 */
class SemanticError {
    private SemanticErrorType errorType;
    private String message;
    private String identifier;
    private int scopeLevel;
    private int line;
    private int column;
    
    public SemanticError(SemanticErrorType errorType, String message, String identifier, int scopeLevel) {
        this.errorType = errorType;
        this.message = message;
        this.identifier = identifier;
        this.scopeLevel = scopeLevel;
        this.line = -1;
        this.column = -1;
    }
    
    public SemanticError(SemanticErrorType errorType, String message, String identifier, 
                        int scopeLevel, int line, int column) {
        this.errorType = errorType;
        this.message = message;
        this.identifier = identifier;
        this.scopeLevel = scopeLevel;
        this.line = line;
        this.column = column;
    }
    
    // Getters
    public SemanticErrorType getErrorType() { return errorType; }
    public String getMessage() { return message; }
    public String getIdentifier() { return identifier; }
    public int getScopeLevel() { return scopeLevel; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(errorType.getDescription()).append("] ");
        sb.append(message);
        if (identifier != null) {
            sb.append(" (标识符: ").append(identifier).append(")");
        }
        if (line >= 0) {
            sb.append(" [行: ").append(line);
            if (column >= 0) {
                sb.append(", 列: ").append(column);
            }
            sb.append("]");
        }
        sb.append(" [作用域: ").append(scopeLevel).append("]");
        return sb.toString();
    }
}

/**
 * 类型检查器
 */
class TypeChecker {
    
    /**
     * 检查两个类型是否兼容
     */
    public static boolean isCompatible(DataType type1, DataType type2) {
        if (type1 == type2) {
            return true;
        }
        
        // 允许的隐式类型转换
        if (type1 == DataType.INT && type2 == DataType.FLOAT) {
            return true; // int 可以隐式转换为 float
        }
        
        return false;
    }
    
    /**
     * 检查赋值是否兼容
     */
    public static boolean isAssignmentCompatible(DataType leftType, DataType rightType) {
        return isCompatible(leftType, rightType);
    }
    
    /**
     * 检查函数参数是否匹配
     */
    public static boolean isParameterCompatible(List<DataType> paramTypes, List<DataType> argTypes) {
        if (paramTypes.size() != argTypes.size()) {
            return false;
        }
        
        for (int i = 0; i < paramTypes.size(); i++) {
            if (!isCompatible(paramTypes.get(i), argTypes.get(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 检查控制表达式类型（if, while, for 条件）
     */
    public static boolean isControlExpressionValid(DataType type) {
        return type == DataType.INT; // Gemini-C 中，控制表达式可以是 int 类型
    }
    
    /**
     * 检查数组下标类型
     */
    public static boolean isArrayIndexValid(DataType type) {
        return type == DataType.INT;
    }
    
    /**
     * 检查一元运算符是否适用于该类型
     */
    public static boolean isUnaryOperatorValid(UnaryOperator operator, DataType type) {
        switch (operator) {
            case PLUS:
            case MINUS:
                return type == DataType.INT || type == DataType.FLOAT;
            case NOT:
                return type == DataType.INT; // 逻辑非
            case INCREMENT:
            case DECREMENT:
                return type == DataType.INT || type == DataType.FLOAT;
            default:
                return false;
        }
    }
    
    /**
     * 检查二元运算符是否适用于该类型
     */
    public static boolean isBinaryOperatorValid(AdditiveOperator operator, DataType leftType, DataType rightType) {
        switch (operator) {
            case PLUS:
            case MINUS:
                return (leftType == DataType.INT || leftType == DataType.FLOAT) &&
                       (rightType == DataType.INT || rightType == DataType.FLOAT);
            default:
                return false;
        }
    }
    
    public static boolean isBinaryOperatorValid(MultiplicativeOperator operator, DataType leftType, DataType rightType) {
        switch (operator) {
            case MULTIPLY:
            case DIVIDE:
            case MODULO:
                return (leftType == DataType.INT || leftType == DataType.FLOAT) &&
                       (rightType == DataType.INT || rightType == DataType.FLOAT);
            default:
                return false;
        }
    }
    
    /**
     * 获取表达式的结果类型
     */
    public static DataType getExpressionResultType(DataType leftType, DataType rightType, 
                                                  AdditiveOperator operator) {
        if (leftType == DataType.FLOAT || rightType == DataType.FLOAT) {
            return DataType.FLOAT;
        }
        return DataType.INT;
    }
    
    public static DataType getExpressionResultType(DataType leftType, DataType rightType, 
                                                  MultiplicativeOperator operator) {
        if (leftType == DataType.FLOAT || rightType == DataType.FLOAT) {
            return DataType.FLOAT;
        }
        return DataType.INT;
    }
    
    /**
     * 检查结构体成员访问
     */
    public static boolean isStructMemberAccessValid(DataType structType, String memberName, 
                                                   SymbolEntry structDef) {
        if (structType != DataType.STRUCT) {
            return false;
        }
        
        if (structDef == null || structDef.getStructInfo() == null) {
            return false;
        }
        
        return structDef.getStructInfo().hasField(memberName);
    }
}
