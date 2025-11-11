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
