package com.gemini.compiler.ast;

import com.gemini.grammar.*;
import org.antlr.v4.runtime.tree.*;

/**
 * AST 构建器
 * 
 * 将 ANTLR 解析树转换为自定义的抽象语法树
 */
public class ASTBuilder extends GeminiCBaseVisitor<ASTNode> {
    
    @Override
    public ASTNode visitProgram(GeminiCParser.ProgramContext ctx) {
        ASTNode[] declarations = new ASTNode[ctx.declaration().size()];
        for (int i = 0; i < ctx.declaration().size(); i++) {
            declarations[i] = visit(ctx.declaration(i));
        }
        return new ProgramNode(declarations, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitStructDeclaration(GeminiCParser.StructDeclarationContext ctx) {
        String structName = ctx.ID().getText();
        FieldDeclarationNode[] fields = new FieldDeclarationNode[ctx.structField().size()];
        
        for (int i = 0; i < ctx.structField().size(); i++) {
            GeminiCParser.StructFieldContext fieldCtx = ctx.structField(i);
            TypeNode type = (TypeNode) visit(fieldCtx.type());
            String fieldName = fieldCtx.ID().getText();
            fields[i] = new FieldDeclarationNode(type, fieldName, 
                fieldCtx.start.getLine(), fieldCtx.start.getCharPositionInLine());
        }
        
        return new StructDeclarationNode(structName, fields, 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitFunctionDeclaration(GeminiCParser.FunctionDeclarationContext ctx) {
        TypeNode returnType = (TypeNode) visit(ctx.type());
        String functionName = ctx.ID().getText();
        
        ParameterNode[] parameters = new ParameterNode[0];
        if (ctx.parameterList() != null) {
            parameters = new ParameterNode[ctx.parameterList().parameter().size()];
            for (int i = 0; i < ctx.parameterList().parameter().size(); i++) {
                GeminiCParser.ParameterContext paramCtx = ctx.parameterList().parameter(i);
                TypeNode paramType = (TypeNode) visit(paramCtx.type());
                String paramName = paramCtx.ID().getText();
                parameters[i] = new ParameterNode(paramType, paramName, 
                    paramCtx.start.getLine(), paramCtx.start.getCharPositionInLine());
            }
        }
        
        BlockNode body = (BlockNode) visit(ctx.block());
        
        return new FunctionDeclarationNode(returnType, functionName, parameters, body,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitVariableDeclaration(GeminiCParser.VariableDeclarationContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());
        VariableDeclaratorNode[] declarators = new VariableDeclaratorNode[ctx.variableDeclarator().size()];
        
        for (int i = 0; i < ctx.variableDeclarator().size(); i++) {
            declarators[i] = (VariableDeclaratorNode) visit(ctx.variableDeclarator(i));
        }
        
        return new VariableDeclarationNode(type, declarators,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitVariableDeclarator(GeminiCParser.VariableDeclaratorContext ctx) {
        String variableName = ctx.ID().getText();
        
        // 处理数组维度
        int[] arrayDimensions = new int[ctx.LBRACKET().size()];
        for (int i = 0; i < ctx.LBRACKET().size(); i++) {
            if (ctx.expression(i) != null) {
                // 这里简化处理，假设数组维度是常量
                arrayDimensions[i] = 0; // 占位符，实际应该计算表达式值
            }
        }
        
        ExpressionNode initializer = null;
        if (ctx.expression(ctx.expression().size() - 1) != null) {
            initializer = (ExpressionNode) visit(ctx.expression(ctx.expression().size() - 1));
        }
        
        return new VariableDeclaratorNode(variableName, arrayDimensions, initializer,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitBlock(GeminiCParser.BlockContext ctx) {
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        return new BlockNode(statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitExpressionStatement(GeminiCParser.ExpressionStatementContext ctx) {
        ExpressionNode expression = null;
        if (ctx.expression() != null) {
            expression = (ExpressionNode) visit(ctx.expression());
        }
        return new ExpressionStatementNode(expression, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitIfStatement(GeminiCParser.IfStatementContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        StatementNode thenStatement = (StatementNode) visit(ctx.statement(0));
        StatementNode elseStatement = null;
        
        if (ctx.statement().size() > 1) {
            elseStatement = (StatementNode) visit(ctx.statement(1));
        }
        
        return new IfStatementNode(condition, thenStatement, elseStatement,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitWhileStatement(GeminiCParser.WhileStatementContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        StatementNode body = (StatementNode) visit(ctx.statement());
        return new WhileStatementNode(condition, body, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitForStatement(GeminiCParser.ForStatementContext ctx) {
        StatementNode initialization = null;
        if (ctx.forInit() != null) {
            initialization = (StatementNode) visit(ctx.forInit());
        }
        
        ExpressionNode condition = null;
        if (ctx.expression() != null) {
            condition = (ExpressionNode) visit(ctx.expression());
        }
        
        ExpressionNode update = null;
        if (ctx.forUpdate() != null) {
            update = (ExpressionNode) visit(ctx.forUpdate());
        }
        
        StatementNode body = (StatementNode) visit(ctx.statement());
        
        return new ForStatementNode(initialization, condition, update, body,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitSwitchStatement(GeminiCParser.SwitchStatementContext ctx) {
        ExpressionNode expression = (ExpressionNode) visit(ctx.expression());
        
        CaseStatementNode[] cases = new CaseStatementNode[ctx.switchCase().size()];
        for (int i = 0; i < ctx.switchCase().size(); i++) {
            cases[i] = (CaseStatementNode) visit(ctx.switchCase(i));
        }
        
        DefaultStatementNode defaultCase = null;
        if (ctx.defaultCase() != null) {
            defaultCase = (DefaultStatementNode) visit(ctx.defaultCase());
        }
        
        return new SwitchStatementNode(expression, cases, defaultCase,
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitSwitchCase(GeminiCParser.SwitchCaseContext ctx) {
        ExpressionNode value = (ExpressionNode) visit(ctx.expression());
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        
        return new CaseStatementNode(value, statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitDefaultCase(GeminiCParser.DefaultCaseContext ctx) {
        StatementNode[] statements = new StatementNode[ctx.statement().size()];
        
        for (int i = 0; i < ctx.statement().size(); i++) {
            statements[i] = (StatementNode) visit(ctx.statement(i));
        }
        
        return new DefaultStatementNode(statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitBreakStatement(GeminiCParser.BreakStatementContext ctx) {
        return new BreakStatementNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitContinueStatement(GeminiCParser.ContinueStatementContext ctx) {
        return new ContinueStatementNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitReturnStatement(GeminiCParser.ReturnStatementContext ctx) {
        ExpressionNode expression = null;
        if (ctx.expression() != null) {
            expression = (ExpressionNode) visit(ctx.expression());
        }
        return new ReturnStatementNode(expression, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    
    @Override
    public ASTNode visitAssignmentExpression(GeminiCParser.AssignmentExpressionContext ctx) {
        if (ctx.assignmentOperator() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.unaryExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.assignmentExpression());
            
            AssignmentOperator operator = AssignmentOperator.ASSIGN;
            if (ctx.assignmentOperator().PLUS_ASSIGN() != null) operator = AssignmentOperator.PLUS_ASSIGN;
            else if (ctx.assignmentOperator().MINUS_ASSIGN() != null) operator = AssignmentOperator.MINUS_ASSIGN;
            else if (ctx.assignmentOperator().MULTIPLY_ASSIGN() != null) operator = AssignmentOperator.MULTIPLY_ASSIGN;
            else if (ctx.assignmentOperator().DIVIDE_ASSIGN() != null) operator = AssignmentOperator.DIVIDE_ASSIGN;
            else if (ctx.assignmentOperator().MODULO_ASSIGN() != null) operator = AssignmentOperator.MODULO_ASSIGN;
            
            return new AssignmentExpressionNode(left, operator, right,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.conditionalExpression());
        }
    }
    
    @Override
    public ASTNode visitConditionalExpression(GeminiCParser.ConditionalExpressionContext ctx) {
        if (ctx.QUESTION() != null) {
            ExpressionNode condition = (ExpressionNode) visit(ctx.logicalOrExpression());
            ExpressionNode trueExpression = (ExpressionNode) visit(ctx.expression(0));
            ExpressionNode falseExpression = (ExpressionNode) visit(ctx.expression(1));
            
            return new ConditionalExpressionNode(condition, trueExpression, falseExpression,
                ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.logicalOrExpression());
        }
    }
    
    @Override
    public ASTNode visitLogicalOrExpression(GeminiCParser.LogicalOrExpressionContext ctx) {
        if (ctx.OR() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.logicalOrExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.logicalAndExpression());
            return new LogicalOrExpressionNode(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.logicalAndExpression());
        }
    }
    
    @Override
    public ASTNode visitLogicalAndExpression(GeminiCParser.LogicalAndExpressionContext ctx) {
        if (ctx.AND() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.logicalAndExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.equalityExpression());
            return new LogicalAndExpressionNode(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.equalityExpression());
        }
    }
    
    @Override
    public ASTNode visitEqualityExpression(GeminiCParser.EqualityExpressionContext ctx) {
        if (ctx.EQ() != null || ctx.NE() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.equalityExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.relationalExpression());
            
            EqualityOperator operator = ctx.EQ() != null ? EqualityOperator.EQUAL : EqualityOperator.NOT_EQUAL;
            return new EqualityExpressionNode(left, operator, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.relationalExpression());
        }
    }
    
    @Override
    public ASTNode visitRelationalExpression(GeminiCParser.RelationalExpressionContext ctx) {
        if (ctx.LT() != null || ctx.GT() != null || ctx.LE() != null || ctx.GE() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.relationalExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.additiveExpression());
            
            RelationalOperator operator = RelationalOperator.LESS_THAN;
            if (ctx.LT() != null) operator = RelationalOperator.LESS_THAN;
            else if (ctx.GT() != null) operator = RelationalOperator.GREATER_THAN;
            else if (ctx.LE() != null) operator = RelationalOperator.LESS_EQUAL;
            else if (ctx.GE() != null) operator = RelationalOperator.GREATER_EQUAL;
            
            return new RelationalExpressionNode(left, operator, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.additiveExpression());
        }
    }
    
    @Override
    public ASTNode visitAdditiveExpression(GeminiCParser.AdditiveExpressionContext ctx) {
        if (ctx.PLUS() != null || ctx.MINUS() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.additiveExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.multiplicativeExpression());
            
            AdditiveOperator operator = ctx.PLUS() != null ? AdditiveOperator.PLUS : AdditiveOperator.MINUS;
            return new AdditiveExpressionNode(left, operator, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.multiplicativeExpression());
        }
    }
    
    @Override
    public ASTNode visitMultiplicativeExpression(GeminiCParser.MultiplicativeExpressionContext ctx) {
        if (ctx.MULTIPLY() != null || ctx.DIVIDE() != null || ctx.MODULO() != null) {
            ExpressionNode left = (ExpressionNode) visit(ctx.multiplicativeExpression());
            ExpressionNode right = (ExpressionNode) visit(ctx.unaryExpression());
            
            MultiplicativeOperator operator = MultiplicativeOperator.MULTIPLY;
            if (ctx.MULTIPLY() != null) operator = MultiplicativeOperator.MULTIPLY;
            else if (ctx.DIVIDE() != null) operator = MultiplicativeOperator.DIVIDE;
            else if (ctx.MODULO() != null) operator = MultiplicativeOperator.MODULO;
            
            return new MultiplicativeExpressionNode(left, operator, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.unaryExpression());
        }
    }
    
    @Override
    public ASTNode visitUnaryExpression(GeminiCParser.UnaryExpressionContext ctx) {
        if (ctx.unaryOperator() != null) {
            ExpressionNode operand = (ExpressionNode) visit(ctx.unaryExpression());
            
            UnaryOperator operator = UnaryOperator.PLUS;
            if (ctx.unaryOperator().PLUS() != null) operator = UnaryOperator.PLUS;
            else if (ctx.unaryOperator().MINUS() != null) operator = UnaryOperator.MINUS;
            else if (ctx.unaryOperator().NOT() != null) operator = UnaryOperator.NOT;
            else if (ctx.unaryOperator().INCREMENT() != null) operator = UnaryOperator.INCREMENT;
            else if (ctx.unaryOperator().DECREMENT() != null) operator = UnaryOperator.DECREMENT;
            
            return new UnaryExpressionNode(operator, operand, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.postfixExpression());
        }
    }
    
    @Override
    public ASTNode visitPostfixExpression(GeminiCParser.PostfixExpressionContext ctx) {
        if (ctx.INCREMENT() != null || ctx.DECREMENT() != null) {
            ExpressionNode operand = (ExpressionNode) visit(ctx.postfixExpression());
            
            PostfixOperator operator = ctx.INCREMENT() != null ? PostfixOperator.INCREMENT : PostfixOperator.DECREMENT;
            return new PostfixExpressionNode(operand, operator, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return visit(ctx.primaryExpression());
        }
    }
    
    @Override
    public ASTNode visitPrimaryExpression(GeminiCParser.PrimaryExpressionContext ctx) {
        if (ctx.ID() != null) {
            return new IdentifierNode(ctx.ID().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.INT_LITERAL() != null) {
            int value = Integer.parseInt(ctx.INT_LITERAL().getText());
            return new IntLiteralNode(value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.FLOAT_LITERAL() != null) {
            float value = Float.parseFloat(ctx.FLOAT_LITERAL().getText());
            return new FloatLiteralNode(value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.CHAR_LITERAL() != null) {
            char value = ctx.CHAR_LITERAL().getText().charAt(1); // 跳过引号
            return new CharLiteralNode(value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.STRING_LITERAL() != null) {
            String value = ctx.STRING_LITERAL().getText().substring(1, ctx.STRING_LITERAL().getText().length() - 1); // 跳过引号
            return new StringLiteralNode(value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (ctx.LPAREN() != null) {
            return visit(ctx.expression());
        } else {
            // 处理其他情况，如函数调用、数组访问等
            return visit(ctx.postfixExpression());
        }
    }
    
    @Override
    public ASTNode visitType(GeminiCParser.TypeContext ctx) {
        DataType dataType = DataType.INT;
        String structName = null;
        int[] arrayDimensions = null;
        
        if (ctx.INT() != null) dataType = DataType.INT;
        else if (ctx.FLOAT() != null) dataType = DataType.FLOAT;
        else if (ctx.CHAR() != null) dataType = DataType.CHAR;
        else if (ctx.STRING() != null) dataType = DataType.STRING;
        else if (ctx.VOID() != null) dataType = DataType.VOID;
        else if (ctx.STRUCT() != null) {
            dataType = DataType.STRUCT;
            structName = ctx.ID().getText();
        }
        
        return new TypeNode(dataType, structName, arrayDimensions, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
}
