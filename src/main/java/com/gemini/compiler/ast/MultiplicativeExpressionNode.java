package com.gemini.compiler.ast;

/**
 * 乘法表达式节点
 */
public class MultiplicativeExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private MultiplicativeOperator operator;
    private ExpressionNode right;
    
    public MultiplicativeExpressionNode(ExpressionNode left, MultiplicativeOperator operator, 
                                       ExpressionNode right, int line, int column) {
        super(ASTNodeType.MULTIPLICATIVE_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public MultiplicativeOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMultiplicativeExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
