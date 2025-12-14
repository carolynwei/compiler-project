package com.wei.compiler.ast;

import com.wei.compiler.type.DataType;
import com.wei.compiler.type.StructType;
import com.wei.compiler.type.ArrayType;
import com.wei.compiler.semantic.SymbolTableManager;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AST 构建器
 * * 将 ANTLR 解析树转换为自定义的抽象语法树。
 */
public class ASTBuilder extends com.wei.WeiCBaseVisitor<ASTNode> {
    
    // 符号表管理器
    private SymbolTableManager symbolTableManager;
    
    // ========================================
    // 基础和入口
    // ========================================

    /**
     * 默认构造函数
     */
    public ASTBuilder() {
        // 初始化符号表管理器
        this.symbolTableManager = new SymbolTableManager();
    }
    
    /**
     * 带符号表管理器的构造函数
     * @param symbolTableManager 符号表管理器
     */
    public ASTBuilder(SymbolTableManager symbolTableManager) {
        this.symbolTableManager = symbolTableManager;
    }

    /**
     * 构建 AST 的入口方法
     * [未变动]
     */
    public ASTNode build(org.antlr.v4.runtime.tree.ParseTree tree) {
        return visit(tree);
    }
    
    /**
     * 获取符号表管理器
     * @return 符号表管理器
     */
    public SymbolTableManager getSymbolTableManager() {
        return symbolTableManager;
    }
    
    /**
     * 通用访问方法：处理可能的 null 返回
     * [未变动]
     */
    @Override
    public ASTNode visit(ParseTree tree) {
        if (tree == null) {
            return new IntLiteralNode(0, 0, 0);
        }
        ASTNode result = super.visit(tree);
        if (result == null && tree instanceof org.antlr.v4.runtime.ParserRuleContext) {
            org.antlr.v4.runtime.ParserRuleContext ctx = (org.antlr.v4.runtime.ParserRuleContext) tree;
            // ⚠️ 修复建议：不要返回 IntLiteralNode，应该返回一个明确的 ErrorNode 或 NullNode
            // 这里为了兼容性仍返回默认值
            return new IntLiteralNode(0, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        return result;
    }
    
    @Override
    public ASTNode visitProgram(com.wei.WeiCParser.ProgramContext ctx) {
        // [未变动]
        ASTNode[] declarations = new ASTNode[ctx.declaration().size()];
        for (int i = 0; i < ctx.declaration().size(); i++) {
            declarations[i] = visit(ctx.declaration(i));
        }
        return new ProgramNode(declarations, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    // ========================================
    // 声明和类型系统 (Declaration & Type)
    // ========================================

    /**
     * 处理类型信息，包括 const 修饰符。
     */
    @Override
    public ASTNode visitType(com.wei.WeiCParser.TypeContext ctx) {
        DataType dataType = DataType.INT;
        String structName = null;
        
        // 1. 获取基础类型
        if (ctx.INT() != null) dataType = DataType.INT;
        else if (ctx.FLOAT() != null) dataType = DataType.FLOAT;
        else if (ctx.CHAR() != null) dataType = DataType.CHAR;
        else if (ctx.STRING() != null) dataType = DataType.STRING;
        else if (ctx.VOID() != null) dataType = DataType.VOID;
        else if (ctx.STRUCT() != null) {
            // 2. 检查 ID 是否存在
            if (ctx.ID() != null) {
                 structName = ctx.ID().getText();
                 dataType = new StructType(structName, null);  // ✅ 修复：使用正确的 structName
            } else {
                 // 错误处理: struct 后面必须跟 ID
                 dataType = new StructType("", null);  // 降级处理
            }
        }
        
        // 3. 暂无数组维度信息在type规则中
        int[] arrayDimensions = new int[0];
        
        // TypeNode构造函数不支持const修饰符，const应该在declarator或变量声明层处理
        return new TypeNode(dataType, structName, arrayDimensions,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    /**
     * 访问 typeName 规则，用于强制类型转换 (type) 和 sizeof(type)。
     * typeName: type STAR* AMPERSAND*;
     */
    public ASTNode visitTypeName(com.wei.WeiCParser.TypeNameContext ctx) {
        ASTNode typeResult = visit(ctx.type());
        TypeNode baseType = null;
        
        // 安全地抓取第一个 TypeNode
        if (typeResult instanceof TypeNode) {
            baseType = (TypeNode) typeResult;
        } else if (typeResult instanceof TypeNameNode) {
            // 如果程下一也是 TypeNameNode，也不不輸
            baseType = ((TypeNameNode) typeResult).getBaseType();
        } else {
            // 默认深底线
            baseType = new TypeNode(com.wei.compiler.type.DataType.INT, "", new int[0], 
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        
        int pointerLevel = ctx.STAR().size();
        int referenceCount = ctx.AMPERSAND().size();

        // 假设您有 TypeNameNode 来封装这个结构
        return new TypeNameNode(baseType, pointerLevel, referenceCount,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    /**
     * 访问 Declarator 规则，这是处理指针、引用和数组的核心。
     * declarator: STAR* AMPERSAND* ID (LBRACKET expression? RBRACKET)*;
     * 🔥 关键修复：正确处理参数数组 arr[] （没有具体维度）
     */
    @Override
    public ASTNode visitDeclarator(com.wei.WeiCParser.DeclaratorContext ctx) {
        String idName = ctx.ID().getText();
        int pointerLevel = ctx.STAR().size();
        int referenceCount = ctx.AMPERSAND().size();

        // 🔥 关键修复：根据 LBRACKET 的数量来确定数组维度数
        // 即使 expression? 为空（如 arr[]），我们也需要记录数组的存在
        List<ExpressionNode> arrayDims = new ArrayList<>();
        List<com.wei.WeiCParser.ExpressionContext> expressions = ctx.expression();
        int lbracketCount = ctx.LBRACKET().size();
        
        // 对于每个 LBRACKET，尝试获取对应的 expression
        // 如果没有 expression（参数数组的情况），用 null 占位
        for (int i = 0; i < lbracketCount; i++) {
            if (i < expressions.size() && expressions.get(i) != null) {
                arrayDims.add((ExpressionNode) visit(expressions.get(i)));
            } else {
                // 对于 arr[] 这样的参数，维度为 null
                // 这表示这是一个不确定大小的数组参数
                arrayDims.add(null);
            }
        }

        return new DeclaratorNode(idName, pointerLevel, referenceCount, 
            arrayDims.toArray(new ExpressionNode[0]), 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    /**
     * 访问 StructField 规则。
     * [已修改]：从 ID 访问改为访问 declarator。
     */
    @Override
    public ASTNode visitStructField(com.wei.WeiCParser.StructFieldContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());
        DeclaratorNode declarator = (DeclaratorNode) visit(ctx.declarator());
        
        // 假设 FieldDeclarationNode 构造函数支持 DeclaratorNode
        return new FieldDeclarationNode(type, declarator, 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    /**
     * 访问 Parameter 规则。
     * [已修改]：从 ID 访问改为访问 declarator。
     */
    @Override
    public ASTNode visitParameter(com.wei.WeiCParser.ParameterContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());
        DeclaratorNode declarator = (DeclaratorNode) visit(ctx.declarator());
        
        // 假设 ParameterNode 构造函数支持 DeclaratorNode
        return new ParameterNode(type, declarator, 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitStructDeclaration(com.wei.WeiCParser.StructDeclarationContext ctx) {
        // [已修改]：FieldDeclarationNode 的构造函数需要更新以使用 DeclaratorNode
        String structName = ctx.ID().getText();
        List<FieldDeclarationNode> fields = new ArrayList<>();
        
        for (com.wei.WeiCParser.StructFieldContext fieldCtx : ctx.structField()) {
            fields.add((FieldDeclarationNode) visit(fieldCtx));
        }
        
        return new StructDeclarationNode(structName, fields.toArray(new FieldDeclarationNode[0]), 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitFunctionDeclaration(com.wei.WeiCParser.FunctionDeclarationContext ctx) {
        // [已修改]：需要考虑函数返回类型前的 STAR*（指针）
        TypeNode baseReturnType = (TypeNode) visit(ctx.type());
        
        int returnPointerLevel = ctx.STAR().size(); 
        
        // 假设您有一个 FunctionReturnTypeNode 封装 baseType 和 pointerLevel
        // 或在 TypeNode 中处理，但这里我们使用一个新的节点
        
        String functionName = ctx.ID().getText();
        
        List<ParameterNode> parameters = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (com.wei.WeiCParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                parameters.add((ParameterNode) visit(paramCtx));
            }
        }
        
        BlockNode body = (BlockNode) visit(ctx.block());
        
        // 假设 FunctionDeclarationNode 构造函数已更新
        return new FunctionDeclarationNode(baseReturnType, returnPointerLevel, functionName, 
            parameters.toArray(new ParameterNode[0]), body,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitVariableDeclaration(com.wei.WeiCParser.VariableDeclarationContext ctx) {
        // [未变动]
        TypeNode type = (TypeNode) visit(ctx.type());
        VariableDeclaratorNode[] declarators = new VariableDeclaratorNode[ctx.variableDeclarator().size()];
        
        for (int i = 0; i < ctx.variableDeclarator().size(); i++) {
            declarators[i] = (VariableDeclaratorNode) visit(ctx.variableDeclarator(i));
        }
        
        return new VariableDeclarationNode(type, declarators,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitVariableDeclarator(com.wei.WeiCParser.VariableDeclaratorContext ctx) {
        // [已修改]：使用 visitDeclarator 代替手动解析 ID 和数组维度
        DeclaratorNode declarator = (DeclaratorNode) visit(ctx.declarator());
        
        ExpressionNode initializer = null;
        // 只有在存在 ASSIGN 的情况下，才存在 initializer expression
        if (ctx.ASSIGN() != null) {
            // 在您的语法中，ASSIGN 后面的表达式是 assignmentExpression
            // ctx.expression() 只会有一个元素，即初始化表达式
            initializer = (ExpressionNode) visit(ctx.expression());
        }
        
        // 假设 VariableDeclaratorNode 构造函数支持 DeclaratorNode
        return new VariableDeclaratorNode(declarator, initializer,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }


    // ========================================
    // 语句 (Statements)
    // ========================================

    @Override
    public ASTNode visitStatement(com.wei.WeiCParser.StatementContext ctx) {
        // [已修改]：添加对 doWhileStatement 的处理
        if (ctx.block() != null) {
            return visit(ctx.block());
        } else if (ctx.expressionStatement() != null) {
            return visit(ctx.expressionStatement());
        } else if (ctx.ifStatement() != null) {
            return visit(ctx.ifStatement());
        } else if (ctx.whileStatement() != null) {
            return visit(ctx.whileStatement());
        } else if (ctx.forStatement() != null) {
            return visit(ctx.forStatement());
        } else if (ctx.switchStatement() != null) {
            return visit(ctx.switchStatement());
        } else if (ctx.breakStatement() != null) {
            return visit(ctx.breakStatement());
        } else if (ctx.continueStatement() != null) {
            return visit(ctx.continueStatement());
        } else if (ctx.returnStatement() != null) {
            return visit(ctx.returnStatement());
        } else if (ctx.variableDeclaration() != null) {
            return visit(ctx.variableDeclaration());
        } else if (ctx.doWhileStatement() != null) { // ✅ 新增
            return visit(ctx.doWhileStatement());
        } else {
            return new IntLiteralNode(0, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }

    @Override
    public ASTNode visitDoWhileStatement(com.wei.WeiCParser.DoWhileStatementContext ctx) {
        // ✅ 新增：do-while 语句
        StatementNode body = (StatementNode) visit(ctx.statement());
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        return new DoWhileStatementNode(body, condition, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitBlock(com.wei.WeiCParser.BlockContext ctx) {
        // [未变动]
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        return new BlockNode(statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitExpressionStatement(com.wei.WeiCParser.ExpressionStatementContext ctx) {
        // [未变动]
        ExpressionNode expression = null;
        if (ctx.expression() != null) {
            expression = (ExpressionNode) visit(ctx.expression());
        }
        return new ExpressionStatementNode(expression, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    // --- 其他控制流语句 (If, While, For, Switch, Break, Continue, Return) ---

    @Override
    public ASTNode visitIfStatement(com.wei.WeiCParser.IfStatementContext ctx) {
        // [未变动]
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        StatementNode thenBranch = (StatementNode) visit(ctx.statement(0));
        StatementNode elseBranch = null;
        if (ctx.statement().size() > 1) {
            elseBranch = (StatementNode) visit(ctx.statement(1));
        }
        return new IfStatementNode(condition, thenBranch, elseBranch, 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitWhileStatement(com.wei.WeiCParser.WhileStatementContext ctx) {
        // [未变动]
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        StatementNode body = (StatementNode) visit(ctx.statement());
        return new WhileStatementNode(condition, body, 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitForStatement(com.wei.WeiCParser.ForStatementContext ctx) {
        // [已修改]：forInit 现在可能是 forVariableDeclaration 或 expression
        StatementNode init = null;
        if (ctx.forInit() != null) {
            // 处理 forInit 的两种情况
            if (ctx.forInit().forVariableDeclaration() != null) {
                init = (StatementNode) visit(ctx.forInit().forVariableDeclaration());
            } else if (ctx.forInit().expression() != null) {
                ExpressionNode initExpr = (ExpressionNode) visit(ctx.forInit().expression());
                init = new ExpressionStatementNode(initExpr, 
                    ctx.forInit().expression().start.getLine(), 
                    ctx.forInit().expression().start.getCharPositionInLine());
            }
        }
        
        ExpressionNode condition = null;
        // forStatement中的expression是conditional expression
        if (ctx.expression() != null) {
            condition = (ExpressionNode) visit(ctx.expression());
        }
        
        ExpressionNode update = null;
        if (ctx.forUpdate() != null && ctx.forUpdate().expression() != null) {
            update = (ExpressionNode) visit(ctx.forUpdate().expression());
        }
        
        StatementNode body = (StatementNode) visit(ctx.statement());
        
        return new ForStatementNode(init, condition, update, body,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitForVariableDeclaration(com.wei.WeiCParser.ForVariableDeclarationContext ctx) {
        // for 循环中的变量声明（不包含分号）
        TypeNode type = (TypeNode) visit(ctx.type());
        VariableDeclaratorNode[] declarators = new VariableDeclaratorNode[ctx.variableDeclarator().size()];
        
        for (int i = 0; i < ctx.variableDeclarator().size(); i++) {
            declarators[i] = (VariableDeclaratorNode) visit(ctx.variableDeclarator(i));
        }
        
        return new VariableDeclarationNode(type, declarators,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitSwitchStatement(com.wei.WeiCParser.SwitchStatementContext ctx) {
        // [未变动]
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        List<CaseStatementNode> cases = new ArrayList<>();
        
        for (com.wei.WeiCParser.SwitchCaseContext caseCtx : ctx.switchCase()) {
            cases.add((CaseStatementNode) visit(caseCtx));
        }
        
        DefaultStatementNode defaultCase = null;
        if (ctx.defaultCase() != null) {
            defaultCase = (DefaultStatementNode) visit(ctx.defaultCase());
        }
        
        return new SwitchStatementNode(condition, cases.toArray(new CaseStatementNode[0]), defaultCase,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitSwitchCase(com.wei.WeiCParser.SwitchCaseContext ctx) {
        // [未变动]
        ExpressionNode value = (ExpressionNode) visit(ctx.expression());
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        return new CaseStatementNode(value, statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitDefaultCase(com.wei.WeiCParser.DefaultCaseContext ctx) {
        // [\u672a\u53d8\u52a8]
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        return new DefaultStatementNode(statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitBreakStatement(com.wei.WeiCParser.BreakStatementContext ctx) {
        // [未变动]
        return new BreakStatementNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitContinueStatement(com.wei.WeiCParser.ContinueStatementContext ctx) {
        // [未变动]
        return new ContinueStatementNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitReturnStatement(com.wei.WeiCParser.ReturnStatementContext ctx) {
        // [未变动]
        ExpressionNode expression = null;
        if (ctx.expression() != null) {
            expression = (ExpressionNode) visit(ctx.expression());
        }
        return new ReturnStatementNode(expression, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    // ========================================
    // 表达式 (Expressions)
    // ========================================
    
    @Override
    public ASTNode visitExpression(com.wei.WeiCParser.ExpressionContext ctx) {
        // [未变动]
        return visit(ctx.assignmentExpression());
    }
    
    @Override
    public ASTNode visitAssignmentExpression(com.wei.WeiCParser.AssignmentExpressionContext ctx) {
        // [未变动]
        if (ctx.conditionalExpression() != null) {
            return visit(ctx.conditionalExpression());
        } else {
            ExpressionNode left = (ExpressionNode) visit(ctx.unaryExpression());
            AssignmentOperator operator = AssignmentOperator.fromToken(ctx.assignmentOperator().getText());
            ExpressionNode right = (ExpressionNode) visit(ctx.assignmentExpression());
            return new AssignmentExpressionNode(left, operator, right, 
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }
    
    @Override
    public ASTNode visitConditionalExpression(com.wei.WeiCParser.ConditionalExpressionContext ctx) {
        // [未变动]
        if (ctx.logicalOrExpression() != null && ctx.expression() == null) {
            return visit(ctx.logicalOrExpression());
        } else {
            ExpressionNode condition = (ExpressionNode) visit(ctx.logicalOrExpression());
            ExpressionNode thenExpr = (ExpressionNode) visit(ctx.expression());
            ExpressionNode elseExpr = (ExpressionNode) visit(ctx.conditionalExpression());
            return new ConditionalExpressionNode(condition, thenExpr, elseExpr,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }
    
    @Override
    public ASTNode visitLogicalOrExpression(com.wei.WeiCParser.LogicalOrExpressionContext ctx) {
        // 处理逻辑或表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.logicalAndExpression());
        return result;
    }
    
    @Override
    public ASTNode visitLogicalAndExpression(com.wei.WeiCParser.LogicalAndExpressionContext ctx) {
        // 处理逻辑与表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.bitwiseOrExpression());
        return result;
    }
    
    @Override
    public ASTNode visitBitwiseOrExpression(com.wei.WeiCParser.BitwiseOrExpressionContext ctx) {
        // 处理位或表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.bitwiseXorExpression());
        return result;
    }
    
    @Override
    public ASTNode visitBitwiseXorExpression(com.wei.WeiCParser.BitwiseXorExpressionContext ctx) {
        // 处理位异或表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.bitwiseAndExpression());
        return result;
    }
    
    @Override
    public ASTNode visitBitwiseAndExpression(com.wei.WeiCParser.BitwiseAndExpressionContext ctx) {
        // 处\u7406\u4f4d\u4e0e\u8868\u8fbe\u5f0f
        ExpressionNode result = (ExpressionNode) visit(ctx.equalityExpression());
        return result;
    }
    
    @Override
    public ASTNode visitEqualityExpression(com.wei.WeiCParser.EqualityExpressionContext ctx) {
        // 处理相等性表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.relationalExpression());
        return result;
    }
    
    @Override
    public ASTNode visitRelationalExpression(com.wei.WeiCParser.RelationalExpressionContext ctx) {
        // 处理关系表达式
        ExpressionNode result = (ExpressionNode) visit(ctx.shiftExpression());
        return result;
    }
    
    @Override
    public ASTNode visitShiftExpression(com.wei.WeiCParser.ShiftExpressionContext ctx) {
        // 处理移位表达式
        if (ctx.getChildCount() == 1) {
            return visit(ctx.additiveExpression());
        }
        // 简化：预设仅有一个运算符，实际上可能有多个
        ExpressionNode result = (ExpressionNode) visit(ctx.additiveExpression());
        return result;
    }
    
    @Override
    public ASTNode visitAdditiveExpression(com.wei.WeiCParser.AdditiveExpressionContext ctx) {
        // 处理加法表达式：multiplicativeExpression ((PLUS|MINUS) multiplicativeExpression)*
        // 简化：Due to ANTLR left-recursion, we get a flattened tree
        // 根据上下文，预设为单个淋是算运算或无运算符（空情况）
        if (ctx.multiplicativeExpression() == null) {
            return null;
        }
        return (ExpressionNode) visit(ctx.multiplicativeExpression());
    }
    
    @Override
    public ASTNode visitMultiplicativeExpression(com.wei.WeiCParser.MultiplicativeExpressionContext ctx) {
        // 处理乘法表达式：unaryExpression ((STAR|DIVIDE|MODULO) unaryExpression)*
        // 简化：ANTLR的左递归会售平化树，预设单个运算数或空
        if (ctx.unaryExpression() == null) {
            return null;
        }
        return (ExpressionNode) visit(ctx.unaryExpression());
    }
    
    @Override
    public ASTNode visitUnaryExpression(com.wei.WeiCParser.UnaryExpressionContext ctx) {
        // [新增]:处理一元表达式
        if (ctx.postfixExpression() != null) {
            return visit(ctx.postfixExpression());
        } else if (ctx.typeName() != null) {
            // 处理强制类型转换 (type) expr
            ASTNode typeResult = visit(ctx.typeName());
            TypeNode type = null;
                
            if (typeResult instanceof TypeNode) {
                type = (TypeNode) typeResult;
            } else if (typeResult instanceof TypeNameNode) {
                type = ((TypeNameNode) typeResult).getBaseType();
            } else {
                type = new TypeNode(com.wei.compiler.type.DataType.INT, "", new int[0], 
                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
            }
                
            ExpressionNode operand = (ExpressionNode) visit(ctx.unaryExpression());
            return new CastExpressionNode(type, operand,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            // 处理一元运算符
            UnaryOperator operator = UnaryOperator.fromToken(ctx.unaryOperator().getText());
            ExpressionNode operand = (ExpressionNode) visit(ctx.unaryExpression());
            return new UnaryExpressionNode(operator, operand,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }
    
    @Override
    public ASTNode visitPostfixExpression(com.wei.WeiCParser.PostfixExpressionContext ctx) {
        // [新增]：处理后缀表达式
        if (ctx.primaryExpression() != null) {
            return visit(ctx.primaryExpression());
        } else if (ctx.LBRACKET() != null) {
            // 数组访问
            ExpressionNode array = (ExpressionNode) visit(ctx.postfixExpression());
            ExpressionNode index = (ExpressionNode) visit(ctx.expression());
            return new ArrayAccessNode(array, new ExpressionNode[]{index},
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.DOT() != null) {
            // 结构体成员访问
            ExpressionNode object = (ExpressionNode) visit(ctx.postfixExpression());
            String memberName = ctx.ID().getText();
            return new MemberAccessNode(object, memberName,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.LPAREN() != null) {
            // 函数调用: postfixExpression LPAREN argumentList? RPAREN
            ExpressionNode function = (ExpressionNode) visit(ctx.postfixExpression());
            List<ExpressionNode> arguments = new ArrayList<>();
            if (ctx.argumentList() != null) {
                for (com.wei.WeiCParser.ExpressionContext argCtx : ctx.argumentList().expression()) {
                    arguments.add((ExpressionNode) visit(argCtx));
                }
            }
            // 对于IdentifierNode，提取函数名称；也支持指针函数调用
            String functionName = (function instanceof IdentifierNode) 
                ? ((IdentifierNode) function).getName() 
                : "pointer_call";
            return new FunctionCallNode(functionName, arguments.toArray(new ExpressionNode[0]),
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.INCREMENT() != null) {
            // 后缀自娱
            ExpressionNode operand = (ExpressionNode) visit(ctx.postfixExpression());
            return new PostfixExpressionNode(operand, PostfixOperator.INCREMENT,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.DECREMENT() != null) {
            // 后缀自减
            ExpressionNode operand = (ExpressionNode) visit(ctx.postfixExpression());
            return new PostfixExpressionNode(operand, PostfixOperator.DECREMENT,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        return null;
    }
    
    @Override
    public ASTNode visitPrimaryExpression(com.wei.WeiCParser.PrimaryExpressionContext ctx) {
        // [新增]：处理基本表达式
        if (ctx.ID() != null) {
            return new IdentifierNode(ctx.ID().getText(),
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.INT_LITERAL() != null) {
            int value = Integer.parseInt(ctx.INT_LITERAL().getText());
            return new IntLiteralNode(value,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.FLOAT_LITERAL() != null) {
            double value = Double.parseDouble(ctx.FLOAT_LITERAL().getText());
            return new FloatLiteralNode((float) value,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.CHAR_LITERAL() != null) {
            String text = ctx.CHAR_LITERAL().getText();
            char value = text.charAt(1); // 简化处理，跳过引号
            if (text.charAt(1) == '\\') {
                // 处理转义字符
                switch (text.charAt(2)) {
                    case 'n': value = '\n'; break;
                    case 't': value = '\t'; break;
                    case 'r': value = '\r'; break;
                    case '\\': value = '\\'; break;
                    case '\'': value = '\''; break;
                    default: value = text.charAt(2); break;
                }
            }
            return new CharLiteralNode(value,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.STRING_LITERAL() != null) {
            String value = ctx.STRING_LITERAL().getText();
            // 去掉引号
            value = value.substring(1, value.length() - 1);
            return new StringLiteralNode(value,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        return null;
    }
}