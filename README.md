# wei-C 编译器

**wei-C** 是一个**完整的类C语言编译器**，使用 Java + ANTLR 4 实现从词法分析到 LLVM IR 代码生成的完整编译流程。

**核心指标**：
| 功能 | 数值 |
|------|------|
| 编译阶段 | 4阶段（词法→语法→语义→代码生成） |
| AST节点 | 65个 |
| 语义检查 | **38种错误检查** |
| 代码优化 | TAC级别优化 |
| 测试覆盖 | 4个示例（100%通过✅） |

---

## 🎯 编译器功能详解

### 1. 完整的编译流程（4阶段）

```
源代码(.gc)
    ↓
[阶段1] 词法/语法分析 → AST（65个节点）
    ↓
[阶段2] 语义分析 → 符号表 + 38种错误检查
    ↓
[阶段3] 中间代码生成 → TAC（三地址代码）
    ↓
[阶段4] 代码生成 → LLVM IR(.ll文件)
```

### 2. 语义检查和分析

编译器支持**38种语义错误检查**，包括：
- ✅ **类型检查**：类型兼容性、隐式转换验证
- ✅ **符号表管理**：变量重复声明、未定义使用、作用域管理
- ✅ **数组和指针**：维度检查、指针运算验证
- ✅ **结构体**：成员访问检查、初始化验证
- ✅ **函数**：参数类型检查、返回值验证、递归调用支持
- ✅ **控制流**：break/continue有效性检查、switch完整性

### 3. 中间代码优化（TAC级别）

编译器在生成 LLVM IR 前进行**TAC级别的优化**：
- ✅ **常量传播**：识别和替换常量表达式
- ✅ **死代码消除**：移除不可达代码和未使用变量
- ✅ **循环优化**：循环不变式外提、内存到寄存器转换
- ✅ **表达式优化**：公共子表达式消除

### 4. 完整的类型系统

支持 C 语言的复杂类型：
- ✅ **基础类型**：`int`, `float`, `char`, `string`
- ✅ **数组类型**：多维数组、函数参数数组
- ✅ **结构体类型**：结构体定义、嵌套、成员访问
- ✅ **指针类型**：指针声明、指针运算（ptr±int, ptr-ptr）
- ✅ **类型转换**：隐式转换、显式强制转换

### 5. 调试和可视化支持

编译器提供**4种调试输出**，帮助理解编译过程：
- `--debug-ast` : 输出抽象语法树
- `--debug-symtable` : 输出符号表信息
- `--debug-ir` : 输出 TAC 中间代码
- `--debug-codegen` : 输出 LLVM IR 生成过程

### 6. 支持的语言特性

| 特性 | 支持情况 |
|------|--------|
| 变量声明和赋值 | ✅ 完全支持 |
| 函数定义和调用 | ✅ 支持递归 |
| 数组操作 | ✅ 多维数组，动态访问 |
| 结构体 | ✅ 定义、初始化、成员访问 |
| 控制流 | ✅ if/else, while, for, switch |
| 运算符 | ✅ 算术、逻辑、比较、位运算 |
| 指针运算 | ✅ 地址操作、指针算术、解引用 |

---

## 🚀 快速开始

### 环境要求
- Java 11+
- Maven 3.6+

### 三步启动

```bash
# 1. 编译项目
mvn clean compile

# 2. 运行编译器
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="src/test/examples/example1.gc output.ll"

# 3. 查看输出
cat output/example1_output/example1_LLVM.ll  # Linux/Mac
type output\example1_output\example1_LLVM.ll # Windows
```

---

## 📚 语言语法

### 数据类型
```c
int a = 10;           // 整数
float b = 3.14;       // 浮点数
char c = 'A';         // 字符
string s = "Hello";   // 字符串
```

### 数组
```c
int arr[10];          // 一维数组
int matrix[3][3];     // 二维数组
arr[0] = 5;           // 数组访问
matrix[1][2] = 10;    // 多维数组访问
```

### 结构体
```c
struct Point {
    int x;
    int y;
};

struct Point p;
p.x = 1;
p.y = 2;
```

### 函数
```c
int add(int a, int b) {
    return a + b;
}

int main() {
    int result = add(5, 3);
    return result;
}
```

### 控制语句
```c
if (condition) { } else { }
while (condition) { }
for (int i = 0; i < 10; i++) { }
switch (value) { case 1: ... break; }
```

### 运算符
```c
// 算术：a + b, a - b, a * b, a / b, a % b
// 比较：a == b, a != b, a < b, a > b, a <= b, a >= b
// 逻辑：a && b, a || b, !a
// 赋值：a = b, a += b, a -= b, a *= b, a /= b, a %= b
// 自增自减：a++, ++a, a--, --a
```

---

## 🔧 编译选项

### 调试标志
添加以下标志查看编译各阶段的产物：

```bash
# 显示所有调试信息
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="input.gc output.ll --debug-all"

# 显示特定信息
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="input.gc output.ll --debug-ast"        # AST
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="input.gc output.ll --debug-symtable"  # 符号表
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="input.gc output.ll --debug-ir"        # TAC
```

### 编译选项表

| 选项 | 说明 | 输出文件 |
|------|------|--------|
| `--debug-ast` | 显示抽象语法树 | `*_AST.txt` |
| `--debug-symtable` | 显示符号表 | `*_SymbolTable.txt` |
| `--debug-ir` | 显示中间代码（TAC） | `*_IR_TAC.tac` |
| `--debug-all` | 显示所有调试信息 | 上述所有文件 |
| `--optimize` | 启用代码优化 | - |

---

## 📁 输出文件结构

所有输出文件位于 `output/<源文件名>_output/` 目录下：

```
output/
└── example1_output/
    ├── example1_LLVM.ll         ← LLVM IR代码（主输出）
    ├── example1_AST.txt         ← 抽象语法树（--debug-ast）
    ├── example1_SymbolTable.txt ← 符号表（--debug-symtable）
    └── example1_IR_TAC.tac      ← 三地址码（--debug-ir）
```

---

## ❌ 错误处理

### 语法错误
编译器会报告语法错误，包括缺少分号、括号不匹配、关键字拼写错误。

### 语义错误 - 38种检查

| 类别 | 错误示例 |
|------|--------|
| **类型检查** | 操作数类型不匹配、赋值左右类型不兼容、参数类型不匹配 |
| **符号表** | 未定义的标识符、标识符重定义、作用域错误 |
| **数组/结构体** | 下标不是整数、维数错误、成员不存在 |
| **函数** | 参数数量不匹配、返回值不匹配 |
| **控制流** | break/continue不在循环中 |
| **其他** | main函数错误、switch类型不匹配 |

### 错误示例
```c
int main() {
    int result = undefined_var;  // 错误：未定义的变量
    float f = "hello";           // 错误：类型不匹配
    int a = 20;                  // 错误：a已定义
    break;                        // 错误：break不在循环中
    return 0;
}
```

---

## 📝 测试示例

项目包含4个完整的测试示例（位于 `src/test/examples/`）：

| 文件 | 演示内容 |
|------|--------|
| `example1.gc` | 基础运算、控制流、数组 |
| `example2.gc` | 函数、递归、数组访问 |
| `example3.gc` | 结构体、成员访问 |
| `example4.gc` | 复杂程序（数组参数、结构体嵌套） |

**所有示例都已 100% 通过编译！** ✅

编译任意示例：
```bash
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" -Dexec.args="src/test/examples/exampleN.gc output.ll --debug-all"
```

---

## 🔍 故障排查

### 常见问题

| 问题 | 解决方案 |
|------|--------|
| 编译失败 | 检查 Java 版本 (`java -version`)、清理缓存 (`mvn clean`) |
| 运行时错误 | 检查输入文件存在性、使用 `--debug-all` 查看详细信息 |
| 内存不足 | 增加 JVM 堆内存：`mvn exec:java -Dexec.mainClass="..." -Xmx2g` |
| 语法错误 | 检查语法正确性、参考语言语法部分 |
| 语义错误 | 检查变量声明、类型匹配、作用域 |

### 调试技巧

```bash
# 查看 AST
java WeiCompiler input.gc output.ll --debug-ast

# 查看符号表
java WeiCompiler input.gc output.ll --debug-symtable

# 查看中间代码
java WeiCompiler input.gc output.ll --debug-ir

# 启用代码优化
java WeiCompiler input.gc output.ll --optimize
```

---

## 📋 项目统计

- **代码规模**: ~15,000+ 行编译器源码
- **语法定义**: 274 行 ANTLR 语法规则
- **编译阶段**: 4 个（词法→语法→语义→代码生成）
- **核心模块**: 6 个（AST、语义分析、类型系统、中间代码、优化、代码生成）
- **测试覆盖**: 4 个功能完整的示例程序
- **完成度**: ✅ 核心功能 100% 完成

---

## 📁 项目结构

```
exp-design/
├── src/main/java/          # 源代码
│   └── com/wei/compiler/
│       ├── ast/            # AST节点定义（65个）
│       ├── semantic/       # 语义分析（符号表、类型检查）
│       ├── ir/             # 中间代码生成（TAC）
│       ├── optimizer/      # 代码优化（常量传播、死代码消除等）
│       ├── codegen/        # 目标代码生成（LLVM IR）
│       └── WeiCompiler.java # 主入口
├── src/test/examples/      # 测试示例（example1-4，全部通过✅）
├── docs/                   # 技术文档（7个深度文档）
├── output/                 # 编译输出文件夹
├── pom.xml                 # Maven配置
└── README.md               # 本文件
```

---

## 📚 技术栈

| 组件 | 版本 |
|------|------|
| Java | 11+ |
| ANTLR | 4.9.3 |
| Maven | 3.6+ |
| LLVM | IR标准 |

---

## 📖 更多资源

深度学习请查看技术文档：

| 文档 | 说明 |
|------|------|
| **[ARCHITECTURE.md](docs/ARCHITECTURE.md)** | 编译器架构与实现 |
| [AST_MODULE.md](docs/AST_MODULE.md) | AST模块详解（65个节点） |
| [SEMANTIC_ANALYSIS.md](docs/SEMANTIC_ANALYSIS.md) | 语义分析与错误检查（38种） |
| [CODE_GENERATION.md](docs/CODE_GENERATION.md) | 代码生成流程 |
| [POINTER_ARITHMETIC.md](docs/POINTER_ARITHMETIC.md) | 指针运算支持 |
| [OPTIMIZER.md](docs/OPTIMIZER.md) | 代码优化 |
| [TEST_SUMMARY.md](docs/TEST_SUMMARY.md) | 测试用例 |

---

## 📌 版本信息

- **版本**: 1.0.0
- **状态**: ✅ 核心编译功能完成
- **最后更新**: 2025年12月14日
