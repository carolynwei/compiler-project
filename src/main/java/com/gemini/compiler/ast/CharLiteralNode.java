package com.gemini.compiler.ast;

/**
 * 字符字面量节点
 */
public class CharLiteralNode extends LiteralNode {
    private char value;
    
    public CharLiteralNode(char value, int line, int column) {
        super(ASTNodeType.CHAR_LITERAL, line, column);
        this.value = value;
    }
    
    public char getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCharLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
