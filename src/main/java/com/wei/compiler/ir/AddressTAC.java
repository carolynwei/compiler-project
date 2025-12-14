package com.wei.compiler.ir;

import java.util.Arrays;

/**
 * 地址 TAC
 * 表示一个可寻址的实体（如数组元素、结构体字段等）
 */
public class AddressTAC {
    public enum AddressKind {
        VARIABLE,        // 简单变量地址
        ARRAY_ELEMENT,   // 数组元素地址
        STRUCT_FIELD,    // 结构体字段地址
        DEREFERENCE      // 指针解引用地址
    }
    
    private AddressKind kind;
    private String base;         // 基地址变量名
    private String[] indices;    // 索引（对于数组）
    private String fieldName;    // 字段名（对于结构体）
    private int fieldOffset;     // 字段偏移（对于结构体）
    private TACType addressType; // 地址指向的类型
    
    // 简单变量地址构造函数
    public AddressTAC(String variableName, TACType type) {
        this.kind = AddressKind.VARIABLE;
        this.base = variableName;
        this.addressType = type;
        this.indices = new String[0];
        this.fieldName = null;
        this.fieldOffset = 0;
    }
    
    // 数组元素地址构造函数
    public AddressTAC(String arrayName, String[] indices, TACType elementType) {
        this.kind = AddressKind.ARRAY_ELEMENT;
        this.base = arrayName;
        this.indices = indices != null ? indices : new String[0];
        this.addressType = elementType;
        this.fieldName = null;
        this.fieldOffset = 0;
    }
    
    // 结构体字段地址构造函数
    public AddressTAC(String structName, String fieldName, int fieldOffset, TACType fieldType) {
        this.kind = AddressKind.STRUCT_FIELD;
        this.base = structName;
        this.fieldName = fieldName;
        this.fieldOffset = fieldOffset;
        this.addressType = fieldType;
        this.indices = new String[0];
    }
    
    // 指针解引用地址构造函数
    public AddressTAC(String pointerName, TACType pointedType, boolean isDereference) {
        this.kind = AddressKind.DEREFERENCE;
        this.base = pointerName;
        this.addressType = pointedType;
        this.indices = new String[0];
        this.fieldName = null;
        this.fieldOffset = 0;
    }
    
    // Getters
    public AddressKind getKind() { return kind; }
    public String getBase() { return base; }
    public String[] getIndices() { return indices; }
    public String getFieldName() { return fieldName; }
    public int getFieldOffset() { return fieldOffset; }
    public TACType getAddressType() { return addressType; }
    
    @Override
    public String toString() {
        switch (kind) {
            case VARIABLE:
                return "&" + base;
            case ARRAY_ELEMENT:
                StringBuilder sb = new StringBuilder();
                sb.append("&").append(base);
                for (String index : indices) {
                    sb.append("[").append(index).append("]");
                }
                return sb.toString();
            case STRUCT_FIELD:
                return "&" + base + "." + fieldName;
            case DEREFERENCE:
                return "*" + base;
            default:
                return "unknown_address";
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        AddressTAC that = (AddressTAC) obj;
        return kind == that.kind &&
               base.equals(that.base) &&
               Arrays.equals(indices, that.indices) &&
               (fieldName == null ? that.fieldName == null : fieldName.equals(that.fieldName)) &&
               fieldOffset == that.fieldOffset &&
               (addressType == null ? that.addressType == null : addressType.equals(that.addressType));
    }
    
    @Override
    public int hashCode() {
        int result = kind.hashCode();
        result = 31 * result + base.hashCode();
        result = 31 * result + Arrays.hashCode(indices);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + fieldOffset;
        result = 31 * result + (addressType != null ? addressType.hashCode() : 0);
        return result;
    }
}