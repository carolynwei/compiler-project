# 🏗️ 编译器架构设计

## 整体架构

wei-C 编译器采用经典的编译架构，实现完整的编译六阶段：

```
源代码 → 词法分析 → 语法分析 → 语义分析 → 中间代码生成 → 代码优化 → 目标代码生成
  .gc      Lexer     Parser    Semantic      TAC          Optimizer    LLVM IR
```

---

## 核心类：WeiCompiler

**文件位置**: `src/main/java/com/wei/compiler/WeiCompiler.java`

### 类概述

`WeiCompiler` 是编译器的主入口类，负责协调所有编译阶段。

### 主要职责

1. **编译流程协调**: 按顺序执行六个编译阶段
2. **错误处理**: 统一处理各阶段的错误
3. **调试支持**: 提供调试开关控制各阶段的输出
4. **配置管理**: 管理编译器配置（优化级别、目标架构等）

### 核心方法

#### `compile(String inputFile, String outputFile)`

主编译方法，执行完整的编译流程：

```java
public void compile(String inputFile, String outputFile) throws IOException {
    // 阶段一：词法分析和语法分析
    ASTNode ast = parseFile(inputFile);
    
    // 阶段二：语义分析
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
    semanticAnalyzer.analyze(ast);
    
    // 阶段三：中间代码生成
    IRGenerator irGenerator = new IRGenerator(semanticAnalyzer.getSymbolTableManager());
    IRProgram irProgram = irGenerator.generate(ast);
    
    // 阶段四：代码优化
    IROptimizer optimizer = new IROptimizer(config.isOptimize());
    IRProgram optimizedIR = optimizer.optimize(irProgram);
    
    // 阶段五：目标代码生成
    CodeGenerator codeGenerator = new CodeGenerator();
    String targetCode = codeGenerator.generate(optimizedIR, semanticAnalyzer.getSymbolTableManager());
    
    // 输出目标代码
    writeToFile(targetCode, outputFile);
}
```

**设计要点**:
- 每个阶段接收上一阶段的输出
- 语义分析器构建符号表，传递给后续阶段
- 优化阶段可选，通过配置控制

#### `parseFile(String inputFile)`

解析源文件，生成抽象语法树：

```java
private ASTNode parseFile(String inputFile) throws IOException {
    // 1. 读取源文件
    String sourceCode = Files.readString(Paths.get(inputFile));
    
    // 2. 创建词法分析器
    WeiCLexer lexer = new WeiCLexer(CharStreams.fromString(sourceCode));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    
    // 3. 创建语法分析器
    WeiCParser parser = new WeiCParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(new CompilerErrorListener());
    
    // 4. 解析程序
    ParseTree parseTree = parser.program();
    
    // 5. 构建抽象语法树
    ASTBuilder astBuilder = new ASTBuilder();
    ASTNode ast = astBuilder.build(parseTree);
    
    return ast;
}
```

**设计要点**:
- 使用 ANTLR 4 进行词法和语法分析
- 自定义错误监听器，不中断编译流程
- ASTBuilder 将 ANTLR 解析树转换为自定义 AST

### 内部类

#### `CompilerConfig`

编译器配置类，管理编译选项：

```java
public static class CompilerConfig {
    private boolean optimize = false;           // 是否启用优化
    private String targetArchitecture = "x86-64"; // 目标架构
    private boolean verbose = false;            // 详细输出
}
```

#### `CompilerErrorListener`

自定义 ANTLR 错误监听器：

```java
private static class CompilerErrorListener extends BaseErrorListener {
    private boolean hasError = false;
    
    @Override
    public void syntaxError(...) {
        hasError = true;
        System.err.println("语法错误 [" + line + ":" + charPositionInLine + "] " + msg);
    }
}
```

**设计要点**:
- 不调用 `System.exit()`，允许程序继续执行
- 记录错误但不中断编译流程
- 支持测试友好的错误处理

### 调试支持

提供四个调试开关：

```java
public static boolean DEBUG_AST = false;       // 显示 AST
public static boolean DEBUG_SYMTABLE = false;   // 显示符号表
public static boolean DEBUG_IR = false;        // 显示中间代码
public static boolean DEBUG_CODEGEN = false;    // 显示代码生成过程
```

---

## 模块依赖关系

```
WeiCompiler
    ├── ASTBuilder (ast/)
    ├── SemanticAnalyzer (semantic/)
    │   └── SymbolTableManager (semantic/)
    ├── IRGenerator (ir/)
    │   └── SymbolTableManager (semantic/)
    ├── IROptimizer (optimizer/)
    └── CodeGenerator (codegen/)
        └── SymbolTableManager (semantic/)
```

### 数据流

1. **AST**: 语法分析 → 语义分析 → 中间代码生成
2. **符号表**: 语义分析构建 → 传递给 IR 生成和代码生成
3. **IR**: 中间代码生成 → 优化 → 代码生成

---

## 设计模式

### 1. Visitor 模式

- `ASTVisitor`: AST 节点访问接口
- `SemanticAnalyzer`: 实现语义分析访问
- `IRGenerator`: 实现 IR 生成访问

### 2. Builder 模式

- `ASTBuilder`: 构建抽象语法树

### 3. Strategy 模式

- `IROptimizer`: 优化策略的容器
- 各种优化 Pass: 不同的优化策略

---

## 错误处理策略

1. **语法错误**: 记录但不中断，尝试继续构建 AST
2. **语义错误**: 记录所有错误，根据严重程度决定是否继续
3. **代码生成错误**: 抛出异常，中断编译

---

## 扩展点

1. **新增优化 Pass**: 实现 `OptimizerPass` 接口，添加到 `IROptimizer`
2. **新增目标架构**: 实现新的 `CodeGenerator` 子类
3. **新增语言特性**: 修改语法文件，更新 AST 节点，扩展语义分析

---

## 编译阶段实现状态

### 各阶段实现详情

| 阶段 | 位置 | 状态 | 说明 |
|------|------|------|------|
| 词法分析 | `WeiC.g4` | ✅ | ANTLR 完整支持 |
| 语法分析 | `WeiC.g4` + `ASTBuilder.java` | ✅ | 340 行语法，65 个 AST 节点 |
| 语义分析 | `semantic/` | ✅ | 38 种错误检查，完整符号表 |
| 中间代码生成 | `ir/` | ✅ | TAC 四元式，基本块，CFG |
| 代码优化 | `optimizer/` | 🚧 | 核心算法已实现（80% 完成） |
| 目标代码生成 | `codegen/` | ✅ | LLVM IR，支持所有数据类型 |

**总体完成度**: 96.7%

---

<div align="center">

**🏗️ 架构设计与阶段实现文档**

Made with ❤️ by wei-C Compiler Team

</div>

