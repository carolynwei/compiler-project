# 🏗️ 项目结构

## 📁 目录结构

```
exp-design/
├── src/
│   ├── main/
│   │   ├── antlr4/com/gemini/grammar/
│   │   │   └── GeminiC.g4              # ANTLR 语法定义
│   │   └── java/com/gemini/compiler/
│   │       ├── GeminiCompiler.java        # 编译器主类
│   │       ├── ast/                      # AST 节点（55个文件）
│   │       ├── semantic/                 # 语义分析（11个文件）
│   │       ├── ir/                       # 中间代码生成（5个文件）
│   │       ├── optimizer/                # 代码优化（9个文件）
│   │       └── codegen/                  # LLVM IR 生成（1个文件）
│   └── test/
│       ├── java/                         # 单元测试
│       └── examples/                     # 测试用例
├── docs/                                 # 技术文档
├── pom.xml                               # Maven 配置
└── README.md                             # 项目主文档
```

---

## 🔄 编译六阶段流程

### 阶段一：词法分析
- **位置**: `src/main/antlr4/com/gemini/grammar/GeminiC.g4`
- **工具**: ANTLR 4 自动生成
- **输出**: Token 流

### 阶段二：语法分析
- **位置**: `ASTBuilder.java`
- **工具**: ANTLR 4 + 自定义 AST 构建器
- **输出**: 抽象语法树 (AST)

### 阶段三：语义分析
- **位置**: `semantic/`
- **主要类**: `SemanticAnalyzer.java`, `SymbolTableManager.java`
- **输出**: 带类型信息的 AST + 符号表

### 阶段四：中间代码生成
- **位置**: `ir/`
- **主要类**: `IRGenerator.java`
- **输出**: 三地址代码 (TAC)

### 阶段五：代码优化
- **位置**: `optimizer/`
- **主要类**: `IROptimizer.java`
- **输出**: 优化后的 TAC

### 阶段六：目标代码生成
- **位置**: `codegen/`
- **主要类**: `CodeGenerator.java`
- **输出**: LLVM IR

---

## 📦 模块说明

### AST 模块 (`ast/`)
- **文件数**: 55 个
- **核心类**: `ASTNode`, `ASTBuilder`, `ASTPrinter`
- **功能**: 构建和管理抽象语法树
- **详细文档**: [AST模块详解](AST_MODULE.md)

### 语义分析模块 (`semantic/`)
- **文件数**: 11 个
- **核心类**: `SemanticAnalyzer`, `SymbolTableManager`, `ExpressionTypeAnalyzer`
- **功能**: 类型检查、作用域管理、错误检测
- **详细文档**: [语义分析详解](SEMANTIC_ANALYSIS.md)

### 中间代码模块 (`ir/`)
- **文件数**: 5 个
- **核心类**: `IRGenerator`, `TACInstruction`, `IRProgram`, `BasicBlock`
- **功能**: 生成三地址代码
- **详细文档**: [中间代码生成详解](IR_GENERATION.md)

### 优化器模块 (`optimizer/`)
- **文件数**: 9 个
- **核心类**: `IROptimizer`, 各种优化 Pass
- **功能**: 代码优化
- **详细文档**: [优化器详解](OPTIMIZER.md)

### 代码生成模块 (`codegen/`)
- **文件数**: 1 个
- **核心类**: `CodeGenerator`
- **功能**: 生成 LLVM IR
- **详细文档**: [代码生成详解](CODE_GENERATION.md)

---

## 🔗 模块依赖

```
GeminiCompiler
    ├── ASTBuilder (ast/)
    ├── SemanticAnalyzer (semantic/)
    │   └── SymbolTableManager (semantic/)
    ├── IRGenerator (ir/)
    │   └── SymbolTableManager (semantic/)
    ├── IROptimizer (optimizer/)
    └── CodeGenerator (codegen/)
        └── SymbolTableManager (semantic/)
```

---

## 📚 详细文档

每个模块的详细说明请参考：
- [架构设计](ARCHITECTURE.md) - 整体架构
- [AST模块详解](AST_MODULE.md) - AST 详细说明
- [语义分析详解](SEMANTIC_ANALYSIS.md) - 语义分析详细说明
- [中间代码生成详解](IR_GENERATION.md) - IR 生成详细说明
- [代码生成详解](CODE_GENERATION.md) - 代码生成详细说明
- [优化器详解](OPTIMIZER.md) - 优化器详细说明

---

<div align="center">

**🏗️ 项目结构文档**

Made with ❤️ by Gemini-C Compiler Team

</div>
