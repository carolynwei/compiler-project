package com.gemini.compiler.ast;

/**
 * 变量声明符节点
 */
public class VariableDeclaratorNode extends ASTNode {
    private String variableName;
    private int[] arrayDimensions;
    private ExpressionNode initializer;
    private ExpressionNode[] arrayInitializers;
    
    public VariableDeclaratorNode(String variableName, int[] arrayDimensions, 
                                 ExpressionNode initializer, int line, int column) {
        super(ASTNodeType.VARIABLE_DECLARATION, line, column);
        this.variableName = variableName;
        this.arrayDimensions = arrayDimensions;
        this.initializer = initializer;
    }
    
    public String getVariableName() { return variableName; }
    public int[] getArrayDimensions() { return arrayDimensions; }
    public ExpressionNode getInitializer() { return initializer; }
    public ExpressionNode[] getArrayInitializers() { return arrayInitializers; }
    public void setArrayInitializers(ExpressionNode[] arrayInitializers) { this.arrayInitializers = arrayInitializers; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableDeclarator(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return initializer != null ? new ASTNode[]{initializer} : new ASTNode[0];
    }
}
