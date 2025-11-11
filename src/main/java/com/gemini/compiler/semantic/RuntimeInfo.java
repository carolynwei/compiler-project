package com.gemini.compiler.semantic;

/**
 * 运行时信息
 */
public class RuntimeInfo {
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