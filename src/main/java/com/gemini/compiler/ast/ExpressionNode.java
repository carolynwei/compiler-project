package com.gemini.compiler.ast;

/**
 * 表达式节点基类
 */
public abstract class ExpressionNode extends ASTNode {
    public ExpressionNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}
