# ⚡ 优化器模块详解

## 模块概述

优化器模块对中间代码进行优化，提高生成代码的效率。

**目录位置**: `src/main/java/com/wei/compiler/optimizer/`

**文件数量**: 11 个 Java 文件（新增CopyPropagationPass和RedundantAssignmentPass）

---

## 核心类详解

### 1. IROptimizer

**文件**: `IROptimizer.java`

**作用**: 优化器调度器，按顺序执行各个优化 Pass。

**优化流水线**:
```java
private List<OptimizerPass> buildPassPipeline() {
    List<OptimizerPass> passes = new ArrayList<>();
    passes.add(new ConstantPropagationPass(debugMode));      // 常量传播
    passes.add(new CopyPropagationPass(debugMode));          // 副本传播（新增）
    passes.add(new RedundantAssignmentPass(debugMode));      // 冗余赋值消除（新增）
    passes.add(new LoopInvariantHoistPass(debugMode));       // 循环不变式外提
    passes.add(new DeadCodeEliminationPass(debugMode));      // 死代码消除
    passes.add(new Mem2RegPass());                           // 内存到寄存器
    passes.add(new CommonSubexpressionEliminationPass(debugMode)); // 公共子表达式消除
    return passes;
}
```

**执行流程**:
```java
public IRProgram optimize(IRProgram irProgram) {
    List<TACInstruction> instructions = new ArrayList<>(irProgram.getInstructions());
    for (OptimizerPass pass : buildPassPipeline()) {
        instructions = pass.run(instructions);
    }
    return optimizedProgram;
}
```

---

### 2. OptimizerPass

**文件**: `OptimizerPass.java`

**作用**: 优化 Pass 基类接口。

**接口定义**:
```java
public interface OptimizerPass {
    List<TACInstruction> run(List<TACInstruction> instructions);
}
```

**设计模式**: Strategy 模式

---

### 3. ConstantPropagationPass

**文件**: `ConstantPropagationPass.java`

**作用**: 常量传播优化。

**优化规则**:
- 将常量值直接替换到使用处
- 消除常量表达式计算

**示例**:
```java
// 优化前:
t1 = 10
t2 = 5
t3 = t1 + t2

// 优化后:
t3 = 15
```

---

### 4. CopyPropagationPass（新增）

**文件**: `CopyPropagationPass.java`

**作用**: 副本传播优化。追踪形如 `x = y` 的赋值，并将所有 x 的使用替换为 y。

**优化原理**:
1. **追踪副本赋值**: 识别形如 `result = temp` 的赋值
2. **传播使用**: 将后续所有对 `result` 的使用替换为 `temp`
3. **递归解析**: 支持副本链传播（如 a=b, b=c, 则 a 最终映射到 c）
4. **流敏感性**: 在LABEL处清除映射以处理分支和循环

**优化效果**: 减少临时变量，为死代码消除提供更多优化机会

**实现示例**:
```
t1 = a
result = t1   // 记录: result -> t1
use(result)   // 替换为: use(t1)
```

---

### 5. RedundantAssignmentPass（新增）

**文件**: `RedundantAssignmentPass.java`

**作用**: 消除冗余的连续赋值。当变量被立即覆盖时，移除前面的赋值。

**消除规则**:
- 同一变量的多个连续赋值
- 仅保留最后一个赋值（其他为冗余）
- 在循环和分支处停止以保证正确性

**优化原理**:
1. **前向扫描**: 对每个赋值，查找该变量的下一个定义
2. **冗余检测**: 如果下一个定义在同一基本块中，则当前赋值冗余
3. **移除冗余**: 跳过冗余赋值，保持控制流完整性

**优化效果**: 直接减少无用赋值，通常与其他pass配合产生效果

**实现示例**:
```
result = c     // 冗余（下一行会立即覆盖）
result = c     // 冗余（下一行会立即覆盖）
result = c     // 冗余（下一行会立即覆盖）
result = b     // 保留
  ↓ 优化后 ↓
result = b     // 直接赋值
```

---

### 6. DeadCodeEliminationPass

**文件**: `DeadCodeEliminationPass.java`

**作用**: 死代码消除。

**消除规则**:
- 未使用的赋值
- 不可达的代码
- 无用的临时变量

---

### 7. CommonSubexpressionEliminationPass

**文件**: `CommonSubexpressionEliminationPass.java`

**作用**: 公共子表达式消除。

**优化规则**:
- 识别重复计算的表达式
- 使用临时变量存储结果
- 替换后续相同表达式

**示例**:
```java
// 优化前:
t1 = a + b
t2 = a + b

// 优化后:
t1 = a + b
t2 = t1
```

---

### 7. CommonSubexpressionEliminationPass

**文件**: `CommonSubexpressionEliminationPass.java`

**作用**: 公共子表达式消除。

**优化规则**:
- 识别重复计算的表达式
- 使用临时变量存储结果
- 替换后续相同表达式

**示例**:
```java
// 优化前:
t1 = a + b
t2 = a + b

// 优化后:
t1 = a + b
t2 = t1
```

---

### 8. LoopInvariantHoistPass

**文件**: `LoopInvariantHoistPass.java`

**作用**: 循环不变式外提。

**优化规则**:
- 识别循环中不变的计算
- 将计算移到循环外
- 减少循环内计算量

---

### 9. Mem2RegPass

**文件**: `Mem2RegPass.java`

**作用**: 内存到寄存器转换。

**优化规则**:
- 识别可以提升为寄存器的变量
- 减少内存访问
- 提高执行效率

---

### 10. ControlFlowGraph

**文件**: `ControlFlowGraph.java`

**作用**: 控制流图构建和分析。

**功能**:
- 构建基本块之间的控制流
- 支持数据流分析
- 支持优化分析

---

### 11. OptimizerUtils

**文件**: `OptimizerUtils.java`

**作用**: 优化器工具类。

**主要方法**:
- `cloneInstruction()`: 克隆TAC指令
- `isNumericLiteral()`: 判断是否为数字常量
- `isComplexExpression()`: 判断是否为复杂表达式（新增）

**isComplexExpression() 方法**:
```java
public static boolean isComplexExpression(String value) {
    if (value == null) return false;
    // 检查是否包含特殊字符（&、[、(等）
    return value.contains("&") || value.contains("[") || 
           value.contains("(") || value.contains("[");
}
```

用于区分简单值（常数或变量名）和复杂表达式（指针、数组访问、函数调用等），CopyPropagationPass 仅在处理简单赋值时才进行传播。

---

## 优化 Pass 执行顺序

优化 Pass 的执行顺序很重要：

1. **常量传播** (ConstantPropagationPass): 先进行常量传播，为后续优化提供基础
2. **副本传播** (CopyPropagationPass): 追踪副本赋值，减少临时变量
3. **冗余赋值消除** (RedundantAssignmentPass): 消除连续的冗余赋值
4. **循环不变式外提** (LoopInvariantHoistPass): 在循环优化前进行
5. **死代码消除** (DeadCodeEliminationPass): 消除无用代码和未使用变量
6. **内存到寄存器** (Mem2RegPass): 提升变量到寄存器
7. **公共子表达式消除** (CommonSubexpressionEliminationPass): 最后进行，利用前面的优化结果

---

## 优化效果

### 优化前
```llvm
%1 = alloca i32
%2 = alloca i32
store i32 10, i32* %1
store i32 5, i32* %2
%3 = load i32, i32* %1
%4 = load i32, i32* %2
%5 = add i32 %3, %4
```

### 优化后
```llvm
%5 = add i32 10, 5  ; 常量折叠
```

---

## 设计要点

1. **模块化设计**: 每个优化 Pass 独立实现
2. **可扩展性**: 易于添加新的优化 Pass
3. **可配置性**: 支持启用/禁用优化
4. **调试支持**: 提供调试模式输出

---

<div align="center">

**⚡ 优化器模块详解**

Made with ❤️ by wei-C Compiler Team

</div>

