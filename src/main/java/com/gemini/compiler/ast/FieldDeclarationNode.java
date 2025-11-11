package com.gemini.compiler.ast;

/**
 * 字段声明节点
 */
public class FieldDeclarationNode extends ASTNode {
    private TypeNode type;
    private String fieldName;
    
    public FieldDeclarationNode(TypeNode type, String fieldName, int line, int column) {
        super(ASTNodeType.VARIABLE_DECLARATION, line, column);
        this.type = type;
        this.fieldName = fieldName;
    }
    
    public TypeNode getType() { return type; }
    public String getFieldName() { return fieldName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFieldDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type};
    }
}
