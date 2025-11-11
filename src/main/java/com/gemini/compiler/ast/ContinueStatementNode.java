package com.gemini.compiler.ast;

/**
 * continue语句节点
 */
public class ContinueStatementNode extends StatementNode {
    public ContinueStatementNode(int line, int column) {
        super(ASTNodeType.CONTINUE_STATEMENT, line, column);
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitContinueStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
