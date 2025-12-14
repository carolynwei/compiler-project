package com.wei.compiler.ast;

public class DoWhileStatementNode extends StatementNode {
    private final StatementNode body;
    private final ExpressionNode condition;

    public DoWhileStatementNode(StatementNode body, ExpressionNode condition, 
                                int line, int col) {
        super(ASTNodeType.DO_WHILE_STATEMENT, line, col);
        this.body = body;
        this.condition = condition;
    }

    public StatementNode getBody() {
        return body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{body, condition};
    }
    
    // 假设您已在 ASTVisitor 中定义了 visitDoWhileStatementNode 方法
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDoWhileStatementNode(this);
    }
}