package com.gemini.compiler.ast;

/**
 * 表达式语句节点
 */
public class ExpressionStatementNode extends StatementNode {
    private ExpressionNode expression;
    
    public ExpressionStatementNode(ExpressionNode expression, int line, int column) {
        super(ASTNodeType.EXPRESSION_STATEMENT, line, column);
        this.expression = expression;
    }
    
    public ExpressionNode getExpression() {
        return expression;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitExpressionStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{expression};
    }
}
