package com.wei.compiler.ast;

public class ShiftExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final ShiftOperator operator;
    private final ExpressionNode right;

    public ShiftExpressionNode(ExpressionNode left, ShiftOperator operator, 
                               ExpressionNode right, int line, int col) {
        super(ASTNodeType.SHIFT_EXPRESSION, line, col);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ShiftOperator getOperator() {
        return operator;
    }

    public ExpressionNode getRight() {
        return right;
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
    
    // 假设您已在 ASTVisitor 中定义了 visitShiftExpressionNode 方法
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitShiftExpressionNode(this);
    }
}