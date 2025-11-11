package com.gemini.compiler.semantic;

import java.util.*;

/**
 * 结构体信息
 */
public class StructInfo {
    private String structName;
    private Map<String, SymbolEntry> fields;
    private int fieldCount;
    
    public StructInfo(String structName) {
        this.structName = structName;
        this.fields = new HashMap<>();
        this.fieldCount = 0;
    }
    
    public void addField(String fieldName, SymbolEntry fieldEntry) {
        fields.put(fieldName, fieldEntry);
        fieldCount++;
    }
    
    public SymbolEntry getField(String fieldName) {
        return fields.get(fieldName);
    }
    
    public boolean hasField(String fieldName) {
        return fields.containsKey(fieldName);
    }
    
    public String getStructName() { return structName; }
    public Map<String, SymbolEntry> getFields() { return fields; }
    public int getFieldCount() { return fieldCount; }

    @Override
    public String toString() {
        return "StructInfo{structName='" + structName + "', fieldCount=" + fieldCount + 
               ", fields=" + fields.keySet() + "}";
    }
}