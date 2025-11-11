package com.gemini.compiler.ast;

/**
 * 相等性表达式节点
 */
public class EqualityExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private EqualityOperator operator;
    private ExpressionNode right;
    
    public EqualityExpressionNode(ExpressionNode left, EqualityOperator operator, 
                                ExpressionNode right, int line, int column) {
        super(ASTNodeType.EQUALITY_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public EqualityOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitEqualityExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
