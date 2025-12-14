// Generated from com/wei/WeiC.g4 by ANTLR 4.9.3
package com.wei;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WeiCParser}.
 */
public interface WeiCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WeiCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(WeiCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(WeiCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(WeiCParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(WeiCParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(WeiCParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(WeiCParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#structField}.
	 * @param ctx the parse tree
	 */
	void enterStructField(WeiCParser.StructFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#structField}.
	 * @param ctx the parse tree
	 */
	void exitStructField(WeiCParser.StructFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(WeiCParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(WeiCParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(WeiCParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(WeiCParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(WeiCParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(WeiCParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(WeiCParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(WeiCParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(WeiCParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(WeiCParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(WeiCParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(WeiCParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(WeiCParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(WeiCParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(WeiCParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(WeiCParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(WeiCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(WeiCParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(WeiCParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(WeiCParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(WeiCParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(WeiCParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(WeiCParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(WeiCParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(WeiCParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(WeiCParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(WeiCParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(WeiCParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(WeiCParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(WeiCParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(WeiCParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(WeiCParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#forVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterForVariableDeclaration(WeiCParser.ForVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#forVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitForVariableDeclaration(WeiCParser.ForVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(WeiCParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(WeiCParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(WeiCParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(WeiCParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(WeiCParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(WeiCParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCase(WeiCParser.SwitchCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCase(WeiCParser.SwitchCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCase(WeiCParser.DefaultCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCase(WeiCParser.DefaultCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(WeiCParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(WeiCParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(WeiCParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(WeiCParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(WeiCParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(WeiCParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(WeiCParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(WeiCParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(WeiCParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(WeiCParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(WeiCParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(WeiCParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(WeiCParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(WeiCParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(WeiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(WeiCParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(WeiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(WeiCParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#bitwiseOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOrExpression(WeiCParser.BitwiseOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#bitwiseOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOrExpression(WeiCParser.BitwiseOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#bitwiseXorExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseXorExpression(WeiCParser.BitwiseXorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#bitwiseXorExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseXorExpression(WeiCParser.BitwiseXorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#bitwiseAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseAndExpression(WeiCParser.BitwiseAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#bitwiseAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseAndExpression(WeiCParser.BitwiseAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(WeiCParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(WeiCParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(WeiCParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(WeiCParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(WeiCParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(WeiCParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(WeiCParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(WeiCParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(WeiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(WeiCParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(WeiCParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(WeiCParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(WeiCParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(WeiCParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(WeiCParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(WeiCParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link WeiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(WeiCParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WeiCParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(WeiCParser.PrimaryExpressionContext ctx);
}