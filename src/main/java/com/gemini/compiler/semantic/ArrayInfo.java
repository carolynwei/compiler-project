package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.DataType;
import java.util.Arrays;

/**
 * 数组信息
 */
public class ArrayInfo {
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