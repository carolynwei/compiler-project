package com.gemini.compiler.ast;

/**
 * 主表达式节点
 */
public class PrimaryExpressionNode extends ExpressionNode {
    private PrimaryType type;
    private Object value;
    
    public PrimaryExpressionNode(PrimaryType type, Object value, int line, int column) {
        super(ASTNodeType.PRIMARY_EXPRESSION, line, column);
        this.type = type;
        this.value = value;
    }
    
    public PrimaryType getType() { return type; }
    public Object getValue() { return value; }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPrimaryExpression(this);
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}