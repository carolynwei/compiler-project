package com.wei.compiler.ast;

import java.util.Arrays;

/**
 * 参数节点
 * **修正：使用 DeclaratorNode 处理复杂声明（指针/数组）**
 */
public class ParameterNode extends ASTNode {
    private final TypeNode type;
    private final DeclaratorNode declarator; // <--- 替换 parameterName 和 arrayDimensions
    
    public ParameterNode(TypeNode type, DeclaratorNode declarator, int line, int column) {
        super(ASTNodeType.PARAMETER, line, column);
        this.type = type;
        this.declarator = declarator;
    }
    
    public TypeNode getType() { return type; }
    
    // 不再直接有 getParameterName() 和 getArrayDimensions()
    public DeclaratorNode getDeclarator() { return declarator; }
    
    // 辅助方法，用于旧代码兼容（如果需要）
    public String getParameterName() { return declarator.getName(); }
    
    // 辅助方法：检查是否有数组维度
    public boolean isArray() { return declarator.getArrayDimensions().length > 0; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParameter(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type, declarator}; // <--- 包含新的 DeclaratorNode
    }
}