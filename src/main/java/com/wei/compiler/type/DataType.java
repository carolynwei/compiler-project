package com.wei.compiler.type;

/**
 * DataType 抽象基类
 * 表示所有数据类型，包括基本类型和复杂类型（指针、数组、结构体等）。
 */
public abstract class DataType {

    // 假设的枚举常量，代表基础类型（您可能需要在其他地方定义，这里简化为静态常量）
    public static final SimpleType VOID = new SimpleType("void", 0, TypeKind.VOID);
    public static final SimpleType INT = new SimpleType("int", 4, TypeKind.INT);
    public static final SimpleType CHAR = new SimpleType("char", 1, TypeKind.CHAR);
    public static final SimpleType FLOAT = new SimpleType("float", 4, TypeKind.FLOAT);
    public static final SimpleType DOUBLE = new SimpleType("double", 8, TypeKind.DOUBLE);
    public static final SimpleType STRING = new SimpleType("string", 8, TypeKind.STRING);

    /**
     * 枚举所有类型的种类，用于简化类型判断。
     */
    public enum TypeKind {
        VOID, INT, CHAR, FLOAT, DOUBLE, STRING, STRUCT, POINTER, ARRAY, REFERENCE, FUNCTION
    }

    private final TypeKind kind;

    public DataType(TypeKind kind) {
        this.kind = kind;
    }

    public TypeKind getKind() {
        return kind;
    }

    // 抽象方法：获取类型名称的字符串表示
    public abstract String getName();

    // 抽象方法：获取类型占用的字节数（简化处理，复杂类型可能需要更多逻辑）
    public abstract int getSize();

    @Override
    public String toString() {
        return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        DataType dataType = (DataType) obj;
        return kind == dataType.kind;
    }
    
    @Override
    public int hashCode() {
        return kind.hashCode();
    }
}