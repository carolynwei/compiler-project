// Generated from com/gemini/grammar/GeminiC.g4 by ANTLR 4.9.3
package com.gemini.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GeminiCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FLOAT=2, CHAR=3, STRING=4, VOID=5, STRUCT=6, IF=7, ELSE=8, WHILE=9, 
		FOR=10, BREAK=11, CONTINUE=12, RETURN=13, SWITCH=14, CASE=15, DEFAULT=16, 
		ID=17, INT_LITERAL=18, FLOAT_LITERAL=19, CHAR_LITERAL=20, STRING_LITERAL=21, 
		PLUS=22, MINUS=23, MULTIPLY=24, DIVIDE=25, MODULO=26, EQ=27, NE=28, LT=29, 
		GT=30, LE=31, GE=32, AND=33, OR=34, NOT=35, ASSIGN=36, PLUS_ASSIGN=37, 
		MINUS_ASSIGN=38, MULTIPLY_ASSIGN=39, DIVIDE_ASSIGN=40, MODULO_ASSIGN=41, 
		INCREMENT=42, DECREMENT=43, SEMICOLON=44, COMMA=45, COLON=46, QUESTION=47, 
		LPAREN=48, RPAREN=49, LBRACE=50, RBRACE=51, LBRACKET=52, RBRACKET=53, 
		DOT=54, LINE_COMMENT=55, BLOCK_COMMENT=56, WS=57;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_structDeclaration = 2, RULE_structField = 3, 
		RULE_functionDeclaration = 4, RULE_parameterList = 5, RULE_parameter = 6, 
		RULE_variableDeclaration = 7, RULE_variableDeclarator = 8, RULE_arrayInitializer = 9, 
		RULE_type = 10, RULE_statement = 11, RULE_block = 12, RULE_expressionStatement = 13, 
		RULE_ifStatement = 14, RULE_whileStatement = 15, RULE_forStatement = 16, 
		RULE_forInit = 17, RULE_forUpdate = 18, RULE_switchStatement = 19, RULE_switchCase = 20, 
		RULE_defaultCase = 21, RULE_breakStatement = 22, RULE_continueStatement = 23, 
		RULE_returnStatement = 24, RULE_expression = 25, RULE_assignmentExpression = 26, 
		RULE_assignmentOperator = 27, RULE_conditionalExpression = 28, RULE_logicalOrExpression = 29, 
		RULE_logicalAndExpression = 30, RULE_equalityExpression = 31, RULE_relationalExpression = 32, 
		RULE_additiveExpression = 33, RULE_multiplicativeExpression = 34, RULE_castExpression = 35, 
		RULE_unaryExpression = 36, RULE_unaryOperator = 37, RULE_postfixExpression = 38, 
		RULE_argumentList = 39, RULE_primaryExpression = 40;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "structDeclaration", "structField", "functionDeclaration", 
			"parameterList", "parameter", "variableDeclaration", "variableDeclarator", 
			"arrayInitializer", "type", "statement", "block", "expressionStatement", 
			"ifStatement", "whileStatement", "forStatement", "forInit", "forUpdate", 
			"switchStatement", "switchCase", "defaultCase", "breakStatement", "continueStatement", 
			"returnStatement", "expression", "assignmentExpression", "assignmentOperator", 
			"conditionalExpression", "logicalOrExpression", "logicalAndExpression", 
			"equalityExpression", "relationalExpression", "additiveExpression", "multiplicativeExpression", 
			"castExpression", "unaryExpression", "unaryOperator", "postfixExpression", 
			"argumentList", "primaryExpression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'float'", "'char'", "'string'", "'void'", "'struct'", 
			"'if'", "'else'", "'while'", "'for'", "'break'", "'continue'", "'return'", 
			"'switch'", "'case'", "'default'", null, null, null, null, null, "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", 
			"'&&'", "'||'", "'!'", "'='", "'+='", "'-='", "'*='", "'/='", "'%='", 
			"'++'", "'--'", "';'", "','", "':'", "'?'", "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "CHAR", "STRING", "VOID", "STRUCT", "IF", "ELSE", 
			"WHILE", "FOR", "BREAK", "CONTINUE", "RETURN", "SWITCH", "CASE", "DEFAULT", 
			"ID", "INT_LITERAL", "FLOAT_LITERAL", "CHAR_LITERAL", "STRING_LITERAL", 
			"PLUS", "MINUS", "MULTIPLY", "DIVIDE", "MODULO", "EQ", "NE", "LT", "GT", 
			"LE", "GE", "AND", "OR", "NOT", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", 
			"MULTIPLY_ASSIGN", "DIVIDE_ASSIGN", "MODULO_ASSIGN", "INCREMENT", "DECREMENT", 
			"SEMICOLON", "COMMA", "COLON", "QUESTION", "LPAREN", "RPAREN", "LBRACE", 
			"RBRACE", "LBRACKET", "RBRACKET", "DOT", "LINE_COMMENT", "BLOCK_COMMENT", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GeminiC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GeminiCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GeminiCParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT))) != 0)) {
				{
				{
				setState(82);
				declaration();
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(88);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public StructDeclarationContext structDeclaration() {
			return getRuleContext(StructDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				structDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				variableDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclarationContext extends ParserRuleContext {
		public TerminalNode STRUCT() { return getToken(GeminiCParser.STRUCT, 0); }
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TerminalNode LBRACE() { return getToken(GeminiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GeminiCParser.RBRACE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public List<StructFieldContext> structField() {
			return getRuleContexts(StructFieldContext.class);
		}
		public StructFieldContext structField(int i) {
			return getRuleContext(StructFieldContext.class,i);
		}
		public StructDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterStructDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitStructDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitStructDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructDeclarationContext structDeclaration() throws RecognitionException {
		StructDeclarationContext _localctx = new StructDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_structDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(STRUCT);
			setState(96);
			match(ID);
			setState(97);
			match(LBRACE);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT))) != 0)) {
				{
				{
				setState(98);
				structField();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			match(RBRACE);
			setState(105);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructFieldContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public StructFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterStructField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitStructField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitStructField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructFieldContext structField() throws RecognitionException {
		StructFieldContext _localctx = new StructFieldContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_structField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			type();
			setState(108);
			match(ID);
			setState(109);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			type();
			setState(112);
			match(ID);
			setState(113);
			match(LPAREN);
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT))) != 0)) {
				{
				setState(114);
				parameterList();
				}
			}

			setState(117);
			match(RPAREN);
			setState(118);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GeminiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GeminiCParser.COMMA, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			parameter();
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(121);
				match(COMMA);
				setState(122);
				parameter();
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public List<TerminalNode> LBRACKET() { return getTokens(GeminiCParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(GeminiCParser.LBRACKET, i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(GeminiCParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(GeminiCParser.RBRACKET, i);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			type();
			setState(129);
			match(ID);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(130);
				match(LBRACKET);
				setState(131);
				match(RBRACKET);
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GeminiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GeminiCParser.COMMA, i);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			type();
			setState(138);
			variableDeclarator();
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(139);
				match(COMMA);
				setState(140);
				variableDeclarator();
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(146);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public List<TerminalNode> LBRACKET() { return getTokens(GeminiCParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(GeminiCParser.LBRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(GeminiCParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(GeminiCParser.RBRACKET, i);
		}
		public TerminalNode ASSIGN() { return getToken(GeminiCParser.ASSIGN, 0); }
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterVariableDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitVariableDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(ID);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(149);
				match(LBRACKET);
				setState(150);
				expression();
				setState(151);
				match(RBRACKET);
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(158);
				match(ASSIGN);
				setState(161);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ID:
				case INT_LITERAL:
				case FLOAT_LITERAL:
				case CHAR_LITERAL:
				case STRING_LITERAL:
				case PLUS:
				case MINUS:
				case NOT:
				case INCREMENT:
				case DECREMENT:
				case LPAREN:
					{
					setState(159);
					expression();
					}
					break;
				case LBRACE:
					{
					setState(160);
					arrayInitializer();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GeminiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GeminiCParser.RBRACE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GeminiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GeminiCParser.COMMA, i);
		}
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(LBRACE);
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(166);
				expression();
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(167);
					match(COMMA);
					setState(168);
					expression();
					}
					}
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(176);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(GeminiCParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(GeminiCParser.FLOAT, 0); }
		public TerminalNode CHAR() { return getToken(GeminiCParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(GeminiCParser.STRING, 0); }
		public TerminalNode VOID() { return getToken(GeminiCParser.VOID, 0); }
		public TerminalNode STRUCT() { return getToken(GeminiCParser.STRUCT, 0); }
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_type);
		try {
			setState(185);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				match(FLOAT);
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				match(CHAR);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(181);
				match(STRING);
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 5);
				{
				setState(182);
				match(VOID);
				}
				break;
			case STRUCT:
				enterOuterAlt(_localctx, 6);
				{
				setState(183);
				match(STRUCT);
				setState(184);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statement);
		try {
			setState(197);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(187);
				block();
				}
				break;
			case ID:
			case INT_LITERAL:
			case FLOAT_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case PLUS:
			case MINUS:
			case NOT:
			case INCREMENT:
			case DECREMENT:
			case SEMICOLON:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(188);
				expressionStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(189);
				ifStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(190);
				whileStatement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(191);
				forStatement();
				}
				break;
			case SWITCH:
				enterOuterAlt(_localctx, 6);
				{
				setState(192);
				switchStatement();
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 7);
				{
				setState(193);
				breakStatement();
				}
				break;
			case CONTINUE:
				enterOuterAlt(_localctx, 8);
				{
				setState(194);
				continueStatement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 9);
				{
				setState(195);
				returnStatement();
				}
				break;
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
			case VOID:
			case STRUCT:
				enterOuterAlt(_localctx, 10);
				{
				setState(196);
				variableDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GeminiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GeminiCParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(LBRACE);
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(200);
				statement();
				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(206);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(208);
				expression();
				}
			}

			setState(211);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GeminiCParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GeminiCParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(IF);
			setState(214);
			match(LPAREN);
			setState(215);
			expression();
			setState(216);
			match(RPAREN);
			setState(217);
			statement();
			setState(220);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(218);
				match(ELSE);
				setState(219);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GeminiCParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(WHILE);
			setState(223);
			match(LPAREN);
			setState(224);
			expression();
			setState(225);
			match(RPAREN);
			setState(226);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GeminiCParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(GeminiCParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(GeminiCParser.SEMICOLON, i);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForUpdateContext forUpdate() {
			return getRuleContext(ForUpdateContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(FOR);
			setState(229);
			match(LPAREN);
			setState(231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(230);
				forInit();
				}
			}

			setState(233);
			match(SEMICOLON);
			setState(235);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(234);
				expression();
				}
			}

			setState(237);
			match(SEMICOLON);
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(238);
				forUpdate();
				}
			}

			setState(241);
			match(RPAREN);
			setState(242);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GeminiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GeminiCParser.COMMA, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_forInit);
		int _la;
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
			case VOID:
			case STRUCT:
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				type();
				setState(245);
				variableDeclarator();
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(246);
					match(COMMA);
					setState(247);
					variableDeclarator();
					}
					}
					setState(252);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case ID:
			case INT_LITERAL:
			case FLOAT_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case PLUS:
			case MINUS:
			case NOT:
			case INCREMENT:
			case DECREMENT:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForUpdateContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForUpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forUpdate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterForUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitForUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitForUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(GeminiCParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GeminiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GeminiCParser.RBRACE, 0); }
		public List<SwitchCaseContext> switchCase() {
			return getRuleContexts(SwitchCaseContext.class);
		}
		public SwitchCaseContext switchCase(int i) {
			return getRuleContext(SwitchCaseContext.class,i);
		}
		public DefaultCaseContext defaultCase() {
			return getRuleContext(DefaultCaseContext.class,0);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(SWITCH);
			setState(259);
			match(LPAREN);
			setState(260);
			expression();
			setState(261);
			match(RPAREN);
			setState(262);
			match(LBRACE);
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(263);
				switchCase();
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(270);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(269);
				defaultCase();
				}
			}

			setState(272);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchCaseContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(GeminiCParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GeminiCParser.COLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterSwitchCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitSwitchCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchCaseContext switchCase() throws RecognitionException {
		SwitchCaseContext _localctx = new SwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_switchCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(CASE);
			setState(275);
			expression();
			setState(276);
			match(COLON);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(277);
				statement();
				}
				}
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultCaseContext extends ParserRuleContext {
		public TerminalNode DEFAULT() { return getToken(GeminiCParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(GeminiCParser.COLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public DefaultCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterDefaultCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitDefaultCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitDefaultCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultCaseContext defaultCase() throws RecognitionException {
		DefaultCaseContext _localctx = new DefaultCaseContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_defaultCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(DEFAULT);
			setState(284);
			match(COLON);
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(285);
				statement();
				}
				}
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(GeminiCParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(BREAK);
			setState(292);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(GeminiCParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			match(CONTINUE);
			setState(295);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(GeminiCParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GeminiCParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(RETURN);
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(298);
				expression();
				}
			}

			setState(301);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			assignmentExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitAssignmentExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_assignmentExpression);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(305);
				conditionalExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(306);
				unaryExpression();
				setState(307);
				assignmentOperator();
				setState(308);
				assignmentExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(GeminiCParser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(GeminiCParser.PLUS_ASSIGN, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(GeminiCParser.MINUS_ASSIGN, 0); }
		public TerminalNode MULTIPLY_ASSIGN() { return getToken(GeminiCParser.MULTIPLY_ASSIGN, 0); }
		public TerminalNode DIVIDE_ASSIGN() { return getToken(GeminiCParser.DIVIDE_ASSIGN, 0); }
		public TerminalNode MODULO_ASSIGN() { return getToken(GeminiCParser.MODULO_ASSIGN, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << PLUS_ASSIGN) | (1L << MINUS_ASSIGN) | (1L << MULTIPLY_ASSIGN) | (1L << DIVIDE_ASSIGN) | (1L << MODULO_ASSIGN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(GeminiCParser.QUESTION, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GeminiCParser.COLON, 0); }
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_conditionalExpression);
		try {
			setState(321);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(314);
				logicalOrExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(315);
				logicalOrExpression(0);
				setState(316);
				match(QUESTION);
				setState(317);
				expression();
				setState(318);
				match(COLON);
				setState(319);
				conditionalExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode OR() { return getToken(GeminiCParser.OR, 0); }
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterLogicalOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitLogicalOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitLogicalOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		return logicalOrExpression(0);
	}

	private LogicalOrExpressionContext logicalOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, _parentState);
		LogicalOrExpressionContext _prevctx = _localctx;
		int _startState = 58;
		enterRecursionRule(_localctx, 58, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(324);
			logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(331);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(326);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(327);
					match(OR);
					setState(328);
					logicalAndExpression(0);
					}
					} 
				}
				setState(333);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public TerminalNode AND() { return getToken(GeminiCParser.AND, 0); }
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitLogicalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		return logicalAndExpression(0);
	}

	private LogicalAndExpressionContext logicalAndExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, _parentState);
		LogicalAndExpressionContext _prevctx = _localctx;
		int _startState = 60;
		enterRecursionRule(_localctx, 60, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(335);
			equalityExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(342);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalAndExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(337);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(338);
					match(AND);
					setState(339);
					equalityExpression(0);
					}
					} 
				}
				setState(344);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public TerminalNode EQ() { return getToken(GeminiCParser.EQ, 0); }
		public TerminalNode NE() { return getToken(GeminiCParser.NE, 0); }
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		return equalityExpression(0);
	}

	private EqualityExpressionContext equalityExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, _parentState);
		EqualityExpressionContext _prevctx = _localctx;
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_equalityExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(346);
			relationalExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(356);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(354);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(348);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(349);
						match(EQ);
						setState(350);
						relationalExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(351);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(352);
						match(NE);
						setState(353);
						relationalExpression(0);
						}
						break;
					}
					} 
				}
				setState(358);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public TerminalNode LT() { return getToken(GeminiCParser.LT, 0); }
		public TerminalNode GT() { return getToken(GeminiCParser.GT, 0); }
		public TerminalNode LE() { return getToken(GeminiCParser.LE, 0); }
		public TerminalNode GE() { return getToken(GeminiCParser.GE, 0); }
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		return relationalExpression(0);
	}

	private RelationalExpressionContext relationalExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, _parentState);
		RelationalExpressionContext _prevctx = _localctx;
		int _startState = 64;
		enterRecursionRule(_localctx, 64, RULE_relationalExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(360);
			additiveExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(376);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(374);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
					case 1:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(362);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(363);
						match(LT);
						setState(364);
						additiveExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(365);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(366);
						match(GT);
						setState(367);
						additiveExpression(0);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(368);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(369);
						match(LE);
						setState(370);
						additiveExpression(0);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(371);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(372);
						match(GE);
						setState(373);
						additiveExpression(0);
						}
						break;
					}
					} 
				}
				setState(378);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(GeminiCParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GeminiCParser.MINUS, 0); }
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		return additiveExpression(0);
	}

	private AdditiveExpressionContext additiveExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, _parentState);
		AdditiveExpressionContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_additiveExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(380);
			multiplicativeExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(390);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(388);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
					case 1:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(382);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(383);
						match(PLUS);
						setState(384);
						multiplicativeExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(385);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(386);
						match(MINUS);
						setState(387);
						multiplicativeExpression(0);
						}
						break;
					}
					} 
				}
				setState(392);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public CastExpressionContext castExpression() {
			return getRuleContext(CastExpressionContext.class,0);
		}
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public TerminalNode MULTIPLY() { return getToken(GeminiCParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(GeminiCParser.DIVIDE, 0); }
		public TerminalNode MODULO() { return getToken(GeminiCParser.MODULO, 0); }
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		return multiplicativeExpression(0);
	}

	private MultiplicativeExpressionContext multiplicativeExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, _parentState);
		MultiplicativeExpressionContext _prevctx = _localctx;
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_multiplicativeExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(394);
			castExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(407);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(405);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(396);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(397);
						match(MULTIPLY);
						setState(398);
						castExpression();
						}
						break;
					case 2:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(399);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(400);
						match(DIVIDE);
						setState(401);
						castExpression();
						}
						break;
					case 3:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(402);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(403);
						match(MODULO);
						setState(404);
						castExpression();
						}
						break;
					}
					} 
				}
				setState(409);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CastExpressionContext extends ParserRuleContext {
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public CastExpressionContext castExpression() {
			return getRuleContext(CastExpressionContext.class,0);
		}
		public CastExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_castExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterCastExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitCastExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CastExpressionContext castExpression() throws RecognitionException {
		CastExpressionContext _localctx = new CastExpressionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_castExpression);
		try {
			setState(416);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(410);
				unaryExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(411);
				match(LPAREN);
				setState(412);
				type();
				setState(413);
				match(RPAREN);
				setState(414);
				castExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_unaryExpression);
		try {
			setState(422);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
			case INT_LITERAL:
			case FLOAT_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(418);
				postfixExpression(0);
				}
				break;
			case PLUS:
			case MINUS:
			case NOT:
			case INCREMENT:
			case DECREMENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(419);
				unaryOperator();
				setState(420);
				unaryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperatorContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(GeminiCParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GeminiCParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(GeminiCParser.NOT, 0); }
		public TerminalNode INCREMENT() { return getToken(GeminiCParser.INCREMENT, 0); }
		public TerminalNode DECREMENT() { return getToken(GeminiCParser.DECREMENT, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_unaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(GeminiCParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(GeminiCParser.RBRACKET, 0); }
		public TerminalNode DOT() { return getToken(GeminiCParser.DOT, 0); }
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode INCREMENT() { return getToken(GeminiCParser.INCREMENT, 0); }
		public TerminalNode DECREMENT() { return getToken(GeminiCParser.DECREMENT, 0); }
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		return postfixExpression(0);
	}

	private PostfixExpressionContext postfixExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, _parentState);
		PostfixExpressionContext _prevctx = _localctx;
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_postfixExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(427);
			primaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(449);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(447);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(429);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(430);
						match(LBRACKET);
						setState(431);
						expression();
						setState(432);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(434);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(435);
						match(DOT);
						setState(436);
						match(ID);
						}
						break;
					case 3:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(437);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(438);
						match(LPAREN);
						setState(440);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
							{
							setState(439);
							argumentList();
							}
						}

						setState(442);
						match(RPAREN);
						}
						break;
					case 4:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(443);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(444);
						match(INCREMENT);
						}
						break;
					case 5:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(445);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(446);
						match(DECREMENT);
						}
						break;
					}
					} 
				}
				setState(451);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GeminiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GeminiCParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			expression();
			setState(457);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(453);
				match(COMMA);
				setState(454);
				expression();
				}
				}
				setState(459);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GeminiCParser.ID, 0); }
		public TerminalNode INT_LITERAL() { return getToken(GeminiCParser.INT_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(GeminiCParser.FLOAT_LITERAL, 0); }
		public TerminalNode CHAR_LITERAL() { return getToken(GeminiCParser.CHAR_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(GeminiCParser.STRING_LITERAL, 0); }
		public TerminalNode LPAREN() { return getToken(GeminiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GeminiCParser.RPAREN, 0); }
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GeminiCListener ) ((GeminiCListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GeminiCVisitor ) return ((GeminiCVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_primaryExpression);
		try {
			setState(469);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(460);
				match(ID);
				}
				break;
			case INT_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(461);
				match(INT_LITERAL);
				}
				break;
			case FLOAT_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(462);
				match(FLOAT_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(463);
				match(CHAR_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(464);
				match(STRING_LITERAL);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(465);
				match(LPAREN);
				setState(466);
				expression();
				setState(467);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 29:
			return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		case 30:
			return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);
		case 31:
			return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);
		case 32:
			return relationalExpression_sempred((RelationalExpressionContext)_localctx, predIndex);
		case 33:
			return additiveExpression_sempred((AdditiveExpressionContext)_localctx, predIndex);
		case 34:
			return multiplicativeExpression_sempred((MultiplicativeExpressionContext)_localctx, predIndex);
		case 38:
			return postfixExpression_sempred((PostfixExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relationalExpression_sempred(RelationalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean additiveExpression_sempred(AdditiveExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean multiplicativeExpression_sempred(MultiplicativeExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 3);
		case 11:
			return precpred(_ctx, 2);
		case 12:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 13:
			return precpred(_ctx, 5);
		case 14:
			return precpred(_ctx, 4);
		case 15:
			return precpred(_ctx, 3);
		case 16:
			return precpred(_ctx, 2);
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3;\u01da\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\7\2"+
		"V\n\2\f\2\16\2Y\13\2\3\2\3\2\3\3\3\3\3\3\5\3`\n\3\3\4\3\4\3\4\3\4\7\4"+
		"f\n\4\f\4\16\4i\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6v"+
		"\n\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7~\n\7\f\7\16\7\u0081\13\7\3\b\3\b\3\b"+
		"\3\b\7\b\u0087\n\b\f\b\16\b\u008a\13\b\3\t\3\t\3\t\3\t\7\t\u0090\n\t\f"+
		"\t\16\t\u0093\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\7\n\u009c\n\n\f\n\16\n"+
		"\u009f\13\n\3\n\3\n\3\n\5\n\u00a4\n\n\5\n\u00a6\n\n\3\13\3\13\3\13\3\13"+
		"\7\13\u00ac\n\13\f\13\16\13\u00af\13\13\5\13\u00b1\n\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00bc\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\5\r\u00c8\n\r\3\16\3\16\7\16\u00cc\n\16\f\16\16\16\u00cf\13\16"+
		"\3\16\3\16\3\17\5\17\u00d4\n\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00df\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\5\22"+
		"\u00ea\n\22\3\22\3\22\5\22\u00ee\n\22\3\22\3\22\5\22\u00f2\n\22\3\22\3"+
		"\22\3\22\3\23\3\23\3\23\3\23\7\23\u00fb\n\23\f\23\16\23\u00fe\13\23\3"+
		"\23\5\23\u0101\n\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u010b"+
		"\n\25\f\25\16\25\u010e\13\25\3\25\5\25\u0111\n\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\7\26\u0119\n\26\f\26\16\26\u011c\13\26\3\27\3\27\3\27\7\27"+
		"\u0121\n\27\f\27\16\27\u0124\13\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32"+
		"\3\32\5\32\u012e\n\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\5\34"+
		"\u0139\n\34\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0144\n"+
		"\36\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u014c\n\37\f\37\16\37\u014f\13"+
		"\37\3 \3 \3 \3 \3 \3 \7 \u0157\n \f \16 \u015a\13 \3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3!\7!\u0165\n!\f!\16!\u0168\13!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\7\"\u0179\n\"\f\"\16\"\u017c\13\"\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\7#\u0187\n#\f#\16#\u018a\13#\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3$\3$\3$\7$\u0198\n$\f$\16$\u019b\13$\3%\3%\3%\3%\3%\3%\5%\u01a3"+
		"\n%\3&\3&\3&\3&\5&\u01a9\n&\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\5(\u01bb\n(\3(\3(\3(\3(\3(\7(\u01c2\n(\f(\16(\u01c5\13(\3)\3)"+
		"\3)\7)\u01ca\n)\f)\16)\u01cd\13)\3*\3*\3*\3*\3*\3*\3*\3*\3*\5*\u01d8\n"+
		"*\3*\2\t<>@BDFN+\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@BDFHJLNPR\2\4\3\2&+\5\2\30\31%%,-\2\u01f5\2W\3\2\2\2\4_\3\2"+
		"\2\2\6a\3\2\2\2\bm\3\2\2\2\nq\3\2\2\2\fz\3\2\2\2\16\u0082\3\2\2\2\20\u008b"+
		"\3\2\2\2\22\u0096\3\2\2\2\24\u00a7\3\2\2\2\26\u00bb\3\2\2\2\30\u00c7\3"+
		"\2\2\2\32\u00c9\3\2\2\2\34\u00d3\3\2\2\2\36\u00d7\3\2\2\2 \u00e0\3\2\2"+
		"\2\"\u00e6\3\2\2\2$\u0100\3\2\2\2&\u0102\3\2\2\2(\u0104\3\2\2\2*\u0114"+
		"\3\2\2\2,\u011d\3\2\2\2.\u0125\3\2\2\2\60\u0128\3\2\2\2\62\u012b\3\2\2"+
		"\2\64\u0131\3\2\2\2\66\u0138\3\2\2\28\u013a\3\2\2\2:\u0143\3\2\2\2<\u0145"+
		"\3\2\2\2>\u0150\3\2\2\2@\u015b\3\2\2\2B\u0169\3\2\2\2D\u017d\3\2\2\2F"+
		"\u018b\3\2\2\2H\u01a2\3\2\2\2J\u01a8\3\2\2\2L\u01aa\3\2\2\2N\u01ac\3\2"+
		"\2\2P\u01c6\3\2\2\2R\u01d7\3\2\2\2TV\5\4\3\2UT\3\2\2\2VY\3\2\2\2WU\3\2"+
		"\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z[\7\2\2\3[\3\3\2\2\2\\`\5\6\4\2]`\5"+
		"\n\6\2^`\5\20\t\2_\\\3\2\2\2_]\3\2\2\2_^\3\2\2\2`\5\3\2\2\2ab\7\b\2\2"+
		"bc\7\23\2\2cg\7\64\2\2df\5\b\5\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2"+
		"\2hj\3\2\2\2ig\3\2\2\2jk\7\65\2\2kl\7.\2\2l\7\3\2\2\2mn\5\26\f\2no\7\23"+
		"\2\2op\7.\2\2p\t\3\2\2\2qr\5\26\f\2rs\7\23\2\2su\7\62\2\2tv\5\f\7\2ut"+
		"\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\7\63\2\2xy\5\32\16\2y\13\3\2\2\2z\177\5"+
		"\16\b\2{|\7/\2\2|~\5\16\b\2}{\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177"+
		"\u0080\3\2\2\2\u0080\r\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\5\26\f\2"+
		"\u0083\u0088\7\23\2\2\u0084\u0085\7\66\2\2\u0085\u0087\7\67\2\2\u0086"+
		"\u0084\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2"+
		"\2\2\u0089\17\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008c\5\26\f\2\u008c\u0091"+
		"\5\22\n\2\u008d\u008e\7/\2\2\u008e\u0090\5\22\n\2\u008f\u008d\3\2\2\2"+
		"\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0094"+
		"\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095\7.\2\2\u0095\21\3\2\2\2\u0096"+
		"\u009d\7\23\2\2\u0097\u0098\7\66\2\2\u0098\u0099\5\64\33\2\u0099\u009a"+
		"\7\67\2\2\u009a\u009c\3\2\2\2\u009b\u0097\3\2\2\2\u009c\u009f\3\2\2\2"+
		"\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a5\3\2\2\2\u009f\u009d"+
		"\3\2\2\2\u00a0\u00a3\7&\2\2\u00a1\u00a4\5\64\33\2\u00a2\u00a4\5\24\13"+
		"\2\u00a3\u00a1\3\2\2\2\u00a3\u00a2\3\2\2\2\u00a4\u00a6\3\2\2\2\u00a5\u00a0"+
		"\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00b0\7\64\2\2\u00a8"+
		"\u00ad\5\64\33\2\u00a9\u00aa\7/\2\2\u00aa\u00ac\5\64\33\2\u00ab\u00a9"+
		"\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00a8\3\2\2\2\u00b0\u00b1\3\2"+
		"\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\7\65\2\2\u00b3\25\3\2\2\2\u00b4\u00bc"+
		"\7\3\2\2\u00b5\u00bc\7\4\2\2\u00b6\u00bc\7\5\2\2\u00b7\u00bc\7\6\2\2\u00b8"+
		"\u00bc\7\7\2\2\u00b9\u00ba\7\b\2\2\u00ba\u00bc\7\23\2\2\u00bb\u00b4\3"+
		"\2\2\2\u00bb\u00b5\3\2\2\2\u00bb\u00b6\3\2\2\2\u00bb\u00b7\3\2\2\2\u00bb"+
		"\u00b8\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\27\3\2\2\2\u00bd\u00c8\5\32\16"+
		"\2\u00be\u00c8\5\34\17\2\u00bf\u00c8\5\36\20\2\u00c0\u00c8\5 \21\2\u00c1"+
		"\u00c8\5\"\22\2\u00c2\u00c8\5(\25\2\u00c3\u00c8\5.\30\2\u00c4\u00c8\5"+
		"\60\31\2\u00c5\u00c8\5\62\32\2\u00c6\u00c8\5\20\t\2\u00c7\u00bd\3\2\2"+
		"\2\u00c7\u00be\3\2\2\2\u00c7\u00bf\3\2\2\2\u00c7\u00c0\3\2\2\2\u00c7\u00c1"+
		"\3\2\2\2\u00c7\u00c2\3\2\2\2\u00c7\u00c3\3\2\2\2\u00c7\u00c4\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c7\u00c6\3\2\2\2\u00c8\31\3\2\2\2\u00c9\u00cd\7\64\2"+
		"\2\u00ca\u00cc\5\30\r\2\u00cb\u00ca\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3\2\2\2\u00cf\u00cd\3\2"+
		"\2\2\u00d0\u00d1\7\65\2\2\u00d1\33\3\2\2\2\u00d2\u00d4\5\64\33\2\u00d3"+
		"\u00d2\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\7."+
		"\2\2\u00d6\35\3\2\2\2\u00d7\u00d8\7\t\2\2\u00d8\u00d9\7\62\2\2\u00d9\u00da"+
		"\5\64\33\2\u00da\u00db\7\63\2\2\u00db\u00de\5\30\r\2\u00dc\u00dd\7\n\2"+
		"\2\u00dd\u00df\5\30\r\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\37\3\2\2\2\u00e0\u00e1\7\13\2\2\u00e1\u00e2\7\62\2\2\u00e2\u00e3\5\64"+
		"\33\2\u00e3\u00e4\7\63\2\2\u00e4\u00e5\5\30\r\2\u00e5!\3\2\2\2\u00e6\u00e7"+
		"\7\f\2\2\u00e7\u00e9\7\62\2\2\u00e8\u00ea\5$\23\2\u00e9\u00e8\3\2\2\2"+
		"\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\7.\2\2\u00ec\u00ee"+
		"\5\64\33\2\u00ed\u00ec\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2"+
		"\u00ef\u00f1\7.\2\2\u00f0\u00f2\5&\24\2\u00f1\u00f0\3\2\2\2\u00f1\u00f2"+
		"\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\7\63\2\2\u00f4\u00f5\5\30\r\2"+
		"\u00f5#\3\2\2\2\u00f6\u00f7\5\26\f\2\u00f7\u00fc\5\22\n\2\u00f8\u00f9"+
		"\7/\2\2\u00f9\u00fb\5\22\n\2\u00fa\u00f8\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc"+
		"\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u0101\3\2\2\2\u00fe\u00fc\3\2"+
		"\2\2\u00ff\u0101\5\64\33\2\u0100\u00f6\3\2\2\2\u0100\u00ff\3\2\2\2\u0101"+
		"%\3\2\2\2\u0102\u0103\5\64\33\2\u0103\'\3\2\2\2\u0104\u0105\7\20\2\2\u0105"+
		"\u0106\7\62\2\2\u0106\u0107\5\64\33\2\u0107\u0108\7\63\2\2\u0108\u010c"+
		"\7\64\2\2\u0109\u010b\5*\26\2\u010a\u0109\3\2\2\2\u010b\u010e\3\2\2\2"+
		"\u010c\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c"+
		"\3\2\2\2\u010f\u0111\5,\27\2\u0110\u010f\3\2\2\2\u0110\u0111\3\2\2\2\u0111"+
		"\u0112\3\2\2\2\u0112\u0113\7\65\2\2\u0113)\3\2\2\2\u0114\u0115\7\21\2"+
		"\2\u0115\u0116\5\64\33\2\u0116\u011a\7\60\2\2\u0117\u0119\5\30\r\2\u0118"+
		"\u0117\3\2\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2"+
		"\2\2\u011b+\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u011e\7\22\2\2\u011e\u0122"+
		"\7\60\2\2\u011f\u0121\5\30\r\2\u0120\u011f\3\2\2\2\u0121\u0124\3\2\2\2"+
		"\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123-\3\2\2\2\u0124\u0122\3"+
		"\2\2\2\u0125\u0126\7\r\2\2\u0126\u0127\7.\2\2\u0127/\3\2\2\2\u0128\u0129"+
		"\7\16\2\2\u0129\u012a\7.\2\2\u012a\61\3\2\2\2\u012b\u012d\7\17\2\2\u012c"+
		"\u012e\5\64\33\2\u012d\u012c\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012f\3"+
		"\2\2\2\u012f\u0130\7.\2\2\u0130\63\3\2\2\2\u0131\u0132\5\66\34\2\u0132"+
		"\65\3\2\2\2\u0133\u0139\5:\36\2\u0134\u0135\5J&\2\u0135\u0136\58\35\2"+
		"\u0136\u0137\5\66\34\2\u0137\u0139\3\2\2\2\u0138\u0133\3\2\2\2\u0138\u0134"+
		"\3\2\2\2\u0139\67\3\2\2\2\u013a\u013b\t\2\2\2\u013b9\3\2\2\2\u013c\u0144"+
		"\5<\37\2\u013d\u013e\5<\37\2\u013e\u013f\7\61\2\2\u013f\u0140\5\64\33"+
		"\2\u0140\u0141\7\60\2\2\u0141\u0142\5:\36\2\u0142\u0144\3\2\2\2\u0143"+
		"\u013c\3\2\2\2\u0143\u013d\3\2\2\2\u0144;\3\2\2\2\u0145\u0146\b\37\1\2"+
		"\u0146\u0147\5> \2\u0147\u014d\3\2\2\2\u0148\u0149\f\3\2\2\u0149\u014a"+
		"\7$\2\2\u014a\u014c\5> \2\u014b\u0148\3\2\2\2\u014c\u014f\3\2\2\2\u014d"+
		"\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e=\3\2\2\2\u014f\u014d\3\2\2\2"+
		"\u0150\u0151\b \1\2\u0151\u0152\5@!\2\u0152\u0158\3\2\2\2\u0153\u0154"+
		"\f\3\2\2\u0154\u0155\7#\2\2\u0155\u0157\5@!\2\u0156\u0153\3\2\2\2\u0157"+
		"\u015a\3\2\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159?\3\2\2\2"+
		"\u015a\u0158\3\2\2\2\u015b\u015c\b!\1\2\u015c\u015d\5B\"\2\u015d\u0166"+
		"\3\2\2\2\u015e\u015f\f\4\2\2\u015f\u0160\7\35\2\2\u0160\u0165\5B\"\2\u0161"+
		"\u0162\f\3\2\2\u0162\u0163\7\36\2\2\u0163\u0165\5B\"\2\u0164\u015e\3\2"+
		"\2\2\u0164\u0161\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0166"+
		"\u0167\3\2\2\2\u0167A\3\2\2\2\u0168\u0166\3\2\2\2\u0169\u016a\b\"\1\2"+
		"\u016a\u016b\5D#\2\u016b\u017a\3\2\2\2\u016c\u016d\f\6\2\2\u016d\u016e"+
		"\7\37\2\2\u016e\u0179\5D#\2\u016f\u0170\f\5\2\2\u0170\u0171\7 \2\2\u0171"+
		"\u0179\5D#\2\u0172\u0173\f\4\2\2\u0173\u0174\7!\2\2\u0174\u0179\5D#\2"+
		"\u0175\u0176\f\3\2\2\u0176\u0177\7\"\2\2\u0177\u0179\5D#\2\u0178\u016c"+
		"\3\2\2\2\u0178\u016f\3\2\2\2\u0178\u0172\3\2\2\2\u0178\u0175\3\2\2\2\u0179"+
		"\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017bC\3\2\2\2"+
		"\u017c\u017a\3\2\2\2\u017d\u017e\b#\1\2\u017e\u017f\5F$\2\u017f\u0188"+
		"\3\2\2\2\u0180\u0181\f\4\2\2\u0181\u0182\7\30\2\2\u0182\u0187\5F$\2\u0183"+
		"\u0184\f\3\2\2\u0184\u0185\7\31\2\2\u0185\u0187\5F$\2\u0186\u0180\3\2"+
		"\2\2\u0186\u0183\3\2\2\2\u0187\u018a\3\2\2\2\u0188\u0186\3\2\2\2\u0188"+
		"\u0189\3\2\2\2\u0189E\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018c\b$\1\2\u018c"+
		"\u018d\5H%\2\u018d\u0199\3\2\2\2\u018e\u018f\f\5\2\2\u018f\u0190\7\32"+
		"\2\2\u0190\u0198\5H%\2\u0191\u0192\f\4\2\2\u0192\u0193\7\33\2\2\u0193"+
		"\u0198\5H%\2\u0194\u0195\f\3\2\2\u0195\u0196\7\34\2\2\u0196\u0198\5H%"+
		"\2\u0197\u018e\3\2\2\2\u0197\u0191\3\2\2\2\u0197\u0194\3\2\2\2\u0198\u019b"+
		"\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019aG\3\2\2\2\u019b"+
		"\u0199\3\2\2\2\u019c\u01a3\5J&\2\u019d\u019e\7\62\2\2\u019e\u019f\5\26"+
		"\f\2\u019f\u01a0\7\63\2\2\u01a0\u01a1\5H%\2\u01a1\u01a3\3\2\2\2\u01a2"+
		"\u019c\3\2\2\2\u01a2\u019d\3\2\2\2\u01a3I\3\2\2\2\u01a4\u01a9\5N(\2\u01a5"+
		"\u01a6\5L\'\2\u01a6\u01a7\5J&\2\u01a7\u01a9\3\2\2\2\u01a8\u01a4\3\2\2"+
		"\2\u01a8\u01a5\3\2\2\2\u01a9K\3\2\2\2\u01aa\u01ab\t\3\2\2\u01abM\3\2\2"+
		"\2\u01ac\u01ad\b(\1\2\u01ad\u01ae\5R*\2\u01ae\u01c3\3\2\2\2\u01af\u01b0"+
		"\f\7\2\2\u01b0\u01b1\7\66\2\2\u01b1\u01b2\5\64\33\2\u01b2\u01b3\7\67\2"+
		"\2\u01b3\u01c2\3\2\2\2\u01b4\u01b5\f\6\2\2\u01b5\u01b6\78\2\2\u01b6\u01c2"+
		"\7\23\2\2\u01b7\u01b8\f\5\2\2\u01b8\u01ba\7\62\2\2\u01b9\u01bb\5P)\2\u01ba"+
		"\u01b9\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01c2\7\63"+
		"\2\2\u01bd\u01be\f\4\2\2\u01be\u01c2\7,\2\2\u01bf\u01c0\f\3\2\2\u01c0"+
		"\u01c2\7-\2\2\u01c1\u01af\3\2\2\2\u01c1\u01b4\3\2\2\2\u01c1\u01b7\3\2"+
		"\2\2\u01c1\u01bd\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c2\u01c5\3\2\2\2\u01c3"+
		"\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4O\3\2\2\2\u01c5\u01c3\3\2\2\2"+
		"\u01c6\u01cb\5\64\33\2\u01c7\u01c8\7/\2\2\u01c8\u01ca\5\64\33\2\u01c9"+
		"\u01c7\3\2\2\2\u01ca\u01cd\3\2\2\2\u01cb\u01c9\3\2\2\2\u01cb\u01cc\3\2"+
		"\2\2\u01ccQ\3\2\2\2\u01cd\u01cb\3\2\2\2\u01ce\u01d8\7\23\2\2\u01cf\u01d8"+
		"\7\24\2\2\u01d0\u01d8\7\25\2\2\u01d1\u01d8\7\26\2\2\u01d2\u01d8\7\27\2"+
		"\2\u01d3\u01d4\7\62\2\2\u01d4\u01d5\5\64\33\2\u01d5\u01d6\7\63\2\2\u01d6"+
		"\u01d8\3\2\2\2\u01d7\u01ce\3\2\2\2\u01d7\u01cf\3\2\2\2\u01d7\u01d0\3\2"+
		"\2\2\u01d7\u01d1\3\2\2\2\u01d7\u01d2\3\2\2\2\u01d7\u01d3\3\2\2\2\u01d8"+
		"S\3\2\2\2\60W_gu\177\u0088\u0091\u009d\u00a3\u00a5\u00ad\u00b0\u00bb\u00c7"+
		"\u00cd\u00d3\u00de\u00e9\u00ed\u00f1\u00fc\u0100\u010c\u0110\u011a\u0122"+
		"\u012d\u0138\u0143\u014d\u0158\u0164\u0166\u0178\u017a\u0186\u0188\u0197"+
		"\u0199\u01a2\u01a8\u01ba\u01c1\u01c3\u01cb\u01d7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}