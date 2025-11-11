# 🔄 中间代码生成模块详解

## 模块概述

中间代码生成模块将 AST 转换为三地址代码 (TAC)，为代码优化和目标代码生成提供中间表示。

**目录位置**: `src/main/java/com/gemini/compiler/ir/`

**文件数量**: 5 个 Java 文件

---

## 核心类详解

### 1. IRGenerator

**文件**: `IRGenerator.java`

**作用**: 中间代码生成器，将 AST 转换为 TAC。

**核心职责**:
1. 遍历 AST 生成 TAC 指令
2. 管理临时变量
3. 生成标签和跳转指令
4. 处理控制流结构

**关键方法**:

#### `generate(ASTNode ast)`
IR 生成入口方法：
```java
public IRProgram generate(ASTNode ast) {
    irProgram = new IRProgram();
    ast.accept(this);
    return irProgram;
}
```

#### `visitExpression(ExpressionNode node)`
生成表达式对应的 TAC：
- 算术表达式 → `ADD`, `SUB`, `MUL`, `DIV`, `MOD`
- 比较表达式 → `EQ`, `NE`, `LT`, `GT`, `LE`, `GE`
- 逻辑表达式 → `AND`, `OR`, `NOT`
- 赋值表达式 → `ASSIGN`, `PLUS_ASSIGN`, 等

**示例**:
```c
// 源代码: a + b * c
// 生成的 TAC:
t1 = b * c
t2 = a + t1
```

#### `visitIfStatement(IfStatementNode node)`
生成 if 语句的 TAC：
```java
@Override
public Void visitIfStatement(IfStatementNode node) {
    String conditionTemp = generateExpressionCode(node.getCondition());
    String trueLabel = irProgram.generateLabel("if_true");
    String falseLabel = irProgram.generateLabel("if_false");
    String endLabel = irProgram.generateLabel("if_end");
    
    // 条件跳转
    irProgram.addInstruction(new TACInstruction(TACOpcode.IF_TRUE, 
        conditionTemp, null, trueLabel));
    irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, 
        null, null, falseLabel));
    
    // true 分支
    irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, 
        null, null, trueLabel));
    node.getThenStatement().accept(this);
    irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, 
        null, null, endLabel));
    
    // false 分支
    irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, 
        null, null, falseLabel));
    if (node.getElseStatement() != null) {
        node.getElseStatement().accept(this);
    }
    
    // 结束标签
    irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, 
        null, null, endLabel));
    return null;
}
```

**设计要点**:
- 使用临时变量存储表达式结果
- 使用标签实现控制流
- 支持嵌套的控制结构

---

### 2. TACInstruction

**文件**: `TACInstruction.java`

**作用**: 三地址代码指令，使用四元式表示。

**结构**:
```java
public class TACInstruction {
    private TACOpcode opcode;    // 操作码
    private String arg1;          // 操作数1
    private String arg2;          // 操作数2
    private String result;       // 结果
    private DataType resultType;  // 结果类型
    private String metadata;     // 元数据（如结构体名称）
}
```

**四元式格式**: `(opcode, arg1, arg2, result)`

**示例**:
- `(ADD, a, b, t1)`: `t1 = a + b`
- `(ASSIGN, 10, _, x)`: `x = 10`
- `(IF_TRUE, condition, _, label)`: `if (condition) goto label`

---

### 3. TACOpcode

**文件**: `TACOpcode.java`

**作用**: TAC 操作码枚举。

**操作码分类**:

#### 算术运算
- `ADD`, `SUB`, `MUL`, `DIV`, `MOD`

#### 比较运算
- `EQ`, `NE`, `LT`, `GT`, `LE`, `GE`

#### 逻辑运算
- `AND`, `OR`, `NOT`

#### 赋值
- `ASSIGN`, `PLUS_ASSIGN`, `MINUS_ASSIGN`, `MUL_ASSIGN`, `DIV_ASSIGN`, `MOD_ASSIGN`

#### 自增自减
- `INCREMENT`, `DECREMENT`

#### 跳转
- `GOTO`: 无条件跳转
- `IF_TRUE`: 条件为真时跳转
- `IF_FALSE`: 条件为假时跳转
- `IF_ZERO`: 条件为零时跳转
- `IF_NONZERO`: 条件非零时跳转
- `SWITCH`: switch 语句分发
- `SELECT`: 三元表达式选择

#### 函数
- `CALL`: 函数调用
- `RETURN`: 返回
- `PARAM`: 参数声明
- `ARG`: 参数传递

#### 数组和结构体
- `ARRAY_ACCESS`: 数组访问
- `ARRAY_ASSIGN`: 数组赋值
- `MEMBER_ACCESS`: 成员访问
- `MEMBER_ASSIGN`: 成员赋值
- `STRUCT_COPY`: 结构体复制

#### 其他
- `LABEL`: 标签
- `ALLOC`: 内存分配
- `LOAD`: 加载值
- `STORE`: 存储值
- `CAST`: 类型转换

---

### 4. IRProgram

**文件**: `IRProgram.java`

**作用**: 中间代码程序容器。

**核心字段**:
```java
private List<TACInstruction> instructions;  // TAC 指令列表
private Map<String, Integer> labels;          // 标签映射
private List<BasicBlock> basicBlocks;        // 基本块列表
private int tempVarCounter;                  // 临时变量计数器
private int labelCounter;                    // 标签计数器
```

**关键方法**:

#### `generateTempVar()`
生成临时变量名：
```java
public String generateTempVar() {
    return "t" + (++tempVarCounter);
}
```

#### `generateLabel()`
生成标签名：
```java
public String generateLabel() {
    return "L" + (++labelCounter);
}
```

---

### 5. BasicBlock

**文件**: `BasicBlock.java`

**作用**: 基本块，用于控制流图 (CFG) 构建。

**基本块定义**:
- 单入口：只有第一个指令可以被跳转到
- 单出口：只有最后一个指令可以跳转
- 最大序列：包含尽可能多的连续指令

**核心字段**:
```java
private String label;                        // 基本块标签
private List<TACInstruction> instructions;    // 指令列表
private List<String> predecessors;            // 前驱基本块
private List<String> successors;              // 后继基本块
```

**CFG 功能**:
- 支持前驱和后继关系
- 用于数据流分析和优化

---

## TAC 生成规则

### 表达式生成

#### 二元表达式
```c
// 源代码: a + b
// TAC:
t1 = a + b
```

#### 复杂表达式
```c
// 源代码: a + b * c
// TAC:
t1 = b * c
t2 = a + t1
```

### 控制流生成

#### if 语句
```c
// 源代码:
if (condition) {
    thenStmt;
} else {
    elseStmt;
}

// TAC:
IF_TRUE condition L1
GOTO L2
L1:
thenStmt
GOTO L3
L2:
elseStmt
L3:
```

#### while 循环
```c
// 源代码:
while (condition) {
    body;
}

// TAC:
L1:
IF_FALSE condition L2
body
GOTO L1
L2:
```

#### for 循环
```c
// 源代码:
for (init; condition; update) {
    body;
}

// TAC:
init
L1:
IF_FALSE condition L2
body
update
GOTO L1
L2:
```

---

## 临时变量管理

**命名规则**: `t1`, `t2`, `t3`, ...

**使用场景**:
- 存储表达式中间结果
- 存储函数调用返回值
- 存储类型转换结果

**生命周期**:
- 在表达式计算时创建
- 在表达式使用后可以重用

---

## 标签管理

**命名规则**:
- 普通标签: `L1`, `L2`, `L3`, ...
- 函数标签: `func_main`, `func_add`, ...
- 控制流标签: `if_true_1`, `while_loop_1`, ...

**使用场景**:
- 控制流跳转目标
- 函数入口
- 循环头和尾

---

## 设计要点

1. **线性表示**: TAC 使用线性列表，便于优化和代码生成
2. **临时变量**: 所有表达式结果使用临时变量存储
3. **标签系统**: 使用标签实现控制流
4. **类型信息**: 保留类型信息，便于后续阶段使用
5. **元数据支持**: 支持结构体名称等元数据

---

<div align="center">

**🔄 中间代码生成模块详解**

Made with ❤️ by Gemini-C Compiler Team

</div>

