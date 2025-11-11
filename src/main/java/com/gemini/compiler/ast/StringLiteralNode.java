package com.gemini.compiler.ast;

/**
 * 字符串字面量节点
 */
public class StringLiteralNode extends LiteralNode {
    private String value;
    
    public StringLiteralNode(String value, int line, int column) {
        super(ASTNodeType.STRING_LITERAL, line, column);
        this.value = value;
    }
    
    public String getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitStringLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
