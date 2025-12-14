# 🎯 代码生成模块详解

## 模块概述

代码生成模块将三地址代码 (TAC) 转换为 LLVM IR，生成可执行的目标代码。

**目录位置**: `src/main/java/com/wei/compiler/codegen/`

**文件数量**: 1 个 Java 文件（`CodeGenerator.java`，约 3200 行）

---

## 核心类：CodeGenerator

**文件**: `CodeGenerator.java`

**作用**: LLVM IR 代码生成器，将 TAC 转换为 LLVM IR。

**核心职责**:
1. 生成 LLVM IR 头部和声明
2. 生成结构体类型定义
3. 生成函数代码
4. 管理变量分配和内存访问
5. 处理控制流转换

---

## 核心数据结构

### 变量管理

```java
private Map<String, String> variableMap;          // 变量名 -> LLVM 寄存器/指针
private Map<String, DataType> variableTypeMap;    // 变量名 -> 数据类型
private Map<String, int[]> arrayDimensionsMap;    // 变量名 -> 数组维度
private Map<String, String> loadedValueCache;     // 已加载值的缓存
private Set<String> pointerTemps;                 // 指针临时变量集合
private Set<String> allocatedTemps;               // 已分配临时变量集合
```

### 内存分配管理

```java
private Map<String, AllocaInfo> pendingAllocas;   // 延迟 alloca 集合
private boolean inEntryBlock;                     // 当前是否在 entry 块
```

**设计要点**:
- 所有变量在 entry 块统一分配（LLVM 要求）
- 使用 `pendingAllocas` 收集需要分配的变量
- 在 entry 块开头统一生成所有 `alloca` 指令

---

## 关键方法详解

### 1. `generate(IRProgram irProgram, SymbolTableManager symbolTable)`

**作用**: 代码生成入口方法。

**流程**:
1. 生成 LLVM IR 头部
2. 生成全局声明（结构体、字符串常量）
3. 生成函数代码
4. 返回生成的 LLVM IR 字符串

---

### 2. `collectAllocas(List<TACInstruction> body)`

**作用**: 收集所有需要分配内存的变量。

**策略**:
- 遍历所有 TAC 指令
- 收集 `arg1`, `arg2`, `result` 中的变量名
- **跳过临时变量**（t1, t2 等），它们使用寄存器
- 将变量添加到 `pendingAllocas`

**关键代码**:
```java
// 跳过临时变量
if (isTemporaryName(varName)) {
    return;
}
// 添加到 pendingAllocas
pendingAllocas.put(varName, new AllocaInfo(...));
```

---

### 3. `generatePendingAllocas()`

**作用**: 在 entry 块开头生成所有 `alloca` 指令。

**实现**:
```java
private void generatePendingAllocas() {
    for (AllocaInfo info : pendingAllocas.values()) {
        String ptr = getRegister();
        llvmCode.append("  ").append(ptr).append(" = alloca ")
                .append(info.llvmType).append(", align 4\n");
        variableMap.put(info.variableName, ptr);
    }
}
```

**设计要点**:
- 所有 `alloca` 必须在 entry 块开头
- 统一生成，避免分散在代码中间

---

### 4. `getOperand(String operand)`

**作用**: 获取操作数的 LLVM IR 表示。

**处理逻辑**:
1. **字面量**: 直接返回（如 `10`, `3.14`）
2. **临时变量**: 
   - 如果是指针临时变量 → 需要 `load`
   - 如果是常量传播结果 → 直接返回值
   - 否则 → 返回寄存器
3. **普通变量**:
   - 检查缓存
   - 从内存 `load` 值
   - 缓存加载结果

**关键代码**:
```java
// 临时变量处理
if (pointerTemps.contains(operand) || allocatedTemps.contains(operand)) {
    loadedValueCache.remove(operand);  // 清缓存
    String valueReg = getRegister();
    llvmCode.append("  ").append(valueReg).append(" = load ...");
    return valueReg;
}
```

---

### 5. `ensureVariablePointer(String variableName, DataType varType)`

**作用**: 确保变量有对应的指针。

**策略**:
- 如果变量在 `pendingAllocas` 中 → 创建临时指针占位符
- 如果不在 entry 块 → 记录警告，不生成 `alloca`
- 在 entry 块 → 可以生成 `alloca`（如果必要）

**设计要点**:
- 强制所有变量在 `collectAllocas` 时收集
- 避免在非 entry 块中生成 `alloca`

---

### 6. `generateFunctionBody(List<TACInstruction> body)`

**作用**: 生成函数体代码。

**流程**:
1. 收集所有变量（`collectAllocas`）
2. 生成所有 `alloca`（`generatePendingAllocas`）
3. 遍历 TAC 指令生成 LLVM IR
4. 处理死代码消除

**死代码处理**:
```java
if (dead) {
    // 跳过不可达指令
    continue;
}
// 如果 LABEL 是 switch/for，生成 br 防止空块
if (isSwitchLabel || isForLabel) {
    llvmCode.append("  br label %").append(labelName).append("\n");
}
```

---

### 7. `generateAlloc(TACInstruction instruction)`

**作用**: 处理 `ALLOC` 指令。

**流程**:
1. 解析变量类型和元数据
2. 检查是否在 `pendingAllocas` 中
3. 如果不在 entry 块 → 添加到 `pendingAllocas`，不生成 `alloca`
4. 如果在 entry 块 → 可以生成 `alloca`（如果必要）

**设计要点**:
- `ALLOC` 指令应该通过 `collectAllocas` 收集
- 不在 entry 块中直接生成 `alloca`

---

### 8. `getLLVMType(DataType dataType)`

**作用**: 将数据类型转换为 LLVM 类型。

**类型映射**:
- `INT` → `i32`
- `FLOAT` → `float`
- `CHAR` → `i8`
- `STRING` → `i8*`
- `VOID` → `void`
- `ARRAY` → 抛出异常（需要更多信息）
- `STRUCT` → 抛出异常（需要结构体名称）

**设计要点**:
- `ARRAY` 和 `STRUCT` 需要额外信息
- 未知类型抛出异常，不默认返回 `i32`

---

### 9. `normalizeStructName(String structName)`

**作用**: 统一结构体名称格式。

**功能**:
- 去除 `%struct.` 前缀
- 去除 `struct.` 前缀
- 返回规范化名称

**设计要点**:
- 确保结构体名称一致性
- 避免前缀不一致导致的错误

---

## TAC 到 LLVM IR 转换规则

### 算术运算

```java
// TAC: (ADD, a, b, t1)
// LLVM IR:
%1 = load i32, i32* %a, align 4
%2 = load i32, i32* %b, align 4
%3 = add i32 %1, %2
```

### 赋值

```java
// TAC: (ASSIGN, 10, _, x)
// LLVM IR:
store i32 10, i32* %x, align 4
```

### 条件跳转

```java
// TAC: (IF_TRUE, condition, _, label)
// LLVM IR:
%1 = load i32, i32* %condition, align 4
%2 = icmp ne i32 %1, 0
br i1 %2, label %label, label %fallthrough
```

### 函数调用

```java
// TAC: (CALL, func, _, result)
// LLVM IR:
%1 = call i32 @func(i32 %arg1, i32 %arg2)
store i32 %1, i32* %result, align 4
```

### 指针运算（新增）

#### 指针加整数 (ptr + int)

```java
// TAC: (ADD, ptr, int_val, result)  // result 类型为指针
// LLVM IR （使用 getelementptr 进行偏移计算）:
%1 = load i32*, i32** %ptr, align 8
%2 = load i32, i32* %int_val, align 4
%3 = getelementptr i32, i32* %1, i32 %2  // 偏移指针

// 整数加指针 (int + ptr) 类似，会交换操作数顺序
```

#### 指针减整数 (ptr - int)

```java
// TAC: (SUB, ptr, int_val, result)  // result 类型为指针
// LLVM IR （也使用 getelementptr，但用缀整数）:
%1 = load i32*, i32** %ptr, align 8
%2 = load i32, i32* %int_val, align 4
%3 = sub i32 0, %2                 // 取负值（向后偏移）
%4 = getelementptr i32, i32* %1, i32 %3
```

#### 指针差值 (ptr1 - ptr2)

```java
// TAC: (SUB, ptr1, ptr2, result)  // 两个操作数都是指针，result 类型为int
// LLVM IR （使用 ptrtoint 转换指针为int64，然后执行整数减法）:
%1 = load i32*, i32** %ptr1, align 8
%2 = load i32*, i32** %ptr2, align 8
%3 = ptrtoint i32* %1 to i64        // 指针 -> 操作数地址
%4 = ptrtoint i32* %2 to i64
%5 = sub i64 %3, %4                 // 整数减法
%6 = sdiv i64 %5, 4                 // 除以元素大小 (sizeof(int))
转换回类型
```

---

## 内存管理策略

### Load-Store 模式

所有变量访问遵循 Load-Compute-Store 模式：

1. **读取**: `load` 从内存读取值
2. **计算**: 使用寄存器进行计算
3. **存储**: `store` 将结果写回内存

### 缓存机制

- `loadedValueCache`: 缓存已加载的值
- 在基本块边界清除缓存
- 在变量赋值后清除缓存

---

## 临时变量处理

### 临时变量分类

1. **寄存器临时变量**: 算术运算结果，直接使用寄存器
2. **指针临时变量**: 数组访问结果，需要 `load`
3. **分配临时变量**: 通过 `ALLOC` 分配，需要 `load`

### 处理策略

- 寄存器临时变量：直接返回寄存器名
- 指针/分配临时变量：强制清缓存后 `load`

---

## 结构体和数组处理

### 结构体

1. 生成结构体类型定义
2. 统一结构体名称格式
3. 使用 `getelementptr` 访问成员

### 数组

1. 生成嵌套数组类型
2. 使用 `getelementptr` 计算地址
3. 支持多维数组

---

## 设计要点总结

1. **统一 alloca 生成**: 所有变量在 entry 块开头分配
2. **临时变量优化**: 临时变量使用寄存器，不分配内存
3. **类型安全**: 完整的类型检查和转换
4. **缓存优化**: 使用缓存减少重复 `load`
5. **错误处理**: 未知类型抛出异常，不静默失败

---

<div align="center">

**🎯 代码生成模块详解**

Made with ❤️ by wei-C Compiler Team

</div>

