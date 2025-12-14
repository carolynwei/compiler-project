package com.wei.compiler.ast;

/**
 * AST 访问者接口
 * * 使用访问者模式遍历 AST，支持不同类型的访问者实现
 * 如语义分析器、中间代码生成器等
 */
public interface ASTVisitor<T> {
    
    // ========================================
    // 1. 程序结构
    // ========================================
    T visitProgram(ProgramNode node);
    
    // ========================================
    // 2. 声明
    // ========================================
    T visitStructDeclaration(StructDeclarationNode node);
    T visitFieldDeclaration(FieldDeclarationNode node);
    T visitFunctionDeclaration(FunctionDeclarationNode node);
    T visitParameter(ParameterNode node);
    T visitVariableDeclaration(VariableDeclarationNode node);
    T visitVariableDeclarator(VariableDeclaratorNode node);

    /**
     * ✅ 新增: 访问 DeclaratorNode，用于解析指针/引用/数组维度。
     */
    T visitDeclaratorNode(DeclaratorNode node);

    // ========================================
    // 3. 语句
    // ========================================
    T visitBlock(BlockNode node);
    T visitExpressionStatement(ExpressionStatementNode node);
    T visitIfStatement(IfStatementNode node);
    T visitWhileStatement(WhileStatementNode node);
    
    /**
     * ✅ 新增: 访问 DoWhileStatementNode。
     */
    T visitDoWhileStatementNode(DoWhileStatementNode node);
    
    T visitForStatement(ForStatementNode node);
    T visitSwitchStatement(SwitchStatementNode node);
    T visitCaseStatement(CaseStatementNode node);
    T visitDefaultStatement(DefaultStatementNode node);
    T visitBreakStatement(BreakStatementNode node);
    T visitContinueStatement(ContinueStatementNode node);
    T visitReturnStatement(ReturnStatementNode node);
    
    // ========================================
    // 4. 表达式
    // ========================================
    T visitAssignmentExpression(AssignmentExpressionNode node);
    T visitConditionalExpression(ConditionalExpressionNode node);
    T visitLogicalOrExpression(LogicalOrExpressionNode node);
    T visitLogicalAndExpression(LogicalAndExpressionNode node);
    
    /**
     * ✅ 新增: 访问 BitwiseExpressionNode，用于位或 (|)、位异或 (^) 和位与 (&)。
     */
    T visitBitwiseExpressionNode(BitwiseExpressionNode node);
    
    T visitEqualityExpression(EqualityExpressionNode node);
    T visitRelationalExpression(RelationalExpressionNode node);
    
    /**
     * ✅ 新增: 访问 ShiftExpressionNode，用于移位 (<<, >>)。
     */
    T visitShiftExpressionNode(ShiftExpressionNode node);
    
    T visitAdditiveExpression(AdditiveExpressionNode node);
    T visitMultiplicativeExpression(MultiplicativeExpressionNode node);
    T visitUnaryExpression(UnaryExpressionNode node);
    T visitPostfixExpression(PostfixExpressionNode node);
    T visitCastExpression(CastExpressionNode node);
    
    // ========================================
    // 5. 字面量和标识符
    // ========================================
    T visitPrimaryExpression(PrimaryExpressionNode node); // 保持原有的 PrimaryExpressionNode 抽象
    T visitIdentifier(IdentifierNode node);
    T visitIntLiteral(IntLiteralNode node);
    T visitFloatLiteral(FloatLiteralNode node);
    T visitCharLiteral(CharLiteralNode node);
    T visitStringLiteral(StringLiteralNode node);
    
    // ========================================
    // 6. 函数调用和访问
    // ========================================
    T visitFunctionCall(FunctionCallNode node);
    T visitArrayAccess(ArrayAccessNode node);
    T visitMemberAccess(MemberAccessNode node);
    
    // ========================================
    // 7. 类型
    // ========================================
    T visitType(TypeNode node);

    /**
     * ✅ 新增: 访问 TypeNameNode，用于强制类型转换和 sizeof(type)。
     */
    T visitTypeNameNode(TypeNameNode node);
}