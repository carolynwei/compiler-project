package com.wei.compiler.type;

/**
 * 简单类型 (SimpleType)
 * 对应 int, char, float 等基础类型。
 */
public class SimpleType extends DataType {
    private final String name;
    private final int size;

    public SimpleType(String name, int size, TypeKind kind) {
        super(kind);
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        
        SimpleType that = (SimpleType) obj;
        return size == that.size && name.equals(that.name);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + size;
        return result;
    }
}