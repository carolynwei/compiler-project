# 🌳 AST 模块详解

## 模块概述

AST (Abstract Syntax Tree) 模块负责将 ANTLR 解析树转换为自定义的抽象语法树，为后续的语义分析和代码生成提供结构化的程序表示。

**目录位置**: `src/main/java/com/gemini/compiler/ast/`

**文件数量**: 55 个 Java 文件

---

## 核心类详解

### 1. ASTNode

**文件**: `ASTNode.java`

**作用**: 所有 AST 节点的基类，提供统一的基础功能。

**关键特性**:
- 位置信息：记录节点在源代码中的行号和列号
- 节点类型：使用 `ASTNodeType` 枚举标识节点类型
- Visitor 模式：支持访问者模式遍历 AST

**核心方法**:
```java
public abstract <T> T accept(ASTVisitor<T> visitor);
public abstract ASTNode[] getChildren();
```

**设计要点**:
- 抽象基类，强制子类实现访问者模式
- 位置信息用于错误报告和调试

---

### 2. ASTBuilder

**文件**: `ASTBuilder.java`

**作用**: 将 ANTLR 解析树转换为自定义 AST。

**核心职责**:
1. 遍历 ANTLR 解析树
2. 为每个语法规则创建对应的 AST 节点
3. 建立节点之间的父子关系

**关键方法**:

#### `build(ParseTree parseTree)`
入口方法，开始构建 AST：
```java
public ASTNode build(ParseTree parseTree) {
    return visit(parseTree);
}
```

#### `visitProgram(GeminiCParser.ProgramContext ctx)`
构建程序根节点：
```java
@Override
public ASTNode visitProgram(GeminiCParser.ProgramContext ctx) {
    ASTNode[] declarations = new ASTNode[ctx.declaration().size()];
    for (int i = 0; i < ctx.declaration().size(); i++) {
        declarations[i] = visit(ctx.declaration(i));
    }
    return new ProgramNode(declarations, ctx.start.getLine(), ctx.start.getCharPositionInLine());
}
```

**设计要点**:
- 继承 `GeminiCBaseVisitor`，使用 ANTLR Visitor 模式
- 递归构建 AST，每个语法规则对应一个 visit 方法
- 保留源代码位置信息

---

### 3. ProgramNode

**文件**: `ProgramNode.java`

**作用**: AST 的根节点，表示整个程序。

**结构**:
```java
public class ProgramNode extends ASTNode {
    private ASTNode[] declarations;  // 全局声明（函数、结构体、变量）
}
```

**包含内容**:
- 结构体声明
- 函数声明
- 全局变量声明

---

### 4. ExpressionNode 及其子类

**基类**: `ExpressionNode.java`

表达式节点层次结构：

```
ExpressionNode
├── LiteralNode
│   ├── IntLiteralNode
│   ├── FloatLiteralNode
│   ├── CharLiteralNode
│   └── StringLiteralNode
├── IdentifierNode
├── UnaryExpressionNode
├── PostfixExpressionNode
├── MultiplicativeExpressionNode
├── AdditiveExpressionNode
├── RelationalExpressionNode
├── EqualityExpressionNode
├── LogicalAndExpressionNode
├── LogicalOrExpressionNode
├── ConditionalExpressionNode
├── AssignmentExpressionNode
├── FunctionCallNode
├── ArrayAccessNode
├── MemberAccessNode
└── CastExpressionNode
```

#### 主要表达式节点说明

**UnaryExpressionNode**: 一元表达式（`-x`, `!x`, `++x`, `--x`）
- 支持的操作符：`+`, `-`, `!`, `++`, `--`
- 操作符类型：`UnaryOperator` 枚举

**BinaryExpressionNode** (抽象): 二元表达式基类
- `MultiplicativeExpressionNode`: `*`, `/`, `%`
- `AdditiveExpressionNode`: `+`, `-`
- `RelationalExpressionNode`: `<`, `>`, `<=`, `>=`
- `EqualityExpressionNode`: `==`, `!=`
- `LogicalAndExpressionNode`: `&&`
- `LogicalOrExpressionNode`: `||`

**AssignmentExpressionNode**: 赋值表达式
- 支持：`=`, `+=`, `-=`, `*=`, `/=`, `%=`
- 操作符类型：`AssignmentOperator` 枚举

**ConditionalExpressionNode**: 三元表达式 `condition ? trueExpr : falseExpr`

**FunctionCallNode**: 函数调用
- 函数名：`IdentifierNode`
- 参数列表：`ExpressionNode[]`

**ArrayAccessNode**: 数组访问 `arr[index]`
- 数组表达式：`ExpressionNode`
- 索引表达式：`ExpressionNode`

**MemberAccessNode**: 成员访问 `obj.member`
- 对象表达式：`ExpressionNode`
- 成员名：`String`

---

### 5. StatementNode 及其子类

**基类**: `StatementNode.java`

语句节点层次结构：

```
StatementNode
├── ExpressionStatementNode
├── BlockNode
├── IfStatementNode
├── WhileStatementNode
├── ForStatementNode
├── SwitchStatementNode
├── CaseStatementNode
├── DefaultStatementNode
├── BreakStatementNode
├── ContinueStatementNode
├── ReturnStatementNode
└── VariableDeclarationNode
```

#### 主要语句节点说明

**BlockNode**: 代码块 `{ ... }`
- 语句列表：`StatementNode[]`

**IfStatementNode**: 条件语句
- 条件表达式：`ExpressionNode`
- then 分支：`StatementNode`
- else 分支：`StatementNode` (可选)

**WhileStatementNode**: while 循环
- 条件表达式：`ExpressionNode`
- 循环体：`StatementNode`

**ForStatementNode**: for 循环
- 初始化：`StatementNode` (可选)
- 条件：`ExpressionNode` (可选)
- 更新：`StatementNode` (可选)
- 循环体：`StatementNode`

**SwitchStatementNode**: switch 语句
- 表达式：`ExpressionNode`
- case 列表：`CaseStatementNode[]`
- default 分支：`DefaultStatementNode` (可选)

**ReturnStatementNode**: return 语句
- 返回值：`ExpressionNode` (可选)

---

### 6. DeclarationNode 及其子类

**FunctionDeclarationNode**: 函数声明
- 返回类型：`TypeNode`
- 函数名：`String`
- 参数列表：`ParameterNode[]`
- 函数体：`BlockNode` (可选，支持函数声明)

**StructDeclarationNode**: 结构体声明
- 结构体名：`String`
- 字段列表：`FieldDeclarationNode[]`

**VariableDeclarationNode**: 变量声明
- 类型：`TypeNode`
- 声明列表：`VariableDeclaratorNode[]`

**FieldDeclarationNode**: 结构体字段声明
- 类型：`TypeNode`
- 字段名：`String`

**ParameterNode**: 函数参数
- 类型：`TypeNode`
- 参数名：`String`
- 数组维度：`int` (支持数组参数)

---

### 7. TypeNode

**文件**: `TypeNode.java`

**作用**: 表示类型信息。

**结构**:
```java
public class TypeNode extends ASTNode {
    private DataType baseType;      // 基础类型（int, float, char, string, void, struct）
    private String structName;      // 结构体名称（如果是结构体类型）
    private int[] arrayDimensions;  // 数组维度（如果是数组类型）
}
```

**支持的类型**:
- 基本类型：`int`, `float`, `char`, `string`, `void`
- 结构体类型：`struct StructName`
- 数组类型：`int[10]`, `int[3][4]`

---

### 8. ASTVisitor

**文件**: `ASTVisitor.java`

**作用**: 访问者模式接口，用于遍历 AST。

**设计模式**: Visitor 模式

**核心方法**:
```java
public interface ASTVisitor<T> {
    T visitProgram(ProgramNode node);
    T visitExpression(ExpressionNode node);
    T visitStatement(StatementNode node);
    // ... 其他 visit 方法
}
```

**使用场景**:
- `SemanticAnalyzer`: 实现语义分析访问
- `IRGenerator`: 实现 IR 生成访问
- `ASTPrinter`: 实现 AST 打印访问

---

### 9. ASTPrinter

**文件**: `ASTPrinter.java`

**作用**: 打印 AST 结构，用于调试。

**功能**:
- 以树形结构打印 AST
- 显示节点类型和位置信息
- 支持缩进显示层次结构

---

## 节点类型枚举

**文件**: `ASTNodeType.java`

定义所有 AST 节点类型：
```java
public enum ASTNodeType {
    PROGRAM, FUNCTION_DECLARATION, STRUCT_DECLARATION,
    VARIABLE_DECLARATION, PARAMETER, FIELD_DECLARATION,
    BLOCK, EXPRESSION_STATEMENT, IF_STATEMENT,
    WHILE_STATEMENT, FOR_STATEMENT, SWITCH_STATEMENT,
    // ... 更多类型
}
```

---

## 数据类型枚举

**文件**: `DataType.java`

定义语言支持的数据类型：
```java
public enum DataType {
    INT, FLOAT, CHAR, STRING, VOID, STRUCT, ARRAY
}
```

---

## 操作符枚举

- `AdditiveOperator`: `+`, `-`
- `MultiplicativeOperator`: `*`, `/`, `%`
- `RelationalOperator`: `<`, `>`, `<=`, `>=`
- `EqualityOperator`: `==`, `!=`
- `UnaryOperator`: `+`, `-`, `!`, `++`, `--`
- `PostfixOperator`: `++`, `--`
- `AssignmentOperator`: `=`, `+=`, `-=`, `*=`, `/=`, `%=`

---

## AST 构建流程

1. **ANTLR 解析**: 生成解析树
2. **ASTBuilder 遍历**: 使用 Visitor 模式遍历解析树
3. **节点创建**: 为每个语法规则创建对应的 AST 节点
4. **关系建立**: 建立父子关系，形成树结构
5. **位置记录**: 记录每个节点的源代码位置

---

## 设计模式

### Visitor 模式
- **接口**: `ASTVisitor<T>`
- **实现**: `ASTBuilder`, `SemanticAnalyzer`, `IRGenerator`
- **优势**: 分离数据结构（AST）和算法（分析、生成）

### Composite 模式
- **基类**: `ASTNode`
- **组合**: 节点可以包含子节点
- **优势**: 统一处理单个节点和节点树

---

## 关键设计决策

1. **位置信息保留**: 所有节点记录源代码位置，便于错误报告
2. **类型信息分离**: 类型信息在语义分析阶段添加，不在 AST 构建时确定
3. **表达式层次**: 按照运算符优先级组织表达式节点
4. **可选节点处理**: 使用 null 表示可选部分（如 else 分支）

---

<div align="center">

**🌳 AST 模块详解**

Made with ❤️ by Gemini-C Compiler Team

</div>

