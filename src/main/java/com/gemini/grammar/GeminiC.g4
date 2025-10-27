grammar GeminiC;

// ========================================
// Gemini-C 语言词法规则 (Lexer Rules)
// ========================================

// 关键字 (Keywords)
INT: 'int';
FLOAT: 'float';
CHAR: 'char';
STRING: 'string';
VOID: 'void';
STRUCT: 'struct';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';

// 标识符 (Identifiers)
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// 字面量 (Literals)
INT_LITERAL: [0-9]+;
FLOAT_LITERAL: [0-9]+ '.' [0-9]+ | '.' [0-9]+ | [0-9]+ '.';
CHAR_LITERAL: '\'' . '\'';
STRING_LITERAL: '"' (~["\\\r\n] | '\\' .)* '"';

// 运算符 (Operators)
// 算术运算符
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';

// 比较运算符
EQ: '==';
NE: '!=';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';

// 逻辑运算符
AND: '&&';
OR: '||';
NOT: '!';

// 赋值运算符
ASSIGN: '=';
PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
MULTIPLY_ASSIGN: '*=';
DIVIDE_ASSIGN: '/=';
MODULO_ASSIGN: '%=';

// 自增自减运算符
INCREMENT: '++';
DECREMENT: '--';

// 分隔符 (Delimiters)
SEMICOLON: ';';
COMMA: ',';
COLON: ':';
QUESTION: '?';

// 括号 (Parentheses)
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';

// 点号 (用于结构体成员访问)
DOT: '.';

// 注释 (Comments) - 跳过
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

// 空白字符 (Whitespace) - 跳过
WS: [ \t\r\n]+ -> skip;

// ========================================
// Gemini-C 语言语法规则 (Parser Rules)
// ========================================

// 程序入口点
program: declaration* EOF;

// 声明 (Declarations)
declaration: 
    structDeclaration
    | functionDeclaration
    | variableDeclaration
    ;

// 结构体声明
structDeclaration: STRUCT ID LBRACE structField* RBRACE SEMICOLON;

structField: type ID SEMICOLON;

// 函数声明
functionDeclaration: type ID LPAREN parameterList? RPAREN block;

parameterList: parameter (COMMA parameter)*;

parameter: type ID;

// 变量声明
variableDeclaration: type variableDeclarator (COMMA variableDeclarator)* SEMICOLON;

variableDeclarator: ID (LBRACKET expression RBRACKET)* (ASSIGN expression)?;

// 类型定义
type: 
    INT
    | FLOAT
    | CHAR
    | STRING
    | VOID
    | STRUCT ID
    ;

// 语句 (Statements)
statement:
    block
    | expressionStatement
    | ifStatement
    | whileStatement
    | forStatement
    | switchStatement
    | breakStatement
    | continueStatement
    | returnStatement
    | variableDeclaration
    ;

// 代码块
block: LBRACE statement* RBRACE;

// 表达式语句
expressionStatement: expression? SEMICOLON;

// if 语句
ifStatement: IF LPAREN expression RPAREN statement (ELSE statement)?;

// while 语句
whileStatement: WHILE LPAREN expression RPAREN statement;

// for 语句
forStatement: FOR LPAREN forInit? SEMICOLON expression? SEMICOLON forUpdate? RPAREN statement;

forInit: variableDeclaration | expressionStatement;

forUpdate: expression;

// switch 语句
switchStatement: SWITCH LPAREN expression RPAREN LBRACE switchCase* defaultCase? RBRACE;

switchCase: CASE expression COLON statement*;

defaultCase: DEFAULT COLON statement*;

// break 语句
breakStatement: BREAK SEMICOLON;

// continue 语句
continueStatement: CONTINUE SEMICOLON;

// return 语句
returnStatement: RETURN expression? SEMICOLON;

// 表达式 (Expressions)
expression:
    assignmentExpression
    ;

assignmentExpression:
    conditionalExpression
    | unaryExpression assignmentOperator assignmentExpression
    ;

assignmentOperator:
    ASSIGN
    | PLUS_ASSIGN
    | MINUS_ASSIGN
    | MULTIPLY_ASSIGN
    | DIVIDE_ASSIGN
    | MODULO_ASSIGN
    ;

conditionalExpression:
    logicalOrExpression
    | logicalOrExpression QUESTION expression COLON conditionalExpression
    ;

logicalOrExpression:
    logicalAndExpression
    | logicalOrExpression OR logicalAndExpression
    ;

logicalAndExpression:
    equalityExpression
    | logicalAndExpression AND equalityExpression
    ;

equalityExpression:
    relationalExpression
    | equalityExpression EQ relationalExpression
    | equalityExpression NE relationalExpression
    ;

relationalExpression:
    additiveExpression
    | relationalExpression LT additiveExpression
    | relationalExpression GT additiveExpression
    | relationalExpression LE additiveExpression
    | relationalExpression GE additiveExpression
    ;

additiveExpression:
    multiplicativeExpression
    | additiveExpression PLUS multiplicativeExpression
    | additiveExpression MINUS multiplicativeExpression
    ;

multiplicativeExpression:
    unaryExpression
    | multiplicativeExpression MULTIPLY unaryExpression
    | multiplicativeExpression DIVIDE unaryExpression
    | multiplicativeExpression MODULO unaryExpression
    ;

unaryExpression:
    postfixExpression
    | unaryOperator unaryExpression
    ;

unaryOperator:
    PLUS
    | MINUS
    | NOT
    | INCREMENT
    | DECREMENT
    ;

postfixExpression:
    primaryExpression
    | postfixExpression LBRACKET expression RBRACKET
    | postfixExpression DOT ID
    | postfixExpression LPAREN argumentList? RPAREN
    | postfixExpression INCREMENT
    | postfixExpression DECREMENT
    ;

argumentList: expression (COMMA expression)*;

primaryExpression:
    ID
    | INT_LITERAL
    | FLOAT_LITERAL
    | CHAR_LITERAL
    | STRING_LITERAL
    | LPAREN expression RPAREN
    ;
