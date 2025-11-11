package com.gemini.compiler.ast;

/**
 * 整数字面量节点
 */
public class IntLiteralNode extends LiteralNode {
    private int value;
    
    public IntLiteralNode(int value, int line, int column) {
        super(ASTNodeType.INT_LITERAL, line, column);
        this.value = value;
    }
    
    public int getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIntLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
