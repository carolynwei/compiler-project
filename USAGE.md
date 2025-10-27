# Gemini-C 编译器使用手册

## 目录
1. [安装和环境配置](#安装和环境配置)
2. [快速开始](#快速开始)
3. [语言语法](#语言语法)
4. [编译器选项](#编译器选项)
5. [错误处理](#错误处理)
6. [示例程序](#示例程序)
7. [故障排除](#故障排除)

## 安装和环境配置

### 系统要求
- Java 11 或更高版本
- Maven 3.6 或更高版本
- 至少 2GB 可用内存
- 1GB 可用磁盘空间

### 安装步骤

1. **安装 Java**
   ```bash
   # Ubuntu/Debian
   sudo apt-get install openjdk-11-jdk
   
   # CentOS/RHEL
   sudo yum install java-11-openjdk-devel
   
   # macOS
   brew install openjdk@11
   
   # Windows
   # 下载并安装 Oracle JDK 或 OpenJDK
   ```

2. **安装 Maven**
   ```bash
   # Ubuntu/Debian
   sudo apt-get install maven
   
   # CentOS/RHEL
   sudo yum install maven
   
   # macOS
   brew install maven
   
   # Windows
   # 下载并安装 Apache Maven
   ```

3. **验证安装**
   ```bash
   java -version
   mvn -version
   ```

## 快速开始

### 1. 编译项目
```bash
# 克隆项目
git clone [repository-url]
cd exp-design

# 编译项目
mvn clean compile
```

### 2. 运行编译器
```bash
# 基本用法
mvn exec:java -Dexec.mainClass="com.gemini.compiler.GeminiCompiler" -Dexec.args="src/test/examples/example1.gc"

# 指定输出文件
mvn exec:java -Dexec.mainClass="com.gemini.compiler.GeminiCompiler" -Dexec.args="src/test/examples/example1.gc output.ll"
```

### 3. 运行测试
```bash
mvn test
```

## 语言语法

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
// if-else
if (condition) {
    // 语句
} else {
    // 语句
}

// while循环
while (condition) {
    // 语句
}

// for循环
for (int i = 0; i < 10; i++) {
    // 语句
}

// switch语句
switch (value) {
    case 1:
        // 语句
        break;
    case 2:
        // 语句
        break;
    default:
        // 语句
}
```

### 运算符
```c
// 算术运算
a + b, a - b, a * b, a / b, a % b

// 比较运算
a == b, a != b, a < b, a > b, a <= b, a >= b

// 逻辑运算
a && b, a || b, !a

// 赋值运算
a = b, a += b, a -= b, a *= b, a /= b, a %= b

// 自增自减
a++, ++a, a--, --a
```

## 编译器选项

### 基本选项
```bash
java GeminiCompiler <输入文件> [输出文件] [选项]
```

### 调试选项
```bash
--debug-ast       # 显示抽象语法树
--debug-symtable  # 显示符号表
--debug-ir        # 显示中间代码
--debug-codegen   # 显示目标代码生成过程
```

### 优化选项
```bash
--optimize        # 启用优化
```

### 示例
```bash
# 启用所有调试选项
java GeminiCompiler input.gc output.ll --debug-ast --debug-symtable --debug-ir --debug-codegen

# 启用优化
java GeminiCompiler input.gc output.ll --optimize
```

## 错误处理

### 语法错误
编译器会报告语法错误，包括：
- 缺少分号
- 括号不匹配
- 关键字拼写错误

### 语义错误
编译器支持 20+ 种语义错误检查：

#### 类型检查错误
- 操作数类型不匹配
- 赋值语句左右类型不兼容
- 函数调用参数类型或数量不匹配
- 控制表达式类型错误
- 返回类型不匹配

#### 声明与作用域错误
- 未定义的标识符
- 标识符重定义
- break或continue语句不在循环体内

#### 数组与结构体错误
- 数组下标不是整数类型
- 数组访问时维数错误
- 对非结构体变量使用成员访问运算符
- 结构体成员不存在
- 结构体定义中存在循环依赖

#### 其他错误
- 变量重复初始化
- 函数调用时使用了不可调用的标识符
- 除数为零
- 无效的左值
- main函数缺少或签名错误
- switch表达式类型与case常量类型不匹配
- 结构体类型未定义

### 错误示例
```c
int main() {
    int a = 10;
    int b = 5;
    
    // 错误1: 未定义的变量
    int result = undefined_var;
    
    // 错误2: 类型不匹配
    float f = "hello";
    
    // 错误3: 重定义
    int a = 20;
    
    // 错误4: break不在循环中
    break;
    
    return 0;
}
```

## 示例程序

### 1. 基本语法测试
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

### 2. 函数和数组测试
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

### 3. 复杂表达式测试
```c
/* Gemini-C 示例程序 3: 复杂表达式和运算符测试 */
int main() {
    int a = 10;
    int b = 5;
    int c = 3;
    int result;
    
    // 算术运算测试
    result = a + b * c;
    result = (a + b) * c;
    result = a / b + c;
    result = a % b;
    
    // 比较运算测试
    int isEqual = (a == b);
    int isNotEqual = (a != b);
    int isLess = (a < b);
    int isGreater = (a > b);
    int isLessEqual = (a <= b);
    int isGreaterEqual = (a >= b);
    
    // 逻辑运算测试
    int logicalAnd = (a > 0) && (b > 0);
    int logicalOr = (a < 0) || (b > 0);
    int logicalNot = !(a > 0);
    
    // 自增自减测试
    a++;
    ++a;
    b--;
    --b;
    
    // 复合赋值测试
    a += b;
    a -= c;
    a *= 2;
    a /= 2;
    a %= 3;
    
    // 条件表达式测试
    result = (a > b) ? a : b;
    
    return result;
}
```

## 故障排除

### 常见问题

#### 1. 编译错误
**问题**: `mvn clean compile` 失败
**解决方案**:
- 检查 Java 版本: `java -version`
- 检查 Maven 版本: `mvn -version`
- 清理 Maven 缓存: `mvn clean`
- 重新下载依赖: `mvn dependency:resolve`

#### 2. 运行时错误
**问题**: 编译器运行时出错
**解决方案**:
- 检查输入文件是否存在
- 检查文件权限
- 查看错误日志
- 使用调试选项获取更多信息

#### 3. 内存不足
**问题**: `OutOfMemoryError`
**解决方案**:
- 增加 JVM 堆内存: `-Xmx2g`
- 使用 64 位 Java
- 关闭不必要的程序

#### 4. 语法错误
**问题**: 语法分析失败
**解决方案**:
- 检查语法是否正确
- 查看错误信息
- 参考语法文档
- 使用示例程序作为模板

#### 5. 语义错误
**问题**: 语义分析失败
**解决方案**:
- 检查变量声明
- 检查类型匹配
- 检查作用域
- 查看错误详情

### 调试技巧

#### 1. 使用调试选项
```bash
java GeminiCompiler input.gc output.ll --debug-ast --debug-symtable --debug-ir --debug-codegen
```

#### 2. 查看中间代码
```bash
java GeminiCompiler input.gc output.ll --debug-ir
```

#### 3. 查看符号表
```bash
java GeminiCompiler input.gc output.ll --debug-symtable
```

#### 4. 查看 AST
```bash
java GeminiCompiler input.gc output.ll --debug-ast
```

### 性能优化

#### 1. 启用优化
```bash
java GeminiCompiler input.gc output.ll --optimize
```

#### 2. 增加内存
```bash
java -Xmx4g GeminiCompiler input.gc output.ll
```

#### 3. 使用并行编译
```bash
mvn compile -T 4
```

### 联系支持

如果遇到问题，请：
1. 查看错误日志
2. 检查系统要求
3. 参考文档
4. 联系技术支持

---

**注意**: 本手册基于 Gemini-C 编译器 v1.0.0 编写。如有更新，请参考最新版本。
