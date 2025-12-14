// Generated from com/wei/WeiC.g4 by ANTLR 4.9.3
package com.wei;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WeiCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FLOAT=2, CHAR=3, STRING=4, VOID=5, STRUCT=6, IF=7, ELSE=8, WHILE=9, 
		FOR=10, DO=11, BREAK=12, CONTINUE=13, RETURN=14, SWITCH=15, CASE=16, DEFAULT=17, 
		CONST=18, SIZEOF=19, STAR=20, AMPERSAND=21, ID=22, INT_LITERAL=23, FLOAT_LITERAL=24, 
		CHAR_LITERAL=25, STRING_LITERAL=26, PLUS=27, MINUS=28, DIVIDE=29, MODULO=30, 
		EQ=31, NE=32, LT=33, GT=34, LE=35, GE=36, AND=37, OR=38, NOT=39, BITWISE_OR=40, 
		BITWISE_XOR=41, BITWISE_NOT=42, LSHIFT=43, RSHIFT=44, ASSIGN=45, PLUS_ASSIGN=46, 
		MINUS_ASSIGN=47, MULTIPLY_ASSIGN=48, DIVIDE_ASSIGN=49, MODULO_ASSIGN=50, 
		INCREMENT=51, DECREMENT=52, SEMICOLON=53, COMMA=54, COLON=55, QUESTION=56, 
		LPAREN=57, RPAREN=58, LBRACE=59, RBRACE=60, LBRACKET=61, RBRACKET=62, 
		DOT=63, LINE_COMMENT=64, BLOCK_COMMENT=65, WS=66;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_structDeclaration = 2, RULE_structField = 3, 
		RULE_functionDeclaration = 4, RULE_parameterList = 5, RULE_parameter = 6, 
		RULE_typeName = 7, RULE_declarator = 8, RULE_variableDeclaration = 9, 
		RULE_variableDeclarator = 10, RULE_unaryOperator = 11, RULE_type = 12, 
		RULE_statement = 13, RULE_block = 14, RULE_expressionStatement = 15, RULE_ifStatement = 16, 
		RULE_whileStatement = 17, RULE_doWhileStatement = 18, RULE_forStatement = 19, 
		RULE_forVariableDeclaration = 20, RULE_forInit = 21, RULE_forUpdate = 22, 
		RULE_switchStatement = 23, RULE_switchCase = 24, RULE_defaultCase = 25, 
		RULE_breakStatement = 26, RULE_continueStatement = 27, RULE_returnStatement = 28, 
		RULE_expression = 29, RULE_assignmentExpression = 30, RULE_assignmentOperator = 31, 
		RULE_conditionalExpression = 32, RULE_logicalOrExpression = 33, RULE_logicalAndExpression = 34, 
		RULE_bitwiseOrExpression = 35, RULE_bitwiseXorExpression = 36, RULE_bitwiseAndExpression = 37, 
		RULE_equalityExpression = 38, RULE_relationalExpression = 39, RULE_shiftExpression = 40, 
		RULE_additiveExpression = 41, RULE_multiplicativeExpression = 42, RULE_unaryExpression = 43, 
		RULE_postfixExpression = 44, RULE_argumentList = 45, RULE_primaryExpression = 46;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "structDeclaration", "structField", "functionDeclaration", 
			"parameterList", "parameter", "typeName", "declarator", "variableDeclaration", 
			"variableDeclarator", "unaryOperator", "type", "statement", "block", 
			"expressionStatement", "ifStatement", "whileStatement", "doWhileStatement", 
			"forStatement", "forVariableDeclaration", "forInit", "forUpdate", "switchStatement", 
			"switchCase", "defaultCase", "breakStatement", "continueStatement", "returnStatement", 
			"expression", "assignmentExpression", "assignmentOperator", "conditionalExpression", 
			"logicalOrExpression", "logicalAndExpression", "bitwiseOrExpression", 
			"bitwiseXorExpression", "bitwiseAndExpression", "equalityExpression", 
			"relationalExpression", "shiftExpression", "additiveExpression", "multiplicativeExpression", 
			"unaryExpression", "postfixExpression", "argumentList", "primaryExpression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'float'", "'char'", "'string'", "'void'", "'struct'", 
			"'if'", "'else'", "'while'", "'for'", "'do'", "'break'", "'continue'", 
			"'return'", "'switch'", "'case'", "'default'", "'const'", "'sizeof'", 
			"'*'", "'&'", null, null, null, null, null, "'+'", "'-'", "'/'", "'%'", 
			"'=='", "'!='", "'<'", "'>'", "'<='", "'>='", "'&&'", "'||'", "'!'", 
			"'|'", "'^'", "'~'", "'<<'", "'>>'", "'='", "'+='", "'-='", "'*='", "'/='", 
			"'%='", "'++'", "'--'", "';'", "','", "':'", "'?'", "'('", "')'", "'{'", 
			"'}'", "'['", "']'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "CHAR", "STRING", "VOID", "STRUCT", "IF", "ELSE", 
			"WHILE", "FOR", "DO", "BREAK", "CONTINUE", "RETURN", "SWITCH", "CASE", 
			"DEFAULT", "CONST", "SIZEOF", "STAR", "AMPERSAND", "ID", "INT_LITERAL", 
			"FLOAT_LITERAL", "CHAR_LITERAL", "STRING_LITERAL", "PLUS", "MINUS", "DIVIDE", 
			"MODULO", "EQ", "NE", "LT", "GT", "LE", "GE", "AND", "OR", "NOT", "BITWISE_OR", 
			"BITWISE_XOR", "BITWISE_NOT", "LSHIFT", "RSHIFT", "ASSIGN", "PLUS_ASSIGN", 
			"MINUS_ASSIGN", "MULTIPLY_ASSIGN", "DIVIDE_ASSIGN", "MODULO_ASSIGN", 
			"INCREMENT", "DECREMENT", "SEMICOLON", "COMMA", "COLON", "QUESTION", 
			"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "DOT", 
			"LINE_COMMENT", "BLOCK_COMMENT", "WS"
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
	public String getGrammarFileName() { return "WeiC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WeiCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(WeiCParser.EOF, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitProgram(this);
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
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << CONST))) != 0)) {
				{
				{
				setState(94);
				declaration();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(100);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				structDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(103);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(104);
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
		public TerminalNode STRUCT() { return getToken(WeiCParser.STRUCT, 0); }
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public TerminalNode LBRACE() { return getToken(WeiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(WeiCParser.RBRACE, 0); }
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterStructDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitStructDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitStructDeclaration(this);
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
			setState(107);
			match(STRUCT);
			setState(108);
			match(ID);
			setState(109);
			match(LBRACE);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << CONST))) != 0)) {
				{
				{
				setState(110);
				structField();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(RBRACE);
			setState(117);
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
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public StructFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterStructField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitStructField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitStructField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructFieldContext structField() throws RecognitionException {
		StructFieldContext _localctx = new StructFieldContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_structField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			type();
			setState(120);
			declarator();
			setState(121);
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
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> STAR() { return getTokens(WeiCParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(WeiCParser.STAR, i);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
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
			setState(123);
			type();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STAR) {
				{
				{
				setState(124);
				match(STAR);
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130);
			match(ID);
			setState(131);
			match(LPAREN);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << CONST))) != 0)) {
				{
				setState(132);
				parameterList();
				}
			}

			setState(135);
			match(RPAREN);
			setState(136);
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
		public List<TerminalNode> COMMA() { return getTokens(WeiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WeiCParser.COMMA, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitParameterList(this);
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
			setState(138);
			parameter();
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(139);
				match(COMMA);
				setState(140);
				parameter();
				}
				}
				setState(145);
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
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			type();
			setState(147);
			declarator();
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

	public static class TypeNameContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> STAR() { return getTokens(WeiCParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(WeiCParser.STAR, i);
		}
		public List<TerminalNode> AMPERSAND() { return getTokens(WeiCParser.AMPERSAND); }
		public TerminalNode AMPERSAND(int i) {
			return getToken(WeiCParser.AMPERSAND, i);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			type();
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STAR) {
				{
				{
				setState(150);
				match(STAR);
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AMPERSAND) {
				{
				{
				setState(156);
				match(AMPERSAND);
				}
				}
				setState(161);
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

	public static class DeclaratorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public List<TerminalNode> STAR() { return getTokens(WeiCParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(WeiCParser.STAR, i);
		}
		public List<TerminalNode> AMPERSAND() { return getTokens(WeiCParser.AMPERSAND); }
		public TerminalNode AMPERSAND(int i) {
			return getToken(WeiCParser.AMPERSAND, i);
		}
		public List<TerminalNode> LBRACKET() { return getTokens(WeiCParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(WeiCParser.LBRACKET, i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(WeiCParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(WeiCParser.RBRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public DeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaratorContext declarator() throws RecognitionException {
		DeclaratorContext _localctx = new DeclaratorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_declarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STAR) {
				{
				{
				setState(162);
				match(STAR);
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AMPERSAND) {
				{
				{
				setState(168);
				match(AMPERSAND);
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(174);
			match(ID);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(175);
				match(LBRACKET);
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
					{
					setState(176);
					expression();
					}
				}

				setState(179);
				match(RBRACKET);
				}
				}
				setState(184);
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
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(WeiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WeiCParser.COMMA, i);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			type();
			setState(186);
			variableDeclarator();
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(187);
				match(COMMA);
				setState(188);
				variableDeclarator();
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(194);
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
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(WeiCParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterVariableDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitVariableDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			declarator();
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(197);
				match(ASSIGN);
				setState(198);
				expression();
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

	public static class UnaryOperatorContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(WeiCParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WeiCParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(WeiCParser.NOT, 0); }
		public TerminalNode BITWISE_NOT() { return getToken(WeiCParser.BITWISE_NOT, 0); }
		public TerminalNode STAR() { return getToken(WeiCParser.STAR, 0); }
		public TerminalNode AMPERSAND() { return getToken(WeiCParser.AMPERSAND, 0); }
		public TerminalNode INCREMENT() { return getToken(WeiCParser.INCREMENT, 0); }
		public TerminalNode DECREMENT() { return getToken(WeiCParser.DECREMENT, 0); }
		public TerminalNode SIZEOF() { return getToken(WeiCParser.SIZEOF, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_unaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT))) != 0)) ) {
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

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(WeiCParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(WeiCParser.FLOAT, 0); }
		public TerminalNode CHAR() { return getToken(WeiCParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(WeiCParser.STRING, 0); }
		public TerminalNode VOID() { return getToken(WeiCParser.VOID, 0); }
		public List<TerminalNode> CONST() { return getTokens(WeiCParser.CONST); }
		public TerminalNode CONST(int i) {
			return getToken(WeiCParser.CONST, i);
		}
		public TerminalNode STRUCT() { return getToken(WeiCParser.STRUCT, 0); }
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type);
		int _la;
		try {
			setState(218);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==CONST) {
					{
					{
					setState(203);
					match(CONST);
					}
					}
					setState(208);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(209);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==CONST) {
					{
					{
					setState(210);
					match(CONST);
					}
					}
					setState(215);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(216);
				match(STRUCT);
				setState(217);
				match(ID);
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
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_statement);
		try {
			setState(231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				block();
				}
				break;
			case SIZEOF:
			case STAR:
			case AMPERSAND:
			case ID:
			case INT_LITERAL:
			case FLOAT_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case PLUS:
			case MINUS:
			case NOT:
			case BITWISE_NOT:
			case INCREMENT:
			case DECREMENT:
			case SEMICOLON:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(221);
				expressionStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				ifStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(223);
				whileStatement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(224);
				forStatement();
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 6);
				{
				setState(225);
				doWhileStatement();
				}
				break;
			case SWITCH:
				enterOuterAlt(_localctx, 7);
				{
				setState(226);
				switchStatement();
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 8);
				{
				setState(227);
				breakStatement();
				}
				break;
			case CONTINUE:
				enterOuterAlt(_localctx, 9);
				{
				setState(228);
				continueStatement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 10);
				{
				setState(229);
				returnStatement();
				}
				break;
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
			case VOID:
			case STRUCT:
			case CONST:
				enterOuterAlt(_localctx, 11);
				{
				setState(230);
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
		public TerminalNode LBRACE() { return getToken(WeiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(WeiCParser.RBRACE, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(LBRACE);
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DO) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << CONST) | (1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(234);
				statement();
				}
				}
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(240);
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
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(242);
				expression();
				}
			}

			setState(245);
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
		public TerminalNode IF() { return getToken(WeiCParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(WeiCParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(IF);
			setState(248);
			match(LPAREN);
			setState(249);
			expression();
			setState(250);
			match(RPAREN);
			setState(251);
			statement();
			setState(254);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(252);
				match(ELSE);
				setState(253);
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
		public TerminalNode WHILE() { return getToken(WeiCParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(WHILE);
			setState(257);
			match(LPAREN);
			setState(258);
			expression();
			setState(259);
			match(RPAREN);
			setState(260);
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

	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(WeiCParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(WeiCParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitDoWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(DO);
			setState(263);
			statement();
			setState(264);
			match(WHILE);
			setState(265);
			match(LPAREN);
			setState(266);
			expression();
			setState(267);
			match(RPAREN);
			setState(268);
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

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(WeiCParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(WeiCParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(WeiCParser.SEMICOLON, i);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(FOR);
			setState(271);
			match(LPAREN);
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << CONST) | (1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(272);
				forInit();
				}
			}

			setState(275);
			match(SEMICOLON);
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(276);
				expression();
				}
			}

			setState(279);
			match(SEMICOLON);
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(280);
				forUpdate();
				}
			}

			setState(283);
			match(RPAREN);
			setState(284);
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

	public static class ForVariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WeiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WeiCParser.COMMA, i);
		}
		public ForVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forVariableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterForVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitForVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitForVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForVariableDeclarationContext forVariableDeclaration() throws RecognitionException {
		ForVariableDeclarationContext _localctx = new ForVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_forVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			type();
			setState(287);
			variableDeclarator();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(288);
				match(COMMA);
				setState(289);
				variableDeclarator();
				}
				}
				setState(294);
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

	public static class ForInitContext extends ParserRuleContext {
		public ForVariableDeclarationContext forVariableDeclaration() {
			return getRuleContext(ForVariableDeclarationContext.class,0);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_forInit);
		try {
			setState(297);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
			case VOID:
			case STRUCT:
			case CONST:
				enterOuterAlt(_localctx, 1);
				{
				setState(295);
				forVariableDeclaration();
				}
				break;
			case SIZEOF:
			case STAR:
			case AMPERSAND:
			case ID:
			case INT_LITERAL:
			case FLOAT_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case PLUS:
			case MINUS:
			case NOT:
			case BITWISE_NOT:
			case INCREMENT:
			case DECREMENT:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(296);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterForUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitForUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitForUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
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
		public TerminalNode SWITCH() { return getToken(WeiCParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(WeiCParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(WeiCParser.RBRACE, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			match(SWITCH);
			setState(302);
			match(LPAREN);
			setState(303);
			expression();
			setState(304);
			match(RPAREN);
			setState(305);
			match(LBRACE);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(306);
				switchCase();
				}
				}
				setState(311);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(312);
				defaultCase();
				}
			}

			setState(315);
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
		public TerminalNode CASE() { return getToken(WeiCParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(WeiCParser.COLON, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterSwitchCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitSwitchCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchCaseContext switchCase() throws RecognitionException {
		SwitchCaseContext _localctx = new SwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_switchCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			match(CASE);
			setState(318);
			expression();
			setState(319);
			match(COLON);
			setState(323);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DO) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << CONST) | (1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(320);
				statement();
				}
				}
				setState(325);
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
		public TerminalNode DEFAULT() { return getToken(WeiCParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(WeiCParser.COLON, 0); }
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterDefaultCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitDefaultCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitDefaultCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultCaseContext defaultCase() throws RecognitionException {
		DefaultCaseContext _localctx = new DefaultCaseContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_defaultCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			match(DEFAULT);
			setState(327);
			match(COLON);
			setState(331);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << VOID) | (1L << STRUCT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DO) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << SWITCH) | (1L << CONST) | (1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << SEMICOLON) | (1L << LPAREN) | (1L << LBRACE))) != 0)) {
				{
				{
				setState(328);
				statement();
				}
				}
				setState(333);
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
		public TerminalNode BREAK() { return getToken(WeiCParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			match(BREAK);
			setState(335);
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
		public TerminalNode CONTINUE() { return getToken(WeiCParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(CONTINUE);
			setState(338);
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
		public TerminalNode RETURN() { return getToken(WeiCParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(WeiCParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(RETURN);
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
				{
				setState(341);
				expression();
				}
			}

			setState(344);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
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
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitAssignmentExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_assignmentExpression);
		try {
			setState(353);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(348);
				conditionalExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(349);
				unaryExpression();
				setState(350);
				assignmentOperator();
				setState(351);
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
		public TerminalNode ASSIGN() { return getToken(WeiCParser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(WeiCParser.PLUS_ASSIGN, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(WeiCParser.MINUS_ASSIGN, 0); }
		public TerminalNode MULTIPLY_ASSIGN() { return getToken(WeiCParser.MULTIPLY_ASSIGN, 0); }
		public TerminalNode DIVIDE_ASSIGN() { return getToken(WeiCParser.DIVIDE_ASSIGN, 0); }
		public TerminalNode MODULO_ASSIGN() { return getToken(WeiCParser.MODULO_ASSIGN, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
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
		public TerminalNode QUESTION() { return getToken(WeiCParser.QUESTION, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(WeiCParser.COLON, 0); }
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_conditionalExpression);
		try {
			setState(364);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(357);
				logicalOrExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				logicalOrExpression(0);
				setState(359);
				match(QUESTION);
				setState(360);
				expression();
				setState(361);
				match(COLON);
				setState(362);
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
		public TerminalNode OR() { return getToken(WeiCParser.OR, 0); }
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterLogicalOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitLogicalOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitLogicalOrExpression(this);
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
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(367);
			logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(374);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(369);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(370);
					match(OR);
					setState(371);
					logicalAndExpression(0);
					}
					} 
				}
				setState(376);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
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
		public BitwiseOrExpressionContext bitwiseOrExpression() {
			return getRuleContext(BitwiseOrExpressionContext.class,0);
		}
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public TerminalNode AND() { return getToken(WeiCParser.AND, 0); }
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitLogicalAndExpression(this);
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
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(378);
			bitwiseOrExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(385);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalAndExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(380);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(381);
					match(AND);
					setState(382);
					bitwiseOrExpression(0);
					}
					} 
				}
				setState(387);
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

	public static class BitwiseOrExpressionContext extends ParserRuleContext {
		public BitwiseXorExpressionContext bitwiseXorExpression() {
			return getRuleContext(BitwiseXorExpressionContext.class,0);
		}
		public BitwiseOrExpressionContext bitwiseOrExpression() {
			return getRuleContext(BitwiseOrExpressionContext.class,0);
		}
		public TerminalNode BITWISE_OR() { return getToken(WeiCParser.BITWISE_OR, 0); }
		public BitwiseOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterBitwiseOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitBitwiseOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitBitwiseOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseOrExpressionContext bitwiseOrExpression() throws RecognitionException {
		return bitwiseOrExpression(0);
	}

	private BitwiseOrExpressionContext bitwiseOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BitwiseOrExpressionContext _localctx = new BitwiseOrExpressionContext(_ctx, _parentState);
		BitwiseOrExpressionContext _prevctx = _localctx;
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_bitwiseOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(389);
			bitwiseXorExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(396);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BitwiseOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_bitwiseOrExpression);
					setState(391);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(392);
					match(BITWISE_OR);
					setState(393);
					bitwiseXorExpression(0);
					}
					} 
				}
				setState(398);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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

	public static class BitwiseXorExpressionContext extends ParserRuleContext {
		public BitwiseAndExpressionContext bitwiseAndExpression() {
			return getRuleContext(BitwiseAndExpressionContext.class,0);
		}
		public BitwiseXorExpressionContext bitwiseXorExpression() {
			return getRuleContext(BitwiseXorExpressionContext.class,0);
		}
		public TerminalNode BITWISE_XOR() { return getToken(WeiCParser.BITWISE_XOR, 0); }
		public BitwiseXorExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseXorExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterBitwiseXorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitBitwiseXorExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitBitwiseXorExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseXorExpressionContext bitwiseXorExpression() throws RecognitionException {
		return bitwiseXorExpression(0);
	}

	private BitwiseXorExpressionContext bitwiseXorExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BitwiseXorExpressionContext _localctx = new BitwiseXorExpressionContext(_ctx, _parentState);
		BitwiseXorExpressionContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_bitwiseXorExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(400);
			bitwiseAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(407);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BitwiseXorExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_bitwiseXorExpression);
					setState(402);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(403);
					match(BITWISE_XOR);
					setState(404);
					bitwiseAndExpression(0);
					}
					} 
				}
				setState(409);
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

	public static class BitwiseAndExpressionContext extends ParserRuleContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public BitwiseAndExpressionContext bitwiseAndExpression() {
			return getRuleContext(BitwiseAndExpressionContext.class,0);
		}
		public TerminalNode AMPERSAND() { return getToken(WeiCParser.AMPERSAND, 0); }
		public BitwiseAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterBitwiseAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitBitwiseAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitBitwiseAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseAndExpressionContext bitwiseAndExpression() throws RecognitionException {
		return bitwiseAndExpression(0);
	}

	private BitwiseAndExpressionContext bitwiseAndExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BitwiseAndExpressionContext _localctx = new BitwiseAndExpressionContext(_ctx, _parentState);
		BitwiseAndExpressionContext _prevctx = _localctx;
		int _startState = 74;
		enterRecursionRule(_localctx, 74, RULE_bitwiseAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(411);
			equalityExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(418);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BitwiseAndExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_bitwiseAndExpression);
					setState(413);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(414);
					match(AMPERSAND);
					setState(415);
					equalityExpression(0);
					}
					} 
				}
				setState(420);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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
		public TerminalNode EQ() { return getToken(WeiCParser.EQ, 0); }
		public TerminalNode NE() { return getToken(WeiCParser.NE, 0); }
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitEqualityExpression(this);
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
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_equalityExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(422);
			relationalExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(432);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(430);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
					case 1:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(424);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(425);
						match(EQ);
						setState(426);
						relationalExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(427);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(428);
						match(NE);
						setState(429);
						relationalExpression(0);
						}
						break;
					}
					} 
				}
				setState(434);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
		public ShiftExpressionContext shiftExpression() {
			return getRuleContext(ShiftExpressionContext.class,0);
		}
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public TerminalNode LT() { return getToken(WeiCParser.LT, 0); }
		public TerminalNode GT() { return getToken(WeiCParser.GT, 0); }
		public TerminalNode LE() { return getToken(WeiCParser.LE, 0); }
		public TerminalNode GE() { return getToken(WeiCParser.GE, 0); }
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitRelationalExpression(this);
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
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_relationalExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(436);
			shiftExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(452);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(450);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(438);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(439);
						match(LT);
						setState(440);
						shiftExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(441);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(442);
						match(GT);
						setState(443);
						shiftExpression(0);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(444);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(445);
						match(LE);
						setState(446);
						shiftExpression(0);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(447);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(448);
						match(GE);
						setState(449);
						shiftExpression(0);
						}
						break;
					}
					} 
				}
				setState(454);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
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

	public static class ShiftExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public ShiftExpressionContext shiftExpression() {
			return getRuleContext(ShiftExpressionContext.class,0);
		}
		public TerminalNode LSHIFT() { return getToken(WeiCParser.LSHIFT, 0); }
		public TerminalNode RSHIFT() { return getToken(WeiCParser.RSHIFT, 0); }
		public ShiftExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterShiftExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitShiftExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExpressionContext shiftExpression() throws RecognitionException {
		return shiftExpression(0);
	}

	private ShiftExpressionContext shiftExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ShiftExpressionContext _localctx = new ShiftExpressionContext(_ctx, _parentState);
		ShiftExpressionContext _prevctx = _localctx;
		int _startState = 80;
		enterRecursionRule(_localctx, 80, RULE_shiftExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(456);
			additiveExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(466);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(464);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new ShiftExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpression);
						setState(458);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(459);
						match(LSHIFT);
						setState(460);
						additiveExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new ShiftExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpression);
						setState(461);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(462);
						match(RSHIFT);
						setState(463);
						additiveExpression(0);
						}
						break;
					}
					} 
				}
				setState(468);
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

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(WeiCParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WeiCParser.MINUS, 0); }
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitAdditiveExpression(this);
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
		int _startState = 82;
		enterRecursionRule(_localctx, 82, RULE_additiveExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(470);
			multiplicativeExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(480);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(478);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
					case 1:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(472);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(473);
						match(PLUS);
						setState(474);
						multiplicativeExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(475);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(476);
						match(MINUS);
						setState(477);
						multiplicativeExpression(0);
						}
						break;
					}
					} 
				}
				setState(482);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
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
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public TerminalNode STAR() { return getToken(WeiCParser.STAR, 0); }
		public TerminalNode DIVIDE() { return getToken(WeiCParser.DIVIDE, 0); }
		public TerminalNode MODULO() { return getToken(WeiCParser.MODULO, 0); }
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
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
		int _startState = 84;
		enterRecursionRule(_localctx, 84, RULE_multiplicativeExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(484);
			unaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(497);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(495);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(486);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(487);
						match(STAR);
						setState(488);
						unaryExpression();
						}
						break;
					case 2:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(489);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(490);
						match(DIVIDE);
						setState(491);
						unaryExpression();
						}
						break;
					case 3:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(492);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(493);
						match(MODULO);
						setState(494);
						unaryExpression();
						}
						break;
					}
					} 
				}
				setState(499);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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

	public static class UnaryExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_unaryExpression);
		try {
			setState(509);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(500);
				match(LPAREN);
				setState(501);
				typeName();
				setState(502);
				match(RPAREN);
				setState(503);
				unaryExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(505);
				postfixExpression(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(506);
				unaryOperator();
				setState(507);
				unaryExpression();
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

	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(WeiCParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(WeiCParser.RBRACKET, 0); }
		public TerminalNode DOT() { return getToken(WeiCParser.DOT, 0); }
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode INCREMENT() { return getToken(WeiCParser.INCREMENT, 0); }
		public TerminalNode DECREMENT() { return getToken(WeiCParser.DECREMENT, 0); }
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitPostfixExpression(this);
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
		int _startState = 88;
		enterRecursionRule(_localctx, 88, RULE_postfixExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(512);
			primaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(534);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(532);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(514);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(515);
						match(LBRACKET);
						setState(516);
						expression();
						setState(517);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(519);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(520);
						match(DOT);
						setState(521);
						match(ID);
						}
						break;
					case 3:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(522);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(523);
						match(LPAREN);
						setState(525);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIZEOF) | (1L << STAR) | (1L << AMPERSAND) | (1L << ID) | (1L << INT_LITERAL) | (1L << FLOAT_LITERAL) | (1L << CHAR_LITERAL) | (1L << STRING_LITERAL) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << BITWISE_NOT) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << LPAREN))) != 0)) {
							{
							setState(524);
							argumentList();
							}
						}

						setState(527);
						match(RPAREN);
						}
						break;
					case 4:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(528);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(529);
						match(INCREMENT);
						}
						break;
					case 5:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(530);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(531);
						match(DECREMENT);
						}
						break;
					}
					} 
				}
				setState(536);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
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
		public List<TerminalNode> COMMA() { return getTokens(WeiCParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WeiCParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
			expression();
			setState(542);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(538);
				match(COMMA);
				setState(539);
				expression();
				}
				}
				setState(544);
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
		public TerminalNode ID() { return getToken(WeiCParser.ID, 0); }
		public TerminalNode INT_LITERAL() { return getToken(WeiCParser.INT_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(WeiCParser.FLOAT_LITERAL, 0); }
		public TerminalNode CHAR_LITERAL() { return getToken(WeiCParser.CHAR_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(WeiCParser.STRING_LITERAL, 0); }
		public TerminalNode LPAREN() { return getToken(WeiCParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WeiCParser.RPAREN, 0); }
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WeiCListener ) ((WeiCListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WeiCVisitor ) return ((WeiCVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_primaryExpression);
		try {
			setState(554);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(545);
				match(ID);
				}
				break;
			case INT_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(546);
				match(INT_LITERAL);
				}
				break;
			case FLOAT_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(547);
				match(FLOAT_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(548);
				match(CHAR_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(549);
				match(STRING_LITERAL);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(550);
				match(LPAREN);
				setState(551);
				expression();
				setState(552);
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
		case 33:
			return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		case 34:
			return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);
		case 35:
			return bitwiseOrExpression_sempred((BitwiseOrExpressionContext)_localctx, predIndex);
		case 36:
			return bitwiseXorExpression_sempred((BitwiseXorExpressionContext)_localctx, predIndex);
		case 37:
			return bitwiseAndExpression_sempred((BitwiseAndExpressionContext)_localctx, predIndex);
		case 38:
			return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);
		case 39:
			return relationalExpression_sempred((RelationalExpressionContext)_localctx, predIndex);
		case 40:
			return shiftExpression_sempred((ShiftExpressionContext)_localctx, predIndex);
		case 41:
			return additiveExpression_sempred((AdditiveExpressionContext)_localctx, predIndex);
		case 42:
			return multiplicativeExpression_sempred((MultiplicativeExpressionContext)_localctx, predIndex);
		case 44:
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
	private boolean bitwiseOrExpression_sempred(BitwiseOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean bitwiseXorExpression_sempred(BitwiseXorExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean bitwiseAndExpression_sempred(BitwiseAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relationalExpression_sempred(RelationalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean shiftExpression_sempred(ShiftExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 2);
		case 12:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean additiveExpression_sempred(AdditiveExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 13:
			return precpred(_ctx, 2);
		case 14:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean multiplicativeExpression_sempred(MultiplicativeExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 15:
			return precpred(_ctx, 3);
		case 16:
			return precpred(_ctx, 2);
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 5);
		case 19:
			return precpred(_ctx, 4);
		case 20:
			return precpred(_ctx, 3);
		case 21:
			return precpred(_ctx, 2);
		case 22:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3D\u022f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\7\2b\n\2\f\2\16\2e\13\2\3\2\3\2\3"+
		"\3\3\3\3\3\5\3l\n\3\3\4\3\4\3\4\3\4\7\4r\n\4\f\4\16\4u\13\4\3\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\6\3\6\7\6\u0080\n\6\f\6\16\6\u0083\13\6\3\6\3\6\3"+
		"\6\5\6\u0088\n\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7\u0090\n\7\f\7\16\7\u0093"+
		"\13\7\3\b\3\b\3\b\3\t\3\t\7\t\u009a\n\t\f\t\16\t\u009d\13\t\3\t\7\t\u00a0"+
		"\n\t\f\t\16\t\u00a3\13\t\3\n\7\n\u00a6\n\n\f\n\16\n\u00a9\13\n\3\n\7\n"+
		"\u00ac\n\n\f\n\16\n\u00af\13\n\3\n\3\n\3\n\5\n\u00b4\n\n\3\n\7\n\u00b7"+
		"\n\n\f\n\16\n\u00ba\13\n\3\13\3\13\3\13\3\13\7\13\u00c0\n\13\f\13\16\13"+
		"\u00c3\13\13\3\13\3\13\3\f\3\f\3\f\5\f\u00ca\n\f\3\r\3\r\3\16\7\16\u00cf"+
		"\n\16\f\16\16\16\u00d2\13\16\3\16\3\16\7\16\u00d6\n\16\f\16\16\16\u00d9"+
		"\13\16\3\16\3\16\5\16\u00dd\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\5\17\u00ea\n\17\3\20\3\20\7\20\u00ee\n\20\f\20\16\20"+
		"\u00f1\13\20\3\20\3\20\3\21\5\21\u00f6\n\21\3\21\3\21\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\5\22\u0101\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\5\25\u0114\n\25\3\25"+
		"\3\25\5\25\u0118\n\25\3\25\3\25\5\25\u011c\n\25\3\25\3\25\3\25\3\26\3"+
		"\26\3\26\3\26\7\26\u0125\n\26\f\26\16\26\u0128\13\26\3\27\3\27\5\27\u012c"+
		"\n\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0136\n\31\f\31\16"+
		"\31\u0139\13\31\3\31\5\31\u013c\n\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32"+
		"\u0144\n\32\f\32\16\32\u0147\13\32\3\33\3\33\3\33\7\33\u014c\n\33\f\33"+
		"\16\33\u014f\13\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\5\36\u0159"+
		"\n\36\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3 \5 \u0164\n \3!\3!\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\5\"\u016f\n\"\3#\3#\3#\3#\3#\3#\7#\u0177\n#\f#\16#\u017a"+
		"\13#\3$\3$\3$\3$\3$\3$\7$\u0182\n$\f$\16$\u0185\13$\3%\3%\3%\3%\3%\3%"+
		"\7%\u018d\n%\f%\16%\u0190\13%\3&\3&\3&\3&\3&\3&\7&\u0198\n&\f&\16&\u019b"+
		"\13&\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u01a3\n\'\f\'\16\'\u01a6\13\'\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\7(\u01b1\n(\f(\16(\u01b4\13(\3)\3)\3)\3)\3)\3)\3)"+
		"\3)\3)\3)\3)\3)\3)\3)\3)\7)\u01c5\n)\f)\16)\u01c8\13)\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\7*\u01d3\n*\f*\16*\u01d6\13*\3+\3+\3+\3+\3+\3+\3+\3+\3+\7+"+
		"\u01e1\n+\f+\16+\u01e4\13+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\7,\u01f2"+
		"\n,\f,\16,\u01f5\13,\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u0200\n-\3.\3.\3.\3"+
		".\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u0210\n.\3.\3.\3.\3.\3.\7.\u0217\n"+
		".\f.\16.\u021a\13.\3/\3/\3/\7/\u021f\n/\f/\16/\u0222\13/\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u022d\n\60\3\60\2\rDFHJLNPRTVZ\61"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVXZ\\^\2\5\7\2\25\27\35\36)),,\65\66\3\2\3\7\3\2/\64\2\u024a\2c"+
		"\3\2\2\2\4k\3\2\2\2\6m\3\2\2\2\by\3\2\2\2\n}\3\2\2\2\f\u008c\3\2\2\2\16"+
		"\u0094\3\2\2\2\20\u0097\3\2\2\2\22\u00a7\3\2\2\2\24\u00bb\3\2\2\2\26\u00c6"+
		"\3\2\2\2\30\u00cb\3\2\2\2\32\u00dc\3\2\2\2\34\u00e9\3\2\2\2\36\u00eb\3"+
		"\2\2\2 \u00f5\3\2\2\2\"\u00f9\3\2\2\2$\u0102\3\2\2\2&\u0108\3\2\2\2(\u0110"+
		"\3\2\2\2*\u0120\3\2\2\2,\u012b\3\2\2\2.\u012d\3\2\2\2\60\u012f\3\2\2\2"+
		"\62\u013f\3\2\2\2\64\u0148\3\2\2\2\66\u0150\3\2\2\28\u0153\3\2\2\2:\u0156"+
		"\3\2\2\2<\u015c\3\2\2\2>\u0163\3\2\2\2@\u0165\3\2\2\2B\u016e\3\2\2\2D"+
		"\u0170\3\2\2\2F\u017b\3\2\2\2H\u0186\3\2\2\2J\u0191\3\2\2\2L\u019c\3\2"+
		"\2\2N\u01a7\3\2\2\2P\u01b5\3\2\2\2R\u01c9\3\2\2\2T\u01d7\3\2\2\2V\u01e5"+
		"\3\2\2\2X\u01ff\3\2\2\2Z\u0201\3\2\2\2\\\u021b\3\2\2\2^\u022c\3\2\2\2"+
		"`b\5\4\3\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2ec\3\2\2\2"+
		"fg\7\2\2\3g\3\3\2\2\2hl\5\6\4\2il\5\n\6\2jl\5\24\13\2kh\3\2\2\2ki\3\2"+
		"\2\2kj\3\2\2\2l\5\3\2\2\2mn\7\b\2\2no\7\30\2\2os\7=\2\2pr\5\b\5\2qp\3"+
		"\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tv\3\2\2\2us\3\2\2\2vw\7>\2\2wx\7"+
		"\67\2\2x\7\3\2\2\2yz\5\32\16\2z{\5\22\n\2{|\7\67\2\2|\t\3\2\2\2}\u0081"+
		"\5\32\16\2~\u0080\7\26\2\2\177~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084"+
		"\u0085\7\30\2\2\u0085\u0087\7;\2\2\u0086\u0088\5\f\7\2\u0087\u0086\3\2"+
		"\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\7<\2\2\u008a"+
		"\u008b\5\36\20\2\u008b\13\3\2\2\2\u008c\u0091\5\16\b\2\u008d\u008e\78"+
		"\2\2\u008e\u0090\5\16\b\2\u008f\u008d\3\2\2\2\u0090\u0093\3\2\2\2\u0091"+
		"\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\r\3\2\2\2\u0093\u0091\3\2\2\2"+
		"\u0094\u0095\5\32\16\2\u0095\u0096\5\22\n\2\u0096\17\3\2\2\2\u0097\u009b"+
		"\5\32\16\2\u0098\u009a\7\26\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2"+
		"\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u00a1\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009e\u00a0\7\27\2\2\u009f\u009e\3\2\2\2\u00a0\u00a3\3\2\2\2"+
		"\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\21\3\2\2\2\u00a3\u00a1"+
		"\3\2\2\2\u00a4\u00a6\7\26\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2"+
		"\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00ad\3\2\2\2\u00a9\u00a7"+
		"\3\2\2\2\u00aa\u00ac\7\27\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2"+
		"\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad"+
		"\3\2\2\2\u00b0\u00b8\7\30\2\2\u00b1\u00b3\7?\2\2\u00b2\u00b4\5<\37\2\u00b3"+
		"\u00b2\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\7@"+
		"\2\2\u00b6\u00b1\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9\23\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\5\32\16"+
		"\2\u00bc\u00c1\5\26\f\2\u00bd\u00be\78\2\2\u00be\u00c0\5\26\f\2\u00bf"+
		"\u00bd\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2"+
		"\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c5\7\67\2\2\u00c5"+
		"\25\3\2\2\2\u00c6\u00c9\5\22\n\2\u00c7\u00c8\7/\2\2\u00c8\u00ca\5<\37"+
		"\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\27\3\2\2\2\u00cb\u00cc"+
		"\t\2\2\2\u00cc\31\3\2\2\2\u00cd\u00cf\7\24\2\2\u00ce\u00cd\3\2\2\2\u00cf"+
		"\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2"+
		"\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00dd\t\3\2\2\u00d4\u00d6\7\24\2\2\u00d5"+
		"\u00d4\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2"+
		"\2\2\u00d8\u00da\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00db\7\b\2\2\u00db"+
		"\u00dd\7\30\2\2\u00dc\u00d0\3\2\2\2\u00dc\u00d7\3\2\2\2\u00dd\33\3\2\2"+
		"\2\u00de\u00ea\5\36\20\2\u00df\u00ea\5 \21\2\u00e0\u00ea\5\"\22\2\u00e1"+
		"\u00ea\5$\23\2\u00e2\u00ea\5(\25\2\u00e3\u00ea\5&\24\2\u00e4\u00ea\5\60"+
		"\31\2\u00e5\u00ea\5\66\34\2\u00e6\u00ea\58\35\2\u00e7\u00ea\5:\36\2\u00e8"+
		"\u00ea\5\24\13\2\u00e9\u00de\3\2\2\2\u00e9\u00df\3\2\2\2\u00e9\u00e0\3"+
		"\2\2\2\u00e9\u00e1\3\2\2\2\u00e9\u00e2\3\2\2\2\u00e9\u00e3\3\2\2\2\u00e9"+
		"\u00e4\3\2\2\2\u00e9\u00e5\3\2\2\2\u00e9\u00e6\3\2\2\2\u00e9\u00e7\3\2"+
		"\2\2\u00e9\u00e8\3\2\2\2\u00ea\35\3\2\2\2\u00eb\u00ef\7=\2\2\u00ec\u00ee"+
		"\5\34\17\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2"+
		"\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3"+
		"\7>\2\2\u00f3\37\3\2\2\2\u00f4\u00f6\5<\37\2\u00f5\u00f4\3\2\2\2\u00f5"+
		"\u00f6\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\7\67\2\2\u00f8!\3\2\2\2"+
		"\u00f9\u00fa\7\t\2\2\u00fa\u00fb\7;\2\2\u00fb\u00fc\5<\37\2\u00fc\u00fd"+
		"\7<\2\2\u00fd\u0100\5\34\17\2\u00fe\u00ff\7\n\2\2\u00ff\u0101\5\34\17"+
		"\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101#\3\2\2\2\u0102\u0103"+
		"\7\13\2\2\u0103\u0104\7;\2\2\u0104\u0105\5<\37\2\u0105\u0106\7<\2\2\u0106"+
		"\u0107\5\34\17\2\u0107%\3\2\2\2\u0108\u0109\7\r\2\2\u0109\u010a\5\34\17"+
		"\2\u010a\u010b\7\13\2\2\u010b\u010c\7;\2\2\u010c\u010d\5<\37\2\u010d\u010e"+
		"\7<\2\2\u010e\u010f\7\67\2\2\u010f\'\3\2\2\2\u0110\u0111\7\f\2\2\u0111"+
		"\u0113\7;\2\2\u0112\u0114\5,\27\2\u0113\u0112\3\2\2\2\u0113\u0114\3\2"+
		"\2\2\u0114\u0115\3\2\2\2\u0115\u0117\7\67\2\2\u0116\u0118\5<\37\2\u0117"+
		"\u0116\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011b\7\67"+
		"\2\2\u011a\u011c\5.\30\2\u011b\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c"+
		"\u011d\3\2\2\2\u011d\u011e\7<\2\2\u011e\u011f\5\34\17\2\u011f)\3\2\2\2"+
		"\u0120\u0121\5\32\16\2\u0121\u0126\5\26\f\2\u0122\u0123\78\2\2\u0123\u0125"+
		"\5\26\f\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2"+
		"\u0126\u0127\3\2\2\2\u0127+\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012c\5"+
		"*\26\2\u012a\u012c\5<\37\2\u012b\u0129\3\2\2\2\u012b\u012a\3\2\2\2\u012c"+
		"-\3\2\2\2\u012d\u012e\5<\37\2\u012e/\3\2\2\2\u012f\u0130\7\21\2\2\u0130"+
		"\u0131\7;\2\2\u0131\u0132\5<\37\2\u0132\u0133\7<\2\2\u0133\u0137\7=\2"+
		"\2\u0134\u0136\5\62\32\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137"+
		"\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u013b\3\2\2\2\u0139\u0137\3\2"+
		"\2\2\u013a\u013c\5\64\33\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\u013e\7>\2\2\u013e\61\3\2\2\2\u013f\u0140\7\22\2"+
		"\2\u0140\u0141\5<\37\2\u0141\u0145\79\2\2\u0142\u0144\5\34\17\2\u0143"+
		"\u0142\3\2\2\2\u0144\u0147\3\2\2\2\u0145\u0143\3\2\2\2\u0145\u0146\3\2"+
		"\2\2\u0146\63\3\2\2\2\u0147\u0145\3\2\2\2\u0148\u0149\7\23\2\2\u0149\u014d"+
		"\79\2\2\u014a\u014c\5\34\17\2\u014b\u014a\3\2\2\2\u014c\u014f\3\2\2\2"+
		"\u014d\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e\65\3\2\2\2\u014f\u014d"+
		"\3\2\2\2\u0150\u0151\7\16\2\2\u0151\u0152\7\67\2\2\u0152\67\3\2\2\2\u0153"+
		"\u0154\7\17\2\2\u0154\u0155\7\67\2\2\u01559\3\2\2\2\u0156\u0158\7\20\2"+
		"\2\u0157\u0159\5<\37\2\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015a"+
		"\3\2\2\2\u015a\u015b\7\67\2\2\u015b;\3\2\2\2\u015c\u015d\5> \2\u015d="+
		"\3\2\2\2\u015e\u0164\5B\"\2\u015f\u0160\5X-\2\u0160\u0161\5@!\2\u0161"+
		"\u0162\5> \2\u0162\u0164\3\2\2\2\u0163\u015e\3\2\2\2\u0163\u015f\3\2\2"+
		"\2\u0164?\3\2\2\2\u0165\u0166\t\4\2\2\u0166A\3\2\2\2\u0167\u016f\5D#\2"+
		"\u0168\u0169\5D#\2\u0169\u016a\7:\2\2\u016a\u016b\5<\37\2\u016b\u016c"+
		"\79\2\2\u016c\u016d\5B\"\2\u016d\u016f\3\2\2\2\u016e\u0167\3\2\2\2\u016e"+
		"\u0168\3\2\2\2\u016fC\3\2\2\2\u0170\u0171\b#\1\2\u0171\u0172\5F$\2\u0172"+
		"\u0178\3\2\2\2\u0173\u0174\f\3\2\2\u0174\u0175\7(\2\2\u0175\u0177\5F$"+
		"\2\u0176\u0173\3\2\2\2\u0177\u017a\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0179"+
		"\3\2\2\2\u0179E\3\2\2\2\u017a\u0178\3\2\2\2\u017b\u017c\b$\1\2\u017c\u017d"+
		"\5H%\2\u017d\u0183\3\2\2\2\u017e\u017f\f\3\2\2\u017f\u0180\7\'\2\2\u0180"+
		"\u0182\5H%\2\u0181\u017e\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2\2"+
		"\2\u0183\u0184\3\2\2\2\u0184G\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u0187"+
		"\b%\1\2\u0187\u0188\5J&\2\u0188\u018e\3\2\2\2\u0189\u018a\f\3\2\2\u018a"+
		"\u018b\7*\2\2\u018b\u018d\5J&\2\u018c\u0189\3\2\2\2\u018d\u0190\3\2\2"+
		"\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018fI\3\2\2\2\u0190\u018e"+
		"\3\2\2\2\u0191\u0192\b&\1\2\u0192\u0193\5L\'\2\u0193\u0199\3\2\2\2\u0194"+
		"\u0195\f\3\2\2\u0195\u0196\7+\2\2\u0196\u0198\5L\'\2\u0197\u0194\3\2\2"+
		"\2\u0198\u019b\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019aK"+
		"\3\2\2\2\u019b\u0199\3\2\2\2\u019c\u019d\b\'\1\2\u019d\u019e\5N(\2\u019e"+
		"\u01a4\3\2\2\2\u019f\u01a0\f\3\2\2\u01a0\u01a1\7\27\2\2\u01a1\u01a3\5"+
		"N(\2\u01a2\u019f\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a4"+
		"\u01a5\3\2\2\2\u01a5M\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8\b(\1\2\u01a8"+
		"\u01a9\5P)\2\u01a9\u01b2\3\2\2\2\u01aa\u01ab\f\4\2\2\u01ab\u01ac\7!\2"+
		"\2\u01ac\u01b1\5P)\2\u01ad\u01ae\f\3\2\2\u01ae\u01af\7\"\2\2\u01af\u01b1"+
		"\5P)\2\u01b0\u01aa\3\2\2\2\u01b0\u01ad\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2"+
		"\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3O\3\2\2\2\u01b4\u01b2\3\2\2\2"+
		"\u01b5\u01b6\b)\1\2\u01b6\u01b7\5R*\2\u01b7\u01c6\3\2\2\2\u01b8\u01b9"+
		"\f\6\2\2\u01b9\u01ba\7#\2\2\u01ba\u01c5\5R*\2\u01bb\u01bc\f\5\2\2\u01bc"+
		"\u01bd\7$\2\2\u01bd\u01c5\5R*\2\u01be\u01bf\f\4\2\2\u01bf\u01c0\7%\2\2"+
		"\u01c0\u01c5\5R*\2\u01c1\u01c2\f\3\2\2\u01c2\u01c3\7&\2\2\u01c3\u01c5"+
		"\5R*\2\u01c4\u01b8\3\2\2\2\u01c4\u01bb\3\2\2\2\u01c4\u01be\3\2\2\2\u01c4"+
		"\u01c1\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2"+
		"\2\2\u01c7Q\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01ca\b*\1\2\u01ca\u01cb"+
		"\5T+\2\u01cb\u01d4\3\2\2\2\u01cc\u01cd\f\4\2\2\u01cd\u01ce\7-\2\2\u01ce"+
		"\u01d3\5T+\2\u01cf\u01d0\f\3\2\2\u01d0\u01d1\7.\2\2\u01d1\u01d3\5T+\2"+
		"\u01d2\u01cc\3\2\2\2\u01d2\u01cf\3\2\2\2\u01d3\u01d6\3\2\2\2\u01d4\u01d2"+
		"\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5S\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d7"+
		"\u01d8\b+\1\2\u01d8\u01d9\5V,\2\u01d9\u01e2\3\2\2\2\u01da\u01db\f\4\2"+
		"\2\u01db\u01dc\7\35\2\2\u01dc\u01e1\5V,\2\u01dd\u01de\f\3\2\2\u01de\u01df"+
		"\7\36\2\2\u01df\u01e1\5V,\2\u01e0\u01da\3\2\2\2\u01e0\u01dd\3\2\2\2\u01e1"+
		"\u01e4\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3U\3\2\2\2"+
		"\u01e4\u01e2\3\2\2\2\u01e5\u01e6\b,\1\2\u01e6\u01e7\5X-\2\u01e7\u01f3"+
		"\3\2\2\2\u01e8\u01e9\f\5\2\2\u01e9\u01ea\7\26\2\2\u01ea\u01f2\5X-\2\u01eb"+
		"\u01ec\f\4\2\2\u01ec\u01ed\7\37\2\2\u01ed\u01f2\5X-\2\u01ee\u01ef\f\3"+
		"\2\2\u01ef\u01f0\7 \2\2\u01f0\u01f2\5X-\2\u01f1\u01e8\3\2\2\2\u01f1\u01eb"+
		"\3\2\2\2\u01f1\u01ee\3\2\2\2\u01f2\u01f5\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3"+
		"\u01f4\3\2\2\2\u01f4W\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f6\u01f7\7;\2\2\u01f7"+
		"\u01f8\5\20\t\2\u01f8\u01f9\7<\2\2\u01f9\u01fa\5X-\2\u01fa\u0200\3\2\2"+
		"\2\u01fb\u0200\5Z.\2\u01fc\u01fd\5\30\r\2\u01fd\u01fe\5X-\2\u01fe\u0200"+
		"\3\2\2\2\u01ff\u01f6\3\2\2\2\u01ff\u01fb\3\2\2\2\u01ff\u01fc\3\2\2\2\u0200"+
		"Y\3\2\2\2\u0201\u0202\b.\1\2\u0202\u0203\5^\60\2\u0203\u0218\3\2\2\2\u0204"+
		"\u0205\f\7\2\2\u0205\u0206\7?\2\2\u0206\u0207\5<\37\2\u0207\u0208\7@\2"+
		"\2\u0208\u0217\3\2\2\2\u0209\u020a\f\6\2\2\u020a\u020b\7A\2\2\u020b\u0217"+
		"\7\30\2\2\u020c\u020d\f\5\2\2\u020d\u020f\7;\2\2\u020e\u0210\5\\/\2\u020f"+
		"\u020e\3\2\2\2\u020f\u0210\3\2\2\2\u0210\u0211\3\2\2\2\u0211\u0217\7<"+
		"\2\2\u0212\u0213\f\4\2\2\u0213\u0217\7\65\2\2\u0214\u0215\f\3\2\2\u0215"+
		"\u0217\7\66\2\2\u0216\u0204\3\2\2\2\u0216\u0209\3\2\2\2\u0216\u020c\3"+
		"\2\2\2\u0216\u0212\3\2\2\2\u0216\u0214\3\2\2\2\u0217\u021a\3\2\2\2\u0218"+
		"\u0216\3\2\2\2\u0218\u0219\3\2\2\2\u0219[\3\2\2\2\u021a\u0218\3\2\2\2"+
		"\u021b\u0220\5<\37\2\u021c\u021d\78\2\2\u021d\u021f\5<\37\2\u021e\u021c"+
		"\3\2\2\2\u021f\u0222\3\2\2\2\u0220\u021e\3\2\2\2\u0220\u0221\3\2\2\2\u0221"+
		"]\3\2\2\2\u0222\u0220\3\2\2\2\u0223\u022d\7\30\2\2\u0224\u022d\7\31\2"+
		"\2\u0225\u022d\7\32\2\2\u0226\u022d\7\33\2\2\u0227\u022d\7\34\2\2\u0228"+
		"\u0229\7;\2\2\u0229\u022a\5<\37\2\u022a\u022b\7<\2\2\u022b\u022d\3\2\2"+
		"\2\u022c\u0223\3\2\2\2\u022c\u0224\3\2\2\2\u022c\u0225\3\2\2\2\u022c\u0226"+
		"\3\2\2\2\u022c\u0227\3\2\2\2\u022c\u0228\3\2\2\2\u022d_\3\2\2\28cks\u0081"+
		"\u0087\u0091\u009b\u00a1\u00a7\u00ad\u00b3\u00b8\u00c1\u00c9\u00d0\u00d7"+
		"\u00dc\u00e9\u00ef\u00f5\u0100\u0113\u0117\u011b\u0126\u012b\u0137\u013b"+
		"\u0145\u014d\u0158\u0163\u016e\u0178\u0183\u018e\u0199\u01a4\u01b0\u01b2"+
		"\u01c4\u01c6\u01d2\u01d4\u01e0\u01e2\u01f1\u01f3\u01ff\u020f\u0216\u0218"+
		"\u0220\u022c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}