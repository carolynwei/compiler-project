# Gemini-C 编译器设计与实现

## 项目概述

**Gemini-C** 是一个完整的类C语言编译器，实现了从词法分析到目标代码生成的完整编译流程。该项目采用 Java + ANTLR 4 技术栈，支持完整的编译过程。

## 语言特性

### 支持的数据类型
- **基本类型**: `int`, `float`, `char`, `string`
- **复合类型**: 多维数组、结构体 (`struct`)

### 支持的运算符
- **算术运算**: `+`, `-`, `*`, `/`, `%`
- **比较运算**: `==`, `!=`, `<`, `>`, `<=`, `>=`
- **逻辑运算**: `&&`, `||`, `!`
- **自增自减**: `++`, `--` (前缀和后缀)
- **复合赋值**: `+=`, `-=`, `*=`, `/=`, `%=`

### 支持的控制语句
- **条件语句**: `if-else`
- **循环语句**: `while`, `for`
- **跳转语句**: `break`, `continue`
- **多分支**: `switch-case-default`

### 其他特性
- 支持行注释 (`//`) 和块注释 (`/* */`)
- 支持多维数组声明和访问
- 支持结构体定义和成员访问
- 完整的语义分析和错误检查

## 项目结构

```
exp-design/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── gemini/
│   │   │   │       ├── compiler/
│   │   │   │       │   ├── GeminiCompiler.java
│   │   │   │       │   ├── ast/
│   │   │   │       │   ├── lexer/
│   │   │   │       │   ├── parser/
│   │   │   │       │   ├── semantic/
│   │   │   │       │   ├── ir/
│   │   │   │       │   └── codegen/
│   │   │   │       └── grammar/
│   │   │   │           └── GeminiC.g4
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   ├── test/
│   │   └── examples/
│   ├── build/
│   ├── target/
│   ├── pom.xml
│   └── README.md
```

## 编译流程

1. **词法分析**: 使用 ANTLR 4 生成词法分析器
2. **语法分析**: 构建抽象语法树 (AST)
3. **语义分析**: 符号表管理和类型检查
4. **中间代码生成**: 生成三地址代码 (TAC)
5. **目标代码生成**: 生成 LLVM IR 代码

## 使用方法

### 环境要求
- Java 11 或更高版本
- Maven 3.6 或更高版本
- ANTLR 4.9 或更高版本

### 编译和运行
```bash
# 编译项目
mvn clean compile

# 运行编译器
mvn exec:java -Dexec.mainClass="com.gemini.compiler.GeminiCompiler" -Dexec.args="input.gc"

# 运行测试
mvn test
```

### 命令行选项
```bash
java GeminiCompiler <输入文件> [输出文件] [选项]

选项:
  --debug-ast      显示抽象语法树
  --debug-symtable 显示符号表
  --debug-ir       显示中间代码
  --debug-codegen  显示目标代码生成过程
  --optimize       启用优化
```

## 示例程序

### 基本语法示例
```c
/* Gemini-C 示例程序 1: 基本语法测试 */
struct Point {
    int x;
    int y;
};

int main() {
    int count = 10;
    float sum = 0.0;
    int matrix[3][3];
    
    struct Point p1;
    string message = "Hello, Gemini!";
    
    p1.x = 1;
    p1.y = 2;
    
    // for循环测试
    for (int i = 0; i < count; i++) {
        matrix[i][i] = i * i;
        sum += (float)i;
    }
    
    // switch语句测试
    switch (count) {
        case 10:
            count++;
            break;
        default:
            count *= 2;
    }
    
    // while语句和break/continue测试
    while (count > 0) {
        if (count % 2 == 0) {
            count--;
            continue;
        }
        if (count == 5) {
            break;
        }
        count -= 1;
    }
    
    return 0;
}
```

### 函数和数组示例
```c
/* Gemini-C 示例程序 2: 函数和数组测试 */
int add(int a, int b) {
    return a + b;
}

int multiply(int a, int b) {
    return a * b;
}

int factorial(int n) {
    if (n <= 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

int main() {
    int numbers[10];
    int result;
    
    // 初始化数组
    for (int i = 0; i < 10; i++) {
        numbers[i] = i + 1;
    }
    
    // 测试函数调用
    result = add(5, 3);
    result = multiply(result, 2);
    
    // 测试递归函数
    result = factorial(5);
    
    // 测试数组操作
    int sum = 0;
    for (int i = 0; i < 10; i++) {
        sum += numbers[i];
    }
    
    return sum;
}
```

## 语义错误检查

编译器支持 20+ 种语义错误检查：

### 类型检查错误
1. 操作数类型不匹配
2. 赋值语句左右类型不兼容
3. 函数调用参数类型或数量不匹配
4. 控制表达式类型错误
5. 返回类型不匹配

### 声明与作用域错误
6. 未定义的标识符
7. 标识符重定义
8. break或continue语句不在循环体内

### 数组与结构体错误
9. 数组下标不是整数类型
10. 数组访问时维数错误
11. 对非结构体变量使用成员访问运算符
12. 结构体成员不存在
13. 结构体定义中存在循环依赖

### 其他错误
14. 变量重复初始化
15. 函数调用时使用了不可调用的标识符
16. 除数为零
17. 无效的左值
18. main函数缺少或签名错误
19. switch表达式类型与case常量类型不匹配
20. 结构体类型未定义

## 中间代码格式

编译器生成三地址代码 (TAC)，使用四元式表示：
```
(操作码, 操作数1, 操作数2, 结果)
```

### 支持的指令类型
- **算术运算**: ADD, SUB, MUL, DIV, MOD
- **比较运算**: EQ, NE, LT, GT, LE, GE
- **逻辑运算**: AND, OR, NOT
- **赋值**: ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN
- **自增自减**: INCREMENT, DECREMENT
- **跳转**: GOTO, IF_TRUE, IF_FALSE, IF_ZERO, IF_NONZERO
- **标签**: LABEL
- **函数调用**: CALL, RETURN
- **数组操作**: ARRAY_ACCESS, ARRAY_ASSIGN
- **结构体操作**: MEMBER_ACCESS, MEMBER_ASSIGN

## LLVM IR 目标代码

编译器生成 LLVM IR 代码，支持：
- 基本数据类型映射 (int → i32, float → float, char → i8)
- 函数定义和调用
- 变量分配和访问
- 控制流语句
- 数组和结构体操作

### 目标代码限制
- 主要支持整型 (`int`) 的目标代码生成
- 结构体和数组支持有限
- 优化级别为 -O2

## 测试

项目包含完整的测试套件：
- 单元测试 (JUnit 5)
- 集成测试
- 错误处理测试
- 性能测试

运行测试：
```bash
mvn test
```

## 开发计划

- [x] 项目结构初始化
- [x] ANTLR 语法文件定义
- [x] 词法分析器实现
- [x] 语法分析器和 AST 构建
- [x] 符号表管理和语义分析
- [x] 中间代码生成
- [x] LLVM IR 目标代码生成
- [x] 测试用例和文档

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 作者

编译器设计与实现课程项目

## 联系方式

如有问题或建议，请通过以下方式联系：
- 邮箱: [your-email@example.com]
- 项目地址: [GitHub Repository URL]