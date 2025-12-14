package com.wei.compiler.semantic;

import com.wei.compiler.ast.*;
import com.wei.compiler.type.DataType;
import com.wei.compiler.type.ArrayType;
import com.wei.compiler.type.PointerType;
import com.wei.compiler.type.StructType;
import java.util.*;

/**
 * 类型分析器
 * 
 * 实现完整的类型推导、检查和转换
 * - 推导每个表达式的类型
 * - 检查二元运算操作数类型兼容性
 * - 检查赋值兼容性
 * - 检查数组/结构体操作合法性
 * - 生成隐式类型转换信息
 */
public class TypeAnalyzer implements ASTVisitor<DataType> {
    
    private SymbolTableManager symbolTableManager;
    private List<SemanticError> errors;
    
    public TypeAnalyzer(SymbolTableManager symbolTableManager) {
        this.symbolTableManager = symbolTableManager;
        this.errors = new ArrayList<>();
    }
    
    /**
     * 分析表达式的类型
     */
    public DataType analyzeExpression(ExpressionNode expr) {
        if (expr == null) {
            return DataType.VOID;
        }
        
        DataType type = expr.accept(this);
        if (type != null) {
            expr.setDataType(type);
        }
        return type;
    }
    
    /**
     * 检查赋值兼容性 (LHS = RHS)
     */
    public boolean checkAssignmentCompatibility(DataType targetType, DataType sourceType, int line, int column) {
        if (targetType == null || sourceType == null) {
            return false;
        }
        
        // 相同类型总是兼容
        if (targetType.equals(sourceType)) { // 使用 equals 检查复杂类型
            return true;
        }
        
        // 1. 数字类型之间可以隐式转换 (小 -> 大)
        if (isNumericType(targetType) && isNumericType(sourceType)) {
            return true;
        }

        // 2. 指针和数组兼容性 (需要自定义 ArrayType 和 PointerType 的兼容性逻辑)
        // 例如：int* 可以赋值给 void*
        // 例如：int[10] (数组名) 可以隐式转换为 int*
        // 例如：0 (空指针常量) 可以赋值给任何指针类型
        
        // 3. 引用兼容性 (如果支持 C++ 引用)

        // 默认不兼容
        addError(new SemanticError(
            SemanticErrorType.TYPE_MISMATCH,
            "不兼容的赋值：无法将 " + sourceType + " 赋值给 " + targetType,
            "",
            symbolTableManager.getCurrentScopeLevel(),
            line,
            column
        ));
        
        return false;
    }
    
    /**
     * 获取二元运算的结果类型
     */
    public DataType getBinaryOperationType(DataType leftType, DataType rightType, 
                                          AdditiveOperator op, int line, int column) {
        // 简化处理：如果任一操作数是 float，则结果是 float；否则是 int
        if (leftType == DataType.FLOAT || rightType == DataType.FLOAT) {
            return DataType.FLOAT;
        }
        return DataType.INT;
    }
    
    public DataType getBinaryOperationType(DataType leftType, DataType rightType, 
                                          MultiplicativeOperator op, int line, int column) {
        // 简化处理：如果任一操作数是 float，则结果是 float；否则是 int
        if (leftType == DataType.FLOAT || rightType == DataType.FLOAT) {
            return DataType.FLOAT;
        }
        return DataType.INT;
    }
    
    public DataType getBinaryOperationType(DataType leftType, DataType rightType, 
                                          RelationalOperator op, int line, int column) {
        // 关系运算符结果总是 int (布尔值)
        return DataType.INT;
    }
    
    public DataType getBinaryOperationType(DataType leftType, DataType rightType, 
                                          EqualityOperator op, int line, int column) {
        // 相等运算符结果总是 int (布尔值)
        return DataType.INT;
    }
    
    public DataType getBinaryOperationType(DataType leftType, DataType rightType, 
                                          BitwiseOperator op, int line, int column) {
        // 位运算符：如果任一操作数是 float 则报错，否则结果是 int
        if (leftType == DataType.FLOAT || rightType == DataType.FLOAT) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "位运算符的操作数不能是浮点类型",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                line,
                column
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        return DataType.INT;
    }
    
    // ==================== 工具方法 ====================
    
    private void addError(SemanticError error) {
        errors.add(error);
    }
    
    public List<SemanticError> getErrors() {
        return errors;
    }
    
    // ==================== AST 访问者方法 ====================
    
    @Override
    public DataType visitAssignmentExpression(AssignmentExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        
        // 新增：检查是否将float隐式转int（丧失精度）
        if (leftType == DataType.INT && rightType == DataType.FLOAT) {
            addError(new SemanticError(
                SemanticErrorType.IMPLICIT_FLOAT_TO_INT_CONVERSION,
                "隐式浮点转整数会丧失精度",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        // 检查赋值兼容性
        checkAssignmentCompatibility(leftType, rightType, node.getLine(), node.getColumn());
        
        return leftType; // 赋值表达式的结果类型是左操作数的类型
    }
    
    @Override
    public DataType visitAdditiveExpression(AdditiveExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        AdditiveOperator op = node.getOperator();
        
        // 支持指针算术
        if (op == AdditiveOperator.ADD) {
            // 情况 1: ptr + int
            if (leftType instanceof PointerType && isNumericType(rightType)) {
                return leftType; // 结果是指针类型
            }
            // 情况 2: int + ptr
            if (isNumericType(leftType) && rightType instanceof PointerType) {
                return rightType; // 结果是指针类型
            }
        } else if (op == AdditiveOperator.SUBTRACT) {
            // 情况 3: ptr - int
            if (leftType instanceof PointerType && isNumericType(rightType)) {
                return leftType; // 结果是指针类型
            }
            // 情况 4: ptr - ptr (返回整数，表示指针差值)
            if (leftType instanceof PointerType && rightType instanceof PointerType) {
                return DataType.INT;
            }
        }
        
        // 检查操作数类型 (数值运算)
        if (!isNumericType(leftType) || !isNumericType(rightType)) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "加法运算符的操作数必须是数值类型或指针类型",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        return getBinaryOperationType(leftType, rightType, node.getOperator(), node.getLine(), node.getColumn());
    }
    
    @Override
    public DataType visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        
        // 检查操作数类型
        if (!isNumericType(leftType) || !isNumericType(rightType)) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "乘法运算符的操作数必须是数值类型",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        return getBinaryOperationType(leftType, rightType, node.getOperator(), node.getLine(), node.getColumn());
    }
    
    @Override
    public DataType visitRelationalExpression(RelationalExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        
        // 检查操作数类型
        if (!isNumericType(leftType) || !isNumericType(rightType)) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "关系运算符的操作数必须是数值类型",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        return getBinaryOperationType(leftType, rightType, node.getOperator(), node.getLine(), node.getColumn());
    }
    
    @Override
    public DataType visitEqualityExpression(EqualityExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        
        // 检查操作数类型
        // 相等性比较允许指针和 void* 比较，以及数值类型比较
        boolean leftIsNumeric = isNumericType(leftType);
        boolean rightIsNumeric = isNumericType(rightType);
        boolean leftIsPointer = leftType instanceof PointerType;
        boolean rightIsPointer = rightType instanceof PointerType;
        
        if (!(leftIsNumeric && rightIsNumeric) && !(leftIsPointer && rightIsPointer)) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "相等运算符的操作数类型不匹配",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        return getBinaryOperationType(leftType, rightType, node.getOperator(), node.getLine(), node.getColumn());
    }
    
    @Override
    public DataType visitBitwiseExpressionNode(BitwiseExpressionNode node) {
        DataType leftType = analyzeExpression(node.getLeft());
        DataType rightType = analyzeExpression(node.getRight());
        
        // 检查操作数类型
        if (isFloatingType(leftType) || isFloatingType(rightType)) {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "位运算符的操作数不能是浮点类型",
                "",
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        return getBinaryOperationType(leftType, rightType, node.getOperator(), node.getLine(), node.getColumn());
    }
    
    @Override
    public DataType visitUnaryExpression(UnaryExpressionNode node) {
        DataType operandType = analyzeExpression(node.getOperand());
        
        switch (node.getOperator()) {
            case PLUS:
            case MINUS:
                // 正负号只能用于数值类型
                if (!isNumericType(operandType)) {
                    addError(new SemanticError(
                        SemanticErrorType.TYPE_MISMATCH,
                        "正负号操作符的操作数必须是数值类型",
                        "",
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                    return DataType.INT; // 返回默认类型避免后续错误
                }
                return operandType;
                
            case NOT:
                // 逻辑非可以用于任何类型（非零为真）
                return DataType.INT; // 结果总是 int (布尔值)
                
            case PRE_INCREMENT:
            case PRE_DECREMENT:
                // 自增自减只能用于数值类型或指针类型
                if (!isNumericType(operandType) && !(operandType instanceof PointerType)) {
                    addError(new SemanticError(
                        SemanticErrorType.TYPE_MISMATCH,
                        "自增自减操作符的操作数必须是数值类型或指针类型",
                        "",
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                    return DataType.INT; // 返回默认类型避免后续错误
                }
                return operandType;
                
            // 🌟 新增：指针解引用 *
            case DEREFERENCE: 
                if (!(operandType instanceof PointerType)) {
                    addError(new SemanticError(
                        SemanticErrorType.TYPE_MISMATCH,
                        "解引用操作符 '*' 的操作数必须是指针类型",
                        "",
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                    return DataType.INT; 
                }
                // 返回指针指向的类型
                return ((PointerType) operandType).getTargetType();
            
            // 🌟 新增：取地址 &
            case ADDRESS_OF:
                // TODO: 检查操作数是否是左值 (LValue)
                // 返回当前类型的指针类型 (例如 int -> int*)
                return new PointerType(operandType); 
                
            // 🌟 新增：按位取反 ~
            case BITWISE_NOT:
                if (operandType != null && !isNumericType(operandType)) {
                    addError(new SemanticError(
                        SemanticErrorType.TYPE_MISMATCH,
                        "按位取反操作符 '~' 的操作数必须是整数类型",
                        "",
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                    return DataType.INT; 
                }
                return operandType != null ? operandType : DataType.INT;
                
            default:
                return DataType.VOID;
        }
    }
    
    @Override
    public DataType visitPostfixExpression(PostfixExpressionNode node) {
        DataType operandType = analyzeExpression(node.getOperand());
        
        switch (node.getOperator()) {
            case INCREMENT:
            case DECREMENT:
                // 后缀自增自减只能用于数值类型或指针类型
                if (!isNumericType(operandType) && !(operandType instanceof PointerType)) {
                    addError(new SemanticError(
                        SemanticErrorType.TYPE_MISMATCH,
                        "后缀自增自减操作符的操作数必须是数值类型或指针类型",
                        "",
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                    return DataType.INT; // 返回默认类型避免后续错误
                }
                return operandType;
            default:
                return DataType.VOID;
        }
    }
    
    @Override
    public DataType visitArrayAccess(ArrayAccessNode node) {
        DataType arrayType = analyzeExpression(node.getArray());
        
        // 1. 检查索引类型
        ExpressionNode[] indices = node.getIndices();
        for (ExpressionNode index : indices) {
            DataType indexType = analyzeExpression(index);
            // 索引必须是数字类型
            if (indexType != null && !isNumericType(indexType)) {
                addError(new SemanticError(
                    SemanticErrorType.TYPE_MISMATCH,
                    "数组下标必须是整数类型",
                    "",
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
            }
            // 新增：检查是否是浮点数作为数组下标
            if (indexType == DataType.FLOAT) {
                addError(new SemanticError(
                    SemanticErrorType.FLOAT_USED_AS_ARRAY_INDEX,
                    "浮点数不能作为数组下标，这会丧失精度",
                    "",
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
            }
        }
        
        // 2. 检查被访问对象是否是数组或指针
        DataType currentType = arrayType;
        
        // 假设 ArrayType 和 PointerType 实现了某种继承或接口
        if (currentType == null) {
            return DataType.INT; 
        }

        // 3. 逐层剥离维度
        // Array Access [] 操作会剥离最外层的数组类型或指针类型
        for (int i = 0; i < indices.length; i++) {
            if (currentType instanceof ArrayType) {
                currentType = ((ArrayType) currentType).getElementType();
            } else if (currentType instanceof PointerType) {
                currentType = ((PointerType) currentType).getTargetType();
            } else {
                addError(new SemanticError(
                    SemanticErrorType.TYPE_MISMATCH,
                    "非数组或指针类型不能使用下标访问",
                    "",
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
                return DataType.INT; 
            }
        }
        
        return currentType; // 返回最终的元素类型或剩余的数组/指针类型
    }
    
    @Override
    public DataType visitMemberAccess(MemberAccessNode node) {
        DataType objectType = analyzeExpression(node.getObject());
        
        // 1. 对象必须是结构体类型
        StructType structType = null;
        if (objectType instanceof StructType) {
            structType = (StructType) objectType;
        } else if (objectType instanceof PointerType) {
            // TODO: 如果您支持 -> 运算符，这里需要处理 (假设您只支持 .)
            // 这里只检查 . 运算符，因此必须是 StructType
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "点运算符 '.' 只能用于结构体对象",
                node.getMemberName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT;
        } else {
            addError(new SemanticError(
                SemanticErrorType.TYPE_MISMATCH,
                "点运算符 '.' 只能用于结构体对象",
                node.getMemberName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT;
        }
        
        // 2. 查找结构体成员
        // 假设 StructType 能够通过其名称查找字段的 SymbolEntry
        com.wei.compiler.semantic.SymbolEntry memberEntry = structType.getField(node.getMemberName());
        if (memberEntry == null) {
            addError(new SemanticError(
                SemanticErrorType.STRUCT_MEMBER_NOT_FOUND,
                "结构体 '" + structType.getName() + "' 中没有成员 '" + node.getMemberName() + "'",
                node.getMemberName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; 
        }
        
        // 3. 返回成员类型
        return memberEntry.getDataType();
    }
    
    @Override
    public DataType visitFunctionCall(FunctionCallNode node) {
        // 检查函数名是否已定义
        SymbolEntry functionEntry = lookupSymbolInAllScopes(node.getFunctionName());
        if (functionEntry == null) {
            addError(new SemanticError(
                SemanticErrorType.UNDEFINED_IDENTIFIER,
                "未定义的函数 '" + node.getFunctionName() + "'",
                node.getFunctionName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        // 检查是否是函数类型
        if (functionEntry.getSymbolType() != SymbolType.FUNCTION) {
            addError(new SemanticError(
                SemanticErrorType.NON_CALLABLE_IDENTIFIER,
                "'" + node.getFunctionName() + "' 不是函数",
                node.getFunctionName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        // 检查参数数量和类型
        ExpressionNode[] arguments = node.getArguments();
        FunctionInfo functionInfo = functionEntry.getFunctionInfo();
        
        if (functionInfo == null) {
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        List<SymbolEntry> parameters = functionInfo.getParameters();
        if (arguments.length != parameters.size()) {
            addError(new SemanticError(
                SemanticErrorType.FUNCTION_PARAMETER_MISMATCH,
                "函数 '" + node.getFunctionName() + "' 期望 " + parameters.size() + " 个参数，但提供了 " + arguments.length + " 个",
                node.getFunctionName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return functionInfo.getReturnType(); // 返回函数的返回类型
        }
        
        // 检查每个参数的类型兼容性
        for (int i = 0; i < arguments.length; i++) {
            DataType argType = analyzeExpression(arguments[i]);
            DataType paramType = parameters.get(i).getDataType();
            
            if (!checkAssignmentCompatibility(paramType, argType, node.getLine(), node.getColumn())) {
                addError(new SemanticError(
                    SemanticErrorType.FUNCTION_PARAMETER_MISMATCH,
                    "函数 '" + node.getFunctionName() + "' 的第 " + (i+1) + " 个参数类型不匹配",
                    node.getFunctionName(),
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
            }
        }
        
        // 返回函数的返回类型
        return functionInfo.getReturnType();
    }
    
    @Override
    public DataType visitIdentifier(IdentifierNode node) {
        SymbolEntry entry = lookupSymbolInAllScopes(node.getName());
        if (entry == null) {
            addError(new SemanticError(
                SemanticErrorType.UNDEFINED_IDENTIFIER,
                "未定义的标识符 '" + node.getName() + "'",
                node.getName(),
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return DataType.INT; // 返回默认类型避免后续错误
        }
        
        DataType type = entry.getDataType();
        
        // 🔥 关键修复：如果是结构体类型，需要确保 StructInfo 正确
        if (type instanceof StructType) {
            StructType structType = (StructType) type;
            if (structType.getStructInfo() == null) {
                // 尝试从符号表查找结构体定义
                SymbolEntry structEntry = symbolTableManager.lookupSymbolWithoutError(structType.getName());
                if (structEntry != null && structEntry.getStructInfo() != null) {
                    type = new StructType(structType.getName(), structEntry.getStructInfo());
                }
            }
        }
        // 如果是结构体定义本身，返回结构体类型
        else if (entry.getSymbolType() == SymbolType.STRUCT_DEFINITION) {
            return new StructType(entry.getName(), entry.getStructInfo());
        }
        
        return type;
    }
    
    @Override
    public DataType visitIntLiteral(IntLiteralNode node) {
        return DataType.INT;
    }
    
    @Override
    public DataType visitFloatLiteral(FloatLiteralNode node) {
        return DataType.FLOAT;
    }
    
    @Override
    public DataType visitCharLiteral(CharLiteralNode node) {
        return DataType.CHAR;
    }
    
    @Override
    public DataType visitStringLiteral(StringLiteralNode node) {
        // 字符串字面量是 char 数组，但在大多数上下文中被视为 char*
        return new PointerType(DataType.CHAR);
    }
    
    @Override
    public DataType visitCastExpression(CastExpressionNode node) {
        // 强制类型转换表达式的结果类型是目标类型
        return node.getTargetType().getDataType();
    }
    
    @Override
    public DataType visitTypeNameNode(TypeNameNode node) {
        // TypeNameNode 本身不产生值，主要用于类型转换和 sizeof 操作
        // 在类型分析中，我们可以返回 VOID 或根据上下文返回相应的类型
        return DataType.VOID;
    }
    
    // ==================== 辅助方法 ====================
    
    /**
     * 检查类型是否为数值类型
     */
    public static boolean isNumericType(DataType type) {
        return type == DataType.INT || type == DataType.FLOAT || type == DataType.CHAR;
    }
    
    /**
     * 检查类型是否为浮点类型
     */
    public static boolean isFloatingType(DataType type) {
        return type == DataType.FLOAT;
    }
    
    /**
     * 检查类型是否与int兼容
     */
    public static boolean isIntCompatible(DataType type) {
        return type == DataType.INT || type == DataType.CHAR;
    }
    
    // ==================== 缺失的访问者方法 ====================
    
    @Override
    public DataType visitProgram(ProgramNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitStructDeclaration(StructDeclarationNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitFieldDeclaration(FieldDeclarationNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitFunctionDeclaration(FunctionDeclarationNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitParameter(ParameterNode node) {
        return node.getType() != null ? node.getType().getDataType() : DataType.VOID;
    }
    
    @Override
    public DataType visitVariableDeclaration(VariableDeclarationNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitVariableDeclarator(VariableDeclaratorNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitDeclaratorNode(DeclaratorNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitBlock(BlockNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitExpressionStatement(ExpressionStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitIfStatement(IfStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitWhileStatement(WhileStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitDoWhileStatementNode(DoWhileStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitForStatement(ForStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitSwitchStatement(SwitchStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitCaseStatement(CaseStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitDefaultStatement(DefaultStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitBreakStatement(BreakStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitContinueStatement(ContinueStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitReturnStatement(ReturnStatementNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitConditionalExpression(ConditionalExpressionNode node) {
        return analyzeExpression(node.getTrueExpression());
    }
    
    @Override
    public DataType visitLogicalOrExpression(LogicalOrExpressionNode node) {
        return DataType.INT; // 逻辑运算结果为int
    }
    
    @Override
    public DataType visitLogicalAndExpression(LogicalAndExpressionNode node) {
        return DataType.INT; // 逻辑运算结果为int
    }
    
    @Override
    public DataType visitShiftExpressionNode(ShiftExpressionNode node) {
        return DataType.INT; // 移位运算结果为int
    }
    
    @Override
    public DataType visitPrimaryExpression(PrimaryExpressionNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitType(TypeNode node) {
        return node != null ? node.getDataType() : DataType.VOID;
    }
    
    /**
     * 辅助方法：在所有作用域中查找符号
     */
    private SymbolEntry lookupSymbolInAllScopes(String name) {
        // 🔥 关键修复：使用符号表管理器的完整查询（从当前作用域向上回溯到全局作用域）
        SymbolEntry result = symbolTableManager.lookupSymbolWithoutError(name);
        System.out.println("[TypeAnalyzer] lookupSymbolInAllScopes(\"" + name + "\") returned: " + (result != null ? result : "null"));
        return result;
    }
}