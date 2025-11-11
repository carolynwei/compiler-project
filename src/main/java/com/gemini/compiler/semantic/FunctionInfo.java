package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.DataType;
import java.util.*;

/**
 * 函数信息
 */
public class FunctionInfo {
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