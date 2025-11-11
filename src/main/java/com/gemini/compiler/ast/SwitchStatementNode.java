package com.gemini.compiler.ast;

/**
 * switch语句节点
 */
public class SwitchStatementNode extends StatementNode {
    private ExpressionNode expression;
    private CaseStatementNode[] cases;
    private DefaultStatementNode defaultCase;
    
    public SwitchStatementNode(ExpressionNode expression, CaseStatementNode[] cases, 
                             DefaultStatementNode defaultCase, int line, int column) {
        super(ASTNodeType.SWITCH_STATEMENT, line, column);
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }
    
    public ExpressionNode getExpression() { return expression; }
    public CaseStatementNode[] getCases() { return cases; }
    public DefaultStatementNode getDefaultCase() { return defaultCase; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSwitchStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[cases.length + (defaultCase != null ? 2 : 1)];
        children[0] = expression;
        System.arraycopy(cases, 0, children, 1, cases.length);
        if (defaultCase != null) {
            children[children.length - 1] = defaultCase;
        }
        return children;
    }
}
