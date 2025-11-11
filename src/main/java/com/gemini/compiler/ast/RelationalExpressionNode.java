package com.gemini.compiler.ast;

/**
 * 关系表达式节点
 */
public class RelationalExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private RelationalOperator operator;
    private ExpressionNode right;
    
    public RelationalExpressionNode(ExpressionNode left, RelationalOperator operator, 
                                   ExpressionNode right, int line, int column) {
        super(ASTNodeType.RELATIONAL_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public RelationalOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitRelationalExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
