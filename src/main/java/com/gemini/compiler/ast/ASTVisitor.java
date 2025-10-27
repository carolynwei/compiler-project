package com.gemini.compiler.ast;

/**
 * AST 访问者接口
 * 
 * 使用访问者模式遍历 AST，支持不同类型的访问者实现
 * 如语义分析器、中间代码生成器等
 */
public interface ASTVisitor<T> {
    
    // 程序结构
    T visitProgram(ProgramNode node);
    
    // 声明
    T visitStructDeclaration(StructDeclarationNode node);
    T visitFieldDeclaration(FieldDeclarationNode node);
    T visitFunctionDeclaration(FunctionDeclarationNode node);
    T visitParameter(ParameterNode node);
    T visitVariableDeclaration(VariableDeclarationNode node);
    T visitVariableDeclarator(VariableDeclaratorNode node);
    
    // 语句
    T visitBlock(BlockNode node);
    T visitExpressionStatement(ExpressionStatementNode node);
    T visitIfStatement(IfStatementNode node);
    T visitWhileStatement(WhileStatementNode node);
    T visitForStatement(ForStatementNode node);
    T visitSwitchStatement(SwitchStatementNode node);
    T visitCaseStatement(CaseStatementNode node);
    T visitDefaultStatement(DefaultStatementNode node);
    T visitBreakStatement(BreakStatementNode node);
    T visitContinueStatement(ContinueStatementNode node);
    T visitReturnStatement(ReturnStatementNode node);
    
    // 表达式
    T visitAssignmentExpression(AssignmentExpressionNode node);
    T visitConditionalExpression(ConditionalExpressionNode node);
    T visitLogicalOrExpression(LogicalOrExpressionNode node);
    T visitLogicalAndExpression(LogicalAndExpressionNode node);
    T visitEqualityExpression(EqualityExpressionNode node);
    T visitRelationalExpression(RelationalExpressionNode node);
    T visitAdditiveExpression(AdditiveExpressionNode node);
    T visitMultiplicativeExpression(MultiplicativeExpressionNode node);
    T visitUnaryExpression(UnaryExpressionNode node);
    T visitPostfixExpression(PostfixExpressionNode node);
    T visitPrimaryExpression(PrimaryExpressionNode node);
    
    // 字面量和标识符
    T visitIdentifier(IdentifierNode node);
    T visitIntLiteral(IntLiteralNode node);
    T visitFloatLiteral(FloatLiteralNode node);
    T visitCharLiteral(CharLiteralNode node);
    T visitStringLiteral(StringLiteralNode node);
    
    // 函数调用和访问
    T visitFunctionCall(FunctionCallNode node);
    T visitArrayAccess(ArrayAccessNode node);
    T visitMemberAccess(MemberAccessNode node);
    
    // 类型
    T visitType(TypeNode node);
}

/**
 * AST 打印器 - 用于调试和可视化
 */
class ASTPrinter implements ASTVisitor<Void> {
    private int indentLevel = 0;
    
    private void printIndent() {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  ");
        }
    }
    
    private void printNode(String nodeType, String details) {
        printIndent();
        System.out.println(nodeType + ": " + details);
    }
    
    private void printNode(String nodeType) {
        printIndent();
        System.out.println(nodeType);
    }
    
    public void print(ASTNode node) {
        if (node != null) {
            node.accept(this);
        }
    }
    
    @Override
    public Void visitProgram(ProgramNode node) {
        printNode("Program");
        indentLevel++;
        for (ASTNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitStructDeclaration(StructDeclarationNode node) {
        printNode("StructDeclaration", node.getStructName());
        indentLevel++;
        for (FieldDeclarationNode field : node.getFields()) {
            field.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitFieldDeclaration(FieldDeclarationNode node) {
        printNode("FieldDeclaration", node.getFieldName());
        indentLevel++;
        node.getType().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
        printNode("FunctionDeclaration", node.getFunctionName());
        indentLevel++;
        node.getReturnType().accept(this);
        for (ParameterNode param : node.getParameters()) {
            param.accept(this);
        }
        node.getBody().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitParameter(ParameterNode node) {
        printNode("Parameter", node.getParameterName());
        indentLevel++;
        node.getType().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitVariableDeclaration(VariableDeclarationNode node) {
        printNode("VariableDeclaration");
        indentLevel++;
        node.getType().accept(this);
        for (VariableDeclaratorNode declarator : node.getDeclarators()) {
            declarator.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitVariableDeclarator(VariableDeclaratorNode node) {
        printNode("VariableDeclarator", node.getVariableName());
        indentLevel++;
        if (node.getInitializer() != null) {
            node.getInitializer().accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitBlock(BlockNode node) {
        printNode("Block");
        indentLevel++;
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitExpressionStatement(ExpressionStatementNode node) {
        printNode("ExpressionStatement");
        indentLevel++;
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitIfStatement(IfStatementNode node) {
        printNode("IfStatement");
        indentLevel++;
        node.getCondition().accept(this);
        node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitWhileStatement(WhileStatementNode node) {
        printNode("WhileStatement");
        indentLevel++;
        node.getCondition().accept(this);
        node.getBody().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitForStatement(ForStatementNode node) {
        printNode("ForStatement");
        indentLevel++;
        if (node.getInitialization() != null) {
            node.getInitialization().accept(this);
        }
        if (node.getCondition() != null) {
            node.getCondition().accept(this);
        }
        if (node.getUpdate() != null) {
            node.getUpdate().accept(this);
        }
        node.getBody().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitSwitchStatement(SwitchStatementNode node) {
        printNode("SwitchStatement");
        indentLevel++;
        node.getExpression().accept(this);
        for (CaseStatementNode caseNode : node.getCases()) {
            caseNode.accept(this);
        }
        if (node.getDefaultCase() != null) {
            node.getDefaultCase().accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitCaseStatement(CaseStatementNode node) {
        printNode("CaseStatement");
        indentLevel++;
        node.getValue().accept(this);
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitDefaultStatement(DefaultStatementNode node) {
        printNode("DefaultStatement");
        indentLevel++;
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitBreakStatement(BreakStatementNode node) {
        printNode("BreakStatement");
        return null;
    }
    
    @Override
    public Void visitContinueStatement(ContinueStatementNode node) {
        printNode("ContinueStatement");
        return null;
    }
    
    @Override
    public Void visitReturnStatement(ReturnStatementNode node) {
        printNode("ReturnStatement");
        indentLevel++;
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitAssignmentExpression(AssignmentExpressionNode node) {
        printNode("AssignmentExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitConditionalExpression(ConditionalExpressionNode node) {
        printNode("ConditionalExpression");
        indentLevel++;
        node.getCondition().accept(this);
        node.getTrueExpression().accept(this);
        node.getFalseExpression().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitLogicalOrExpression(LogicalOrExpressionNode node) {
        printNode("LogicalOrExpression");
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitLogicalAndExpression(LogicalAndExpressionNode node) {
        printNode("LogicalAndExpression");
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitEqualityExpression(EqualityExpressionNode node) {
        printNode("EqualityExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitRelationalExpression(RelationalExpressionNode node) {
        printNode("RelationalExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitAdditiveExpression(AdditiveExpressionNode node) {
        printNode("AdditiveExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        printNode("MultiplicativeExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitUnaryExpression(UnaryExpressionNode node) {
        printNode("UnaryExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getOperand().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitPostfixExpression(PostfixExpressionNode node) {
        printNode("PostfixExpression", node.getOperator().getSymbol());
        indentLevel++;
        node.getOperand().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitPrimaryExpression(PrimaryExpressionNode node) {
        printNode("PrimaryExpression", node.getType().toString());
        return null;
    }
    
    @Override
    public Void visitIdentifier(IdentifierNode node) {
        printNode("Identifier", node.getName());
        return null;
    }
    
    @Override
    public Void visitIntLiteral(IntLiteralNode node) {
        printNode("IntLiteral", String.valueOf(node.getValue()));
        return null;
    }
    
    @Override
    public Void visitFloatLiteral(FloatLiteralNode node) {
        printNode("FloatLiteral", String.valueOf(node.getValue()));
        return null;
    }
    
    @Override
    public Void visitCharLiteral(CharLiteralNode node) {
        printNode("CharLiteral", String.valueOf(node.getValue()));
        return null;
    }
    
    @Override
    public Void visitStringLiteral(StringLiteralNode node) {
        printNode("StringLiteral", node.getValue());
        return null;
    }
    
    @Override
    public Void visitFunctionCall(FunctionCallNode node) {
        printNode("FunctionCall", node.getFunctionName());
        indentLevel++;
        for (ExpressionNode arg : node.getArguments()) {
            arg.accept(this);
        }
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitArrayAccess(ArrayAccessNode node) {
        printNode("ArrayAccess");
        indentLevel++;
        node.getArray().accept(this);
        node.getIndex().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitMemberAccess(MemberAccessNode node) {
        printNode("MemberAccess", node.getMemberName());
        indentLevel++;
        node.getObject().accept(this);
        indentLevel--;
        return null;
    }
    
    @Override
    public Void visitType(TypeNode node) {
        printNode("Type", node.getDataType().toString());
        return null;
    }
}
