package com.wei.compiler.type;

import com.wei.compiler.ast.ExpressionNode;

/**
 * 数组类型 (ArrayType)
 * 存储元素类型和维度信息。
 */
public class ArrayType extends DataType {
    private final DataType elementType;
    private final ExpressionNode dimension; // 记录 AST 表达式

    public ArrayType(DataType elementType, ExpressionNode dimension) {
        super(TypeKind.ARRAY);
        this.elementType = elementType;
        this.dimension = dimension;
    }

    public DataType getElementType() {
        return elementType;
    }

    public ExpressionNode getDimension() {
        return dimension;
    }

    @Override
    public String getName() {
        // 简化处理，实际需要计算维度大小
        return elementType.getName() + "[]";
    }

    @Override
    public int getSize() {
        // 复杂计算，实际应在运行时或编译期计算常量
        return -1; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        
        ArrayType arrayType = (ArrayType) obj;
        return elementType.equals(arrayType.elementType);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + elementType.hashCode();
        return result;
    }
}