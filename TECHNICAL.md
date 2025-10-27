# Gemini-C 编译器技术文档

## 目录
1. [架构设计](#架构设计)
2. [词法分析](#词法分析)
3. [语法分析](#语法分析)
4. [语义分析](#语义分析)
5. [中间代码生成](#中间代码生成)
6. [目标代码生成](#目标代码生成)
7. [优化技术](#优化技术)
8. [错误处理](#错误处理)
9. [性能分析](#性能分析)

## 架构设计

### 整体架构
Gemini-C 编译器采用经典的编译架构，包含以下主要组件：

```
源程序 → 词法分析 → 语法分析 → 语义分析 → 中间代码生成 → 目标代码生成 → 目标程序
```

### 技术栈
- **开发语言**: Java 11+
- **词法/语法分析**: ANTLR 4.9+
- **构建工具**: Maven 3.6+
- **测试框架**: JUnit 5
- **目标代码**: LLVM IR

### 模块设计
```
com.gemini.compiler/
├── GeminiCompiler.java          # 主编译器类
├── ast/                         # 抽象语法树
│   ├── ASTNode.java
│   ├── ASTVisitor.java
│   ├── ASTBuilder.java
│   └── ASTPrinter.java
├── semantic/                    # 语义分析
│   ├── SymbolTableManager.java
│   ├── SemanticAnalyzer.java
│   ├── SymbolEntry.java
│   └── SemanticError.java
├── ir/                          # 中间代码
│   ├── IRProgram.java
│   └── IRGenerator.java
└── codegen/                     # 目标代码生成
    └── CodeGenerator.java
```

## 词法分析

### ANTLR 语法文件
词法分析器由 ANTLR 4 根据 `GeminiC.g4` 文件自动生成。

### 词法规则
```antlr
// 关键字
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

// 标识符
ID: [a-zA-Z_][a-zA-Z0-9_]*;

// 字面量
INT_LITERAL: [0-9]+;
FLOAT_LITERAL: [0-9]+ '.' [0-9]+ | '.' [0-9]+ | [0-9]+ '.';
CHAR_LITERAL: '\'' . '\'';
STRING_LITERAL: '"' (~["\\\r\n] | '\\' .)* '"';

// 运算符
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';
EQ: '==';
NE: '!=';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
AND: '&&';
OR: '||';
NOT: '!';
ASSIGN: '=';
PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
MULTIPLY_ASSIGN: '*=';
DIVIDE_ASSIGN: '/=';
MODULO_ASSIGN: '%=';
INCREMENT: '++';
DECREMENT: '--';

// 分隔符
SEMICOLON: ';';
COMMA: ',';
COLON: ':';
QUESTION: '?';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
DOT: '.';

// 注释和空白
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS: [ \t\r\n]+ -> skip;
```

### 词法分析器特性
- 支持 Unicode 标识符
- 自动处理注释和空白
- 错误恢复机制
- 位置信息跟踪

## 语法分析

### 语法规则
```antlr
program: declaration* EOF;

declaration: 
    structDeclaration
    | functionDeclaration
    | variableDeclaration
    ;

structDeclaration: STRUCT ID LBRACE structField* RBRACE SEMICOLON;

functionDeclaration: type ID LPAREN parameterList? RPAREN block;

variableDeclaration: type variableDeclarator (COMMA variableDeclarator)* SEMICOLON;

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

expression:
    assignmentExpression
    ;

assignmentExpression:
    conditionalExpression
    | unaryExpression assignmentOperator assignmentExpression
    ;

// ... 更多语法规则
```

### AST 构建
使用访问者模式构建抽象语法树：

```java
public class ASTBuilder extends GeminiCBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitProgram(GeminiCParser.ProgramContext ctx) {
        ASTNode[] declarations = new ASTNode[ctx.declaration().size()];
        for (int i = 0; i < ctx.declaration().size(); i++) {
            declarations[i] = visit(ctx.declaration(i));
        }
        return new ProgramNode(declarations, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    // ... 其他访问者方法
}
```

### AST 节点类型
- `ProgramNode`: 程序根节点
- `StructDeclarationNode`: 结构体声明
- `FunctionDeclarationNode`: 函数声明
- `VariableDeclarationNode`: 变量声明
- `BlockNode`: 代码块
- `IfStatementNode`: if语句
- `WhileStatementNode`: while语句
- `ForStatementNode`: for语句
- `SwitchStatementNode`: switch语句
- `ExpressionNode`: 表达式节点
- `LiteralNode`: 字面量节点

## 语义分析

### 符号表管理
使用栈式符号表管理作用域：

```java
public class SymbolTableManager {
    private Stack<Map<String, SymbolEntry>> symbolTableStack;
    private int currentScopeLevel;
    
    public void enterScope() {
        Map<String, SymbolEntry> newScope = new HashMap<>();
        symbolTableStack.push(newScope);
        currentScopeLevel++;
    }
    
    public void exitScope() {
        if (symbolTableStack.size() > 1) {
            symbolTableStack.pop();
            currentScopeLevel--;
        }
    }
    
    public SymbolEntry lookupSymbol(String name) {
        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }
}
```

### 符号表条目
```java
public class SymbolEntry {
    private String name;
    private SymbolType symbolType;
    private DataType dataType;
    private int scopeLevel;
    private SymbolKind kind;
    private ArrayInfo arrayInfo;
    private StructInfo structInfo;
    private FunctionInfo functionInfo;
    private RuntimeInfo runtimeInfo;
}
```

### 类型检查
```java
public class TypeChecker {
    public static boolean isCompatible(DataType type1, DataType type2) {
        if (type1 == type2) {
            return true;
        }
        
        // 允许的隐式类型转换
        if (type1 == DataType.INT && type2 == DataType.FLOAT) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isAssignmentCompatible(DataType leftType, DataType rightType) {
        return isCompatible(leftType, rightType);
    }
}
```

### 语义错误检查
支持 20+ 种语义错误检查：

1. **类型检查错误**
   - 操作数类型不匹配
   - 赋值语句左右类型不兼容
   - 函数调用参数类型或数量不匹配
   - 控制表达式类型错误
   - 返回类型不匹配

2. **声明与作用域错误**
   - 未定义的标识符
   - 标识符重定义
   - break或continue语句不在循环体内

3. **数组与结构体错误**
   - 数组下标不是整数类型
   - 数组访问时维数错误
   - 对非结构体变量使用成员访问运算符
   - 结构体成员不存在
   - 结构体定义中存在循环依赖

4. **其他错误**
   - 变量重复初始化
   - 函数调用时使用了不可调用的标识符
   - 除数为零
   - 无效的左值
   - main函数缺少或签名错误
   - switch表达式类型与case常量类型不匹配
   - 结构体类型未定义

## 中间代码生成

### 三地址代码 (TAC)
使用四元式表示：`(操作码, 操作数1, 操作数2, 结果)`

### 指令类型
```java
enum TACOpcode {
    // 算术运算
    ADD, SUB, MUL, DIV, MOD,
    
    // 比较运算
    EQ, NE, LT, GT, LE, GE,
    
    // 逻辑运算
    AND, OR, NOT,
    
    // 赋值
    ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN,
    
    // 自增自减
    INCREMENT, DECREMENT,
    
    // 跳转
    GOTO, IF_TRUE, IF_FALSE, IF_ZERO, IF_NONZERO,
    
    // 标签
    LABEL,
    
    // 函数调用
    CALL, RETURN,
    
    // 数组操作
    ARRAY_ACCESS, ARRAY_ASSIGN,
    
    // 结构体操作
    MEMBER_ACCESS, MEMBER_ASSIGN,
    
    // 其他
    PARAM, ARG, ALLOC, LOAD, STORE
}
```

### 中间代码生成
```java
public class IRGenerator implements ASTVisitor<Void> {
    private IRProgram irProgram;
    private SymbolTableManager symbolTableManager;
    
    @Override
    public Void visitAssignmentExpression(AssignmentExpressionNode node) {
        String left = node.getLeft().accept(this);
        String right = node.getRight().accept(this);
        
        switch (node.getOperator()) {
            case ASSIGN:
                irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, right, null, left));
                break;
            case PLUS_ASSIGN:
                String temp1 = irProgram.generateTempVar();
                irProgram.addInstruction(new TACInstruction(TACOpcode.ADD, left, right, temp1));
                irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, temp1, null, left));
                break;
            // ... 其他赋值运算符
        }
        
        return null;
    }
}
```

### 基本块划分
```java
public class BasicBlock {
    private String label;
    private List<TACInstruction> instructions;
    private Set<String> predecessors;
    private Set<String> successors;
    
    public void addInstruction(TACInstruction instruction) {
        instructions.add(instruction);
    }
    
    public void addPredecessor(String predLabel) {
        predecessors.add(predLabel);
    }
    
    public void addSuccessor(String succLabel) {
        successors.add(succLabel);
    }
}
```

## 目标代码生成

### LLVM IR 生成
```java
public class CodeGenerator {
    private StringBuilder llvmCode;
    private Map<String, String> variableMap;
    private int registerCounter;
    
    public String generate(IRProgram irProgram) {
        generateHeader();
        generateGlobalDeclarations();
        generateFunctions(irProgram);
        generateMainFunction(irProgram);
        return llvmCode.toString();
    }
    
    private void generateAdd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
}
```

### 类型映射
- `int` → `i32`
- `float` → `float`
- `char` → `i8`
- `string` → `i8*`
- `struct` → `%struct.StructName`

### 指令映射
- `ADD` → `add i32`
- `SUB` → `sub i32`
- `MUL` → `mul i32`
- `DIV` → `sdiv i32`
- `MOD` → `srem i32`
- `EQ` → `icmp eq i32`
- `NE` → `icmp ne i32`
- `LT` → `icmp slt i32`
- `GT` → `icmp sgt i32`
- `LE` → `icmp sle i32`
- `GE` → `icmp sge i32`

## 优化技术

### 基本块优化
1. **公共子表达式消除**
2. **死代码消除**
3. **常量折叠**
4. **代数化简**

### 控制流优化
1. **循环优化**
2. **分支优化**
3. **跳转优化**

### 寄存器分配
使用 LLVM 的寄存器分配器：
- 图着色算法
- 线性扫描算法
- 贪心算法

## 错误处理

### 错误类型
```java
enum SemanticErrorType {
    TYPE_MISMATCH,
    INCOMPATIBLE_ASSIGNMENT,
    FUNCTION_PARAMETER_MISMATCH,
    CONTROL_EXPRESSION_TYPE_ERROR,
    RETURN_TYPE_MISMATCH,
    UNDEFINED_IDENTIFIER,
    REDEFINITION,
    BREAK_CONTINUE_OUTSIDE_LOOP,
    ARRAY_INDEX_TYPE_ERROR,
    ARRAY_DIMENSION_ERROR,
    NON_STRUCT_MEMBER_ACCESS,
    STRUCT_MEMBER_NOT_FOUND,
    STRUCT_CIRCULAR_DEPENDENCY,
    DUPLICATE_INITIALIZATION,
    NON_CALLABLE_IDENTIFIER,
    DIVISION_BY_ZERO,
    INVALID_LVALUE,
    MAIN_FUNCTION_MISSING,
    SWITCH_CASE_TYPE_MISMATCH,
    STRUCT_TYPE_UNDEFINED
}
```

### 错误报告
```java
public class SemanticError {
    private SemanticErrorType errorType;
    private String message;
    private String identifier;
    private int scopeLevel;
    private int line;
    private int column;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(errorType.getDescription()).append("] ");
        sb.append(message);
        if (identifier != null) {
            sb.append(" (标识符: ").append(identifier).append(")");
        }
        if (line >= 0) {
            sb.append(" [行: ").append(line);
            if (column >= 0) {
                sb.append(", 列: ").append(column);
            }
            sb.append("]");
        }
        sb.append(" [作用域: ").append(scopeLevel).append("]");
        return sb.toString();
    }
}
```

## 性能分析

### 时间复杂度
- **词法分析**: O(n)
- **语法分析**: O(n)
- **语义分析**: O(n)
- **中间代码生成**: O(n)
- **目标代码生成**: O(n)

### 空间复杂度
- **符号表**: O(s)，其中 s 是符号数量
- **AST**: O(n)，其中 n 是语法树节点数量
- **中间代码**: O(i)，其中 i 是指令数量

### 性能优化
1. **内存管理**
   - 使用对象池
   - 及时释放资源
   - 避免内存泄漏

2. **算法优化**
   - 使用高效的数据结构
   - 避免重复计算
   - 缓存计算结果

3. **编译优化**
   - 启用 LLVM 优化
   - 使用优化选项
   - 并行编译

### 基准测试
```java
@Test
public void testPerformance() {
    long startTime = System.currentTimeMillis();
    
    // 编译大文件
    compiler.compile("large_input.gc", "output.ll");
    
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    
    assertTrue(duration < 5000, "编译时间应该小于5秒");
}
```

---

**注意**: 本技术文档基于 Gemini-C 编译器 v1.0.0 编写。如有更新，请参考最新版本。
