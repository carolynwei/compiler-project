package com.gemini.compiler.ast;

/**
 * 类型节点
 */
public class TypeNode extends ASTNode {
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
