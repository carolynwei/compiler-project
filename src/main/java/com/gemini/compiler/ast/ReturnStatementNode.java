package com.gemini.compiler.ast;

/**
 * return语句节点
 */
public class ReturnStatementNode extends StatementNode {
    private ExpressionNode expression;
    
    public ReturnStatementNode(ExpressionNode expression, int line, int column) {
        super(ASTNodeType.RETURN_STATEMENT, line, column);
        this.expression = expression;
    }
    
    public ExpressionNode getExpression() { return expression; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitReturnStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return expression != null ? new ASTNode[]{expression} : new ASTNode[0];
    }
}
