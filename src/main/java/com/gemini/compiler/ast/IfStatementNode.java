package com.gemini.compiler.ast;

/**
 * if语句节点
 */
public class IfStatementNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode thenStatement;
    private StatementNode elseStatement;
    
    public IfStatementNode(ExpressionNode condition, StatementNode thenStatement, 
                          StatementNode elseStatement, int line, int column) {
        super(ASTNodeType.IF_STATEMENT, line, column);
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    
    public ExpressionNode getCondition() { return condition; }
    public StatementNode getThenStatement() { return thenStatement; }
    public StatementNode getElseStatement() { return elseStatement; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIfStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return elseStatement != null ? 
            new ASTNode[]{condition, thenStatement, elseStatement} : 
            new ASTNode[]{condition, thenStatement};
    }
}
