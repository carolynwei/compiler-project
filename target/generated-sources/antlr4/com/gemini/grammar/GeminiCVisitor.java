// Generated from com/gemini/grammar/GeminiC.g4 by ANTLR 4.9.3
package com.gemini.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GeminiCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GeminiCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GeminiCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(GeminiCParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(GeminiCParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#structField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructField(GeminiCParser.StructFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(GeminiCParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(GeminiCParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(GeminiCParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(GeminiCParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(GeminiCParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(GeminiCParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GeminiCParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GeminiCParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GeminiCParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(GeminiCParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(GeminiCParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(GeminiCParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(GeminiCParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(GeminiCParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(GeminiCParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(GeminiCParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#switchCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchCase(GeminiCParser.SwitchCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#defaultCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCase(GeminiCParser.DefaultCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(GeminiCParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(GeminiCParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(GeminiCParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GeminiCParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(GeminiCParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(GeminiCParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(GeminiCParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(GeminiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(GeminiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(GeminiCParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(GeminiCParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(GeminiCParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(GeminiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(GeminiCParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(GeminiCParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(GeminiCParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(GeminiCParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(GeminiCParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GeminiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(GeminiCParser.PrimaryExpressionContext ctx);
}