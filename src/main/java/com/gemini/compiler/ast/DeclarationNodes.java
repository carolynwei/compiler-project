package com.gemini.compiler.ast;

/**
 * 程序节点 - AST 的根节点
 */
public class ProgramNode extends ASTNode {
    private ASTNode[] declarations;
    
    public ProgramNode(ASTNode[] declarations, int line, int column) {
        super(ASTNodeType.PROGRAM, line, column);
        this.declarations = declarations;
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

/**
 * 结构体声明节点
 */
class StructDeclarationNode extends ASTNode {
    private String structName;
    private FieldDeclarationNode[] fields;
    
    public StructDeclarationNode(String structName, FieldDeclarationNode[] fields, int line, int column) {
        super(ASTNodeType.STRUCT_DECLARATION, line, column);
        this.structName = structName;
        this.fields = fields;
    }
    
    public String getStructName() { return structName; }
    public FieldDeclarationNode[] getFields() { return fields; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitStructDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return fields;
    }
}

/**
 * 字段声明节点
 */
class FieldDeclarationNode extends ASTNode {
    private TypeNode type;
    private String fieldName;
    
    public FieldDeclarationNode(TypeNode type, String fieldName, int line, int column) {
        super(ASTNodeType.VARIABLE_DECLARATION, line, column);
        this.type = type;
        this.fieldName = fieldName;
    }
    
    public TypeNode getType() { return type; }
    public String getFieldName() { return fieldName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFieldDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type};
    }
}

/**
 * 函数声明节点
 */
class FunctionDeclarationNode extends ASTNode {
    private TypeNode returnType;
    private String functionName;
    private ParameterNode[] parameters;
    private BlockNode body;
    
    public FunctionDeclarationNode(TypeNode returnType, String functionName, 
                                  ParameterNode[] parameters, BlockNode body, int line, int column) {
        super(ASTNodeType.FUNCTION_DECLARATION, line, column);
        this.returnType = returnType;
        this.functionName = functionName;
        this.parameters = parameters;
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
        ASTNode[] children = new ASTNode[parameters.length + 2];
        children[0] = returnType;
        System.arraycopy(parameters, 0, children, 1, parameters.length);
        children[children.length - 1] = body;
        return children;
    }
}

/**
 * 参数节点
 */
class ParameterNode extends ASTNode {
    private TypeNode type;
    private String parameterName;
    
    public ParameterNode(TypeNode type, String parameterName, int line, int column) {
        super(ASTNodeType.PARAMETER, line, column);
        this.type = type;
        this.parameterName = parameterName;
    }
    
    public TypeNode getType() { return type; }
    public String getParameterName() { return parameterName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParameter(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{type};
    }
}

/**
 * 变量声明节点
 */
class VariableDeclarationNode extends ASTNode {
    private TypeNode type;
    private VariableDeclaratorNode[] declarators;
    
    public VariableDeclarationNode(TypeNode type, VariableDeclaratorNode[] declarators, int line, int column) {
        super(ASTNodeType.VARIABLE_DECLARATION, line, column);
        this.type = type;
        this.declarators = declarators;
    }
    
    public TypeNode getType() { return type; }
    public VariableDeclaratorNode[] getDeclarators() { return declarators; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableDeclaration(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[declarators.length + 1];
        children[0] = type;
        System.arraycopy(declarators, 0, children, 1, declarators.length);
        return children;
    }
}

/**
 * 变量声明符节点
 */
class VariableDeclaratorNode extends ASTNode {
    private String variableName;
    private int[] arrayDimensions;
    private ExpressionNode initializer;
    
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
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableDeclarator(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return initializer != null ? new ASTNode[]{initializer} : new ASTNode[0];
    }
}
