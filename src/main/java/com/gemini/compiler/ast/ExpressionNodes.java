package com.gemini.compiler.ast;

/**
 * 表达式节点基类
 */
abstract class ExpressionNode extends ASTNode {
    public ExpressionNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}

/**
 * 赋值表达式节点
 */
class AssignmentExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private AssignmentOperator operator;
    private ExpressionNode right;
    
    public AssignmentExpressionNode(ExpressionNode left, AssignmentOperator operator, 
                                  ExpressionNode right, int line, int column) {
        super(ASTNodeType.ASSIGNMENT_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public AssignmentOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAssignmentExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 赋值运算符枚举
 */
enum AssignmentOperator {
    ASSIGN("="),
    PLUS_ASSIGN("+="),
    MINUS_ASSIGN("-="),
    MULTIPLY_ASSIGN("*="),
    DIVIDE_ASSIGN("/="),
    MODULO_ASSIGN("%=");
    
    private final String symbol;
    
    AssignmentOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 条件表达式节点 (三元运算符)
 */
class ConditionalExpressionNode extends ExpressionNode {
    private ExpressionNode condition;
    private ExpressionNode trueExpression;
    private ExpressionNode falseExpression;
    
    public ConditionalExpressionNode(ExpressionNode condition, ExpressionNode trueExpression, 
                                   ExpressionNode falseExpression, int line, int column) {
        super(ASTNodeType.CONDITIONAL_EXPRESSION, line, column);
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }
    
    public ExpressionNode getCondition() { return condition; }
    public ExpressionNode getTrueExpression() { return trueExpression; }
    public ExpressionNode getFalseExpression() { return falseExpression; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitConditionalExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{condition, trueExpression, falseExpression};
    }
}

/**
 * 逻辑或表达式节点
 */
class LogicalOrExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;
    
    public LogicalOrExpressionNode(ExpressionNode left, ExpressionNode right, int line, int column) {
        super(ASTNodeType.LOGICAL_OR_EXPRESSION, line, column);
        this.left = left;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalOrExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 逻辑与表达式节点
 */
class LogicalAndExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;
    
    public LogicalAndExpressionNode(ExpressionNode left, ExpressionNode right, int line, int column) {
        super(ASTNodeType.LOGICAL_AND_EXPRESSION, line, column);
        this.left = left;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalAndExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 相等性表达式节点
 */
class EqualityExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private EqualityOperator operator;
    private ExpressionNode right;
    
    public EqualityExpressionNode(ExpressionNode left, EqualityOperator operator, 
                                ExpressionNode right, int line, int column) {
        super(ASTNodeType.EQUALITY_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public EqualityOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitEqualityExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 相等性运算符枚举
 */
enum EqualityOperator {
    EQUAL("=="),
    NOT_EQUAL("!=");
    
    private final String symbol;
    
    EqualityOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 关系表达式节点
 */
class RelationalExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private RelationalOperator operator;
    private ExpressionNode right;
    
    public RelationalExpressionNode(ExpressionNode left, RelationalOperator operator, 
                                   ExpressionNode right, int line, int column) {
        super(ASTNodeType.RELATIONAL_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public RelationalOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitRelationalExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 关系运算符枚举
 */
enum RelationalOperator {
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_EQUAL("<="),
    GREATER_EQUAL(">=");
    
    private final String symbol;
    
    RelationalOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 加法表达式节点
 */
class AdditiveExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private AdditiveOperator operator;
    private ExpressionNode right;
    
    public AdditiveExpressionNode(ExpressionNode left, AdditiveOperator operator, 
                                 ExpressionNode right, int line, int column) {
        super(ASTNodeType.ADDITIVE_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public AdditiveOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 加法运算符枚举
 */
enum AdditiveOperator {
    PLUS("+"),
    MINUS("-");
    
    private final String symbol;
    
    AdditiveOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}

/**
 * 乘法表达式节点
 */
class MultiplicativeExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private MultiplicativeOperator operator;
    private ExpressionNode right;
    
    public MultiplicativeExpressionNode(ExpressionNode left, MultiplicativeOperator operator, 
                                       ExpressionNode right, int line, int column) {
        super(ASTNodeType.MULTIPLICATIVE_EXPRESSION, line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ExpressionNode getLeft() { return left; }
    public MultiplicativeOperator getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMultiplicativeExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{left, right};
    }
}

/**
 * 乘法运算符枚举
 */
enum MultiplicativeOperator {
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%");
    
    private final String symbol;
    
    MultiplicativeOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() { return symbol; }
}
