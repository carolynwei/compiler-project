package com.gemini.compiler.ast;

/**
 * 程序节点 - AST 的根节点
 */
public class ProgramNode extends ASTNode {
    private ASTNode[] declarations;
    
    public ProgramNode(ASTNode[] declarations, int line, int column) {
        super(ASTNodeType.PROGRAM, line, column);
        // ✅ 确保声明数组永远非空
        this.declarations = (declarations != null) ? declarations : new ASTNode[0];
    }
    
    public ASTNode[] getDeclarations() {
        return declarations;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitProgram(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return declarations;
    }
}
