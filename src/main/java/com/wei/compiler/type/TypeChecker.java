package com.wei.compiler.type;

import com.wei.compiler.ast.ExpressionNode;
import com.wei.compiler.ast.IntLiteralNode;

/**
 * TypeChecker 类
 * 负责进行各种类型检查和兼容性判断。
 * ⚠️ 注意：这里所有方法都是静态工具方法。
 */
public class TypeChecker {

    private TypeChecker() {
        // 静态工具类
    }

    // --- 核心兼容性检查 ---

    /**
     * 检查类型是否兼容（例如，函数返回类型和 return 表达式类型）。
     * 这是一个简化版本，实际的 C 语言兼容性规则非常复杂。
     * @param expected 期望的类型
     * @param actual 实际的类型
     * @return 是否兼容
     */
    public static boolean isCompatible(DataType expected, DataType actual) {
        if (expected == null || actual == null) {
            return false;
        }

        // 1. 完全相同则兼容
        if (expected.equals(actual)) {
            return true;
        }
        
        // 2. 指针类型检查
        if (expected instanceof PointerType && actual instanceof PointerType) {
            // 检查指针目标类型是否兼容（简化为相同）
            DataType expectedTarget = ((PointerType) expected).getTargetType();
            DataType actualTarget = ((PointerType) actual).getTargetType();
            return isCompatible(expectedTarget, actualTarget);
        }
        
        // 3. 数组类型检查
        if (expected instanceof ArrayType && actual instanceof ArrayType) {
            // 检查数组元素类型是否兼容
            DataType expectedElement = ((ArrayType) expected).getElementType();
            DataType actualElement = ((ArrayType) actual).getElementType();
            return isCompatible(expectedElement, actualElement);
        }
        
        // 🔥 新增：数组到指针的隐式转换 (C语言规则)
        // 例如：int arr[5] (ArrayType) 可以隐式转换为 int* (PointerType)
        if (expected instanceof PointerType && actual instanceof ArrayType) {
            PointerType ptrType = (PointerType) expected;
            ArrayType arrType = (ArrayType) actual;
            // 检查指针指向的类型与数组元素类型是否兼容
            return isCompatible(ptrType.getTargetType(), arrType.getElementType());
        }
        
        // 4. 结构体类型检查（🔥 现在支持结构体值赋值）
        if (expected instanceof StructType && actual instanceof StructType) {
            // 检查结构体名称是否相同
            String expectedName = ((StructType) expected).getName();
            String actualName = ((StructType) actual).getName();
            // 🔥 修复：有效性检查 + null 安全检查
            // 相同名称的结构体可以赋值（值复制语义）
            return expectedName != null && actualName != null && expectedName.equals(actualName);
        }

        // 5. 整数和浮点数之间可以隐式转换 (提升)
        if ((isIntegerType(expected) || isFloatingType(expected)) && (isIntegerType(actual) || isFloatingType(actual))) {
            return true;
        }

        // 6. 指针可以和整型字面量 0 兼容（NULL）
        if (expected instanceof PointerType && actual.getKind() == DataType.TypeKind.INT) {
            // 需要进一步检查实际值是否是 0，但此处简化为类型检查
            return true;
        }
        
        // 7. void* 可以兼容任何指针
        if (expected instanceof PointerType && 
            ((PointerType)expected).getTargetType().getKind() == DataType.TypeKind.VOID && 
            actual instanceof PointerType) {
             return true;
        }
        
        return false;
    }

    /**
     * 检查赋值兼容性 (左值 = 右值)。
     */
    public static boolean isAssignmentCompatible(DataType left, DataType right) {
        // 赋值兼容通常比一般兼容性要求更严格，但仍允许隐式转换
        if (isCompatible(left, right)) {
            return true;
        }
        
        // 允许从 int 赋值给 float/double
        if (isIntegerType(right) && isFloatingType(left)) {
            return true;
        }

        return false;
    }

    // --- 简单类型判断工具 ---

    public static boolean isIntegerType(DataType type) {
        return type.getKind() == DataType.TypeKind.INT || type.getKind() == DataType.TypeKind.CHAR;
    }

    public static boolean isFloatingType(DataType type) {
        return type.getKind() == DataType.TypeKind.FLOAT || type.getKind() == DataType.TypeKind.DOUBLE;
    }

    public static boolean isNumericType(DataType type) {
        return isIntegerType(type) || isFloatingType(type);
    }

    /**
     * 检查表达式类型是否可以作为控制表达式 (if, while 条件)
     * 在 C 语言中，任何非 void 的标量类型都可以，实际就是任何非 void 的数值或指针。
     */
    public static boolean isControlExpressionValid(DataType type) {
        return isNumericType(type) || type instanceof PointerType;
    }
    
    // --- 数组/Case 专用检查 ---
    
    /**
     * 检查类型是否可以作为数组维度或 Case 标签 (要求是整数类型)。
     */
    public static boolean isIntCompatible(DataType type) {
        return isIntegerType(type);
    }
    
    /**
     * 尝试获取表达式的常量整数值。
     * 仅支持字面量，实际编译器会递归计算常量表达式。
     * @param expr 表达式节点
     * @return 常量值 (Long)，如果不是常量或类型不对则返回 null
     */
    public static Long getConstantIntValue(ExpressionNode expr) {
        if (expr instanceof IntLiteralNode) {
            // ⚠️ 假设 IntLiteralNode 存储了 long 类型的常量值
            return (long) ((IntLiteralNode) expr).getValue(); 
        } 
        // 实际应用中，还需要解析如 'A' (char literal), (1 + 2) (const expression)
        return null;
    }
    
    /**
     * 检查类型是否为指定的类型种类
     * @param type 待检查的类型
     * @param kind 期望的类型种类
     * @return 是否匹配
     */
    public static boolean isKind(DataType type, DataType.TypeKind kind) {
        return type != null && type.getKind() == kind;
    }
}