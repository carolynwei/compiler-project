package com.wei.compiler.ast;

import com.wei.compiler.type.DataType;

/**
 * 表达式节点基类
 */
public abstract class ExpressionNode extends ASTNode {
    protected DataType dataType;  // 表达式的数据类型
    
    public ExpressionNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
        this.dataType = null;  // 默认未知
    }
    
    public ExpressionNode(ASTNodeType nodeType, int line, int column, DataType dataType) {
        super(nodeType, line, column);
        this.dataType = dataType;
    }
    
    // Getters and Setters
    public DataType getDataType() {
        return dataType;
    }
    
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
