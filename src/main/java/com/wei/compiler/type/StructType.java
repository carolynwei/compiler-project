package com.wei.compiler.type;

import com.wei.compiler.semantic.StructInfo;

/**
 * 结构体类型 (StructType)
 * 存储结构体信息。
 */
public class StructType extends DataType {
    private final String name;
    private final StructInfo structInfo;

    public StructType(String name, StructInfo structInfo) {
        super(TypeKind.STRUCT);
        this.name = name;
        this.structInfo = structInfo;
    }

    public String getName() {
        return name;
    }

    public StructInfo getStructInfo() {
        return structInfo;
    }
    
    public boolean hasField(String fieldName) {
        return structInfo != null && structInfo.hasField(fieldName);
    }
    
    public com.wei.compiler.semantic.SymbolEntry getField(String fieldName) {
        return structInfo != null ? structInfo.getField(fieldName) : null;
    }

    @Override
    public int getSize() {
        return structInfo != null ? structInfo.getSizeInBytes() : 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        
        StructType that = (StructType) obj;
        return name.equals(that.name);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}