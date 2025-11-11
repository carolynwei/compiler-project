package com.gemini.compiler.ast;

/**
 * 赋值表达式节点
 */
public class AssignmentExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private AssignmentOperator operator;
    private ExpressionNode right;
    
    public AssignmentExpressionNode(ExpressionNode left, AssignmentOperator operator, 
                                  ExpressionNode right, int line, int column) {
        super(ASTNodeType.ASSIGNMENT_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public AssignmentOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAssignmentExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}
