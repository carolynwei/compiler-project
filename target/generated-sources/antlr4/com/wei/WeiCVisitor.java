// Generated from com/wei/WeiC.g4 by ANTLR 4.9.3
package com.wei;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WeiCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WeiCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WeiCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(WeiCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(WeiCParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(WeiCParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#structField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructField(WeiCParser.StructFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(WeiCParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(WeiCParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(WeiCParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(WeiCParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(WeiCParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(WeiCParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(WeiCParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(WeiCParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(WeiCParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(WeiCParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(WeiCParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(WeiCParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(WeiCParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(WeiCParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(WeiCParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(WeiCParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#forVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForVariableDeclaration(WeiCParser.ForVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(WeiCParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(WeiCParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(WeiCParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#switchCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchCase(WeiCParser.SwitchCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#defaultCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCase(WeiCParser.DefaultCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(WeiCParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(WeiCParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(WeiCParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(WeiCParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(WeiCParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(WeiCParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(WeiCParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(WeiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(WeiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#bitwiseOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseOrExpression(WeiCParser.BitwiseOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#bitwiseXorExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseXorExpression(WeiCParser.BitwiseXorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#bitwiseAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseAndExpression(WeiCParser.BitwiseAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(WeiCParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(WeiCParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(WeiCParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(WeiCParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(WeiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(WeiCParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(WeiCParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(WeiCParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link WeiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(WeiCParser.PrimaryExpressionContext ctx);
}