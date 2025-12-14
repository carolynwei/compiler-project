package com.wei.compiler.semantic;

import com.wei.compiler.type.DataType;

// 实际 ArrayInfo 通常集成在 ArrayType 中，但为了兼容您的 SymbolEntry 设计，我们保留这个类。
// 它可以用来存储计算后的常量维度大小。

/**
 * ArrayInfo 类
 * 存储与数组相关的计算信息。
 */
public class ArrayInfo {
    private final long totalElements; // 元素总数
    private final int elementSize;   // 单个元素大小（字节）
    private final int[] dimensions;  // 数组维度
    private final DataType elementType; // 元素类型

    public ArrayInfo(long totalElements, int elementSize, int[] dimensions, DataType elementType) {
        this.totalElements = totalElements;
        this.elementSize = elementSize;
        this.dimensions = dimensions != null ? dimensions : new int[0];
        this.elementType = elementType;
    }

    public ArrayInfo(long totalElements, int elementSize) {
        this(totalElements, elementSize, null, DataType.VOID);
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getElementSize() {
        return elementSize;
    }
    
    public int[] getDimensions() {
        return dimensions;
    }
    
    public DataType getElementType() {
        return elementType;
    }
}