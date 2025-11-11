package com.gemini.compiler.ast;

/**
 * 类型转换表达式节点
 * 例如：(float)i, (int)x
 */
public class CastExpressionNode extends ExpressionNode {
    private TypeNode targetType;      // 目标类型
    private ExpressionNode expression; // 被转换的表达式
    
    public CastExpressionNode(TypeNode targetType, ExpressionNode expression, int line, int column) {
        super(ASTNodeType.CAST_EXPRESSION, line, column);
        this.targetType = targetType;
        this.expression = expression;
    }
    
    public TypeNode getTargetType() { return targetType; }
    public ExpressionNode getExpression() { return expression; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCastExpression(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{targetType, expression};
    }
}

