package com.gemini.compiler.ast;

/**
 * 变量声明节点
 * 在代码块中，变量声明也被视为一种语句
 */
public class VariableDeclarationNode extends StatementNode {
    private TypeNode type;
    private VariableDeclaratorNode[] declarators;
    
    public VariableDeclarationNode(TypeNode type, VariableDeclaratorNode[] declarators, int line, int column) {
        super(ASTNodeType.VARIABLE_DECLARATION, line, column);
        this.type = type;
        // ✅ 确保声明符数组永远非空
        this.declarators = (declarators != null) ? declarators : new VariableDeclaratorNode[0];
    }
    
    public TypeNode getType() { return type; }
    public VariableDeclaratorNode[] getDeclarators() { return declarators; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        // ✅ 安全地处理可能为空的声明符
        int declaratorCount = (declarators != null) ? declarators.length : 0;
        ASTNode[] children = new ASTNode[declaratorCount + 1];
        children[0] = type;
        if (declarators != null && declarators.length > 0) {
            System.arraycopy(declarators, 0, children, 1, declarators.length);
        }
        return children;
    }
}
