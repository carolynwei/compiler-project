# ⚡ 优化器模块详解

## 模块概述

优化器模块对中间代码进行优化，提高生成代码的效率。

**目录位置**: `src/main/java/com/gemini/compiler/optimizer/`

**文件数量**: 9 个 Java 文件

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

### 4. DeadCodeEliminationPass

**文件**: `DeadCodeEliminationPass.java`

**作用**: 死代码消除。

**消除规则**:
- 未使用的赋值
- 不可达的代码
- 无用的临时变量

---

### 5. CommonSubexpressionEliminationPass

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

### 6. LoopInvariantHoistPass

**文件**: `LoopInvariantHoistPass.java`

**作用**: 循环不变式外提。

**优化规则**:
- 识别循环中不变的计算
- 将计算移到循环外
- 减少循环内计算量

---

### 7. Mem2RegPass

**文件**: `Mem2RegPass.java`

**作用**: 内存到寄存器转换。

**优化规则**:
- 识别可以提升为寄存器的变量
- 减少内存访问
- 提高执行效率

---

### 8. ControlFlowGraph

**文件**: `ControlFlowGraph.java`

**作用**: 控制流图构建和分析。

**功能**:
- 构建基本块之间的控制流
- 支持数据流分析
- 支持优化分析

---

### 9. OptimizerUtils

**文件**: `OptimizerUtils.java`

**作用**: 优化器工具类。

**功能**:
- 提供优化相关的工具方法
- 辅助优化 Pass 实现

---

## 优化 Pass 执行顺序

优化 Pass 的执行顺序很重要：

1. **常量传播**: 先进行常量传播，为后续优化提供基础
2. **循环不变式外提**: 在循环优化前进行
3. **死代码消除**: 消除无用代码
4. **内存到寄存器**: 提升变量到寄存器
5. **公共子表达式消除**: 最后进行，利用前面的优化结果

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

Made with ❤️ by Gemini-C Compiler Team

</div>

