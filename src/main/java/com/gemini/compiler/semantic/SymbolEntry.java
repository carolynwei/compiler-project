package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.*;
import java.util.*;

/**
 * 符号表条目
 * 
 * 表示符号表中的一个条目，包含标识符的所有相关信息
 */
public class SymbolEntry {
    
    // 符号名称
    private String name;
    
    // 符号类型
    private SymbolType symbolType;
    
    // 数据类型
    private DataType dataType;
    
    // 作用域级别
    private int scopeLevel;
    
    // 符号种类
    private SymbolKind kind;
    
    // 数组信息
    private ArrayInfo arrayInfo;
    
    // 结构体信息
    private StructInfo structInfo;
    
    // 函数信息
    private FunctionInfo functionInfo;
    
    // 运行时信息
    private RuntimeInfo runtimeInfo;
    
    // 构造函数
    public SymbolEntry(String name, SymbolType symbolType, DataType dataType, 
                      int scopeLevel, SymbolKind kind) {
        this.name = name;
        this.symbolType = symbolType;
        this.dataType = dataType;
        this.scopeLevel = scopeLevel;
        this.kind = kind;
        this.runtimeInfo = new RuntimeInfo();
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public SymbolType getSymbolType() { return symbolType; }
    public DataType getDataType() { return dataType; }
    public int getScopeLevel() { return scopeLevel; }
    public SymbolKind getKind() { return kind; }
    public ArrayInfo getArrayInfo() { return arrayInfo; }
    public StructInfo getStructInfo() { return structInfo; }
    public FunctionInfo getFunctionInfo() { return functionInfo; }
    public RuntimeInfo getRuntimeInfo() { return runtimeInfo; }
    
    public void setArrayInfo(ArrayInfo arrayInfo) { this.arrayInfo = arrayInfo; }
    public void setStructInfo(StructInfo structInfo) { this.structInfo = structInfo; }
    public void setFunctionInfo(FunctionInfo functionInfo) { this.functionInfo = functionInfo; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SymbolEntry{");
        sb.append("name='").append(name).append("', ");
        sb.append("symbolType=").append(symbolType).append(", ");
        sb.append("dataType=").append(dataType).append(", ");
        sb.append("scopeLevel=").append(scopeLevel).append(", ");
        sb.append("kind=").append(kind);
        if (arrayInfo != null) sb.append(", arrayInfo=").append(arrayInfo);
        if (structInfo != null) sb.append(", structInfo=").append(structInfo);
        if (functionInfo != null) sb.append(", functionInfo=").append(functionInfo);
        sb.append("}");
        return sb.toString();
    }
}

/**
 * 符号类型枚举
 */
enum SymbolType {
    VARIABLE,
    FUNCTION,
    STRUCT_DEFINITION,
    PARAMETER,
    TEMPORARY
}

/**
 * 符号种类枚举
 */
enum SymbolKind {
    LOCAL,
    GLOBAL,
    PARAMETER,
    STRUCT_MEMBER
}

/**
 * 数组信息
 */
class ArrayInfo {
    private DataType elementType;
    private int[] dimensions;
    private int totalSize;
    
    public ArrayInfo(DataType elementType, int[] dimensions) {
        this.elementType = elementType;
        this.dimensions = dimensions;
        this.totalSize = 1;
        for (int dim : dimensions) {
            this.totalSize *= dim;
        }
    }
    
    public DataType getElementType() { return elementType; }
    public int[] getDimensions() { return dimensions; }
    public int getTotalSize() { return totalSize; }
    public int getDimensionCount() { return dimensions.length; }
    
    @Override
    public String toString() {
        return "ArrayInfo{elementType=" + elementType + ", dimensions=" + Arrays.toString(dimensions) + 
               ", totalSize=" + totalSize + "}";
    }
}

/**
 * 结构体信息
 */
class StructInfo {
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

/**
 * 函数信息
 */
class FunctionInfo {
    private DataType returnType;
    private List<SymbolEntry> parameters;
    private boolean isDefined;
    
    public FunctionInfo(DataType returnType) {
        this.returnType = returnType;
        this.parameters = new ArrayList<>();
        this.isDefined = false;
    }
    
    public void addParameter(SymbolEntry parameter) {
        parameters.add(parameter);
    }
    
    public DataType getReturnType() { return returnType; }
    public List<SymbolEntry> getParameters() { return parameters; }
    public boolean isDefined() { return isDefined; }
    public void setDefined(boolean defined) { this.isDefined = defined; }
    public int getParameterCount() { return parameters.size(); }
    
    @Override
    public String toString() {
        return "FunctionInfo{returnType=" + returnType + ", parameterCount=" + parameters.size() + 
               ", isDefined=" + isDefined + "}";
    }
}

/**
 * 运行时信息
 */
class RuntimeInfo {
    private int offset; // 在栈帧或数据区的偏移量
    private Object initialValue; // 初始值
    private boolean isInitialized;
    
    public RuntimeInfo() {
        this.offset = -1;
        this.initialValue = null;
        this.isInitialized = false;
    }
    
    public int getOffset() { return offset; }
    public void setOffset(int offset) { this.offset = offset; }
    public Object getInitialValue() { return initialValue; }
    public void setInitialValue(Object initialValue) { this.initialValue = initialValue; }
    public boolean isInitialized() { return isInitialized; }
    public void setInitialized(boolean initialized) { this.isInitialized = initialized; }
    
    @Override
    public String toString() {
        return "RuntimeInfo{offset=" + offset + ", initialValue=" + initialValue + 
               ", isInitialized=" + isInitialized + "}";
    }
}
