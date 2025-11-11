# 🔍 语义分析模块详解

## 模块概述

语义分析模块负责类型检查、作用域管理和语义错误检测。它遍历 AST，构建符号表，并进行 20+ 种语义错误检查。

**目录位置**: `src/main/java/com/gemini/compiler/semantic/`

**文件数量**: 11 个 Java 文件

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

**错误类型**:
- `UNDEFINED_IDENTIFIER`: 未定义的标识符
- `REDEFINITION`: 重定义
- `TYPE_MISMATCH`: 类型不匹配
- `INVALID_ASSIGNMENT`: 无效赋值
- `FUNCTION_PARAMETER_MISMATCH`: 函数参数不匹配
- `INVALID_RETURN_TYPE`: 返回类型错误
- `ARRAY_INDEX_NOT_INTEGER`: 数组下标非整数
- `INVALID_MEMBER_ACCESS`: 无效成员访问
- `BREAK_OUTSIDE_LOOP`: break 不在循环内
- `CONTINUE_OUTSIDE_LOOP`: continue 不在循环内
- 更多...

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

### 1. 符号表构建阶段

遍历 AST，构建符号表：
1. 遇到声明 → 插入符号表
2. 遇到作用域开始 → 进入新作用域
3. 遇到作用域结束 → 退出作用域

### 2. 类型检查阶段

检查类型兼容性：
1. 表达式类型推断
2. 赋值类型检查
3. 函数调用参数类型检查
4. 返回类型检查

### 3. 作用域检查阶段

检查作用域规则：
1. 标识符是否定义
2. 标识符是否重定义
3. break/continue 是否在循环内

### 4. 其他检查

- 数组下标检查
- 结构体成员访问检查
- 函数调用检查
- main 函数检查

---

## 错误处理策略

1. **错误收集**: 收集所有错误，不中断分析
2. **错误报告**: 提供详细的错误信息（位置、类型、消息）
3. **错误分类**: 区分严重错误和警告
4. **继续编译**: 某些错误不阻止代码生成（用于测试）

---

## 设计模式

### Visitor 模式
- `SemanticAnalyzer` 实现 `ASTVisitor<Void>`
- 分离 AST 遍历和语义分析逻辑

### 栈式作用域管理
- 使用栈实现嵌套作用域
- 支持作用域的进入和退出

---

<div align="center">

**🔍 语义分析模块详解**

Made with ❤️ by Gemini-C Compiler Team

</div>

