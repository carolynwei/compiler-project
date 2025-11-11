package com.gemini.compiler.ast;

/**
 * 代码块节点
 */
public class BlockNode extends StatementNode {
    private StatementNode[] statements;
    
    public BlockNode(StatementNode[] statements, int line, int column) {
        super(ASTNodeType.BLOCK, line, column);
        this.statements = statements;
    }
    
    public StatementNode[] getStatements() {
        return statements;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBlock(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return statements;
    }
}
