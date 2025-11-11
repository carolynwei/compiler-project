package com.gemini.compiler.ast;

/**
 * 后缀表达式节点
 */
public class PostfixExpressionNode extends ExpressionNode {
    private ExpressionNode operand;
    private PostfixOperator operator;
    
    public PostfixExpressionNode(ExpressionNode operand, PostfixOperator operator, int line, int column) {
        super(ASTNodeType.POSTFIX_EXPRESSION, line, column);
        this.operand = operand;
        this.operator = operator;
    }
    
    public ExpressionNode getOperand() { return operand; }
    public PostfixOperator getOperator() { return operator; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPostfixExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{operand};
    }
}
