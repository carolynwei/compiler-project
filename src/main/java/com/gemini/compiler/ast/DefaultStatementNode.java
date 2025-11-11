package com.gemini.compiler.ast;

/**
 * default语句节点
 */
public class DefaultStatementNode extends StatementNode {
    private StatementNode[] statements;
    
    public DefaultStatementNode(StatementNode[] statements, int line, int column) {
        super(ASTNodeType.DEFAULT_STATEMENT, line, column);
        this.statements = statements;
    }
    
    public StatementNode[] getStatements() { return statements; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDefaultStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return statements;
    }
}
