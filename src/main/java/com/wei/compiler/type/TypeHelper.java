package com.wei.compiler.type;

import com.wei.compiler.ast.ExpressionNode;
import com.wei.compiler.ast.DeclaratorNode;
import com.wei.compiler.semantic.SemanticError;
import com.wei.compiler.semantic.SemanticErrorType;
import java.util.List;

/**
 * TypeHelper 类
 * 负责从基础类型和声明符（DeclaratorNode）中构建复杂的最终数据类型。
 */
public class TypeHelper {

    private TypeHelper() {
        // 静态工具类
    }

    /**
     * 构造复杂类型（指针、引用、数组）的组合。
     * 遵循 C 语言的声明解析顺序：数组/函数 -> 指针 -> 引用。
     * * @param baseType 声明的基础类型 (例如：DataType.INT, StructType)
     * @param declarator 包含修饰符信息的 DeclaratorNode
     * @param errors 用于收集在类型构造过程中发现的语义错误
     * @return 最终构造的复杂 DataType
     */
    public static DataType constructComplexType(
            DataType baseType,
            DeclaratorNode declarator,
            List<SemanticError> errors) {

        DataType currentType = baseType;

        // 🔥 关键修复：正确处理数组维度，包括参数数组 arr[] （维度为 null）
        ExpressionNode[] dimensions = declarator.getArrayDimensions();
        if (dimensions != null) {
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
              // errors.add(new SemanticError(...)); // 空下无前罚常数
        } else if (referenceCount == 1) {
            // 引用必须是其最终类型（即不能是数组）
            if (currentType.getKind() == DataType.TypeKind.ARRAY) {
                 // 不能声明数组的引用
                 // errors.add(new SemanticError(...)); // 空下无前罚常数
            } else {
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
    public static DataType createStructType(String name, com.wei.compiler.semantic.StructInfo structInfo) {
        return new StructType(name, structInfo);
    }
    
    /**
     * 检查两个类型是否兼容
     * @param expected 期望的类型
     * @param actual 实际的类型
     * @return 是否兼容
     */
    public static boolean isCompatible(DataType expected, DataType actual) {
        return TypeChecker.isCompatible(expected, actual);
    }
}