package com.gemini.compiler.semantic;

import com.gemini.compiler.ast.*;
import java.util.*;

/**
 * 语义分析器
 * 
 * 实现完整的语义分析，包括符号表管理和类型检查
 * 支持 20+ 种语义错误检查
 */
public class SemanticAnalyzer implements ASTVisitor<Void> {
    
    private SymbolTableManager symbolTableManager;
    private boolean debugMode;
    private List<SemanticError> errors;
    
    // 当前分析的上下文
    private DataType currentFunctionReturnType;
    private boolean inLoopContext;
    private boolean inSwitchContext;
    
    public SemanticAnalyzer() {
        this.symbolTableManager = new SymbolTableManager();
        this.debugMode = false;
        this.errors = new ArrayList<>();
        this.currentFunctionReturnType = DataType.VOID;
        this.inLoopContext = false;
        this.inSwitchContext = false;
    }
    
    /**
     * 分析 AST
     */
    public void analyze(ASTNode ast) {
        System.out.println("\n--- 阶段二：语义分析 ---");
        
        // 设置调试模式
        symbolTableManager.setDebugMode(debugMode);
        
        // 遍历 AST 进行语义分析
        ast.accept(this);
        
        // 检查是否有 main 函数
        checkMainFunction();
        
        // 打印结果
        if (debugMode) {
            symbolTableManager.displaySymbolTable();
        }
        
        symbolTableManager.printErrors();
        
        if (hasErrors()) {
            System.err.println("语义分析失败，发现 " + getErrorCount() + " 个错误");
            System.exit(1);
        } else {
            System.out.println("语义分析通过");
        }
    }
    
    /**
     * 设置调试模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * 添加错误
     */
    private void addError(SemanticError error) {
        errors.add(error);
        symbolTableManager.getErrors().add(error);
    }
    
    /**
     * 检查是否有错误
     */
    public boolean hasErrors() {
        return !errors.isEmpty() || symbolTableManager.hasErrors();
    }
    
    /**
     * 获取错误数量
     */
    public int getErrorCount() {
        return errors.size() + symbolTableManager.getErrors().size();
    }
    
    /**
     * 获取所有错误
     */
    public List<SemanticError> getErrors() {
        List<SemanticError> allErrors = new ArrayList<>(errors);
        allErrors.addAll(symbolTableManager.getErrors());
        return allErrors;
    }
    
    // ==================== AST 访问者方法 ====================
    
    @Override
    public Void visitProgram(ProgramNode node) {
        // 分析所有声明
        for (ASTNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
        return null;
    }
    
    @Override
    public Void visitStructDeclaration(StructDeclarationNode node) {
        String structName = node.getStructName();
        
        // 检查结构体重定义
        if (symbolTableManager.isDefinedInCurrentScope(structName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION,
                "结构体 '" + structName + "' 已定义",
                structName,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return null;
        }
        
        // 创建结构体定义符号
        SymbolEntry structEntry = new SymbolEntry(
            structName,
            SymbolType.STRUCT_DEFINITION,
            DataType.STRUCT,
            symbolTableManager.getCurrentScopeLevel(),
            SymbolKind.GLOBAL
        );
        
        // 创建结构体信息
        StructInfo structInfo = new StructInfo(structName);
        
        // 进入结构体作用域
        symbolTableManager.enterScope();
        
        // 分析结构体成员
        for (FieldDeclarationNode field : node.getFields()) {
            field.accept(this);
            
            // 添加成员到结构体信息
            String fieldName = field.getFieldName();
            DataType fieldType = field.getType().getDataType();
            
            SymbolEntry fieldEntry = new SymbolEntry(
                fieldName,
                SymbolType.VARIABLE,
                fieldType,
                symbolTableManager.getCurrentScopeLevel(),
                SymbolKind.STRUCT_MEMBER
            );
            
            structInfo.addField(fieldName, fieldEntry);
        }
        
        // 退出结构体作用域
        symbolTableManager.exitScope();
        
        // 设置结构体信息
        structEntry.setStructInfo(structInfo);
        
        // 插入结构体定义
        symbolTableManager.insertSymbol(structEntry);
        
        return null;
    }
    
    @Override
    public Void visitFieldDeclaration(FieldDeclarationNode node) {
        // 检查字段重定义
        String fieldName = node.getFieldName();
        if (symbolTableManager.isDefinedInCurrentScope(fieldName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION,
                "结构体成员 '" + fieldName + "' 已定义",
                fieldName,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return null;
        }
        
        // 创建字段符号
        SymbolEntry fieldEntry = new SymbolEntry(
            fieldName,
            SymbolType.VARIABLE,
            node.getType().getDataType(),
            symbolTableManager.getCurrentScopeLevel(),
            SymbolKind.STRUCT_MEMBER
        );
        
        symbolTableManager.insertSymbol(fieldEntry);
        return null;
    }
    
    @Override
    public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
        String functionName = node.getFunctionName();
        DataType returnType = node.getReturnType().getDataType();
        
        // 检查函数重定义
        if (symbolTableManager.isDefinedInCurrentScope(functionName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION,
                "函数 '" + functionName + "' 已定义",
                functionName,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
            return null;
        }
        
        // 创建函数符号
        SymbolEntry functionEntry = new SymbolEntry(
            functionName,
            SymbolType.FUNCTION,
            returnType,
            symbolTableManager.getCurrentScopeLevel(),
            SymbolKind.GLOBAL
        );
        
        // 创建函数信息
        FunctionInfo functionInfo = new FunctionInfo(returnType);
        
        // 进入函数作用域
        symbolTableManager.enterScope();
        currentFunctionReturnType = returnType;
        
        // 分析参数
        for (ParameterNode param : node.getParameters()) {
            param.accept(this);
            
            // 添加参数到函数信息
            SymbolEntry paramEntry = new SymbolEntry(
                param.getParameterName(),
                SymbolType.PARAMETER,
                param.getType().getDataType(),
                symbolTableManager.getCurrentScopeLevel(),
                SymbolKind.PARAMETER
            );
            
            functionInfo.addParameter(paramEntry);
            symbolTableManager.insertSymbol(paramEntry);
        }
        
        // 分析函数体
        node.getBody().accept(this);
        
        // 标记函数已定义
        functionInfo.setDefined(true);
        
        // 退出函数作用域
        symbolTableManager.exitScope();
        currentFunctionReturnType = DataType.VOID;
        
        // 设置函数信息
        functionEntry.setFunctionInfo(functionInfo);
        
        // 插入函数定义
        symbolTableManager.insertSymbol(functionEntry);
        
        return null;
    }
    
    @Override
    public Void visitParameter(ParameterNode node) {
        // 参数已经在函数声明中处理
        return null;
    }
    
    @Override
    public Void visitVariableDeclaration(VariableDeclarationNode node) {
        DataType variableType = node.getType().getDataType();
        
        // 分析每个声明符
        for (VariableDeclaratorNode declarator : node.getDeclarators()) {
            String variableName = declarator.getVariableName();
            
            // 检查变量重定义
            if (symbolTableManager.isDefinedInCurrentScope(variableName)) {
                addError(new SemanticError(
                    SemanticErrorType.REDEFINITION,
                    "变量 '" + variableName + "' 已定义",
                    variableName,
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
                continue;
            }
            
            // 创建变量符号
            SymbolEntry variableEntry = new SymbolEntry(
                variableName,
                SymbolType.VARIABLE,
                variableType,
                symbolTableManager.getCurrentScopeLevel(),
                SymbolKind.LOCAL
            );
            
            // 处理数组类型
            if (declarator.getArrayDimensions() != null && declarator.getArrayDimensions().length > 0) {
                ArrayInfo arrayInfo = new ArrayInfo(variableType, declarator.getArrayDimensions());
                variableEntry.setArrayInfo(arrayInfo);
                variableEntry.setSymbolType(SymbolType.VARIABLE);
                variableEntry.setDataType(DataType.ARRAY);
            }
            
            // 处理初始化
            if (declarator.getInitializer() != null) {
                // 分析初始化表达式
                DataType initType = analyzeExpression(declarator.getInitializer());
                
                // 检查类型兼容性
                if (!TypeChecker.isAssignmentCompatible(variableType, initType)) {
                    addError(new SemanticError(
                        SemanticErrorType.INCOMPATIBLE_ASSIGNMENT,
                        "初始化表达式类型不兼容",
                        variableName,
                        symbolTableManager.getCurrentScopeLevel(),
                        node.getLine(),
                        node.getColumn()
                    ));
                }
                
                variableEntry.getRuntimeInfo().setInitialized(true);
            }
            
            symbolTableManager.insertSymbol(variableEntry);
        }
        
        return null;
    }
    
    @Override
    public Void visitVariableDeclarator(VariableDeclaratorNode node) {
        // 变量声明符已经在变量声明中处理
        return null;
    }
    
    @Override
    public Void visitBlock(BlockNode node) {
        // 进入新的作用域
        symbolTableManager.enterScope();
        
        // 分析所有语句
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        
        // 退出作用域
        symbolTableManager.exitScope();
        
        return null;
    }
    
    @Override
    public Void visitExpressionStatement(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            analyzeExpression(node.getExpression());
        }
        return null;
    }
    
    @Override
    public Void visitIfStatement(IfStatementNode node) {
        // 分析条件表达式
        DataType conditionType = analyzeExpression(node.getCondition());
        
        // 检查条件表达式类型
        if (!TypeChecker.isControlExpressionValid(conditionType)) {
            addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "if语句条件表达式类型错误",
                null,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        // 分析 then 语句
        node.getThenStatement().accept(this);
        
        // 分析 else 语句
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }
        
        return null;
    }
    
    @Override
    public Void visitWhileStatement(WhileStatementNode node) {
        // 分析条件表达式
        DataType conditionType = analyzeExpression(node.getCondition());
        
        // 检查条件表达式类型
        if (!TypeChecker.isControlExpressionValid(conditionType)) {
            addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "while语句条件表达式类型错误",
                null,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        // 设置循环上下文
        boolean oldInLoopContext = inLoopContext;
        inLoopContext = true;
        
        // 分析循环体
        node.getBody().accept(this);
        
        // 恢复循环上下文
        inLoopContext = oldInLoopContext;
        
        return null;
    }
    
    @Override
    public Void visitForStatement(ForStatementNode node) {
        // 分析初始化
        if (node.getInitialization() != null) {
            node.getInitialization().accept(this);
        }
        
        // 分析条件表达式
        if (node.getCondition() != null) {
            DataType conditionType = analyzeExpression(node.getCondition());
            
            // 检查条件表达式类型
            if (!TypeChecker.isControlExpressionValid(conditionType)) {
                addError(new SemanticError(
                    SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                    "for语句条件表达式类型错误",
                    null,
                    symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(),
                    node.getColumn()
                ));
            }
        }
        
        // 分析更新表达式
        if (node.getUpdate() != null) {
            analyzeExpression(node.getUpdate());
        }
        
        // 设置循环上下文
        boolean oldInLoopContext = inLoopContext;
        inLoopContext = true;
        
        // 分析循环体
        node.getBody().accept(this);
        
        // 恢复循环上下文
        inLoopContext = oldInLoopContext;
        
        return null;
    }
    
    @Override
    public Void visitSwitchStatement(SwitchStatementNode node) {
        // 分析 switch 表达式
        DataType switchType = analyzeExpression(node.getExpression());
        
        // 设置 switch 上下文
        boolean oldInSwitchContext = inSwitchContext;
        inSwitchContext = true;
        
        // 分析所有 case
        for (CaseStatementNode caseNode : node.getCases()) {
            caseNode.accept(this);
            
            // 检查 case 值类型
            DataType caseType = analyzeExpression(caseNode.getValue());
            if (!TypeChecker.isCompatible(switchType, caseType)) {
                addError(new SemanticError(
                    SemanticErrorType.SWITCH_CASE_TYPE_MISMATCH,
                    "case常量类型与switch表达式类型不匹配",
                    null,
                    symbolTableManager.getCurrentScopeLevel(),
                    caseNode.getLine(),
                    caseNode.getColumn()
                ));
            }
        }
        
        // 分析 default case
        if (node.getDefaultCase() != null) {
            node.getDefaultCase().accept(this);
        }
        
        // 恢复 switch 上下文
        inSwitchContext = oldInSwitchContext;
        
        return null;
    }
    
    @Override
    public Void visitCaseStatement(CaseStatementNode node) {
        // 分析 case 值
        analyzeExpression(node.getValue());
        
        // 分析 case 语句
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        
        return null;
    }
    
    @Override
    public Void visitDefaultStatement(DefaultStatementNode node) {
        // 分析 default 语句
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        
        return null;
    }
    
    @Override
    public Void visitBreakStatement(BreakStatementNode node) {
        // 检查 break 语句是否在循环或 switch 中
        if (!inLoopContext && !inSwitchContext) {
            addError(new SemanticError(
                SemanticErrorType.BREAK_CONTINUE_OUTSIDE_LOOP,
                "break语句不在循环或switch语句中",
                null,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        return null;
    }
    
    @Override
    public Void visitContinueStatement(ContinueStatementNode node) {
        // 检查 continue 语句是否在循环中
        if (!inLoopContext) {
            addError(new SemanticError(
                SemanticErrorType.BREAK_CONTINUE_OUTSIDE_LOOP,
                "continue语句不在循环语句中",
                null,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        return null;
    }
    
    @Override
    public Void visitReturnStatement(ReturnStatementNode node) {
        DataType returnType = DataType.VOID;
        
        // 分析返回表达式
        if (node.getExpression() != null) {
            returnType = analyzeExpression(node.getExpression());
        }
        
        // 检查返回类型
        if (!TypeChecker.isCompatible(currentFunctionReturnType, returnType)) {
            addError(new SemanticError(
                SemanticErrorType.RETURN_TYPE_MISMATCH,
                "返回类型与函数声明不匹配",
                null,
                symbolTableManager.getCurrentScopeLevel(),
                node.getLine(),
                node.getColumn()
            ));
        }
        
        return null;
    }
    
    // ==================== 表达式分析方法 ====================
    
    /**
     * 分析表达式并返回其类型
     */
    private DataType analyzeExpression(ExpressionNode expression) {
        if (expression == null) {
            return DataType.VOID;
        }
        
        return expression.accept(new ExpressionTypeAnalyzer());
    }
    
    /**
     * 检查 main 函数
     */
    private void checkMainFunction() {
        SymbolEntry mainFunction = symbolTableManager.lookupSymbol("main");
        
        if (mainFunction == null) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "缺少main函数",
                "main",
                symbolTableManager.getCurrentScopeLevel()
            ));
        } else if (mainFunction.getSymbolType() != SymbolType.FUNCTION) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "main不是函数",
                "main",
                symbolTableManager.getCurrentScopeLevel()
            ));
        } else if (mainFunction.getDataType() != DataType.INT) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "main函数返回类型应为int",
                "main",
                symbolTableManager.getCurrentScopeLevel()
            ));
        }
    }
    
    // ==================== 其他访问者方法 ====================
    
    @Override
    public Void visitAssignmentExpression(AssignmentExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitConditionalExpression(ConditionalExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitLogicalOrExpression(LogicalOrExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitLogicalAndExpression(LogicalAndExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitEqualityExpression(EqualityExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitRelationalExpression(RelationalExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitAdditiveExpression(AdditiveExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitUnaryExpression(UnaryExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitPostfixExpression(PostfixExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitPrimaryExpression(PrimaryExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitCastExpression(CastExpressionNode node) {
        return null;
    }
    
    @Override
    public Void visitIdentifier(IdentifierNode node) {
        return null;
    }
    
    @Override
    public Void visitIntLiteral(IntLiteralNode node) {
        return null;
    }
    
    @Override
    public Void visitFloatLiteral(FloatLiteralNode node) {
        return null;
    }
    
    @Override
    public Void visitCharLiteral(CharLiteralNode node) {
        return null;
    }
    
    @Override
    public Void visitStringLiteral(StringLiteralNode node) {
        return null;
    }
    
    @Override
    public Void visitFunctionCall(FunctionCallNode node) {
        return null;
    }
    
    @Override
    public Void visitArrayAccess(ArrayAccessNode node) {
        return null;
    }
    
    @Override
    public Void visitMemberAccess(MemberAccessNode node) {
        return null;
    }
    
    @Override
    public Void visitType(TypeNode node) {
        return null;
    }
}
