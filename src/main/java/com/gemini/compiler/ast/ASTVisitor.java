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
    T visitCastExpression(CastExpressionNode node);
    
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

