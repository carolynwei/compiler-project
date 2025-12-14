package com.wei.compiler.ast;

public class BitwiseExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final BitwiseOperator operator;
    private final ExpressionNode right;

    public BitwiseExpressionNode(ExpressionNode left, BitwiseOperator operator, 
                                 ExpressionNode right, int line, int col) {
        super(ASTNodeType.BITWISE_EXPRESSION, line, col);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public BitwiseOperator getOperator() {
        return operator;
    }

    public ExpressionNode getRight() {
        return right;
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
    
    // 假设您已在 ASTVisitor 中定义了 visitBitwiseExpressionNode 方法
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBitwiseExpressionNode(this);
    }
}