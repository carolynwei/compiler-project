package com.gemini.compiler.ast;

/**
 * 参数节点
 * ✅ 支持数组参数（如 int arr[], int matrix[][]）
 */
public class ParameterNode extends ASTNode {
    private TypeNode type;
    private String parameterName;
    private int arrayDimensions; // 数组维度数量（0表示非数组）
    
    public ParameterNode(TypeNode type, String parameterName, int line, int column) {
        this(type, parameterName, 0, line, column);
    }
    
    public ParameterNode(TypeNode type, String parameterName, int arrayDimensions, int line, int column) {
        super(ASTNodeType.PARAMETER, line, column);
        this.type = type;
        this.parameterName = parameterName;
        this.arrayDimensions = arrayDimensions;
    }
    
    public TypeNode getType() { return type; }
    public String getParameterName() { return parameterName; }
    public int getArrayDimensions() { return arrayDimensions; }
    public boolean isArray() { return arrayDimensions > 0; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParameter(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type};
    }
}
