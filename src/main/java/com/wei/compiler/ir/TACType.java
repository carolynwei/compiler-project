package com.wei.compiler.ir;

import java.util.List;
import java.util.ArrayList;

/**
 * TAC 类型系统
 * 用于在中间代码中保留类型信息
 */
public class TACType {
    public enum TypeKind {
        INT, FLOAT, CHAR, STRING, VOID, 
        ARRAY, STRUCT, POINTER,
        ADDRESS  // 地址类型（表示一个可寻址的实体）
    }
    
    private TypeKind kind;
    private String typeName;  // 对于 struct 和 array，保存具体类型名
    private TACType elementType;  // 对于数组和指针，保存元素类型
    private int arraySize;  // 对于数组，保存大小
    private List<StructField> fields; // 对于结构体，保存字段信息
    
    // 结构体字段信息
    public static class StructField {
        private String name;
        private TACType type;
        private int offset;
        
        public StructField(String name, TACType type, int offset) {
            this.name = name;
            this.type = type;
            this.offset = offset;
        }
        
        public String getName() { return name; }
        public TACType getType() { return type; }
        public int getOffset() { return offset; }
    }
    
    // 基本类型构造函数
    public TACType(TypeKind kind) {
        this.kind = kind;
        this.typeName = kind.toString().toLowerCase();
        this.fields = new ArrayList<>();
    }
    
    // 数组类型构造函数
    public TACType(TACType elementType, int arraySize) {
        this.kind = TypeKind.ARRAY;
        this.elementType = elementType;
        this.arraySize = arraySize;
        this.typeName = "array[" + arraySize + "]" + elementType.getTypeName();
        this.fields = new ArrayList<>();
    }
    
    // 结构体类型构造函数
    public TACType(String structName, List<StructField> fields) {
        this.kind = TypeKind.STRUCT;
        this.typeName = structName;
        this.fields = fields != null ? fields : new ArrayList<>();
        this.elementType = null;
        this.arraySize = 0;
    }
    
    // 指针类型构造函数
    public TACType(TypeKind kind, TACType pointedType) {
        this.kind = kind;
        this.elementType = pointedType;
        this.typeName = pointedType.getTypeName() + "*";
        this.fields = new ArrayList<>();
        this.arraySize = 0;
    }
    
    // 地址类型构造函数
    public TACType(TypeKind kind, TACType baseType, boolean isAddress) {
        this.kind = kind;
        this.elementType = baseType;
        this.typeName = baseType.getTypeName() + "&";
        this.fields = new ArrayList<>();
        this.arraySize = 0;
    }
    
    // Getters
    public TypeKind getKind() { return kind; }
    public String getTypeName() { return typeName; }
    public TACType getElementType() { return elementType; }
    public int getArraySize() { return arraySize; }
    public List<StructField> getFields() { return fields; }
    
    /**
     * 获取结构体字段的偏移量
     * @param fieldName 字段名称
     * @return 字段偏移量，如果字段不存在则返回-1
     */
    public int getFieldOffset(String fieldName) {
        if (fields != null) {
            for (StructField field : fields) {
                if (field.getName().equals(fieldName)) {
                    return field.getOffset();
                }
            }
        }
        return -1;
    }
    
    // 工具方法
    public boolean isPrimitive() {
        return kind == TypeKind.INT || kind == TypeKind.FLOAT || 
               kind == TypeKind.CHAR || kind == TypeKind.STRING || 
               kind == TypeKind.VOID;
    }
    
    public boolean isArray() {
        return kind == TypeKind.ARRAY;
    }
    
    public boolean isFloat() {
        return kind == TypeKind.FLOAT;
    }
    
    public boolean isInt() {
        return kind == TypeKind.INT;
    }
    
    public boolean isChar() {
        return kind == TypeKind.CHAR;
    }
    
    public boolean isStruct() {
        return kind == TypeKind.STRUCT;
    }
    
    public boolean isPointer() {
        return kind == TypeKind.POINTER;
    }
    
    public boolean isAddress() {
        return kind == TypeKind.ADDRESS;
    }
    
    @Override
    public String toString() {
        return typeName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        TACType tacType = (TACType) obj;
        return kind == tacType.kind && 
               typeName.equals(tacType.typeName) &&
               arraySize == tacType.arraySize &&
               (elementType == null ? tacType.elementType == null : elementType.equals(tacType.elementType));
    }
    
    @Override
    public int hashCode() {
        int result = kind.hashCode();
        result = 31 * result + typeName.hashCode();
        result = 31 * result + arraySize;
        result = 31 * result + (elementType != null ? elementType.hashCode() : 0);
        return result;
    }
}