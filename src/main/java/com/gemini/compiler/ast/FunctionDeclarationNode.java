package com.gemini.compiler.ast;

/**
 * 函数声明节点
 */
public class FunctionDeclarationNode extends ASTNode {
    private TypeNode returnType;
    private String functionName;
    private ParameterNode[] parameters;
    private BlockNode body;
    
    public FunctionDeclarationNode(TypeNode returnType, String functionName, 
                                  ParameterNode[] parameters, BlockNode body, int line, int column) {
        super(ASTNodeType.FUNCTION_DECLARATION, line, column);
        this.returnType = returnType;
        this.functionName = functionName;
        // ✅ 确保参数数组永远非空
        this.parameters = (parameters != null) ? parameters : new ParameterNode[0];
        this.body = body;
    }
    
    public TypeNode getReturnType() { return returnType; }
    public String getFunctionName() { return functionName; }
    public ParameterNode[] getParameters() { return parameters; }
    public BlockNode getBody() { return body; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFunctionDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        // ✅ 安全地处理可能为空的子节点
        int paramCount = (parameters != null) ? parameters.length : 0;
        ASTNode[] children = new ASTNode[paramCount + 2];
        children[0] = returnType;
        if (parameters != null && parameters.length > 0) {
            System.arraycopy(parameters, 0, children, 1, parameters.length);
        }
        children[children.length - 1] = body;
        return children;
    }
}
