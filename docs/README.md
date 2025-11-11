# 📚 技术文档索引

## 📖 文档导航

### 入门文档

| 文档 | 说明 |
|------|------|
| [构建指南](BUILD_INSTRUCTIONS.md) | 详细的构建和配置说明 |
| [项目结构](PROJECT_STRUCTURE.md) | 项目目录结构和模块说明 |
| [编译阶段](COMPILATION_STAGES.md) | 编译六阶段实现状态 |

### 技术详解

| 文档 | 说明 | 包含的Java类 |
|------|------|------------|
| [架构设计](ARCHITECTURE.md) | 编译器整体架构和设计 | `GeminiCompiler.java` |
| [AST模块详解](AST_MODULE.md) | AST构建和节点详解 | `ast/` 目录下所有类（55个文件） |
| [语义分析详解](SEMANTIC_ANALYSIS.md) | 语义分析和符号表详解 | `semantic/` 目录下所有类（11个文件） |
| [中间代码生成详解](IR_GENERATION.md) | TAC生成详解 | `ir/` 目录下所有类（5个文件） |
| [代码生成详解](CODE_GENERATION.md) | LLVM IR生成详解 | `codegen/CodeGenerator.java` |
| [优化器详解](OPTIMIZER.md) | 代码优化详解 | `optimizer/` 目录下所有类（9个文件） |

---

## 🎯 快速导航

### 我想了解...

- **整体架构** → [架构设计](ARCHITECTURE.md)
- **AST如何构建** → [AST模块详解](AST_MODULE.md)
- **类型检查如何工作** → [语义分析详解](SEMANTIC_ANALYSIS.md)
- **如何生成TAC** → [中间代码生成详解](IR_GENERATION.md)
- **如何生成LLVM IR** → [代码生成详解](CODE_GENERATION.md)
- **优化如何实现** → [优化器详解](OPTIMIZER.md)

---

## 📝 文档说明

所有技术文档都包含：
- ✅ 模块概述
- ✅ 核心类详细说明
- ✅ 关键方法解释
- ✅ 代码示例
- ✅ 设计决策说明
- ✅ 每个Java文件的透彻解释

---

## 📊 文档统计

| 模块 | 文档 | Java文件数 | 详细程度 |
|------|------|-----------|---------|
| 架构 | [ARCHITECTURE.md](ARCHITECTURE.md) | 1 | ⭐⭐⭐⭐⭐ |
| AST | [AST_MODULE.md](AST_MODULE.md) | 55 | ⭐⭐⭐⭐⭐ |
| 语义分析 | [SEMANTIC_ANALYSIS.md](SEMANTIC_ANALYSIS.md) | 11 | ⭐⭐⭐⭐⭐ |
| 中间代码 | [IR_GENERATION.md](IR_GENERATION.md) | 5 | ⭐⭐⭐⭐⭐ |
| 代码生成 | [CODE_GENERATION.md](CODE_GENERATION.md) | 1 | ⭐⭐⭐⭐⭐ |
| 优化器 | [OPTIMIZER.md](OPTIMIZER.md) | 9 | ⭐⭐⭐⭐⭐ |

---

<div align="center">

**📚 完整的技术文档库**

Made with ❤️ by Gemini-C Compiler Team

</div>
