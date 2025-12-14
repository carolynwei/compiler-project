# Wei-C 编译器符号表快照示例

## 简介

本文档通过一个复杂的函数编译过程，展示符号表在不同作用域下的状态变化，帮助理解编译器如何管理标识符和作用域。

---

## 示例代码

```c
// 全局变量
int x = 10;
float arr[5];

// 结构体定义
struct Point {
    int px;
    float py;
};

// 全局函数声明
int add(int a, int b);

// 复杂函数定义
int findMax(int arr[], int size) {
    int max = arr[0];           // 局部变量
    int i;                       // 局部变量
    
    for (i = 1; i < size; i++) {
        int temp = arr[i];       // 循环块局部变量
        if (temp > max) {
            max = temp;
            int log = i;         // 嵌套块局部变量
        }
    }
    
    return max;
}

// main 函数
int main() {
    int result;
    struct Point p;
    
    {
        // 代码块
        int block_var = 5;
        result = findMax(arr, 5);
    }
    
    return 0;
}
```

---

## 符号表快照序列

### 阶段 1：初始化全局作用域（Scope Level 0）

**时间点**：编译开始，进入全局作用域

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 1                   │
│ Current Scope Level: 0          │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（处理完全局声明后）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `x` | VARIABLE | INT | GLOBAL | 0 | 已初始化，全局地址 `$global_0` |
| 2 | `arr` | VARIABLE | ARRAY<FLOAT>[5] | GLOBAL | 0 | 已初始化，全局地址 `$global_4` |
| 3 | `Point` | STRUCT_DEFINITION | STRUCT<Point> | GLOBAL | 0 | 成员：px(INT), py(FLOAT) |
| 4 | `add` | FUNCTION | INT | GLOBAL | 0 | 参数：a(INT), b(INT)，已定义 |
| 5 | `findMax` | FUNCTION | INT | GLOBAL | 0 | 参数：arr(ARRAY<INT>), size(INT)，已定义 |
| 6 | `main` | FUNCTION | INT | GLOBAL | 0 | 参数：无，已定义 |

**符号表栈快照**：
```
symbolTableStack = [
    {
        "x": SymbolEntry[name='x', type=VARIABLE, dataType=INT, kind=GLOBAL, scope=0],
        "arr": SymbolEntry[name='arr', type=VARIABLE, dataType=ARRAY<FLOAT>[5], kind=GLOBAL, scope=0],
        "Point": SymbolEntry[name='Point', type=STRUCT_DEFINITION, dataType=STRUCT<Point>, kind=GLOBAL, scope=0],
        "add": SymbolEntry[name='add', type=FUNCTION, dataType=INT, kind=GLOBAL, scope=0],
        "findMax": SymbolEntry[name='findMax', type=FUNCTION, dataType=INT, kind=GLOBAL, scope=0],
        "main": SymbolEntry[name='main', type=FUNCTION, dataType=INT, kind=GLOBAL, scope=0]
    }
]
currentScopeLevel = 0
```

---

### 阶段 2：进入 findMax 函数（Scope Level 1）

**时间点**：开始分析 `findMax` 函数体

**操作**：
1. 插入函数 `findMax` 的参数到符号表
2. 进入新的作用域（Level 1）

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 2                   │
│ Current Scope Level: 1          │
├─────────────────────────────────┤
│ Scope[1] (Function findMax)      │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（Scope[1]）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `arr` | VARIABLE | ARRAY<INT> | PARAMETER | 1 | 参数，未初始化 |
| 2 | `size` | VARIABLE | INT | PARAMETER | 1 | 参数，未初始化 |
| 3 | `max` | VARIABLE | INT | LOCAL | 1 | 局部变量，已初始化为 `arr[0]` |
| 4 | `i` | VARIABLE | INT | LOCAL | 1 | 局部变量，未初始化 |

**符号表栈快照**：
```
symbolTableStack = [
    {  // Scope[0] - Global
        "x": SymbolEntry[...],
        "arr": SymbolEntry[...],
        "Point": SymbolEntry[...],
        "add": SymbolEntry[...],
        "findMax": SymbolEntry[...],
        "main": SymbolEntry[...]
    },
    {  // Scope[1] - findMax Function
        "arr": SymbolEntry[name='arr', type=VARIABLE, dataType=ARRAY<INT>, kind=PARAMETER, scope=1],
        "size": SymbolEntry[name='size', type=VARIABLE, dataType=INT, kind=PARAMETER, scope=1],
        "max": SymbolEntry[name='max', type=VARIABLE, dataType=INT, kind=LOCAL, scope=1, init=true],
        "i": SymbolEntry[name='i', type=VARIABLE, dataType=INT, kind=LOCAL, scope=1]
    }
]
currentScopeLevel = 1
```

**作用域可见性**（在 Scope 1 中查找符号时）：
- 查找 `i` → 在 Scope[1] 找到 ✓
- 查找 `max` → 在 Scope[1] 找到 ✓
- 查找 `x` → 在 Scope[1] 未找到，向上查找，在 Scope[0] 找到 ✓
- 查找 `undefined_var` → 在所有作用域都未找到 ✗

---

### 阶段 3：进入 for 循环块（Scope Level 2）

**时间点**：执行 `for (i = 1; i < size; i++) { ... }` 的块体

**操作**：
1. for 循环的初始化在父作用域完成
2. 进入新的作用域（Level 2）处理循环体

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 3                   │
│ Current Scope Level: 2          │
├─────────────────────────────────┤
│ Scope[2] (For Loop Body)        │
├─────────────────────────────────┤
│ Scope[1] (Function findMax)      │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（Scope[2]）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `temp` | VARIABLE | INT | LOCAL | 2 | 局部变量，已初始化为 `arr[i]` |

**符号表栈快照**：
```
symbolTableStack = [
    {  // Scope[0] - Global
        "x": ..., "arr": ..., "Point": ..., "add": ..., "findMax": ..., "main": ...
    },
    {  // Scope[1] - findMax Function
        "arr": ..., "size": ..., "max": ..., "i": ...
    },
    {  // Scope[2] - For Loop Body
        "temp": SymbolEntry[name='temp', type=VARIABLE, dataType=INT, kind=LOCAL, scope=2, init=true]
    }
]
currentScopeLevel = 2
```

**关键点**：
- 在 Scope[2] 中可以访问 `temp`（本作用域）
- 在 Scope[2] 中可以访问 `i`, `max`, `size`（来自 Scope[1]）
- 在 Scope[2] 中可以访问 `x`, `arr`（来自 Scope[0]，但 `arr` 是全局数组，不是参数 `arr`，会发生遮蔽）
- ⚠️ **遮蔽问题**：参数 `arr` (Scope[1]) 被全局 `arr` (Scope[0]) 遮蔽

---

### 阶段 4：进入 if 语句块（Scope Level 3）

**时间点**：执行 `if (temp > max) { ... }` 的块体

**操作**：
1. 进入新的作用域（Level 3）处理 if 块

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 4                   │
│ Current Scope Level: 3          │
├─────────────────────────────────┤
│ Scope[3] (If Block)             │
├─────────────────────────────────┤
│ Scope[2] (For Loop Body)        │
├─────────────────────────────────┤
│ Scope[1] (Function findMax)      │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（Scope[3]）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `log` | VARIABLE | INT | LOCAL | 3 | 局部变量，已初始化为 `i` |

**符号表栈快照**：
```
symbolTableStack = [
    {  // Scope[0] - Global
        "x": ..., "arr": ..., "Point": ..., "add": ..., "findMax": ..., "main": ...
    },
    {  // Scope[1] - findMax Function
        "arr": ..., "size": ..., "max": ..., "i": ...
    },
    {  // Scope[2] - For Loop Body
        "temp": SymbolEntry[name='temp', type=VARIABLE, dataType=INT, kind=LOCAL, scope=2, init=true]
    },
    {  // Scope[3] - If Block
        "log": SymbolEntry[name='log', type=VARIABLE, dataType=INT, kind=LOCAL, scope=3, init=true]
    }
]
currentScopeLevel = 3
```

**符号查找路径示例**：
- 查找 `log` → Scope[3] 找到 ✓ (返回) → 时间 O(1)
- 查找 `temp` → Scope[3] 未找到，Scope[2] 找到 ✓ → 时间 O(2)
- 查找 `max` → Scope[3] → Scope[2] → Scope[1] 找到 ✓ → 时间 O(3)
- 查找 `x` → Scope[3] → Scope[2] → Scope[1] → Scope[0] 找到 ✓ → 时间 O(4)

---

### 阶段 5：退出 if 语句块（回到 Scope Level 2）

**时间点**：if 块执行完毕

**操作**：
1. 退出 Scope[3]（if 块）
2. `log` 变量的作用域结束，无法再访问

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 3                   │
│ Current Scope Level: 2          │
├─────────────────────────────────┤
│ Scope[2] (For Loop Body)        │
├─────────────────────────────────┤
│ Scope[1] (Function findMax)      │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**重要变化**：
- Scope[3] 完全从栈中移除
- 所有 Scope[3] 的符号变为不可访问
- currentScopeLevel 回到 2
- 可以继续访问 Scope[0]、Scope[1]、Scope[2] 的符号

---

### 阶段 6：退出 for 循环块（回到 Scope Level 1）

**时间点**：for 循环体执行完毕

**操作**：
1. 退出 Scope[2]（for 循环块）
2. `temp` 变量的作用域结束

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 2                   │
│ Current Scope Level: 1          │
├─────────────────────────────────┤
│ Scope[1] (Function findMax)      │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表回到阶段 2 的状态**

---

### 阶段 7：退出 findMax 函数（回到 Scope Level 0）

**时间点**：findMax 函数处理完毕，return 语句执行

**操作**：
1. 退出 Scope[1]（findMax 函数）
2. 所有局部变量和参数变为不可访问
3. 回到全局作用域

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 1                   │
│ Current Scope Level: 0          │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表回到阶段 1 的状态**

---

### 阶段 8：进入 main 函数（Scope Level 1）

**时间点**：开始分析 `main` 函数体

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 2                   │
│ Current Scope Level: 1          │
├─────────────────────────────────┤
│ Scope[1] (Function main)        │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（Scope[1]）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `result` | VARIABLE | INT | LOCAL | 1 | 局部变量，未初始化 |
| 2 | `p` | VARIABLE | STRUCT<Point> | LOCAL | 1 | 局部变量，未初始化 |

---

### 阶段 9：进入 main 函数内的代码块（Scope Level 2）

**时间点**：执行 `{ int block_var = 5; ... }` 代码块

**栈结构**：
```
┌─────────────────────────────────┐
│ Stack Size: 3                   │
│ Current Scope Level: 2          │
├─────────────────────────────────┤
│ Scope[2] (Code Block in main)   │
├─────────────────────────────────┤
│ Scope[1] (Function main)        │
├─────────────────────────────────┤
│ Scope[0] (Global Scope)         │
└─────────────────────────────────┘
```

**符号表内容**（Scope[2]）：

| # | 符号名 | 符号类型 | 数据类型 | 种类 | 作用域 | 详细信息 |
|----|--------|---------|---------|------|--------|---------|
| 1 | `block_var` | VARIABLE | INT | LOCAL | 2 | 局部变量，已初始化为 5 |

---

## 关键概念总结

### 1. 作用域级别（Scope Level）

| 级别 | 说明 | 栈位置 | 示例 |
|------|------|--------|------|
| 0 | 全局作用域 | 栈底 | 全局变量、函数声明 |
| 1+ | 局部作用域 | 栈上方 | 函数、代码块、循环 |

### 2. 符号查找算法

```
查找流程（从内向外）：
当前作用域(Scope N) 
    → 上层作用域(Scope N-1) 
    → ... 
    → 全局作用域(Scope 0)

返回：找到的第一个匹配的符号 ✓
未找到：记录 UNDEFINED_IDENTIFIER 错误 ✗
```

**时间复杂度**：O(k)，其中 k 是从当前作用域到找到符号的作用域级数

### 3. 符号遮蔽（Shadowing）

当内层作用域定义了与外层作用域相同名称的符号时：

```c
int x = 10;        // Scope[0] - Global

int func() {       // Scope[1] - Function
    int x = 20;    // Scope[1] - 遮蔽全局 x
    {              // Scope[2] - Code Block
        int x = 30; // Scope[2] - 遮蔽函数级 x
        // 这里的 x 是 30（最内层）
    }
    // 这里的 x 是 20（函数级）
}
// 这里的 x 是 10（全局）
```

### 4. 作用域的三个关键操作

| 操作 | 时间 | 说明 |
|------|------|------|
| `enterScope()` | O(1) | 进入新的作用域（创建新 HashMap，压栈） |
| `exitScope()` | O(1) | 退出当前作用域（弹栈） |
| `lookupSymbol(name)` | O(k) | 查找符号，k 为作用域深度 |

### 5. 符号表栈的不变量

1. **栈大小 ≥ 1**：全局作用域始终存在
2. **currentScopeLevel = 栈大小 - 1**：作用域级别与栈深度一致
3. **栈底始终是全局作用域**：Scope[0] 永不弹出
4. **符号只在定义的作用域内**：不跨作用域存储

---

## 符号表大小统计

### 按阶段的符号统计

| 阶段 | 作用域深度 | 全局符号 | 局部符号 | 总计 | 备注 |
|------|----------|---------|---------|------|------|
| 1 | 1 | 6 | 0 | 6 | 全局声明完成 |
| 2 | 2 | 6 | 4 | 10 | 进入 findMax |
| 3 | 3 | 6 | 5 | 11 | 进入 for 循环 |
| 4 | 4 | 6 | 6 | 12 | 进入 if 块 |
| 5 | 3 | 6 | 5 | 11 | 退出 if 块 |
| 6 | 2 | 6 | 4 | 10 | 退出 for 循环 |
| 7 | 1 | 6 | 0 | 6 | 退出 findMax |
| 8 | 2 | 6 | 2 | 8 | 进入 main |
| 9 | 3 | 6 | 3 | 9 | 进入代码块 |

---

## 编译器实现细节

### SymbolTableManager 的栈操作示例

```java
// 初始化
SymbolTableManager manager = new SymbolTableManager();
// → symbolTableStack.size() = 1, currentScopeLevel = 0

// 进入 findMax
manager.enterScope();
// → symbolTableStack.size() = 2, currentScopeLevel = 1

// 进入 for 循环
manager.enterScope();
// → symbolTableStack.size() = 3, currentScopeLevel = 2

// 进入 if 块
manager.enterScope();
// → symbolTableStack.size() = 4, currentScopeLevel = 3

// 退出 if 块
manager.exitScope();
// → symbolTableStack.size() = 3, currentScopeLevel = 2
// ✓ Scope[3] 的所有符号被清除

// 退出 for 循环
manager.exitScope();
// → symbolTableStack.size() = 2, currentScopeLevel = 1

// 退出 findMax
manager.exitScope();
// → symbolTableStack.size() = 1, currentScopeLevel = 0

// 尝试退出全局作用域（被阻止）
manager.exitScope();
// → 不执行，保证栈不为空
// → symbolTableStack.size() = 1, currentScopeLevel = 0（不变）
```

### 符号插入示例

```java
// 在 Scope[0] 插入全局变量
SymbolEntry xEntry = new SymbolEntry("x", SymbolType.VARIABLE, DataType.INT, 0, SymbolKind.GLOBAL);
manager.insertSymbol(xEntry);
// → symbolTableStack.get(0).put("x", xEntry)

// 在 Scope[1] 插入函数参数
manager.enterScope();  // 现在 currentScopeLevel = 1
SymbolEntry arrEntry = new SymbolEntry("arr", SymbolType.VARIABLE, DataType.ARRAY_INT, 1, SymbolKind.PARAMETER);
manager.insertSymbol(arrEntry);
// → symbolTableStack.get(1).put("arr", arrEntry)
```

### 符号查找示例

```java
// 在 Scope[2] 查找 "max"
// 当前 currentScopeLevel = 2
manager.enterScope();
manager.enterScope();

SymbolEntry found = manager.lookupSymbolWithoutError("max");
// → 搜索流程：
//   - Scope[2].containsKey("max") → false
//   - Scope[1].containsKey("max") → true  ✓
//   - 返回 symbolTableStack.get(1).get("max")

// 查找不存在的符号
SymbolEntry notFound = manager.lookupSymbolWithoutError("undefined");
// → 搜索流程：
//   - Scope[2].containsKey("undefined") → false
//   - Scope[1].containsKey("undefined") → false
//   - Scope[0].containsKey("undefined") → false
//   - 返回 null
```

---

## 常见错误检测

### 错误 1：重定义（Redefinition）

```c
int x = 10;
int x = 20;  // ✗ 错误：在相同作用域重定义
```

**检测**：`isDefinedInCurrentScope("x")` 返回 true

### 错误 2：未定义（Undefined Identifier）

```c
int x = y + 1;  // ✗ 错误：y 未定义
```

**检测**：`lookupSymbol("y")` 返回 null

### 错误 3：作用域泄漏（Scope Leak）

```c
int func() {
    int x = 10;
}
int main() {
    return x;  // ✗ 错误：x 超出作用域
}
```

**检测**：在 Scope[1] (main) 查找 "x"，不会在 Scope[1] (func) 中查找

---

## 性能分析

### 查找性能

**最坏情况**：O(d)，d 为当前作用域深度
- 例如：在深度为 4 的作用域中查找全局变量需要查询 4 次

**平均情况**：O(1) ~ O(d)，取决于符号定义的位置

**优化建议**：
1. 常见的全局符号可以缓存
2. 局部作用域通常不深（≤ 5），性能足够

### 空间复杂度

- 全局作用域：O(n)，n 为全局符号数
- 每个局部作用域：O(m)，m 为局部符号数
- 总体：O(n + Σm)

---

## 总结

符号表是编译器的核心数据结构，通过**栈式结构**管理嵌套作用域：

1. **进入作用域** → 压栈 → 当前级别 +1
2. **插入符号** → 添加到当前作用域的 HashMap
3. **查找符号** → 从栈顶向栈底查找（内向外）
4. **退出作用域** → 弹栈 → 当前级别 -1 → 该级别所有符号不可访问

这种设计**简洁高效**，充分支持 C 语言的嵌套作用域和符号遮蔽机制。

