package com.gemini.compiler.ast;

/**
 * 数组访问节点
 */
public class ArrayAccessNode extends ExpressionNode {
    private ExpressionNode array;
    private ExpressionNode[] indices;
    
    public ArrayAccessNode(ExpressionNode array, ExpressionNode[] indices, int line, int column) {
        super(ASTNodeType.ARRAY_ACCESS, line, column);
        this.array = array;
        this.indices = indices;
    }
    
    public ExpressionNode getArray() { return array; }
    public ExpressionNode[] getIndices() { return indices; }
    
    // 获取单个索引（用于简单数组访问）
    public ExpressionNode getIndex() { 
        return indices.length > 0 ? indices[0] : null; 
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitArrayAccess(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[indices.length + 1];
        children[0] = array;
        System.arraycopy(indices, 0, children, 1, indices.length);
        return children;
    }
}
