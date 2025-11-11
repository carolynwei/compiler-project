package com.gemini.compiler.ast;

/**
 * 条件表达式节点 (三元运算符)
 */
public class ConditionalExpressionNode extends ExpressionNode {
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
