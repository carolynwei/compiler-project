package com.wei.compiler.type;

/**
 * 引用类型 (ReferenceType) - 假定支持 C++ 风格的引用
 */
public class ReferenceType extends DataType {
    private final DataType targetType;

    public ReferenceType(DataType targetType) {
        super(TypeKind.REFERENCE);
        this.targetType = targetType;
    }

    public DataType getTargetType() {
        return targetType;
    }

    @Override
    public String getName() {
        return targetType.getName() + "&";
    }

    @Override
    public int getSize() {
        // 引用在实现上通常与指针大小相同，但语义不同
        return 8; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        
        ReferenceType that = (ReferenceType) obj;
        return targetType.equals(that.targetType);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + targetType.hashCode();
        return result;
    }
}