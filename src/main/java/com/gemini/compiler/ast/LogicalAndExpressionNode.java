package com.gemini.compiler.ast;

/**
 * 逻辑与表达式节点
 */
public class LogicalAndExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;
    
    public LogicalAndExpressionNode(ExpressionNode left, ExpressionNode right, int line, int column) {
        super(ASTNodeType.LOGICAL_AND_EXPRESSION, line, column);
        this.left = left;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalAndExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
