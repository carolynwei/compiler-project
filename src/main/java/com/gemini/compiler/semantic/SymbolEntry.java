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

    public void setSymbolType(SymbolType symbolType) { this.symbolType = symbolType; }
    public void setDataType(DataType dataType) { this.dataType = dataType; }
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