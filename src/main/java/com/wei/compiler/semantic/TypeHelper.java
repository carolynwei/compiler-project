package com.wei.compiler.semantic;

import com.wei.compiler.ast.*;
import com.wei.compiler.type.DataType;
import com.wei.compiler.type.StructType;
import com.wei.compiler.type.PointerType;
import com.wei.compiler.type.ArrayType;
import com.wei.compiler.type.ReferenceType;
import java.util.List;

/**
 * 类型助手类
 * 提供类型构造和类型检查的辅助方法
 */
public class TypeHelper {
    
    /**
     * 构造复杂类型（数组、指针等）
     * 遵循 C 语言的声明解析顺序：数组/函数 -> 指针 -> 引用
     * @param baseType 基础类型
     * @param declarator 声明符
     * @param errors 错误列表
     * @return 构造后的复杂类型
     */
    public static DataType constructComplexType(DataType baseType, DeclaratorNode declarator, List<SemanticError> errors) {
        DataType currentType = baseType;

        // 🔥 关键修复：正确处理数组维度，包括参数数组 arr[] （维度为 null）
        ExpressionNode[] dimensions = declarator.getArrayDimensions();
        if (dimensions != null && dimensions.length > 0) {
            for (ExpressionNode dimExpr : dimensions) {
                // 每次迭代将当前类型包装成一个数组类型
                // 即使 dimExpr 为 null（参数数组），也创建 ArrayType
                currentType = new ArrayType(currentType, dimExpr);
            }
        }
        
        // 2. 处理指针级别 (PointerType)
        int pointerLevel = declarator.getPointerLevel();
        for (int i = 0; i < pointerLevel; i++) {
            // 每次迭代将当前类型包装成一个指针类型
            currentType = new PointerType(currentType);
        }

        // 3. 处理引用 (ReferenceType)
        int referenceCount = declarator.getReferenceCount();
        if (referenceCount > 1) {
            // 不允许声明多级引用
        } else if (referenceCount == 1) {
            // 引用必须是其最终类型（即不能是数组）
            if (currentType.getKind() != DataType.TypeKind.ARRAY) {
                currentType = new ReferenceType(currentType);
            }
        }
        
        return currentType;
    }
    
    /**
     * 创建结构体类型
     * @param name 结构体名称
     * @param structInfo 结构体信息
     * @return 结构体类型
     */
    public static DataType createStructType(String name, StructInfo structInfo) {
        return new StructType(name, structInfo);
    }
    
    /**
     * 检查类型是否为数值类型
     * @param type 待检查的类型
     * @return 是否为数值类型
     */
    public static boolean isNumericType(DataType type) {
        return type == DataType.INT || type == DataType.FLOAT || type == DataType.CHAR;
    }
    
    /**
     * 检查类型是否与int兼容
     * @param type 待检查的类型
     * @return 是否与int兼容
     */
    public static boolean isIntCompatible(DataType type) {
        return type == DataType.INT || type == DataType.CHAR;
    }
    
    /**
     * 获取常量表达式的整数值
     * @param expr 表达式节点
     * @return 常量值，如果不是常量表达式则返回null
     */
    public static Long getConstantIntValue(ExpressionNode expr) {
        if (expr instanceof IntLiteralNode) {
            return (long) ((IntLiteralNode) expr).getValue();
        } else if (expr instanceof CharLiteralNode) {
            return (long) ((CharLiteralNode) expr).getValue();
        }
        return null;
    }
    
    /**
     * 检查两个类型是否兼容
     * @param type1 类型1 (目标类型)
     * @param type2 类型2 (源类型)
     * @return 是否兼容
     */
    public static boolean isCompatible(DataType type1, DataType type2) {
        if (type1 == type2) {
            return true;
        }
        
        // 允许的隐式类型转换
        if (type1 == DataType.INT && type2 == DataType.CHAR) {
            return true;
        }
        
        if (type1 == DataType.FLOAT && (type2 == DataType.INT || type2 == DataType.CHAR)) {
            return true;
        }
        
        // 🔥 新增：结构体值赋值支持
        // 相同名称的结构体可以赋值（值复制语义）
        if (type1 instanceof StructType && type2 instanceof StructType) {
            String name1 = ((StructType) type1).getName();
            String name2 = ((StructType) type2).getName();
            return name1 != null && name2 != null && name1.equals(name2);
        }
        
        // ✅ 新增：数组到指针的隐式转换 (C语言规则)
        // int arr[5] (ArrayType) 可以隐式转换为 int* (PointerType)
        if (type1 instanceof PointerType && type2 instanceof ArrayType) {
            PointerType ptrType = (PointerType) type1;
            ArrayType arrType = (ArrayType) type2;
            // 检查指针指向的类型与数组元素类型是否相同
            return ptrType.getTargetType().equals(arrType.getElementType());
        }
        
        // ✅ 新增：指针兼容性 (void* 与任何指针兼容)
        if (type1 instanceof PointerType && type2 instanceof PointerType) {
            PointerType ptr1 = (PointerType) type1;
            PointerType ptr2 = (PointerType) type2;
            // 同类型指针总是兼容
            if (ptr1.getTargetType().equals(ptr2.getTargetType())) {
                return true;
            }
            // void* 与任何指针兼容 (简化处理)
            // TODO: 检查 ptr1.getTargetType() 或 ptr2.getTargetType() 是否为 VOID
        }
        
        return false;
    }
}