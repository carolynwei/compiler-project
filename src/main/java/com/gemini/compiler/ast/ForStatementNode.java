package com.gemini.compiler.ast;

/**
 * for语句节点
 */
public class ForStatementNode extends StatementNode {
    private StatementNode initialization;
    private ExpressionNode condition;
    private ExpressionNode update;  // 修改为 ExpressionNode
    private StatementNode body;
    
    public ForStatementNode(StatementNode initialization, ExpressionNode condition, 
                           ExpressionNode update, StatementNode body, int line, int column) {
        super(ASTNodeType.FOR_STATEMENT, line, column);
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }
    
    public StatementNode getInitialization() { return initialization; }
    public ExpressionNode getCondition() { return condition; }
    public ExpressionNode getUpdate() { return update; }  // 返回类型改为 ExpressionNode
    public StatementNode getBody() { return body; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitForStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{initialization, condition, update, body};
    }
}
