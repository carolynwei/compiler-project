package com.gemini.compiler.ir;

import com.gemini.compiler.ast.*;
import com.gemini.compiler.semantic.*;
import java.util.*;

/**
 * 中间代码生成器
 * 
 * 将 AST 转换为三地址代码 (TAC)
 */
public class IRGenerator implements ASTVisitor<Void> {
    
    private IRProgram irProgram;
    private SymbolTableManager symbolTableManager;
    private boolean debugMode;
    
    // 当前上下文
    private String currentFunction;
    private Stack<String> breakLabels;
    private Stack<String> continueLabels;
    private Map<String, String> switchLabels;
    
    public IRGenerator() {
        this.irProgram = new IRProgram();
        this.symbolTableManager = new SymbolTableManager();
        this.debugMode = false;
        this.currentFunction = null;
        this.breakLabels = new Stack<>();
        this.continueLabels = new Stack<>();
        this.switchLabels = new HashMap<>();
    }
    
    public IRGenerator(SymbolTableManager symbolTableManager) {
        this.irProgram = new IRProgram();
        this.symbolTableManager = symbolTableManager;
        this.debugMode = false;
        this.currentFunction = null;
        this.breakLabels = new Stack<>();
        this.continueLabels = new Stack<>();
        this.switchLabels = new HashMap<>();
    }
    
    /**
     * 生成中间代码
     */
    public IRProgram generate(ASTNode ast) {
        System.out.println("\n--- 阶段三：中间代码生成 ---");
        
        // 遍历 AST 生成中间代码
        ast.accept(this);
        
        // 显示中间代码
        if (debugMode) {
            System.out.println(irProgram);
            irProgram.displayBasicBlocks();
        }
        
        System.out.println("中间代码生成完成");
        return irProgram;
    }
    
    /**
     * 设置调试模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * 生成表达式代码并返回结果变量
     */
    private String generateExpression(ExpressionNode expression) {
        if (expression == null) {
            return null;
        }
        
        return expression.accept(new ExpressionIRGenerator());
    }
    
    // ==================== AST 访问者方法 ====================
    
    @Override
    public Void visitProgram(ProgramNode node) {
        // 生成所有声明的代码
        for (ASTNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
        return null;
    }
    
    @Override
    public Void visitStructDeclaration(StructDeclarationNode node) {
        // 结构体声明不生成代码，只记录类型信息
        return null;
    }
    
    @Override
    public Void visitFieldDeclaration(FieldDeclarationNode node) {
        // 字段声明不生成代码
        return null;
    }
    
    @Override
    public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
        String functionName = node.getFunctionName();
        currentFunction = functionName;
        
        // 生成函数标签
        String functionLabel = irProgram.generateLabel("func_" + functionName);
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, functionLabel));
        
        // 进入函数作用域
        symbolTableManager.enterScope();
        
        // 生成参数代码
        for (ParameterNode param : node.getParameters()) {
            param.accept(this);
        }
        
        // 生成函数体代码
        node.getBody().accept(this);
        
        // 退出函数作用域
        symbolTableManager.exitScope();
        currentFunction = null;
        
        return null;
    }
    
    @Override
    public Void visitParameter(ParameterNode node) {
        // 参数已经在函数声明中处理
        return null;
    }
    
    @Override
    public Void visitVariableDeclaration(VariableDeclarationNode node) {
        // 生成变量声明代码
        for (VariableDeclaratorNode declarator : node.getDeclarators()) {
            String variableName = declarator.getVariableName();
            
            // 分配变量空间
            irProgram.addInstruction(new TACInstruction(TACOpcode.ALLOC, "4", null, variableName));
            
            // 如果有初始化表达式，生成赋值代码
            if (declarator.getInitializer() != null) {
                String initValue = generateExpression(declarator.getInitializer());
                irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, initValue, null, variableName));
            }
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
        
        // 生成所有语句的代码
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
            generateExpression(node.getExpression());
        }
        return null;
    }
    
    @Override
    public Void visitIfStatement(IfStatementNode node) {
        // 生成条件表达式代码
        String condition = generateExpression(node.getCondition());
        
        // 生成跳转标签
        String elseLabel = irProgram.generateLabel("else");
        String endLabel = irProgram.generateLabel("endif");
        
        // 生成条件跳转
        irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, elseLabel));
        
        // 生成 then 语句代码
        node.getThenStatement().accept(this);
        
        // 生成跳转到结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
        
        // 生成 else 标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, elseLabel));
        
        // 生成 else 语句代码
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }
        
        // 生成结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    @Override
    public Void visitWhileStatement(WhileStatementNode node) {
        // 生成循环标签
        String loopLabel = irProgram.generateLabel("while");
        String bodyLabel = irProgram.generateLabel("while_body");
        String endLabel = irProgram.generateLabel("while_end");
        
        // 生成循环开始标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, loopLabel));
        
        // 生成条件表达式代码
        String condition = generateExpression(node.getCondition());
        
        // 生成条件跳转
        irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, endLabel));
        
        // 生成循环体标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, bodyLabel));
        
        // 设置 break 和 continue 标签
        breakLabels.push(endLabel);
        continueLabels.push(loopLabel);
        
        // 生成循环体代码
        node.getBody().accept(this);
        
        // 恢复 break 和 continue 标签
        breakLabels.pop();
        continueLabels.pop();
        
        // 生成跳转到循环开始
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, loopLabel));
        
        // 生成循环结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    @Override
    public Void visitForStatement(ForStatementNode node) {
        // 生成循环标签
        String initLabel = irProgram.generateLabel("for_init");
        String conditionLabel = irProgram.generateLabel("for_condition");
        String bodyLabel = irProgram.generateLabel("for_body");
        String updateLabel = irProgram.generateLabel("for_update");
        String endLabel = irProgram.generateLabel("for_end");
        
        // 生成初始化标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, initLabel));
        
        // 生成初始化代码
        if (node.getInitialization() != null) {
            node.getInitialization().accept(this);
        }
        
        // 生成条件标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, conditionLabel));
        
        // 生成条件表达式代码
        if (node.getCondition() != null) {
            String condition = generateExpression(node.getCondition());
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, endLabel));
        }
        
        // 生成循环体标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, bodyLabel));
        
        // 设置 break 和 continue 标签
        breakLabels.push(endLabel);
        continueLabels.push(updateLabel);
        
        // 生成循环体代码
        node.getBody().accept(this);
        
        // 恢复 break 和 continue 标签
        breakLabels.pop();
        continueLabels.pop();
        
        // 生成更新标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, updateLabel));
        
        // 生成更新表达式代码
        if (node.getUpdate() != null) {
            generateExpression(node.getUpdate());
        }
        
        // 生成跳转到条件检查
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, conditionLabel));
        
        // 生成循环结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    @Override
    public Void visitSwitchStatement(SwitchStatementNode node) {
        // 生成 switch 表达式代码
        String switchValue = generateExpression(node.getExpression());
        
        // 生成 switch 标签
        String switchLabel = irProgram.generateLabel("switch");
        String endLabel = irProgram.generateLabel("switch_end");
        
        // 生成 switch 开始标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, switchLabel));
        
        // 设置 break 标签
        breakLabels.push(endLabel);
        
        // 生成所有 case 代码
        for (CaseStatementNode caseNode : node.getCases()) {
            caseNode.accept(this);
        }
        
        // 生成 default case 代码
        if (node.getDefaultCase() != null) {
            node.getDefaultCase().accept(this);
        }
        
        // 恢复 break 标签
        breakLabels.pop();
        
        // 生成 switch 结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    @Override
    public Void visitCaseStatement(CaseStatementNode node) {
        // 生成 case 值代码
        String caseValue = generateExpression(node.getValue());
        
        // 生成 case 标签
        String caseLabel = irProgram.generateLabel("case");
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, caseLabel));
        
        // 生成 case 语句代码
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        
        return null;
    }
    
    @Override
    public Void visitDefaultStatement(DefaultStatementNode node) {
        // 生成 default 标签
        String defaultLabel = irProgram.generateLabel("default");
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, defaultLabel));
        
        // 生成 default 语句代码
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        
        return null;
    }
    
    @Override
    public Void visitBreakStatement(BreakStatementNode node) {
        if (!breakLabels.isEmpty()) {
            String breakLabel = breakLabels.peek();
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, breakLabel));
        }
        return null;
    }
    
    @Override
    public Void visitContinueStatement(ContinueStatementNode node) {
        if (!continueLabels.isEmpty()) {
            String continueLabel = continueLabels.peek();
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, continueLabel));
        }
        return null;
    }
    
    @Override
    public Void visitReturnStatement(ReturnStatementNode node) {
        if (node.getExpression() != null) {
            String returnValue = generateExpression(node.getExpression());
            irProgram.addInstruction(new TACInstruction(TACOpcode.RETURN, returnValue, null, null));
        } else {
            irProgram.addInstruction(new TACInstruction(TACOpcode.RETURN, null, null, null));
        }
        return null;
    }
    
    // ==================== 表达式代码生成 ====================
    
    /**
     * 表达式代码生成器
     */
    private class ExpressionIRGenerator implements ASTVisitor<String> {
        
        @Override
        public String visitProgram(ProgramNode node) {
            return null;
        }
        
        @Override
        public String visitStructDeclaration(StructDeclarationNode node) {
            return null;
        }
        
        @Override
        public String visitFieldDeclaration(FieldDeclarationNode node) {
            return null;
        }
        
        @Override
        public String visitFunctionDeclaration(FunctionDeclarationNode node) {
            return null;
        }
        
        @Override
        public String visitParameter(ParameterNode node) {
            return null;
        }
        
        @Override
        public String visitVariableDeclaration(VariableDeclarationNode node) {
            return null;
        }
        
        @Override
        public String visitVariableDeclarator(VariableDeclaratorNode node) {
            return null;
        }
        
        @Override
        public String visitBlock(BlockNode node) {
            return null;
        }
        
        @Override
        public String visitExpressionStatement(ExpressionStatementNode node) {
            return null;
        }
        
        @Override
        public String visitIfStatement(IfStatementNode node) {
            return null;
        }
        
        @Override
        public String visitWhileStatement(WhileStatementNode node) {
            return null;
        }
        
        @Override
        public String visitForStatement(ForStatementNode node) {
            return null;
        }
        
        @Override
        public String visitSwitchStatement(SwitchStatementNode node) {
            return null;
        }
        
        @Override
        public String visitCaseStatement(CaseStatementNode node) {
            return null;
        }
        
        @Override
        public String visitDefaultStatement(DefaultStatementNode node) {
            return null;
        }
        
        @Override
        public String visitBreakStatement(BreakStatementNode node) {
            return null;
        }
        
        @Override
        public String visitContinueStatement(ContinueStatementNode node) {
            return null;
        }
        
        @Override
        public String visitReturnStatement(ReturnStatementNode node) {
            return null;
        }
        
        @Override
        public String visitAssignmentExpression(AssignmentExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            // 根据赋值运算符生成相应的代码
            switch (node.getOperator()) {
                case ASSIGN:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, right, null, left));
                    break;
                case PLUS_ASSIGN:
                    String temp1 = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ADD, left, right, temp1));
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp1, null, left));
                    break;
                case MINUS_ASSIGN:
                    String temp2 = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.SUB, left, right, temp2));
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp2, null, left));
                    break;
                case MULTIPLY_ASSIGN:
                    String temp3 = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.MUL, left, right, temp3));
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp3, null, left));
                    break;
                case DIVIDE_ASSIGN:
                    String temp4 = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.DIV, left, right, temp4));
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp4, null, left));
                    break;
                case MODULO_ASSIGN:
                    String temp5 = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.MOD, left, right, temp5));
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp5, null, left));
                    break;
            }
            
            return left;
        }
        
        @Override
        public String visitConditionalExpression(ConditionalExpressionNode node) {
            String condition = node.getCondition().accept(this);
            String trueValue = node.getTrueExpression().accept(this);
            String falseValue = node.getFalseExpression().accept(this);
            
            String result = irProgram.generateTempVar();
            String trueLabel = irProgram.generateLabel("true");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成条件跳转
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, condition, null, trueLabel));
            
            // 生成 false 分支
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, falseValue, null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 true 分支
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, trueLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, trueValue, null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        @Override
        public String visitLogicalOrExpression(LogicalOrExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            String trueLabel = irProgram.generateLabel("true");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成左操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, left, null, trueLabel));
            
            // 生成右操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, right, null, trueLabel));
            
            // 生成 false 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "0", null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 true 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, trueLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "1", null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        @Override
        public String visitLogicalAndExpression(LogicalAndExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            String falseLabel = irProgram.generateLabel("false");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成左操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, left, null, falseLabel));
            
            // 生成右操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, right, null, falseLabel));
            
            // 生成 true 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "1", null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 false 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, falseLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "0", null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        @Override
        public String visitEqualityExpression(EqualityExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode = (node.getOperator() == EqualityOperator.EQUAL) ? TACOpcode.EQ : TACOpcode.NE;
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        @Override
        public String visitRelationalExpression(RelationalExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode;
            switch (node.getOperator()) {
                case LESS_THAN: opcode = TACOpcode.LT; break;
                case GREATER_THAN: opcode = TACOpcode.GT; break;
                case LESS_EQUAL: opcode = TACOpcode.LE; break;
                case GREATER_EQUAL: opcode = TACOpcode.GE; break;
                default: opcode = TACOpcode.LT; break;
            }
            
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        @Override
        public String visitAdditiveExpression(AdditiveExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode = (node.getOperator() == AdditiveOperator.PLUS) ? TACOpcode.ADD : TACOpcode.SUB;
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        @Override
        public String visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode;
            switch (node.getOperator()) {
                case MULTIPLY: opcode = TACOpcode.MUL; break;
                case DIVIDE: opcode = TACOpcode.DIV; break;
                case MODULO: opcode = TACOpcode.MOD; break;
                default: opcode = TACOpcode.MUL; break;
            }
            
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        @Override
        public String visitUnaryExpression(UnaryExpressionNode node) {
            String operand = node.getOperand().accept(this);
            
            String result = irProgram.generateTempVar();
            
            switch (node.getOperator()) {
                case PLUS:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, operand, null, result));
                    break;
                case MINUS:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.SUB, "0", operand, result));
                    break;
                case NOT:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.NOT, operand, null, result));
                    break;
                case INCREMENT:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.INCREMENT, null, null, operand));
                    return operand;
                case DECREMENT:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.DECREMENT, null, null, operand));
                    return operand;
            }
            
            return result;
        }
        
        @Override
        public String visitPostfixExpression(PostfixExpressionNode node) {
            String operand = node.getOperand().accept(this);
            
            switch (node.getOperator()) {
                case INCREMENT:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.INCREMENT, null, null, operand));
                    break;
                case DECREMENT:
                    irProgram.addInstruction(new TACInstruction(TACOpcode.DECREMENT, null, null, operand));
                    break;
            }
            
            return operand;
        }
        
        @Override
        public String visitPrimaryExpression(PrimaryExpressionNode node) {
            // 主表达式的处理
            return null;
        }
        
        @Override
        public String visitIdentifier(IdentifierNode node) {
            return node.getName();
        }
        
        @Override
        public String visitIntLiteral(IntLiteralNode node) {
            return String.valueOf(node.getValue());
        }
        
        @Override
        public String visitFloatLiteral(FloatLiteralNode node) {
            return String.valueOf(node.getValue());
        }
        
        @Override
        public String visitCharLiteral(CharLiteralNode node) {
            return String.valueOf((int) node.getValue());
        }
        
        @Override
        public String visitStringLiteral(StringLiteralNode node) {
            return "\"" + node.getValue() + "\"";
        }
        
        @Override
        public String visitFunctionCall(FunctionCallNode node) {
            String functionName = node.getFunctionName();
            
            // 生成参数代码
            for (ExpressionNode arg : node.getArguments()) {
                String argValue = arg.accept(this);
                irProgram.addInstruction(new TACInstruction(TACOpcode.ARG, argValue, null, null));
            }
            
            // 生成函数调用
            String result = irProgram.generateTempVar();
            irProgram.addInstruction(new TACInstruction(TACOpcode.CALL, functionName, null, result));
            
            return result;
        }
        
        @Override
        public String visitArrayAccess(ArrayAccessNode node) {
            String array = node.getArray().accept(this);
            String index = node.getIndex().accept(this);
            
            String result = irProgram.generateTempVar();
            irProgram.addInstruction(new TACInstruction(TACOpcode.ARRAY_ACCESS, array, index, result));
            
            return result;
        }
        
        @Override
        public String visitMemberAccess(MemberAccessNode node) {
            String object = node.getObject().accept(this);
            String memberName = node.getMemberName();
            
            String result = irProgram.generateTempVar();
            irProgram.addInstruction(new TACInstruction(TACOpcode.MEMBER_ACCESS, object, memberName, result));
            
            return result;
        }
        
        @Override
        public String visitType(TypeNode node) {
            return null;
        }
    }
}
