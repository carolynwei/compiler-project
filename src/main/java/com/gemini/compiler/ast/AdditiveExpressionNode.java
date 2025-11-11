package com.gemini.compiler.ast;

/**
 * 加法表达式节点
 */
public class AdditiveExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private AdditiveOperator operator;
    private ExpressionNode right;
    
    public AdditiveExpressionNode(ExpressionNode left, AdditiveOperator operator, 
                                 ExpressionNode right, int line, int column) {
        super(ASTNodeType.ADDITIVE_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public AdditiveOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
