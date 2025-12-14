package com.wei.compiler.type;

/**
 * 指针类型 (PointerType)
 * 存储指向的类型。
 */
public class PointerType extends DataType {
    private final DataType targetType;
    private static final int POINTER_SIZE = 8; // 假设 64 位系统指针大小为 8 字节

    public PointerType(DataType targetType) {
        super(TypeKind.POINTER);
        this.targetType = targetType;
    }

    public DataType getTargetType() {
        return targetType;
    }

    @Override
    public String getName() {
        return targetType.getName() + "*";
    }

    @Override
    public int getSize() {
        return POINTER_SIZE;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        
        PointerType that = (PointerType) obj;
        return targetType.equals(that.targetType);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + targetType.hashCode();
        return result;
    }
}