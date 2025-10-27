package com.gemini.compiler.ast;

/**
 * 抽象语法树 (AST) 节点基类
 * 
 * 所有 AST 节点都继承自此类，提供统一的基础功能
 */
public abstract class ASTNode {
    
    // 节点位置信息
    protected int line;
    protected int column;
    
    // 节点类型
    protected ASTNodeType nodeType;
    
    public ASTNode(ASTNodeType nodeType, int line, int column) {
        this.nodeType = nodeType;
        this.line = line;
        this.column = column;
    }
    
    // Getters
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public ASTNodeType getNodeType() { return nodeType; }
    
    // 抽象方法：接受访问者
    public abstract <T> T accept(ASTVisitor<T> visitor);
    
    // 抽象方法：获取子节点
    public abstract ASTNode[] getChildren();
}

/**
 * AST 节点类型枚举
 */
enum ASTNodeType {
    // 程序结构
    PROGRAM,
    
    // 声明
    STRUCT_DECLARATION,
    FUNCTION_DECLARATION,
    VARIABLE_DECLARATION,
    PARAMETER,
    
    // 语句
    BLOCK,
    EXPRESSION_STATEMENT,
    IF_STATEMENT,
    WHILE_STATEMENT,
    FOR_STATEMENT,
    SWITCH_STATEMENT,
    CASE_STATEMENT,
    DEFAULT_STATEMENT,
    BREAK_STATEMENT,
    CONTINUE_STATEMENT,
    RETURN_STATEMENT,
    
    // 表达式
    ASSIGNMENT_EXPRESSION,
    CONDITIONAL_EXPRESSION,
    LOGICAL_OR_EXPRESSION,
    LOGICAL_AND_EXPRESSION,
    EQUALITY_EXPRESSION,
    RELATIONAL_EXPRESSION,
    ADDITIVE_EXPRESSION,
    MULTIPLICATIVE_EXPRESSION,
    UNARY_EXPRESSION,
    POSTFIX_EXPRESSION,
    PRIMARY_EXPRESSION,
    
    // 字面量和标识符
    IDENTIFIER,
    INT_LITERAL,
    FLOAT_LITERAL,
    CHAR_LITERAL,
    STRING_LITERAL,
    
    // 类型
    TYPE,
    
    // 函数调用
    FUNCTION_CALL,
    ARGUMENT_LIST,
    
    // 数组访问
    ARRAY_ACCESS,
    
    // 结构体成员访问
    MEMBER_ACCESS
}
