package com.gemini.compiler.ast;

/**
 * 标识符节点
 */
public class IdentifierNode extends ExpressionNode {
    private String name;
    
    public IdentifierNode(String name, int line, int column) {
        super(ASTNodeType.IDENTIFIER, line, column);
        this.name = name;
    }
    
    public String getName() { return name; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
