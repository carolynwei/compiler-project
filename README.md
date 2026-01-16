# wei-C 编译器

**wei-C** 是一个**完整的类C语言编译器**，使用 Java + ANTLR 4 实现从词法分析到 LLVM IR 代码生成的完整编译流程。

**核心指标**：
| 功能 | 数值 |
|------|------|
| 编译阶段 | 6阶段（词法→语法→语义→中间代码→优化→代码生成） |
| AST节点 | 62个 |
| 语义检查 | **40种语义错误检查** |
| 代码优化 | TAC级别优化 |
| 测试覆盖 | 4个示例（100%通过✅） |

---

## 🎯 编译器功能详解

### 1. 完整的编译流程（6阶段）

```
源程序(.gc)
    ↓
[阶段1] 词法分析程序 → Token流
    ↓
[阶段2] 语法分析程序 → AST（65个节点）
    ↓
[阶段3] 语义分析程序 → 符号表 + 40种语义错误检查
    ↓
[阶段4] 中间代码生成程序 → TAC（三地址代码）
    ↓
[阶段5] 代码优化程序 → 优化后的TAC
    ↓
[阶段6] 目标代码生成程序 → LLVM IR(.ll文件)
    ↓
目标程序
```

### 2. 语义检查和分析

编译器支持**40种语义错误检查**，在源码中以 `SemanticErrorType` 枚举定义。主要覆盖类型检查、声明/作用域、数组/结构体、函数及控制流等类别。下面列出全部错误类型及简短说明：

- `TYPE_MISMATCH`: 类型不匹配
- `INCOMPATIBLE_ASSIGNMENT`: 赋值类型不兼容
- `FUNCTION_PARAMETER_MISMATCH`: 函数参数类型或数量不匹配
- `CONTROL_EXPRESSION_TYPE_ERROR`: 控制语句条件类型错误（如 if/for/switch 条件）
- `RETURN_TYPE_MISMATCH`: 返回类型与函数声明不匹配
- `UNDEFINED_IDENTIFIER`: 未定义的标识符（使用前未声明）
- `REDEFINITION`: 标识符重定义（在同一作用域多次定义）
- `BREAK_CONTINUE_OUTSIDE_LOOP`: `break`/`continue` 不在循环或 switch 中使用
- `ARRAY_INDEX_TYPE_ERROR`: 数组下标不是整数类型
- `ARRAY_DIMENSION_ERROR`: 数组访问时维数不匹配或错误
- `NON_STRUCT_MEMBER_ACCESS`: 对非结构体变量使用成员访问运算符
- `STRUCT_MEMBER_NOT_FOUND`: 结构体成员不存在
- `STRUCT_CIRCULAR_DEPENDENCY`: 结构体定义中存在循环依赖
- `DUPLICATE_INITIALIZATION`: 变量重复初始化
- `NON_CALLABLE_IDENTIFIER`: 尝试调用非函数标识符
- `DIVISION_BY_ZERO`: 常量除法中出现除数为零（静态检测）
- `INVALID_LVALUE`: 无效的左值（不能被赋值）
- `MAIN_FUNCTION_MISSING`: `main` 函数缺失或签名不正确（应返回 `int`）
- `SWITCH_CASE_TYPE_MISMATCH`: `switch` 表达式类型与 `case` 常量类型不匹配
- `STRUCT_TYPE_UNDEFINED`: 使用了未定义的结构体类型
- `ARRAY_SIZE_NEGATIVE`: 数组大小为负数
- `FUNCTION_RECURSION_DEPTH`: 函数递归深度超限（防护检测）
- `VARIABLE_NOT_INITIALIZED`: 变量可能在使用前未初始化（静态警告）
- `CONSTANT_MODIFICATION`: 尝试修改常量
- `INVALID_OPERATOR_USAGE`: 运算符使用不当（类型不支持）
- `EXPECTED_CONSTANT_EXPRESSION`: 期望常量表达式（如 `case` 标签）
- `REPEATED_CASE_LABEL`: 重复的 `case` 常量标签
- `REPEATED_DEFAULT_LABEL`: 重复的 `default` 标签
- `INVALID_ARRAY_DIMENSION`: 无效的数组维度表达式
- `FLOAT_USED_AS_ARRAY_INDEX`: 浮点数不能作为数组下标
- `IMPLICIT_FLOAT_TO_INT_CONVERSION`: 隐式浮点到整型转换可能丧失精度（警告）
- `UNREACHABLE_CODE`: 不可达代码检测
- `FUNCTION_CALLED_BEFORE_DECLARATION`: 函数在声明前被调用（视语言规则）
- `SHADOWED_VARIABLE`: 局部变量遮蔽外层变量（警告）
- `UNUSED_VARIABLE`: 变量已声明但未使用（警告）
- `STRUCT_SIZE_ZERO`: 结构体没有字段（大小为零）
- `VOID_PARAMETER_TYPE`: 参数类型不能为 `void`
- `MULTIPLE_DEFAULTS_IN_SWITCH`: `switch` 中存在多个 `default` 分支
- `VOID_FUNCTION_RETURN_VALUE`: `void` 函数返回值错误（不应返回值）
- `POTENTIAL_NULL_POINTER`: 可能的空指针访问（静态警告）

这些错误由 `SemanticAnalyzer`、`TypeAnalyzer` 与 `SymbolTableManager` 协同检测，错误种类与实现位于 `src/main/java/com/wei/compiler/semantic/` 路径下。

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

## 📊 符号表快照机制

编译器通过**栈式作用域管理**和**快照捕获**实现完整的符号表历史记录。`SymbolTableManager` 在 `exitScope()` 时自动保存已退出作用域的快照，并在生成符号表输出时按退出顺序显示所有快照。

### 快照结构

每个快照 (`ScopeSnapshot`) 包含：
- **快照 ID**（唯一递增编号）：按作用域退出顺序编号（1, 2, 3, ...）
- **作用域级别**（level）：0 = 全局作用域，1+ = 局部作用域（函数参数、函数内部、循环、条件块等）
- **符号集合**：该作用域内所有符号（变量、函数参数、结构体成员等）

### 输出格式示例

```
=== 完整符号表 ===
--- 已退出的作用域快照 (按退出顺序) ---
作用域 1:
  SymbolEntry[name='x', type=VARIABLE, dataType=int, kind=STRUCT_MEMBER, scope=1]
  SymbolEntry[name='y', type=VARIABLE, dataType=int, kind=STRUCT_MEMBER, scope=1]

作用域 2:
  SymbolEntry[name='i', type=VARIABLE, dataType=int, kind=LOCAL, scope=2, init=true]

作用域 3:
  SymbolEntry[name='count', type=VARIABLE, dataType=int, kind=LOCAL, scope=1, init=true]
  SymbolEntry[name='sum', type=VARIABLE, dataType=float, kind=LOCAL, scope=1, init=true]
```

### 作用域说明

- **快照ID**：使用唯一的快照编号替代重复的作用域级别标签，避免歧义。若在同一程序中存在多个相同 level 的作用域（如多个嵌套函数、多个循环等），快照ID能唯一区分它们的退出顺序。
- **作用域级别**（scope字段）：符号表条目中的 `scope` 字段记录该符号所在的作用域级别（0-based）。
- **符号类型**（kind 字段）：
  - `GLOBAL` - 全局作用域定义的函数/变量
  - `PARAMETER` - 函数形参
  - `LOCAL` - 局部变量（在函数体内声明）
  - `STRUCT_MEMBER` - 结构体成员
  
### 调试步骤

启用符号表调试：
```bash
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" \
  -Dexec.args="src/test/examples/example1.gc output.ll --debug-symtable" 2>&1 | tee debug.log
```

这会输出：
1. **栈式作用域日志**（实时进入/退出信息）
2. **符号表快照**（最终输出文件中的格式）
3. **语义分析日志**（错误收集结果）

---
## ❌ 错误处理

### 语法错误
编译器会报告语法错误，包括缺少分号、括号不匹配、关键字拼写错误。

### 语义错误 - 40种检查

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

| 文件 | 演示内容 | 快照数量 |
|------|--------|--------|
| `example1.gc` | 基础运算、控制流、数组 | 3个（struct成员、for循环、main函数） |
| `example2.gc` | 函数、递归、数组访问 | 6个（3个函数参数作用域、2个for循环、main函数） |
| `example3.gc` | 复杂表达式、所有运算符 | 1个（main函数所有局部变量） |
| `example4.gc` | 复杂程序（嵌套循环、结构体数组） | 8个（struct成员、findMax函数、main的嵌套作用域） |

**所有示例都已 100% 通过编译！** ✅

### 符号表快照示例对比

#### Example 1 - 基础示例
```
作用域 1: struct Point 的成员（x, y）
作用域 2: for 循环内的 i
作用域 3: main 函数的本地变量（count, sum, p1, matrix）
```

#### Example 2 - 多函数示例
```
作用域 1: add 函数参数（a, b）
作用域 2: multiply 函数参数（a, b）← 注意快照ID唯一性避免混淆
作用域 3: factorial 函数参数（n）
作用域 4: main 内第一个 for 循环（i）
作用域 5: main 内第二个 for 循环（i）
作用域 6: main 函数的本地变量（result, numbers, sum）
```

#### Example 3 - 单函数复杂表达式
```
作用域 1: main 函数内所有局部变量（a, b, c, result, isEqual, 等）
```

#### Example 4 - 嵌套和复杂结构
```
作用域 1: struct Student 的成员（id, age, grade）
作用域 2: findMax 内第一个 for 循环（i）
作用域 3: findMax 函数参数（arr, size）和本地变量（max）
作用域 4: main 内第一个 for 循环（i）
作用域 5: main 内嵌套 if 块（temp）← 在第二个 for 循环内
作用域 6: main 内第二个 for 循环（j）
作用域 7: main 内第一个 for 循环（重新进入时新建的作用域）
作用域 8: main 函数的本地变量（scores, students, maxScore, choice）
```

编译任意示例并查看符号表：
```bash
mvn exec:java -Dexec.mainClass="com.wei.compiler.WeiCompiler" \
  -Dexec.args="src/test/examples/example1.gc output.ll --debug-symtable"

# 查看生成的符号表文件
cat output/example1_output/example1_SymbolTable.txt
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
- **编译阶段**: 6 个（词法→语法→语义→中间代码→优化→代码生成）
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
| [SEMANTIC_ANALYSIS.md](docs/SEMANTIC_ANALYSIS.md) | 语义分析与错误检查（48种） |
| [CODE_GENERATION.md](docs/CODE_GENERATION.md) | 代码生成流程 |
| [POINTER_ARITHMETIC.md](docs/POINTER_ARITHMETIC.md) | 指针运算支持 |
| [OPTIMIZER.md](docs/OPTIMIZER.md) | 代码优化 |
| [TEST_SUMMARY.md](docs/TEST_SUMMARY.md) | 测试用例 |
| [Symbol_Table_Snapshot.md](Symbol_Table_Snapshot.md) | 符号表快照|

---

## 📌 版本信息

- **版本**: 1.0.0
- **状态**: ✅ 核心编译功能完成
- **最后更新**: 2025年12月14日





