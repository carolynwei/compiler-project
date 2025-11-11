package com.gemini.compiler.ast;

/**
 * 结构体声明节点
 */
public class StructDeclarationNode extends ASTNode {
    private String structName;
    private FieldDeclarationNode[] fields;
    
    public StructDeclarationNode(String structName, FieldDeclarationNode[] fields, int line, int column) {
        super(ASTNodeType.STRUCT_DECLARATION, line, column);
        this.structName = structName;
        this.fields = fields;
    }
    
    public String getStructName() { return structName; }
    public FieldDeclarationNode[] getFields() { return fields; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitStructDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return fields;
    }
}
