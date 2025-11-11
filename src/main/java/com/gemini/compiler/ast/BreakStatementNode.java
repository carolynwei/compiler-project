package com.gemini.compiler.ast;

/**
 * break语句节点
 */
public class BreakStatementNode extends StatementNode {
    public BreakStatementNode(int line, int column) {
        super(ASTNodeType.BREAK_STATEMENT, line, column);
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBreakStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
