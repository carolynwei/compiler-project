// Generated from com/gemini/grammar/GeminiC.g4 by ANTLR 4.9.3
package com.gemini.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GeminiCParser}.
 */
public interface GeminiCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GeminiCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GeminiCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(GeminiCParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(GeminiCParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(GeminiCParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(GeminiCParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#structField}.
	 * @param ctx the parse tree
	 */
	void enterStructField(GeminiCParser.StructFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#structField}.
	 * @param ctx the parse tree
	 */
	void exitStructField(GeminiCParser.StructFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(GeminiCParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(GeminiCParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(GeminiCParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(GeminiCParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(GeminiCParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(GeminiCParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(GeminiCParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(GeminiCParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(GeminiCParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(GeminiCParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(GeminiCParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(GeminiCParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GeminiCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GeminiCParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GeminiCParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GeminiCParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(GeminiCParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(GeminiCParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(GeminiCParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(GeminiCParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(GeminiCParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(GeminiCParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(GeminiCParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(GeminiCParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(GeminiCParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(GeminiCParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(GeminiCParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(GeminiCParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(GeminiCParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(GeminiCParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(GeminiCParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(GeminiCParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCase(GeminiCParser.SwitchCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCase(GeminiCParser.SwitchCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCase(GeminiCParser.DefaultCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCase(GeminiCParser.DefaultCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(GeminiCParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(GeminiCParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(GeminiCParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(GeminiCParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(GeminiCParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(GeminiCParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GeminiCParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GeminiCParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(GeminiCParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(GeminiCParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(GeminiCParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(GeminiCParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(GeminiCParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(GeminiCParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(GeminiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(GeminiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(GeminiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(GeminiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(GeminiCParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(GeminiCParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(GeminiCParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(GeminiCParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(GeminiCParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(GeminiCParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(GeminiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(GeminiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(GeminiCParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(GeminiCParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(GeminiCParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(GeminiCParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(GeminiCParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(GeminiCParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(GeminiCParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(GeminiCParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(GeminiCParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(GeminiCParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeminiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(GeminiCParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeminiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(GeminiCParser.PrimaryExpressionContext ctx);
}