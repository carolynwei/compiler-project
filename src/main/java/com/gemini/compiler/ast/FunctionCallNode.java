package com.gemini.compiler.ast;

/**
 * 函数调用节点
 */
public class FunctionCallNode extends ExpressionNode {
    private String functionName;
    private ExpressionNode[] arguments;
    
    public FunctionCallNode(String functionName, ExpressionNode[] arguments, int line, int column) {
        super(ASTNodeType.FUNCTION_CALL, line, column);
        this.functionName = functionName;
        this.arguments = arguments;
    }
    
    public String getFunctionName() { return functionName; }
    public ExpressionNode[] getArguments() { return arguments; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return arguments;
    }
}
