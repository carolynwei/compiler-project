package com.gemini.compiler.ast;

/**
 * 浮点数字面量节点
 */
public class FloatLiteralNode extends LiteralNode {
    private float value;
    
    public FloatLiteralNode(float value, int line, int column) {
        super(ASTNodeType.FLOAT_LITERAL, line, column);
        this.value = value;
    }
    
    public float getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFloatLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
