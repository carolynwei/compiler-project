package com.gemini.compiler.ast;

/**
 * 逻辑或表达式节点
 */
public class LogicalOrExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;
    
    public LogicalOrExpressionNode(ExpressionNode left, ExpressionNode right, int line, int column) {
        super(ASTNodeType.LOGICAL_OR_EXPRESSION, line, column);
        this.left = left;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalOrExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
