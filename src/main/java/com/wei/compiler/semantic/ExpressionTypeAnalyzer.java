package com.wei.compiler.semantic;

import com.wei.compiler.ast.*;
import com.wei.compiler.type.DataType;
import java.util.*;

/**
 * 表达式类型分析器
 * 
 * 分析表达式的类型，用于语义检查，并设置表达式节点的数据类型
 */
class ExpressionTypeAnalyzer implements ASTVisitor<DataType> {
    
    private SymbolTableManager symbolTableManager;
    
    public ExpressionTypeAnalyzer() {
        this.symbolTableManager = new SymbolTableManager();
    }
    
    public ExpressionTypeAnalyzer(SymbolTableManager symbolTableManager) {
        this.symbolTableManager = symbolTableManager;
    }
    
    /**
     * 辅助方法：设置表达式的类型并返回
     */
    private DataType setAndReturnType(ExpressionNode node, DataType type) {
        if (node instanceof ExpressionNode) {
            ((ExpressionNode) node).setDataType(type);
        }
        return type;
    }
    
    @Override
    public DataType visitProgram(ProgramNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitStructDeclaration(StructDeclarationNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitFieldDeclaration(FieldDeclarationNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitFunctionDeclaration(FunctionDeclarationNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitParameter(ParameterNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitVariableDeclaration(VariableDeclarationNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitVariableDeclarator(VariableDeclaratorNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitBlock(BlockNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitExpressionStatement(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitIfStatement(IfStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitWhileStatement(WhileStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitForStatement(ForStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitSwitchStatement(SwitchStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitCaseStatement(CaseStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitDefaultStatement(DefaultStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitBreakStatement(BreakStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitContinueStatement(ContinueStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitReturnStatement(ReturnStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitAssignmentExpression(AssignmentExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 检查赋值兼容性
        if (!TypeChecker.isAssignmentCompatible(leftType, rightType)) {
            // 错误已在语义分析器中处理
        }
        
        return setAndReturnType(node, leftType);
    }
    
    @Override
    public DataType visitConditionalExpression(ConditionalExpressionNode node) {
        DataType conditionType = node.getCondition().accept(this);
        DataType trueType = node.getTrueExpression().accept(this);
        DataType falseType = node.getFalseExpression().accept(this);
        
        // 条件表达式的结果类型是 true 和 false 表达式的公共类型
        DataType resultType;
        if (TypeChecker.isCompatible(trueType, falseType)) {
            resultType = trueType;
        } else if (TypeChecker.isCompatible(falseType, trueType)) {
            resultType = falseType;
        } else {
            resultType = com.wei.compiler.type.DataType.INT; // 默认返回 int
        }
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitLogicalOrExpression(LogicalOrExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 逻辑运算结果类型为 int
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
    
    @Override
    public DataType visitLogicalAndExpression(LogicalAndExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 逻辑运算结果类型为 int
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
    
    @Override
    public DataType visitEqualityExpression(EqualityExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 相等性运算结果类型为 int
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
    
    @Override
    public DataType visitRelationalExpression(RelationalExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 关系运算结果类型为 int
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
    
    @Override
    public DataType visitAdditiveExpression(AdditiveExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        DataType resultType = TypeChecker.getExpressionResultType(leftType, rightType, node.getOperator());
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        DataType resultType = TypeChecker.getExpressionResultType(leftType, rightType, node.getOperator());
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitUnaryExpression(UnaryExpressionNode node) {
        DataType operandType = node.getOperand().accept(this);
        
        // 检查一元运算符的有效性
        if (!TypeChecker.isUnaryOperatorValid(node.getOperator(), operandType)) {
            // 错误已在语义分析器中处理
        }
        
        // 一元运算的结果类型通常与操作数类型相同
        return setAndReturnType(node, operandType);
    }
    
    @Override
    public DataType visitPostfixExpression(PostfixExpressionNode node) {
        DataType operandType = node.getOperand().accept(this);
        
        // 后缀运算的结果类型与操作数类型相同
        return setAndReturnType(node, operandType);
    }
    
    @Override
    public DataType visitPrimaryExpression(PrimaryExpressionNode node) {
        // 主表达式的类型取决于其内容
        DataType resultType;
        switch (node.getType()) {
            case IDENTIFIER:
                resultType = DataType.INT; // 简化处理
                break;
            case INT_LITERAL:
                resultType = DataType.INT;
                break;
            case FLOAT_LITERAL:
                resultType = DataType.FLOAT;
                break;
            case CHAR_LITERAL:
                resultType = DataType.CHAR;
                break;
            case STRING_LITERAL:
                resultType = DataType.STRING;
                break;
            case PARENTHESIZED_EXPRESSION:
                resultType = DataType.INT; // 简化处理
                break;
            default:
                resultType = DataType.VOID;
        }
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitCastExpression(CastExpressionNode node) {
        // 类型转换表达式，返回目标类型
        DataType resultType = DataType.INT; // 简化处理 - 应该返回实际的目标类型
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitIdentifier(IdentifierNode node) {
        // 查找标识符
        SymbolEntry entry = symbolTableManager.lookupSymbol(node.getName());
        
        if (entry == null) {
            return setAndReturnType(node, DataType.VOID);
        }
        
        DataType type = entry.getDataType();
        return setAndReturnType(node, type);
    }
    
    @Override
    public DataType visitIntLiteral(IntLiteralNode node) {
        return setAndReturnType(node, DataType.INT);
    }
    
    @Override
    public DataType visitFloatLiteral(FloatLiteralNode node) {
        return setAndReturnType(node, DataType.FLOAT);
    }
    
    @Override
    public DataType visitCharLiteral(CharLiteralNode node) {
        return setAndReturnType(node, DataType.CHAR);
    }
    
    @Override
    public DataType visitStringLiteral(StringLiteralNode node) {
        return setAndReturnType(node, DataType.STRING);
    }
    
    @Override
    public DataType visitFunctionCall(FunctionCallNode node) {
        // 查找函数
        SymbolEntry functionEntry = symbolTableManager.lookupSymbol(node.getFunctionName());
        
        if (functionEntry == null || functionEntry.getSymbolType() != SymbolType.FUNCTION) {
            return DataType.VOID;
        }
        
        // 检查参数
        if (functionEntry.getFunctionInfo() != null) {
            List<SymbolEntry> parameters = functionEntry.getFunctionInfo().getParameters();
            ExpressionNode[] arguments = node.getArguments();
            
            if (parameters.size() != arguments.length) {
                // 参数数量不匹配
                return DataType.VOID;
            }
            
            // 检查参数类型
            for (int i = 0; i < parameters.size(); i++) {
                DataType paramType = parameters.get(i).getDataType();
                DataType argType = arguments[i].accept(this);
                
                if (!TypeChecker.isCompatible(paramType, argType)) {
                    // 参数类型不匹配
                    return DataType.VOID;
                }
            }
        }
        
        return functionEntry.getDataType();
    }
    
    @Override
    public DataType visitArrayAccess(ArrayAccessNode node) {
        DataType arrayType = node.getArray().accept(this);
        
        // 分析所有索引
        ExpressionNode[] indices = node.getIndices();
        for (ExpressionNode index : indices) {
            DataType indexType = index.accept(this);
            
            // 检查数组下标类型
            if (!TypeChecker.isArrayIndexValid(indexType)) {
                // 错误已在语义分析器中处理
            }
        }
        
        // 获取数组信息
        if (node.getArray() instanceof IdentifierNode) {
            IdentifierNode arrayIdentifier = (IdentifierNode) node.getArray();
            SymbolEntry arrayEntry = symbolTableManager.lookupSymbol(arrayIdentifier.getName());
            
            if (arrayEntry != null && arrayEntry.getArrayInfo() != null) {
                ArrayInfo arrayInfo = arrayEntry.getArrayInfo();
                int[] dimensions = arrayInfo.getDimensions();
                
                // 每进行一次下标访问，维度减少一层
                int remainingDimensions = dimensions.length - indices.length;
                DataType resultType;
                if (remainingDimensions > 0) {
                    // 还有未访问的维度，返回数组类型
                    resultType = new com.wei.compiler.type.ArrayType(arrayInfo.getElementType(), null);
                } else {
                    // 已经访问完所有维度，返回元素类型
                    resultType = arrayInfo.getElementType();
                }
                
                return setAndReturnType(node, resultType);
            }
        }
        
        // 默认处理：返回数组元素的类型
        DataType resultType;
        if (arrayType.getKind() == com.wei.compiler.type.DataType.TypeKind.ARRAY) {
            // 这里需要从符号表中获取数组的元素类型
            // 简化处理，返回 int
            resultType = com.wei.compiler.type.DataType.INT;
        } else if (arrayType instanceof com.wei.compiler.type.ArrayType) {
            // 获取数组的元素类型
            resultType = ((com.wei.compiler.type.ArrayType) arrayType).getElementType();
        } else {
            resultType = com.wei.compiler.type.DataType.VOID;
        }
        
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitMemberAccess(MemberAccessNode node) {
        DataType objectType = node.getObject().accept(this);
        
        // 检查是否为结构体类型
        if (!(objectType instanceof com.wei.compiler.type.StructType)) {
            // 错误已在语义分析器中处理
            return setAndReturnType(node, com.wei.compiler.type.DataType.VOID);
        }
        
        // 查找结构体对象
        if (!(node.getObject() instanceof IdentifierNode)) {
            return setAndReturnType(node, com.wei.compiler.type.DataType.VOID);
        }
        
        IdentifierNode objectIdentifier = (IdentifierNode) node.getObject();
        SymbolEntry structObjectEntry = symbolTableManager.lookupSymbol(objectIdentifier.getName());
        
        if (structObjectEntry == null) {
            return setAndReturnType(node, com.wei.compiler.type.DataType.VOID);
        }
        
        // 查找成员
        DataType resultType = com.wei.compiler.type.DataType.VOID;
        if (structObjectEntry.getStructInfo() != null) {
            SymbolEntry memberEntry = structObjectEntry.getStructInfo().getField(node.getMemberName());
            if (memberEntry != null) {
                resultType = memberEntry.getDataType();
            }
        }
        
        return setAndReturnType(node, resultType);
    }
    
    @Override
    public DataType visitType(TypeNode node) {
        return node.getDataType();
    }
    
    @Override
    public DataType visitTypeNameNode(TypeNameNode node) {
        return node.getBaseType() != null ? node.getBaseType().getDataType() : com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitDeclaratorNode(DeclaratorNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitDoWhileStatementNode(DoWhileStatementNode node) {
        return com.wei.compiler.type.DataType.VOID;
    }
    
    @Override
    public DataType visitBitwiseExpressionNode(BitwiseExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
    
    @Override
    public DataType visitShiftExpressionNode(ShiftExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return setAndReturnType(node, com.wei.compiler.type.DataType.INT);
    }
}
