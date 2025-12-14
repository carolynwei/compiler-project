package com.wei.compiler.semantic;

// 引入所有必要的依赖
import com.wei.compiler.type.DataType; 
import com.wei.compiler.semantic.SymbolType;
import com.wei.compiler.semantic.SymbolKind;
import com.wei.compiler.semantic.FunctionInfo;
import com.wei.compiler.semantic.StructInfo;
import com.wei.compiler.semantic.RuntimeInfo;
import com.wei.compiler.semantic.ArrayInfo;
import java.util.*;

/**
 * 符号表条目 (SymbolEntry)
 * * 表示符号表中的一个条目，包含标识符的所有相关信息，是语义分析的核心数据结构。
 */
public class SymbolEntry {
    
    // 符号名称
    private final String name;
    
    // 符号类型：VARIABLE, FUNCTION, STRUCT_DEFINITION 等
    private SymbolType symbolType;
    
    // 数据类型：INT, POINTER<INT>, STRUCT<Person> 等
    private DataType dataType;
    
    // 作用域级别：0 (全局), 1+ (局部)
    private final int scopeLevel;
    
    // 符号种类：GLOBAL, LOCAL, PARAMETER, STRUCT_MEMBER
    private final SymbolKind kind;
    
    // 数组信息 (如果 dataType 是 ArrayType，此信息可用于优化或记录运行时大小)
    private ArrayInfo arrayInfo;
    
    // 结构体信息 (如果 symbolType 是 STRUCT_DEFINITION)
    private StructInfo structInfo;
    
    // 函数信息 (如果 symbolType 是 FUNCTION)
    private FunctionInfo functionInfo;
    
    // 运行时信息 (初始化状态、地址等)
    private final RuntimeInfo runtimeInfo;

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
    
    // --- Getters and Setters ---
    public String getName() { return name; }
    public SymbolType getSymbolType() { return symbolType; }
    public DataType getDataType() { return dataType; }
    public int getScopeLevel() { return scopeLevel; }
    public SymbolKind getKind() { return kind; }
    
    // 专用信息 Getters
    public ArrayInfo getArrayInfo() { return arrayInfo; }
    public StructInfo getStructInfo() { return structInfo; }
    public FunctionInfo getFunctionInfo() { return functionInfo; }
    public RuntimeInfo getRuntimeInfo() { return runtimeInfo; }

    public void setSymbolType(SymbolType symbolType) { this.symbolType = symbolType; }
    public void setDataType(DataType dataType) { this.dataType = dataType; }
    public void setArrayInfo(ArrayInfo arrayInfo) { this.arrayInfo = arrayInfo; }
    public void setStructInfo(StructInfo structInfo) { this.structInfo = structInfo; }
    public void setFunctionInfo(FunctionInfo functionInfo) { this.functionInfo = functionInfo; }
    
    /**
     * 判断此符号是否是变量或参数
     */
    public boolean isVariable() {
        return symbolType == SymbolType.VARIABLE || symbolType == SymbolType.PARAMETER;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SymbolEntry[");
        sb.append("name='").append(name).append("'");
        sb.append(", type=").append(symbolType);
        sb.append(", dataType=").append(dataType);
        sb.append(", kind=").append(kind);
        sb.append(", scope=").append(scopeLevel);
        
        // 只有在存在特定信息时才打印
        if (structInfo != null) sb.append(", struct=").append(structInfo.getName());
        if (functionInfo != null) sb.append(", funcInfo=[defined:").append(functionInfo.isDefined()).append("]");
        if (runtimeInfo.isInitialized()) sb.append(", init=true");
        
        sb.append("]");
        return sb.toString();
    }
}