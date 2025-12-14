package com.wei.compiler.semantic;

/**
 * RuntimeInfo 类
 * 存储与代码生成和运行时检查相关的状态，如是否已初始化、栈偏移量等。
 */
public class RuntimeInfo {
    private boolean isInitialized;
    private int stackOffset; // 栈/内存中的偏移量（用于代码生成）
    // private Object constantValue; // 如果是常量

    public RuntimeInfo() {
        this.isInitialized = false;
        this.stackOffset = 0;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public int getStackOffset() {
        return stackOffset;
    }

    public void setStackOffset(int stackOffset) {
        this.stackOffset = stackOffset;
    }
}