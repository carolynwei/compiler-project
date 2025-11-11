package com.gemini.compiler.ast;

/**
 * 语句节点基类
 */
public abstract class StatementNode extends ASTNode {
    public StatementNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}
