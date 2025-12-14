package com.wei.compiler.semantic;

import java.util.*;

/**
 * StructInfo 类
 * 存储结构体特有的信息，例如成员变量列表。
 */
public class StructInfo {
    private final String name;
    // 使用 LinkedHashMap 保持成员定义的顺序
    private final Map<String, SymbolEntry> fields; 
    private int sizeInBytes; // 结构体总大小（用于代码生成阶段）

    public StructInfo(String name) {
        this.name = name;
        this.fields = new LinkedHashMap<>();
        this.sizeInBytes = 0;
    }

    public String getName() {
        return name;
    }

    public Map<String, SymbolEntry> getFields() {
        return fields;
    }

    public void addField(String fieldName, SymbolEntry fieldEntry) {
        this.fields.put(fieldName, fieldEntry);
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(int sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }
    
    public boolean hasField(String fieldName) {
        return this.fields.containsKey(fieldName);
    }
    
    public SymbolEntry getField(String fieldName) {
        return this.fields.get(fieldName);
    }
    
    public int getFieldCount() {
        return this.fields.size();
    }
}