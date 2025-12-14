package com.wei.compiler.ast;

/**
 * 变量声明符节点
 */
public class VariableDeclaratorNode extends ASTNode {
    private DeclaratorNode declarator;
    private ExpressionNode initializer;
    private ExpressionNode[] arrayInitializers;
    
    public VariableDeclaratorNode(DeclaratorNode declarator, ExpressionNode initializer, 
                                 int line, int column) {
        super(ASTNodeType.TYPE, line, column);
        this.declarator = declarator;
        this.initializer = initializer;
    }
    
    public DeclaratorNode getDeclarator() { return declarator; }
    public ExpressionNode getInitializer() { return initializer; }
    public ExpressionNode[] getArrayInitializers() { return arrayInitializers; }
    public void setArrayInitializers(ExpressionNode[] arrayInitializers) { this.arrayInitializers = arrayInitializers; }
    
    // 便利方法：从 declarator 中提取变量名称
    public String getVariableName() {
        return declarator != null ? declarator.getName() : null;
    }
    
    // 便利方法：从 declarator 中提取数组维度
    public ExpressionNode[] getArrayDimensions() {
        return declarator != null ? declarator.getArrayDimensions() : null;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableDeclarator(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return initializer != null ? new ASTNode[]{declarator, initializer} : new ASTNode[]{declarator};
    }
}
