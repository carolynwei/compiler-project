package com.gemini.compiler.ast;

/**
 * case语句节点
 */
public class CaseStatementNode extends StatementNode {
    private ExpressionNode value;
    private StatementNode[] statements;
    
    public CaseStatementNode(ExpressionNode value, StatementNode[] statements, int line, int column) {
        super(ASTNodeType.CASE_STATEMENT, line, column);
        this.value = value;
        this.statements = statements;
    }
    
    public ExpressionNode getValue() { return value; }
    public StatementNode[] getStatements() { return statements; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCaseStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[statements.length + 1];
        children[0] = value;
        System.arraycopy(statements, 0, children, 1, statements.length);
        return children;
    }
}
