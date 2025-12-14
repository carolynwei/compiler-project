package com.wei.compiler.ast;

/**
 * 字段声明节点
 * **修正：使用 DeclaratorNode 处理复杂声明（指针/数组）**
 */
public class FieldDeclarationNode extends ASTNode {
    private final TypeNode type;
    private final DeclaratorNode declarator; // <--- 替换 fieldName

    public FieldDeclarationNode(TypeNode type, DeclaratorNode declarator, int line, int column) {
        // 节点的类型可能需要更通用，如果 VariableDeclaration 是用于局部变量，这里可能需要一个 FIELD_DECLARATION 类型
        super(ASTNodeType.FIELD_DECLARATION, line, column); 
        this.type = type;
        this.declarator = declarator;
    }
    
    public TypeNode getType() { return type; }
    
    // 不再直接有 getFieldName()，需要通过 DeclaratorNode 获取
    public DeclaratorNode getDeclarator() { return declarator; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFieldDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type, declarator}; // <--- 包含新的 DeclaratorNode
    }
}