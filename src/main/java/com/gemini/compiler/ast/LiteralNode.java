package com.gemini.compiler.ast;

/**
 * 字面量节点基类
 */
public abstract class LiteralNode extends ExpressionNode {
    public LiteralNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}
