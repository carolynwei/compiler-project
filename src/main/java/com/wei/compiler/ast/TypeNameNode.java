package com.wei.compiler.ast;

/**
 * 类型名称节点
 * 用于强制类型转换 (type) 和 sizeof(type)
 */
public class TypeNameNode extends ASTNode {
    private TypeNode baseType;
    private int pointerLevel;
    private int referenceCount;
    
    public TypeNameNode(TypeNode baseType, int pointerLevel, int referenceCount, int line, int column) {
        super(ASTNodeType.TYPE, line, column);
        this.baseType = baseType;
        this.pointerLevel = pointerLevel;
        this.referenceCount = referenceCount;
    }
    
    public TypeNode getBaseType() { return baseType; }
    public int getPointerLevel() { return pointerLevel; }
    public int getReferenceCount() { return referenceCount; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitTypeNameNode(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{baseType};
    }
}