package com.gemini.compiler.ast;

/**
 * 一元表达式节点
 */
class UnaryExpressionNode extends ExpressionNode {
    private UnaryOperator operator;
    private ExpressionNode operand;
    
    public UnaryExpressionNode(UnaryOperator operator, ExpressionNode operand, int line, int column) {
        super(ASTNodeType.UNARY_EXPRESSION, line, column);
        this.operator = operator;
        this.operand = operand;
    }
    
    public UnaryOperator getOperator() { return operator; }
    public ExpressionNode getOperand() { return operand; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{operand};
    }
}

/**
 * 一元运算符枚举
 */
enum UnaryOperator {
    PLUS("+"),
    MINUS("-"),
    NOT("!"),
    INCREMENT("++"),
    DECREMENT("--");
    
    private final String symbol;
    
    UnaryOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 后缀表达式节点
 */
class PostfixExpressionNode extends ExpressionNode {
    private ExpressionNode operand;
    private PostfixOperator operator;
    
    public PostfixExpressionNode(ExpressionNode operand, PostfixOperator operator, int line, int column) {
        super(ASTNodeType.POSTFIX_EXPRESSION, line, column);
        this.operand = operand;
        this.operator = operator;
    }
    
    public ExpressionNode getOperand() { return operand; }
    public PostfixOperator getOperator() { return operator; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPostfixExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{operand};
    }
}

/**
 * 后缀运算符枚举
 */
enum PostfixOperator {
    INCREMENT("++"),
    DECREMENT("--");
    
    private final String symbol;
    
    PostfixOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 主表达式节点
 */
class PrimaryExpressionNode extends ExpressionNode {
    private PrimaryType type;
    private Object value;
    
    public PrimaryExpressionNode(PrimaryType type, Object value, int line, int column) {
        super(ASTNodeType.PRIMARY_EXPRESSION, line, column);
        this.type = type;
        this.value = value;
    }
    
    public PrimaryType getType() { return type; }
    public Object getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPrimaryExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 主表达式类型枚举
 */
enum PrimaryType {
    IDENTIFIER,
    INT_LITERAL,
    FLOAT_LITERAL,
    CHAR_LITERAL,
    STRING_LITERAL,
    PARENTHESIZED_EXPRESSION
}

/**
 * 标识符节点
 */
class IdentifierNode extends ExpressionNode {
    private String name;
    
    public IdentifierNode(String name, int line, int column) {
        super(ASTNodeType.IDENTIFIER, line, column);
        this.name = name;
    }
    
    public String getName() { return name; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 字面量节点基类
 */
abstract class LiteralNode extends ExpressionNode {
    public LiteralNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}

/**
 * 整数字面量节点
 */
class IntLiteralNode extends LiteralNode {
    private int value;
    
    public IntLiteralNode(int value, int line, int column) {
        super(ASTNodeType.INT_LITERAL, line, column);
        this.value = value;
    }
    
    public int getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIntLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 浮点数字面量节点
 */
class FloatLiteralNode extends LiteralNode {
    private float value;
    
    public FloatLiteralNode(float value, int line, int column) {
        super(ASTNodeType.FLOAT_LITERAL, line, column);
        this.value = value;
    }
    
    public float getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFloatLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 字符字面量节点
 */
class CharLiteralNode extends LiteralNode {
    private char value;
    
    public CharLiteralNode(char value, int line, int column) {
        super(ASTNodeType.CHAR_LITERAL, line, column);
        this.value = value;
    }
    
    public char getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCharLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 字符串字面量节点
 */
class StringLiteralNode extends LiteralNode {
    private String value;
    
    public StringLiteralNode(String value, int line, int column) {
        super(ASTNodeType.STRING_LITERAL, line, column);
        this.value = value;
    }
    
    public String getValue() { return value; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitStringLiteral(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 函数调用节点
 */
class FunctionCallNode extends ExpressionNode {
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

/**
 * 数组访问节点
 */
class ArrayAccessNode extends ExpressionNode {
    private ExpressionNode array;
    private ExpressionNode index;
    
    public ArrayAccessNode(ExpressionNode array, ExpressionNode index, int line, int column) {
        super(ASTNodeType.ARRAY_ACCESS, line, column);
        this.array = array;
        this.index = index;
    }
    
    public ExpressionNode getArray() { return array; }
    public ExpressionNode getIndex() { return index; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitArrayAccess(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{array, index};
    }
}

/**
 * 成员访问节点
 */
class MemberAccessNode extends ExpressionNode {
    private ExpressionNode object;
    private String memberName;
    
    public MemberAccessNode(ExpressionNode object, String memberName, int line, int column) {
        super(ASTNodeType.MEMBER_ACCESS, line, column);
        this.object = object;
        this.memberName = memberName;
    }
    
    public ExpressionNode getObject() { return object; }
    public String getMemberName() { return memberName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMemberAccess(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{object};
    }
}

/**
 * 类型节点
 */
class TypeNode extends ASTNode {
    private DataType dataType;
    private String structName;
    private int[] arrayDimensions;
    
    public TypeNode(DataType dataType, String structName, int[] arrayDimensions, int line, int column) {
        super(ASTNodeType.TYPE, line, column);
        this.dataType = dataType;
        this.structName = structName;
        this.arrayDimensions = arrayDimensions;
    }
    
    public DataType getDataType() { return dataType; }
    public String getStructName() { return structName; }
    public int[] getArrayDimensions() { return arrayDimensions; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitType(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * 数据类型枚举
 */
enum DataType {
    INT,
    FLOAT,
    CHAR,
    STRING,
    VOID,
    STRUCT,
    ARRAY
}
