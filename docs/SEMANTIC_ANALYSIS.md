# 🔍 语义分析模块详解

## 模块概述

语义分析模块调责类型检查、作用域管理和语义错误检测。它遍历 AST，构建符号表，并进行 38 种语义错误检查。

**目录位置**: `src/main/java/com/wei/compiler/semantic/`

**文件数量**: 14 个 Java 文件

---

## 核心类详解

### 1. SemanticAnalyzer

**文件**: `SemanticAnalyzer.java`

**作用**: 语义分析器主类，实现完整的语义分析流程。

**核心职责**:
1. 遍历 AST 构建符号表
2. 进行类型检查
3. 检测语义错误
4. 验证作用域规则

**关键方法**:

#### `analyze(ASTNode ast)`
语义分析入口方法：
```java
public void analyze(ASTNode ast) {
    // 遍历 AST 进行语义分析
    ast.accept(this);
    
    // 检查是否有 main 函数
    checkMainFunction();
    
    // 打印结果
    if (debugMode) {
        symbolTableManager.displaySymbolTable();
    }
}
```

**设计要点**:
- 实现 `ASTVisitor<Void>` 接口，使用 Visitor 模式遍历 AST
- 维护上下文信息（当前函数返回类型、是否在循环中等）
- 收集所有错误，不中断分析过程

#### `visitProgram(ProgramNode node)`
分析程序根节点：
```java
@Override
public Void visitProgram(ProgramNode node) {
    // 进入全局作用域
    symbolTableManager.enterScope();
    
    // 分析所有全局声明
    for (ASTNode decl : node.getDeclarations()) {
        decl.accept(this);
    }
    
    // 退出全局作用域
    symbolTableManager.exitScope();
    return null;
}
```

#### `visitFunctionDeclaration(FunctionDeclarationNode node)`
分析函数声明：
```java
@Override
public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
    // 1. 检查函数重定义
    // 2. 创建函数符号表条目
    // 3. 进入函数作用域
    // 4. 分析参数
    // 5. 分析函数体
    // 6. 检查返回类型
    // 7. 退出函数作用域
}
```

**关键检查**:
- 函数重定义检查
- 参数类型检查
- 返回类型检查
- 函数体内变量作用域

---

### 2. SymbolTableManager

**文件**: `SymbolTableManager.java`

**作用**: 符号表管理器，使用栈式结构管理作用域。

**核心数据结构**:
```java
private Stack<Map<String, SymbolEntry>> symbolTableStack;
private int currentScopeLevel;
```

**关键方法**:

#### `enterScope()`
进入新作用域：
```java
public void enterScope() {
    Map<String, SymbolEntry> newScope = new HashMap<>();
    symbolTableStack.push(newScope);
    currentScopeLevel++;
}
```

#### `exitScope()`
退出当前作用域：
```java
public void exitScope() {
    if (symbolTableStack.size() > 1) { // 保留全局作用域
        symbolTableStack.pop();
        currentScopeLevel--;
    }
}
```

#### `insertSymbol(SymbolEntry entry)`
插入符号到当前作用域：
```java
public boolean insertSymbol(SymbolEntry entry) {
    Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
    String name = entry.getName();
    
    // 检查重定义错误
    if (currentScope.containsKey(name)) {
        addError(new SemanticError(...));
        return false;
    }
    
    currentScope.put(name, entry);
    return true;
}
```

#### `lookupSymbol(String name)`
查找符号（从当前作用域向上查找）：
```java
public SymbolEntry lookupSymbol(String name) {
    // 从栈顶开始查找
    for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
        Map<String, SymbolEntry> scope = symbolTableStack.get(i);
        if (scope.containsKey(name)) {
            return scope.get(name);
        }
    }
    return null; // 未找到
}
```

**设计要点**:
- 使用栈实现嵌套作用域
- 查找时从内层作用域向外层查找
- 支持全局作用域和局部作用域

---

### 3. SymbolEntry

**文件**: `SymbolEntry.java`

**作用**: 符号表条目，存储标识符的所有信息。

**核心字段**:
```java
private String name;              // 符号名称
private SymbolType symbolType;    // 符号类型（变量、函数、结构体等）
private DataType dataType;        // 数据类型
private int scopeLevel;           // 作用域级别
private SymbolKind kind;          // 符号种类
private ArrayInfo arrayInfo;      // 数组信息（如果是数组）
private StructInfo structInfo;   // 结构体信息（如果是结构体）
private FunctionInfo functionInfo;// 函数信息（如果是函数）
private RuntimeInfo runtimeInfo; // 运行时信息
```

**使用场景**:
- 变量：存储类型、作用域、是否初始化
- 函数：存储参数列表、返回类型
- 结构体：存储字段信息
- 数组：存储维度信息

---

### 4. ExpressionTypeAnalyzer

**文件**: `ExpressionTypeAnalyzer.java`

**作用**: 表达式类型分析器，分析表达式的类型。

**核心方法**:

#### `analyzeType(ExpressionNode expr)`
分析表达式类型：
```java
public DataType analyzeType(ExpressionNode expr) {
    if (expr instanceof IntLiteralNode) {
        return DataType.INT;
    } else if (expr instanceof FloatLiteralNode) {
        return DataType.FLOAT;
    } else if (expr instanceof IdentifierNode) {
        // 从符号表查找类型
        SymbolEntry entry = symbolTableManager.lookupSymbol(...);
        return entry.getDataType();
    } else if (expr instanceof BinaryExpressionNode) {
        // 分析二元表达式的类型
        DataType leftType = analyzeType(left);
        DataType rightType = analyzeType(right);
        return inferBinaryType(leftType, rightType);
    }
    // ... 更多情况
}
```

**类型推断规则**:
- 整数运算：`int + int → int`
- 浮点运算：`float + float → float`, `int + float → float`
- 比较运算：`int == int → int` (布尔值用整数表示)
- 逻辑运算：`int && int → int`

**指针运算规则** (新增):
- 指针加整数：`ptr + int → ptr`, `int + ptr → ptr`（指向偏移后的内存地址）
- 指针减整数：`ptr - int → ptr`（指向向后偏移的地址）
- 指针差值：`ptr - ptr → int`（两指针间的字节距离）
- 示例：
  ```java
  int arr[5];
  int* p1 = arr;        // p1 指向 arr[0]
  int* p2 = p1 + 1;     // p2 指向 arr[1]，地址偏移 sizeof(int) 字节
  int* p3 = p1 + 2;     // p3 指向 arr[2]，地址偏移 2*sizeof(int) 字节
  int diff = p3 - p1;   // diff = 2（元素个数）
  int* p4 = p3 - 1;     // p4 指向 arr[1]
  ```

---

### 5. ArrayInfo

**文件**: `ArrayInfo.java`

**作用**: 存储数组信息。

**核心字段**:
```java
private DataType elementType;    // 元素类型
private int[] dimensions;        // 维度数组
```

**示例**:
- `int[10]`: `elementType=INT`, `dimensions=[10]`
- `int[3][4]`: `elementType=INT`, `dimensions=[3, 4]`
- `int arr[]` (参数): `elementType=INT`, `dimensions=[-1]` (未知大小)

---

### 6. StructInfo

**文件**: `StructInfo.java`

**作用**: 存储结构体信息。

**核心字段**:
```java
private String structName;                    // 结构体名称
private Map<String, SymbolEntry> fields;      // 字段映射（字段名 -> 符号条目）
```

**功能**:
- 存储结构体字段
- 支持字段查找
- 支持字段类型查询

---

### 7. FunctionInfo

**文件**: `FunctionInfo.java`

**作用**: 存储函数信息。

**核心字段**:
```java
private DataType returnType;              // 返回类型
private List<SymbolEntry> parameters;      // 参数列表
```

**功能**:
- 存储函数签名
- 支持参数类型检查
- 支持重载检查（未来扩展）

---

### 8. SemanticError

**文件**: `SemanticError.java`

**作用**: 语义错误表示。

**核心字段**:
```java
private SemanticErrorType errorType;  // 错误类型
private String message;               // 错误消息
private String identifier;            // 相关标识符
private int scopeLevel;               // 作用域级别
```

---

### 9. SemanticErrorType

**文件**: `SemanticErrorType.java`

**作用**: 语义错误类型枚举。

**48 种错误类型**（核心38种 + 扩展11种）:

**类型检查错误 (5种)**:
- `TYPE_MISMATCH`: 类型不匹配
- `INCOMPATIBLE_ASSIGNMENT`: 赋值类型不兼容
- `FUNCTION_PARAMETER_MISMATCH`: 函数参数类型或数量不匹配
- `CONTROL_EXPRESSION_TYPE_ERROR`: 控制表达式类型错误
- `RETURN_TYPE_MISMATCH`: 返回类型不匹配

**声明与作用域错误 (3种)**:
- `UNDEFINED_IDENTIFIER`: 未定义的标识符
- `REDEFINITION`: 重定义错误
- `BREAK_CONTINUE_OUTSIDE_LOOP`: break或continue语句不在循环体内

**数组与结构体错误 (5种)**:
- `ARRAY_INDEX_TYPE_ERROR`: 数组下标不是整数类型
- `ARRAY_DIMENSION_ERROR`: 数组访问时维数错误
- `NON_STRUCT_MEMBER_ACCESS`: 对非结构体变量使用成员访问运算符
- `STRUCT_MEMBER_NOT_FOUND`: 结构体成员不存在
- `STRUCT_CIRCULAR_DEPENDENCY`: 结构体定义中存在循环依赖

**其他错误 (14种)**:
- `DUPLICATE_INITIALIZATION`: 变量重复初始化
- `NON_CALLABLE_IDENTIFIER`: 函数调用时使用了不可调用的标识符
- `DIVISION_BY_ZERO`: 除数为零
- `INVALID_LVALUE`: 无效的左值
- `MAIN_FUNCTION_MISSING`: main函数缺少或签名错误
- `SWITCH_CASE_TYPE_MISMATCH`: switch表达式类型与case常量类型不匹配
- `STRUCT_TYPE_UNDEFINED`: 结构体类型未定义
- `ARRAY_SIZE_NEGATIVE`: 数组大小为负数
- `FUNCTION_RECURSION_DEPTH`: 函数递归深度过大
- `VARIABLE_NOT_INITIALIZED`: 变量未初始化
- `CONSTANT_MODIFICATION`: 修改常量
- `INVALID_OPERATOR_USAGE`: 运算符使用错误
- `EXPECTED_CONSTANT_EXPRESSION`: 期望常量表达式
- `REPEATED_CASE_LABEL`: 重复的case标签
- `REPEATED_DEFAULT_LABEL`: 重复的default标签
**扩展错误 (11种)**:
- `FLOAT_USED_AS_ARRAY_INDEX`: 浮点数不能作为数组下标
- `IMPLICIT_FLOAT_TO_INT_CONVERSION`: 隐式浮点转整数可能丧失精度
- `UNREACHABLE_CODE`: 不可达代码
- `FUNCTION_CALLED_BEFORE_DECLARATION`: 函数在声明前被调用
- `SHADOWED_VARIABLE`: 局部变量遮蔽了外层变量
- `UNUSED_VARIABLE`: 变量已声明但未使用
- `STRUCT_SIZE_ZERO`: 结构体大小为零
- `VOID_PARAMETER_TYPE`: 参数类型不能为void
- `MULTIPLE_DEFAULTS_IN_SWITCH`: switch中有多个default分支
- `VOID_FUNCTION_RETURN_VALUE`: void函数不应该返回值
- `POTENTIAL_NULL_POINTER`: 可能的空指针访问

---

### 10. SymbolType

**文件**: `SymbolType.java`

**作用**: 符号类型枚举。

**类型**:
- `VARIABLE`: 变量
- `FUNCTION`: 函数
- `STRUCT_DEFINITION`: 结构体定义
- `PARAMETER`: 参数
- `FIELD`: 结构体字段

---

### 11. SymbolKind

**文件**: `SymbolKind.java`

**作用**: 符号种类枚举。

**种类**:
- `LOCAL`: 局部变量
- `GLOBAL`: 全局变量
- `PARAMETER`: 参数
- `FUNCTION`: 函数
- `STRUCT`: 结构体



---

## 语义检查流程

详见 [编译阶段文档](COMPILATION_STAGES.md)中的「阶段三」节点。

---

## 错误处理策略

1. **错误收集**: 收集所有错误，不中断分析
2. **错误报告**: 提供详细的错误信息（位置、类型、消息）
3. **错误分类**: 区分严重错误和警告
4. **继续编译**: 某些错误不阻止代码生成（用于测试）

---

## 设计模式

详见 [架构设计](ARCHITECTURE.md)。

---

<div align="center">

**🔍 语义分析模块详解**

Made with ❤️ by wei-C Compiler Team

</div>

