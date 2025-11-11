package com.gemini.compiler.ast;

/**
 * 一元表达式节点
 */
public class UnaryExpressionNode extends ExpressionNode {
    private UnaryOperator operator;
    private ExpressionNode operand;
    
    public UnaryExpressionNode(UnaryOperator operator, ExpressionNode operand, int line, int column) {
        super(ASTNodeType.UNARY_EXPRESSION, line, column);
        this.operator = operator;
        this.operand = operand;
    }
    
    public UnaryOperator getOperator() { return operator; }
    public ExpressionNode getOperand() { return operand; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{operand};
    }
}
