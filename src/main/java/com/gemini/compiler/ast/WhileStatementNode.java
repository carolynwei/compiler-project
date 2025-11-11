package com.gemini.compiler.ast;

/**
 * while语句节点
 */
public class WhileStatementNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode body;
    
    public WhileStatementNode(ExpressionNode condition, StatementNode body, int line, int column) {
        super(ASTNodeType.WHILE_STATEMENT, line, column);
        this.condition = condition;
        this.body = body;
    }
    
    public ExpressionNode getCondition() { return condition; }
    public StatementNode getBody() { return body; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitWhileStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{condition, body};
    }
}
