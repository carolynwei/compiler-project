# 🔨 Gemini-C 编译器 - 构建指南

## 环境要求

| 工具 | 版本 | 说明 |
|-----|------|------|
| Java | 11+ | JDK 开发环境 |
| Maven | 3.6+ | 项目构建工具 |

---

## 🚀 构建步骤

### 一键构建（推荐）

```bash
mvn clean install
```

这将依次执行：
1. 清理 (`clean`)
2. 生成 ANTLR 代码 (`generate-sources`)
3. 编译 (`compile`)
4. 测试 (`test`)
5. 打包 (`package`)
6. 安装到本地仓库 (`install`)

### 分步构建

```bash
# 步骤 1: 清理项目
mvn clean

# 步骤 2: 生成 ANTLR 代码
mvn generate-sources

# 步骤 3: 编译项目
mvn compile

# 步骤 4: 运行测试（可选）
mvn test

# 步骤 5: 打包成 JAR（可选）
mvn package
```

---

## 📂 项目目录结构

```
exp-design/
├── src/
│   ├── main/
│   │   ├── antlr4/                      # ANTLR 语法文件
│   │   │   └── com/gemini/grammar/
│   │   │       └── GeminiC.g4           # 语法定义
│   │   ├── java/                        # Java 源代码
│   │   │   └── com/gemini/compiler/
│   │   │       ├── GeminiCompiler.java
│   │   │       ├── ast/                 # AST 节点
│   │   │       ├── semantic/            # 语义分析
│   │   │       ├── ir/                  # 中间代码生成
│   │   │       └── codegen/             # 目标代码生成
│   │   └── resources/
│   └── test/                            # 测试代码
│       ├── java/                        # 单元测试
│       └── examples/                     # 测试用例
├── target/                              # 构建输出（自动生成）
│   ├── generated-sources/
│   │   └── antlr4/                      # ANTLR 生成的代码
│   ├── classes/                         # 编译后的 .class 文件
│   └── gemini-compiler-*.jar           # JAR 包
└── pom.xml                              # Maven 配置文件
```

---

## 🔧 Maven 命令详解

### 常用命令

| 命令 | 说明 |
|------|------|
| `mvn clean` | 清理构建输出 |
| `mvn compile` | 编译源代码 |
| `mvn test` | 运行测试 |
| `mvn package` | 打包成 JAR |
| `mvn install` | 安装到本地仓库 |
| `mvn clean install` | 清理并完整构建 |

### ANTLR 相关

| 命令 | 说明 |
|------|------|
| `mvn generate-sources` | 生成 ANTLR 代码 |
| `mvn clean generate-sources` | 清理后重新生成 |

---

## 🐛 常见问题

### Q1: 构建失败，提示找不到 ANTLR 类？

**A**: 确保先运行 `mvn generate-sources` 生成 ANTLR 代码，然后再编译。

```bash
mvn clean generate-sources compile
```

### Q2: 如何查看详细的构建日志？

**A**: 使用 `-X` 参数：

```bash
mvn clean install -X
```

### Q3: 如何跳过测试？

**A**: 使用 `-DskipTests` 参数：

```bash
mvn clean install -DskipTests
```

### Q4: 如何只编译不打包？

**A**: 使用 `compile` 而不是 `package`：

```bash
mvn clean compile
```

---

## 📦 生成的文件

### ANTLR 生成的文件

位置：`target/generated-sources/antlr4/com/gemini/grammar/`

- `GeminiCLexer.java` - 词法分析器
- `GeminiCParser.java` - 语法分析器
- `GeminiCVisitor.java` - Visitor 接口
- `GeminiCBaseVisitor.java` - Visitor 基类
- `GeminiCListener.java` - Listener 接口（可选）
- `GeminiCBaseListener.java` - Listener 基类（可选）

### JAR 文件

位置：`target/`

- `gemini-compiler-1.0-SNAPSHOT.jar` - 不包含依赖
- `gemini-compiler-1.0-SNAPSHOT-jar-with-dependencies.jar` - 包含所有依赖 ✅

---

## ✅ 验证构建

构建成功后，您应该看到：

```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

可以运行测试验证：

```bash
mvn test
```

---

## 📚 更多信息

- 📖 [README.md](../README.md) - 项目主文档
- 🚀 [QUICK_START.md](../QUICK_START.md) - 快速开始指南
- 🏗️ [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - 项目结构说明

---

<div align="center">

**🔨 构建愉快！**

Made with ❤️ by Gemini-C Compiler Team

</div>
