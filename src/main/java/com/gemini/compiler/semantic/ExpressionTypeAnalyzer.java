package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.*;
import java.util.*;

/**
 * 表达式类型分析器
 * 
 * 分析表达式的类型，用于语义检查
 */
class ExpressionTypeAnalyzer implements ASTVisitor<DataType> {
    
    private SymbolTableManager symbolTableManager;
    
    public ExpressionTypeAnalyzer() {
        this.symbolTableManager = new SymbolTableManager();
    }
    
    public ExpressionTypeAnalyzer(SymbolTableManager symbolTableManager) {
        this.symbolTableManager = symbolTableManager;
    }
    
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
        return DataType.VOID;
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
    public DataType visitBlock(BlockNode node) {
        return DataType.VOID;
    }
    
    @Override
    public DataType visitExpressionStatement(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
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
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return DataType.VOID;
    }
    
    @Override
    public DataType visitAssignmentExpression(AssignmentExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 检查赋值兼容性
        if (!TypeChecker.isAssignmentCompatible(leftType, rightType)) {
            // 错误已在语义分析器中处理
        }
        
        return leftType;
    }
    
    @Override
    public DataType visitConditionalExpression(ConditionalExpressionNode node) {
        DataType conditionType = node.getCondition().accept(this);
        DataType trueType = node.getTrueExpression().accept(this);
        DataType falseType = node.getFalseExpression().accept(this);
        
        // 条件表达式的结果类型是 true 和 false 表达式的公共类型
        if (TypeChecker.isCompatible(trueType, falseType)) {
            return trueType;
        } else if (TypeChecker.isCompatible(falseType, trueType)) {
            return falseType;
        } else {
            return DataType.INT; // 默认返回 int
        }
    }
    
    @Override
    public DataType visitLogicalOrExpression(LogicalOrExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 逻辑运算结果类型为 int
        return DataType.INT;
    }
    
    @Override
    public DataType visitLogicalAndExpression(LogicalAndExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 逻辑运算结果类型为 int
        return DataType.INT;
    }
    
    @Override
    public DataType visitEqualityExpression(EqualityExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 相等性运算结果类型为 int
        return DataType.INT;
    }
    
    @Override
    public DataType visitRelationalExpression(RelationalExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        // 关系运算结果类型为 int
        return DataType.INT;
    }
    
    @Override
    public DataType visitAdditiveExpression(AdditiveExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        return TypeChecker.getExpressionResultType(leftType, rightType, node.getOperator());
    }
    
    @Override
    public DataType visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        DataType leftType = node.getLeft().accept(this);
        DataType rightType = node.getRight().accept(this);
        
        return TypeChecker.getExpressionResultType(leftType, rightType, node.getOperator());
    }
    
    @Override
    public DataType visitUnaryExpression(UnaryExpressionNode node) {
        DataType operandType = node.getOperand().accept(this);
        
        // 检查一元运算符的有效性
        if (!TypeChecker.isUnaryOperatorValid(node.getOperator(), operandType)) {
            // 错误已在语义分析器中处理
        }
        
        // 一元运算的结果类型通常与操作数类型相同
        return operandType;
    }
    
    @Override
    public DataType visitPostfixExpression(PostfixExpressionNode node) {
        DataType operandType = node.getOperand().accept(this);
        
        // 后缀运算的结果类型与操作数类型相同
        return operandType;
    }
    
    @Override
    public DataType visitPrimaryExpression(PrimaryExpressionNode node) {
        // 主表达式的类型取决于其内容
        switch (node.getType()) {
            case IDENTIFIER:
                return DataType.INT; // 简化处理
            case INT_LITERAL:
                return DataType.INT;
            case FLOAT_LITERAL:
                return DataType.FLOAT;
            case CHAR_LITERAL:
                return DataType.CHAR;
            case STRING_LITERAL:
                return DataType.STRING;
            case PARENTHESIZED_EXPRESSION:
                return DataType.INT; // 简化处理
            default:
                return DataType.VOID;
        }
    }
    
    @Override
    public DataType visitIdentifier(IdentifierNode node) {
        // 查找标识符
        SymbolEntry entry = symbolTableManager.lookupSymbol(node.getName());
        
        if (entry == null) {
            return DataType.VOID;
        }
        
        return entry.getDataType();
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
        return DataType.STRING;
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
        DataType indexType = node.getIndex().accept(this);
        
        // 检查数组下标类型
        if (!TypeChecker.isArrayIndexValid(indexType)) {
            // 错误已在语义分析器中处理
        }
        
        // 数组访问的结果类型是数组元素的类型
        if (arrayType == DataType.ARRAY) {
            // 这里需要从符号表中获取数组的元素类型
            // 简化处理，返回 int
            return DataType.INT;
        }
        
        return DataType.VOID;
    }
    
    @Override
    public DataType visitMemberAccess(MemberAccessNode node) {
        DataType objectType = node.getObject().accept(this);
        
        // 检查是否为结构体类型
        if (objectType != DataType.STRUCT) {
            // 错误已在语义分析器中处理
            return DataType.VOID;
        }
        
        // 查找结构体定义
        SymbolEntry structEntry = symbolTableManager.lookupSymbol(node.getMemberName());
        
        if (structEntry == null || structEntry.getSymbolType() != SymbolType.STRUCT_DEFINITION) {
            return DataType.VOID;
        }
        
        // 查找成员
        if (structEntry.getStructInfo() != null) {
            SymbolEntry memberEntry = structEntry.getStructInfo().getField(node.getMemberName());
            if (memberEntry != null) {
                return memberEntry.getDataType();
            }
        }
        
        return DataType.VOID;
    }
    
    @Override
    public DataType visitType(TypeNode node) {
        return node.getDataType();
    }
}
