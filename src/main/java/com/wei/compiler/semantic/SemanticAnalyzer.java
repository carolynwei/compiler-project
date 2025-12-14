package com.wei.compiler.semantic;

import com.wei.compiler.ast.*;
import com.wei.compiler.type.DataType;
import java.util.*;

/**
 * 语义分析器
 * * 实现完整的语义分析，包括符号表管理和类型检查。
 * 假设：
 * 1. TypeAnalyzer, SymbolTableManager, DataType, TypeChecker, TypeHelper 已在项目中实现。
 * 2. TypeChecker.isCompatible(), TypeChecker.isControlExpressionValid(), 
 * TypeChecker.isNumericType(), TypeChecker.isIntCompatible(),
 * TypeChecker.getConstantIntValue() 等方法已实现。
 */
public class SemanticAnalyzer implements ASTVisitor<Void> {
    
    private SymbolTableManager symbolTableManager;
    private TypeAnalyzer typeAnalyzer;
    private boolean debugMode;
    
    // 当前分析的上下文
    private DataType currentFunctionReturnType;
    private boolean inLoopContext; // 循环上下文 (for, while, do-while)
    private boolean inSwitchContext; // switch 上下文
    
    // 用于检查 switch 语句中的 case 常量是否重复
    private Set<Long> caseConstants;

    public SemanticAnalyzer(SymbolTableManager symbolTableManager, TypeAnalyzer typeAnalyzer) {
        this.symbolTableManager = symbolTableManager;
        this.typeAnalyzer = typeAnalyzer;
        this.debugMode = false;
        this.currentFunctionReturnType = DataType.VOID;
        this.inLoopContext = false;
        this.inSwitchContext = false;
        this.caseConstants = new HashSet<>();
    }
    
    // 简化构造函数，以便在调用者中初始化 TypeAnalyzer
    public SemanticAnalyzer() {
        // 假设 TypeAnalyzer 和 SymbolTableManager 可以在这里实例化
        // 实际项目中可能需要从外部传入
        this.symbolTableManager = new SymbolTableManager();
        this.typeAnalyzer = new TypeAnalyzer(symbolTableManager);
        this.debugMode = false;
        this.currentFunctionReturnType = DataType.VOID;
        this.inLoopContext = false;
        this.inSwitchContext = false;
        this.caseConstants = new HashSet<>();
    }
    
    /**
     * 分析 AST
     */
    public void analyze(ASTNode ast) {
        System.out.println("\n--- 阶段二：语义分析 ---");
        
        symbolTableManager.setDebugMode(debugMode);
        ast.accept(this);
        
        // 收集类型分析的错误
        for (SemanticError error : typeAnalyzer.getErrors()) {
            symbolTableManager.addSemanticError(error);
        }
        
        checkMainFunction();
        
        if (debugMode) {
            symbolTableManager.displaySymbolTable();
        }
        
        symbolTableManager.printErrors();
        
        if (hasErrors()) {
            System.err.println("语义分析失败，发现 " + getErrorCount() + " 个错误");
        } else {
            System.out.println("语义分析通过");
        }
    }
    
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    public SymbolTableManager getSymbolTableManager() {
        return symbolTableManager;
    }
    
    private void addError(SemanticError error) {
        symbolTableManager.addSemanticError(error);
    }
    
    public boolean hasErrors() {
        return symbolTableManager.hasErrors();
    }
    
    public int getErrorCount() {
        return symbolTableManager.getErrors().size();
    }
    
    public List<SemanticError> getErrors() {
        return symbolTableManager.getErrors();
    }
    
    public DataType analyzeExpression(ExpressionNode expr) {
        if (expr == null) {
            return DataType.VOID;
        }
        
        DataType type = typeAnalyzer.analyzeExpression(expr);
        if (type != null) {
            expr.setDataType(type);
        }
        return type;
    }

    private void checkMainFunction() {
        SymbolEntry mainFunction = symbolTableManager.lookupSymbol("main");
        
        if (mainFunction == null) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "缺少main函数", "main", symbolTableManager.getCurrentScopeLevel()
            ));
        } else if (mainFunction.getSymbolType() != SymbolType.FUNCTION) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "main不是函数", "main", symbolTableManager.getCurrentScopeLevel()
            ));
        } else if (mainFunction.getDataType() != DataType.INT) {
            addError(new SemanticError(
                SemanticErrorType.MAIN_FUNCTION_MISSING,
                "main函数返回类型应为int", "main", symbolTableManager.getCurrentScopeLevel()
            ));
        }
    }

    // ==================== AST 访问者方法 ====================
    
    @Override
    public Void visitProgram(ProgramNode node) {
        for (ASTNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
        return null;
    }
    
    @Override
    public Void visitStructDeclaration(StructDeclarationNode node) {
        String structName = node.getStructName();
        
        if (symbolTableManager.isDefinedInCurrentScope(structName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION, "结构体 '" + structName + "' 已定义", 
                structName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
            return null;
        }
        
        StructInfo structInfo = new StructInfo(structName);
        DataType structType = TypeHelper.createStructType(structName, structInfo);
        
        SymbolEntry structEntry = new SymbolEntry(
            structName, SymbolType.STRUCT_DEFINITION, structType,
            symbolTableManager.getCurrentScopeLevel(), SymbolKind.GLOBAL
        );
        
        symbolTableManager.enterScope();
        
        for (FieldDeclarationNode field : node.getFields()) {
            field.accept(this);
            SymbolEntry fieldEntry = symbolTableManager.lookupSymbol(field.getDeclarator().getName());
            if (fieldEntry != null) {
                structInfo.addField(fieldEntry.getName(), fieldEntry);
            }
        }
        
        // 新增：检查结构体是否为空
        if (structInfo.getFieldCount() == 0) {
            addError(new SemanticError(
                SemanticErrorType.STRUCT_SIZE_ZERO,
                "结构体 '" + structName + "' 为空（没有字段）",
                structName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        symbolTableManager.exitScope();
        
        structEntry.setStructInfo(structInfo);
        symbolTableManager.insertSymbol(structEntry);
        
        return null;
    }
    
    @Override
    public Void visitDoWhileStatementNode(DoWhileStatementNode node) {
        boolean oldInLoopContext = inLoopContext;
        inLoopContext = true;

        node.getBody().accept(this);
        DataType conditionType = this.analyzeExpression(node.getCondition());

        if (!TypeChecker.isControlExpressionValid(conditionType)) {
            addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "do-while语句条件表达式类型错误", null, symbolTableManager.getCurrentScopeLevel(), 
                node.getLine(), node.getColumn()
            ));
        }

        inLoopContext = oldInLoopContext;
        return null;
    }

    @Override
    public Void visitFieldDeclaration(FieldDeclarationNode node) {
        node.getType().accept(this); 
        DeclaratorNode declarator = node.getDeclarator();
        declarator.accept(this); 
        String fieldName = declarator.getName();

        // ⚠️ 假设 TypeHelper.constructComplexType 已实现
        DataType finalFieldType = TypeHelper.constructComplexType(
            node.getType().getDataType(), declarator, this.getErrors()
        );
        
        if (symbolTableManager.isDefinedInCurrentScope(fieldName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION, "结构体成员 '" + fieldName + "' 已定义", 
                fieldName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
            return null;
        }
        
        SymbolEntry fieldEntry = new SymbolEntry(
            fieldName, SymbolType.VARIABLE, finalFieldType, 
            symbolTableManager.getCurrentScopeLevel(), SymbolKind.STRUCT_MEMBER
        );
        
        symbolTableManager.insertSymbol(fieldEntry);
        return null;
    }
    
    @Override
    public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
        String functionName = node.getFunctionName();
        DataType returnType = node.getReturnType().getDataType();
        
        if (symbolTableManager.isDefinedInCurrentScope(functionName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION, "函数 '" + functionName + "' 已定义", 
                functionName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
            return null;
        }
        
        SymbolEntry functionEntry = new SymbolEntry(
            functionName, SymbolType.FUNCTION, returnType,
            symbolTableManager.getCurrentScopeLevel(), SymbolKind.GLOBAL
        );
        FunctionInfo functionInfo = new FunctionInfo(returnType);
        functionEntry.setFunctionInfo(functionInfo);
        
        symbolTableManager.insertSymbol(functionEntry);
        
        symbolTableManager.enterScope();
        DataType oldFunctionReturnType = currentFunctionReturnType;
        currentFunctionReturnType = returnType;
        
        for (ParameterNode param : node.getParameters()) {
            param.accept(this);
            
            DeclaratorNode declarator = param.getDeclarator();
            String paramName = declarator.getName(); 
            
            // ⚠️ 假设 TypeHelper.constructComplexType 已实现
            DataType baseType = param.getType().getDataType();
            DataType finalParamType = TypeHelper.constructComplexType(
                baseType, declarator, this.getErrors() 
            );
            
            // 🔥 调试输出：检查参数类型是否正确构造
            // System.out.println("[DEBUG] Parameter: " + paramName + ", baseType=" + baseType + ", finalParamType=" + finalParamType + 
            //     ", arrayDims=" + (declarator.getArrayDimensions() == null ? "null" : declarator.getArrayDimensions().length));
            
            // 🔥 关键修复：为参数正确设置数组维度信息
            // 参数 arr 应该是 int[]，即使在函数签名中声明为 int arr[]
            SymbolEntry paramEntry = new SymbolEntry(
                paramName, SymbolType.PARAMETER, finalParamType,
                symbolTableManager.getCurrentScopeLevel(), SymbolKind.PARAMETER
            );
            
            functionInfo.addParameter(paramEntry);
            symbolTableManager.insertSymbol(paramEntry);
        }
        
        // 处理函数体：如果是 BlockNode，不要让它创建新作用域
        StatementNode functionBody = node.getBody();
        if (functionBody instanceof BlockNode) {
            BlockNode blockBody = (BlockNode) functionBody;
            StatementNode[] statements = blockBody.getStatements();
            boolean previousPathTerminated = false;
            for (int i = 0; i < statements.length; i++) {
                StatementNode statement = statements[i];
                
                if (previousPathTerminated) {
                    addError(new SemanticError(
                        SemanticErrorType.UNREACHABLE_CODE,
                        "不可达的代码",
                        null, symbolTableManager.getCurrentScopeLevel(), statement.getLine(), statement.getColumn()
                    ));
                }
                
                statement.accept(this);
                
                if (statement instanceof ReturnStatementNode) {
                    previousPathTerminated = true;
                }
            }
        } else {
            functionBody.accept(this);
        }
        
        functionInfo.setDefined(true);
        
        symbolTableManager.exitScope();
        currentFunctionReturnType = oldFunctionReturnType;
        
        return null;
    }
    
    @Override
    public Void visitParameter(ParameterNode node) {
        node.getType().accept(this);
        DeclaratorNode declarator = node.getDeclarator();
        declarator.accept(this); 
        String paramName = declarator.getName();
        
        // 新增：检查参数类型不能为void
        DataType paramType = node.getType().getDataType();
        if (paramType == DataType.VOID) {
            addError(new SemanticError(
                SemanticErrorType.VOID_PARAMETER_TYPE,
                "参数类型不能为void",
                paramName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        if (symbolTableManager.isDefinedInCurrentScope(paramName)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION, "参数 '" + paramName + "' 已定义", 
                paramName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        // 构造 SymbolEntry 的逻辑已移至 visitFunctionDeclaration
        return null;
    }
    
    @Override
    public Void visitVariableDeclaration(VariableDeclarationNode node) {
        node.getType().accept(this);
        DataType baseType = node.getType().getDataType();
        
        // 🔥 关键修复：如果基础类型是结构体，需要从符号表查找其 StructInfo
        if (baseType instanceof com.wei.compiler.type.StructType) {
            com.wei.compiler.type.StructType structType = (com.wei.compiler.type.StructType) baseType;
            if (structType.getStructInfo() == null) {
                // 尝试从符号表查找该结构体的定义
                SymbolEntry structEntry = symbolTableManager.lookupSymbolWithoutError(structType.getName());
                if (structEntry != null && structEntry.getStructInfo() != null) {
                    baseType = new com.wei.compiler.type.StructType(structType.getName(), structEntry.getStructInfo());
                }
            }
        }
        
        for (VariableDeclaratorNode declaratorNode : node.getDeclarators()) {
            declaratorNode.accept(this);
            DeclaratorNode declarator = declaratorNode.getDeclarator();
            declarator.accept(this);
            String variableName = declarator.getName();
            
            // 构造复杂类型
            DataType finalVariableType = TypeHelper.constructComplexType(
                baseType, declarator, this.getErrors()
            );
            
            if (symbolTableManager.isDefinedInCurrentScope(variableName)) {
                addError(new SemanticError(
                    SemanticErrorType.REDEFINITION, "变量 '" + variableName + "' 已定义", 
                    variableName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
                ));
                continue;
            }
            
            SymbolKind kind = symbolTableManager.getCurrentScopeLevel() == 0 ? 
                              SymbolKind.GLOBAL : SymbolKind.LOCAL;
            SymbolEntry variableEntry = new SymbolEntry(
                variableName, SymbolType.VARIABLE, finalVariableType,
                symbolTableManager.getCurrentScopeLevel(), kind
            );
            
            if (declaratorNode.getInitializer() != null) {
                DataType initType = this.analyzeExpression(declaratorNode.getInitializer());
                
                // 🔥 修复：现在 TypeHelper.isCompatible 已支持结构体值赋值和数组到指针转换
                if (!TypeHelper.isCompatible(finalVariableType, initType)) {
                    addError(new SemanticError(
                        SemanticErrorType.INCOMPATIBLE_ASSIGNMENT,
                        "初始化表达式类型不兼容: 无法将 '" + initType + "' 赋值给 '" + finalVariableType + "'",
                        variableName, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
                    ));
                }
                variableEntry.getRuntimeInfo().setInitialized(true);
            }
            
            symbolTableManager.insertSymbol(variableEntry);
        }
        
        return null;
    }

    /**
     * 【修正】处理变量声明中的 VariableDeclaratorNode
     */
    @Override
    public Void visitVariableDeclarator(VariableDeclaratorNode node) {
        // 注意：由于ASTNode没有getParent方法，我们需要在visitVariableDeclaration中处理VariableDeclaratorNode
        // 这里留空，实际处理在visitVariableDeclaration中完成
        return null;
    }
    
    @Override
    public Void visitDeclaratorNode(DeclaratorNode node) {
        for (ExpressionNode dimExpr : node.getArrayDimensions()) {
            // 🔥 跳过 null 维度（简化参数数组处理）
            // 对于 arr[] 的参数，维度为 null，不需要验证
            if (dimExpr == null) {
                continue;
            }
            
            DataType dimType = this.analyzeExpression(dimExpr);
            // ⚠️ 假设 TypeHelper.isIntCompatible 已实现
            if (!TypeHelper.isIntCompatible(dimType)) {
                addError(new SemanticError(
                    SemanticErrorType.INVALID_ARRAY_DIMENSION,
                    "数组维度必须是整数类型", null, symbolTableManager.getCurrentScopeLevel(),
                    node.getLine(), node.getColumn()
                ));
            }
        }
        return null;
    }

    @Override
    public Void visitTypeNameNode(TypeNameNode node) {
        node.getBaseType().accept(this);
        return null;
    }

    @Override
    public Void visitBlock(BlockNode node) {
        symbolTableManager.enterScope();
        
        boolean previousPathTerminated = false;
        StatementNode[] statements = node.getStatements();
        for (int i = 0; i < statements.length; i++) {
            StatementNode statement = statements[i];
            
            // 检查是否是不可达的代码
            if (previousPathTerminated) {
                addError(new SemanticError(
                    SemanticErrorType.UNREACHABLE_CODE,
                    "不可达的代码",
                    null, symbolTableManager.getCurrentScopeLevel(), statement.getLine(), statement.getColumn()
                ));
            }
            
            statement.accept(this);
            
            // 检查是否是控制流终止的语句
            if (statement instanceof ReturnStatementNode || statement instanceof BreakStatementNode) {
                previousPathTerminated = true;
            }
        }
        
        symbolTableManager.exitScope();
        return null;
    }
    
    @Override
    public Void visitExpressionStatement(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            this.analyzeExpression(node.getExpression());
        }
        return null;
    }
    
    @Override
    public Void visitIfStatement(IfStatementNode node) {
        DataType conditionType = this.analyzeExpression(node.getCondition());
        if (!TypeHelper.isNumericType(conditionType)) {
            addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "if语句条件表达式类型错误", null, symbolTableManager.getCurrentScopeLevel(), 
                node.getLine(), node.getColumn()
            ));
        }
        node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }
        return null;
    }
    
    @Override
    public Void visitWhileStatement(WhileStatementNode node) {
        DataType conditionType = this.analyzeExpression(node.getCondition());
        if (!TypeHelper.isNumericType(conditionType)) {
            addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "while语句条件表达式类型错误", null, symbolTableManager.getCurrentScopeLevel(), 
                node.getLine(), node.getColumn()
            ));
        }
        boolean oldInLoopContext = inLoopContext;
        inLoopContext = true;
        node.getBody().accept(this);
        inLoopContext = oldInLoopContext;
        return null;
    }
    
    @Override
    public Void visitForStatement(ForStatementNode node) {
        // ✅ for 循环创建新作用域（C99 标准）
        // 初始化、条件、更新都在这个作用域中
        symbolTableManager.enterScope();
        
        if (node.getInitialization() != null) {
            node.getInitialization().accept(this);
        }
        if (node.getCondition() != null) {
            DataType conditionType = this.analyzeExpression(node.getCondition());
            if (!TypeHelper.isNumericType(conditionType)) {
                addError(new SemanticError(
                    SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                    "for语句条件表达式类型错误", null, symbolTableManager.getCurrentScopeLevel(), 
                    node.getLine(), node.getColumn()
                ));
            }
        }
        if (node.getUpdate() != null) {
            this.analyzeExpression(node.getUpdate());
        }
        boolean oldInLoopContext = inLoopContext;
        inLoopContext = true;
        
        // 处理循环体：如果是 BlockNode，不要让它创建新作用域
        StatementNode body = node.getBody();
        if (body instanceof BlockNode) {
            BlockNode blockBody = (BlockNode) body;
            StatementNode[] statements = blockBody.getStatements();
            boolean previousPathTerminated = false;
            for (int i = 0; i < statements.length; i++) {
                StatementNode statement = statements[i];
                
                if (previousPathTerminated) {
                    addError(new SemanticError(
                        SemanticErrorType.UNREACHABLE_CODE,
                        "不可达的代码",
                        null, symbolTableManager.getCurrentScopeLevel(), statement.getLine(), statement.getColumn()
                    ));
                }
                
                statement.accept(this);
                
                if (statement instanceof ReturnStatementNode || statement instanceof BreakStatementNode) {
                    previousPathTerminated = true;
                }
            }
        } else {
            body.accept(this);
        }
        
        inLoopContext = oldInLoopContext;
        symbolTableManager.exitScope();
        return null;
    }
    
    /**
     * 【修正】Switch 语句访问方法
     */
    @Override
    public Void visitSwitchStatement(SwitchStatementNode node) {
        DataType switchType = this.analyzeExpression(node.getExpression());
        
        // 1. 检查 switch 表达式类型
        if (!TypeHelper.isNumericType(switchType)) { // ⚠️ 假设 TypeHelper.isNumericType 已实现
             addError(new SemanticError(
                SemanticErrorType.CONTROL_EXPRESSION_TYPE_ERROR,
                "switch 表达式必须是整数类型 (int, char)", null, 
                symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        // 2. 设置 switch 上下文并初始化 case 检查
        boolean oldInSwitchContext = inSwitchContext;
        inSwitchContext = true;
        this.caseConstants.clear(); // 清空当前 switch 语句的 case 集合
        boolean defaultFound = false;

        // 3. 分析 switch 语句体 (通常是 BlockNode，其中包含 CaseStatementNode 和 DefaultStatementNode)
        // 遍历所有 case 语句
        for (CaseStatementNode caseNode : node.getCases()) {
            if (caseNode instanceof CaseStatementNode) {
                visitCaseStatementInternal(caseNode, switchType);
            }
        }
        
        // 处理 default 语句
        if (node.getDefaultCase() != null) {
            visitDefaultStatementInternal(node.getDefaultCase(), defaultFound);
            defaultFound = true;
        }
        
        // 4. 恢复 switch 上下文
        inSwitchContext = oldInSwitchContext;
        this.caseConstants.clear(); // 释放资源
        
        return null;
    }
    
    /**
     * 【内部实现】处理 Case 语句的复杂语义
     */
    private void visitCaseStatementInternal(CaseStatementNode node, DataType switchType) {
        DataType caseType = this.analyzeExpression(node.getValue());
        
        // 检查 case 值是否是常量表达式 (这里简化为检查 LiteralNode)
        if (!(node.getValue() instanceof LiteralNode)) {
             addError(new SemanticError(
                SemanticErrorType.EXPECTED_CONSTANT_EXPRESSION,
                "case 标签必须是常量表达式", null, symbolTableManager.getCurrentScopeLevel(),
                node.getLine(), node.getColumn()
            ));
        }
        
        // 检查 case 值类型兼容性
        if (!TypeHelper.isCompatible(switchType, caseType)) { // ⚠️ 假设 TypeHelper.isCompatible 已实现
             addError(new SemanticError(
                SemanticErrorType.SWITCH_CASE_TYPE_MISMATCH,
                "case 常量类型 ('" + caseType + "') 与 switch 表达式类型 ('" + switchType + "') 不匹配",
                null, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        } else {
             // 检查 case 常量重复 (需要 TypeHelper 能够计算常量值)
             Long constantValue = TypeHelper.getConstantIntValue(node.getValue()); // ⚠️ 假设 TypeHelper.getConstantIntValue 已实现
             if (constantValue != null) {
                 if (caseConstants.contains(constantValue)) {
                     addError(new SemanticError(
                        SemanticErrorType.REPEATED_CASE_LABEL,
                        "重复的 case 常量值: " + constantValue,
                        null, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
                    ));
                 } else {
                     caseConstants.add(constantValue);
                 }
             }
        }
        
        // 分析 case 语句
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
    }
    
    /**
     * 【内部实现】处理 Default 语句的复杂语义
     */
    private void visitDefaultStatementInternal(DefaultStatementNode node, boolean defaultFound) {
         if (defaultFound) {
             addError(new SemanticError(
                SemanticErrorType.REPEATED_DEFAULT_LABEL,
                "重复的 default 标签", null, symbolTableManager.getCurrentScopeLevel(),
                node.getLine(), node.getColumn()
            ));
         }
         
         // 分析 default 语句
         for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
    }

    /**
     * 【仅为兼容保留】访问 Case 语句（实际访问逻辑在 visitSwitchStatement 中处理）
     */
    @Override
    public Void visitCaseStatement(CaseStatementNode node) {
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        return null;
    }
    
    /**
     * 【仅为兼容保留】访问 Default 语句（实际访问逻辑在 visitSwitchStatement 中处理）
     */
    @Override
    public Void visitDefaultStatement(DefaultStatementNode node) {
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
                "break 语句不在循环或 switch 语句中", null, symbolTableManager.getCurrentScopeLevel(),
                node.getLine(), node.getColumn()
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
                "continue 语句不在循环语句中", null, symbolTableManager.getCurrentScopeLevel(),
                node.getLine(), node.getColumn()
            ));
        }
        return null;
    }
    
    @Override
    public Void visitReturnStatement(ReturnStatementNode node) {
        DataType returnedType = DataType.VOID;
        
        if (node.getExpression() != null) {
            returnedType = typeAnalyzer.analyzeExpression(node.getExpression());
        }
        
        // 新增：检查void函数是否返回value
        if (currentFunctionReturnType == DataType.VOID && node.getExpression() != null) {
            addError(new SemanticError(
                SemanticErrorType.VOID_FUNCTION_RETURN_VALUE,
                "void函数不应该返回值",
                null, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        // 检查返回类型
        if (!TypeHelper.isCompatible(currentFunctionReturnType, returnedType)) {
            addError(new SemanticError(
                SemanticErrorType.RETURN_TYPE_MISMATCH,
                "返回类型 ('" + returnedType + "') 与函数声明的返回类型 ('" + currentFunctionReturnType + "') 不匹配",
                null, symbolTableManager.getCurrentScopeLevel(), node.getLine(), node.getColumn()
            ));
        }
        
        return null;
    }

    // ==================== 其他表达式访问方法 (委托给 TypeAnalyzer) ====================
    // 这些方法保持不变
    @Override public Void visitBitwiseExpressionNode(BitwiseExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitShiftExpressionNode(ShiftExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitAssignmentExpression(AssignmentExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitConditionalExpression(ConditionalExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitLogicalOrExpression(LogicalOrExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitLogicalAndExpression(LogicalAndExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitEqualityExpression(EqualityExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitRelationalExpression(RelationalExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitAdditiveExpression(AdditiveExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitMultiplicativeExpression(MultiplicativeExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitUnaryExpression(UnaryExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitPostfixExpression(PostfixExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitPrimaryExpression(PrimaryExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitCastExpression(CastExpressionNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitIdentifier(IdentifierNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitIntLiteral(IntLiteralNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitFloatLiteral(FloatLiteralNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitCharLiteral(CharLiteralNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitStringLiteral(StringLiteralNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitFunctionCall(FunctionCallNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitArrayAccess(ArrayAccessNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitMemberAccess(MemberAccessNode node) { typeAnalyzer.analyzeExpression(node); return null; }
    @Override public Void visitType(TypeNode node) { return null; }
}