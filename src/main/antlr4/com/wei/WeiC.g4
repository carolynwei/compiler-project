grammar WeiC;

// ========================================
// Wei-C 语言词法规则 (Lexer Rules)
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
DO: 'do';             // ✅ 新增: do-while 循环
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
CONST: 'const';       // ✅ 新增: 类型限定符
SIZEOF: 'sizeof';     // ✅ 新增: sizeof 操作符

STAR: '*';           // 解引用/乘法
AMPERSAND: '&';      // 取地址/位与/引用 (复用为位与和引用，避免歧义)

// 标识符 (Identifiers)
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// 字面量 (Literals)
INT_LITERAL: [0-9]+;
FLOAT_LITERAL:
      [0-9]+ ('.' [0-9]*)? EXPONENT
    | [0-9]* '.' [0-9]+ EXPONENT?
    ;

fragment EXPONENT: ('e'|'E') ('+'|'-')? [0-9]+;
CHAR_LITERAL: '\'' ( ~['\\\r\n] | '\\' . ) '\'' ;
STRING_LITERAL: '"' (~["\\\r\n] | '\\' .)* '"';

// 运算符 (Operators)
// 算术运算符
PLUS: '+';
MINUS: '-';
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

// 位运算符
BITWISE_OR: '|';      // ✅ 新增: 位或
BITWISE_XOR: '^';     // ✅ 新增: 位异或
BITWISE_NOT: '~';     // ✅ 新增: 位非
LSHIFT: '<<';         // ✅ 新增: 左移
RSHIFT: '>>';         // ✅ 新增: 右移

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
// Wei-C 语言语法规则 (Parser Rules)
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

structField: type declarator SEMICOLON;

// 函数声明
functionDeclaration: type STAR* ID LPAREN parameterList? RPAREN block;

parameterList: parameter (COMMA parameter)*;

parameter: type declarator;

// 类型名称 (用于强制类型转换)
typeName: type STAR* AMPERSAND*; 

// 声明符 - 支持指针、引用和数组
declarator
    : STAR* AMPERSAND* ID (LBRACKET expression? RBRACKET)* ;


// 变量声明
variableDeclaration: type variableDeclarator (COMMA variableDeclarator)* SEMICOLON;
variableDeclarator: declarator (ASSIGN expression)?;

// 一元操作符集合
unaryOperator:
    PLUS
    | MINUS
    | NOT
    | BITWISE_NOT // ✅ 新增: 位非
    | STAR       // 解引用
    | AMPERSAND  // 取地址
    | INCREMENT
    | DECREMENT
    | SIZEOF     // ✅ 新增: sizeof
    ;

// 类型定义 - 允许 const 修饰
type: 
    CONST* (INT | FLOAT | CHAR | STRING | VOID) 
    | CONST* STRUCT ID 
    ;

// 语句 (Statements)
statement:
    block
    | expressionStatement
    | ifStatement
    | whileStatement
    | forStatement
    | doWhileStatement // ✅ 新增
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

// do-while 语句
doWhileStatement: DO statement WHILE LPAREN expression RPAREN SEMICOLON; // ✅ 新增

// for 语句
forStatement: FOR LPAREN forInit? SEMICOLON expression? SEMICOLON forUpdate? RPAREN statement;

// for 循环初始化 - 变量声明不包含分号
forVariableDeclaration: type variableDeclarator (COMMA variableDeclarator)*;

forInit: forVariableDeclaration | expression; // 表达式而不是 expressionStatement

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
    | unaryExpression assignmentOperator assignmentExpression // unaryExpression 包含 *ptr, a[i] 等 LValues
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

// 逻辑或 ||
logicalOrExpression:
    logicalAndExpression
    | logicalOrExpression OR logicalAndExpression 
    ;

// 逻辑与 &&
logicalAndExpression:
    bitwiseOrExpression // 新增层级
    | logicalAndExpression AND bitwiseOrExpression
    ;

// 位或 |
bitwiseOrExpression:
    bitwiseXorExpression
    | bitwiseOrExpression BITWISE_OR bitwiseXorExpression
    ;

// 位异或 ^
bitwiseXorExpression:
    bitwiseAndExpression
    | bitwiseXorExpression BITWISE_XOR bitwiseAndExpression
    ;

// 位与 &
bitwiseAndExpression:
    equalityExpression 
    | bitwiseAndExpression AMPERSAND equalityExpression // 使用 AMPERSAND 作为位与
    ;

// 相等性 == !=
equalityExpression:
    relationalExpression
    | equalityExpression EQ relationalExpression
    | equalityExpression NE relationalExpression
    ;

// 关系运算符 < > <= >=
relationalExpression:
    shiftExpression // 新增层级
    | relationalExpression LT shiftExpression
    | relationalExpression GT shiftExpression
    | relationalExpression LE shiftExpression
    | relationalExpression GE shiftExpression
    ;

// 移位运算符 << >>
shiftExpression:
    additiveExpression
    | shiftExpression LSHIFT additiveExpression 
    | shiftExpression RSHIFT additiveExpression 
    ;

// 加法/减法 + -
additiveExpression:
    multiplicativeExpression
    | additiveExpression PLUS multiplicativeExpression
    | additiveExpression MINUS multiplicativeExpression
    ;

// 乘法/除法/取模 * / %
multiplicativeExpression:
    unaryExpression
    | multiplicativeExpression STAR unaryExpression // 使用 STAR
    | multiplicativeExpression DIVIDE unaryExpression
    | multiplicativeExpression MODULO unaryExpression
    ;

// 一元表达式 - 包括强制类型转换和 sizeof
unaryExpression:
    LPAREN typeName RPAREN unaryExpression // ✅ 新增: 强制类型转换 (type) expr
    | postfixExpression
    | unaryOperator unaryExpression
    ;

// 后缀表达式 - 数组访问、函数调用、成员访问、自增/自减
postfixExpression:
    primaryExpression
    | postfixExpression LBRACKET expression RBRACKET // 数组访问
    | postfixExpression DOT ID // 结构体成员访问
    | postfixExpression LPAREN argumentList? RPAREN // 函数调用
    | postfixExpression INCREMENT // 后缀自增
    | postfixExpression DECREMENT // 后缀自减
    ;

argumentList: expression (COMMA expression)*;

// 基本表达式 - ID, 字面量, (expression)
primaryExpression:
    ID
    | INT_LITERAL
    | FLOAT_LITERAL
    | CHAR_LITERAL
    | STRING_LITERAL
    | LPAREN expression RPAREN
    ;