package com.wei.compiler.semantic;

import com.wei.compiler.type.DataType;
import java.util.*;

/**
 * FunctionInfo 类
 * 存储函数特有的信息，例如参数列表和定义状态。
 */
public class FunctionInfo {
    private final DataType returnType;
    private final List<SymbolEntry> parameters;
    private boolean isDefined; // 是否已提供函数体 ({} block)

    public FunctionInfo(DataType returnType) {
        this.returnType = returnType;
        this.parameters = new ArrayList<>();
        this.isDefined = false;
    }

    public DataType getReturnType() {
        return returnType;
    }

    public List<SymbolEntry> getParameters() {
        return parameters;
    }

    public void addParameter(SymbolEntry param) {
        this.parameters.add(param);
    }

    public boolean isDefined() {
        return isDefined;
    }

    public void setDefined(boolean defined) {
        isDefined = defined;
    }
    
    // 可以添加获取函数签名、检查参数数量等方法
}