# 🔗 指针运算支持

## 概述

Wei-C 编译器现已支持四种指针运算操作，完整实现了 C 语言的指针算术功能。

**支持的运算**:
- `ptr + int` - 指针加整数
- `int + ptr` - 整数加指针  
- `ptr - int` - 指针减整数
- `ptr - ptr` - 指针差值（返回整数）

---

## 实现原理

### 1. 语义分析 (TypeAnalyzer)

在 `TypeAnalyzer.visitAdditiveExpression()` 中添加指针运算的类型检查：

```java
// ptr + int 或 int + ptr -> 返回指针类型
if (leftType instanceof PointerType && isNumericType(rightType)) {
    return leftType;
}
if (isNumericType(leftType) && rightType instanceof PointerType) {
    return rightType;
}

// ptr - int -> 返回指针类型
if (leftType instanceof PointerType && isNumericType(rightType)) {
    return leftType;
}

// ptr - ptr -> 返回整数类型
if (leftType instanceof PointerType && rightType instanceof PointerType) {
    return DataType.INT;
}
```

**关键点**:
- 指针加减整数返回指针类型
- 指针差值返回整数类型（元素个数）
- 只有指针之间的加法是允许的

### 2. 中间代码生成 (IRGenerator)

在 `ExpressionIRGenerator.visitAdditiveExpression()` 中生成 TAC 指令：

```java
// 检查是否为指针运算
if (leftType.isPointer() && rightType.isInt()) {
    // ptr ± int -> 生成 ADD/SUB，结果类型为指针
    TACInstruction inst = new TACInstruction(
        operator == AdditiveOperator.ADD ? TACOpcode.ADD : TACOpcode.SUB,
        leftOperand, rightOperand, result
    );
    inst.setResultType(leftType);  // 结果类型为指针
}

if (leftType.isInt() && rightType.isPointer()) {
    // int + ptr -> 交换操作数，结果类型为指针
    TACInstruction inst = new TACInstruction(
        TACOpcode.ADD,
        rightOperand, leftOperand, result
    );
    inst.setResultType(rightType);
}

if (leftType.isPointer() && rightType.isPointer()) {
    // ptr - ptr -> 生成 SUB，结果类型为整数
    TACInstruction inst = new TACInstruction(
        TACOpcode.SUB,
        leftOperand, rightOperand, result
    );
    inst.setResultType(DataType.INT);
}
```

### 3. LLVM 代码生成 (CodeGenerator)

在 `CodeGenerator` 中实现 LLVM IR 转换：

#### ptr + int 的处理

```java
// 使用 getelementptr 指令进行指针算术
private void generateAdd(TACInstruction instruction) {
    // ...获取操作数...
    
    if (resultType.isPointer() || resultType.isAddress()) {
        // 指针运算：使用 getelementptr
        String elemType = operandType.endsWith("*") 
            ? operandType.substring(0, operandType.length() - 1) 
            : "i32";
        llvmCode.append("  ").append(result)
                .append(" = getelementptr ").append(elemType)
                .append(", ").append(operandType).append(" ")
                .append(arg1).append(", i32 ").append(arg2).append("\n");
    }
}
```

#### ptr - int 的处理

```java
private void generateSub(TACInstruction instruction) {
    // ...参数获取...
    
    if (arg1Type.isPointer() && arg2Type.isInt()) {
        // ptr - int: 使用负数偏移的 getelementptr
        String elemType = operandType.endsWith("*") 
            ? operandType.substring(0, operandType.length() - 1) 
            : "i32";
        String negReg = getRegister();
        // 计算负数偏移
        llvmCode.append("  ").append(negReg).append(" = sub i32 0, ")
                .append(arg2).append("\n");
        // 使用负数偏移
        llvmCode.append("  ").append(result)
                .append(" = getelementptr ").append(elemType)
                .append(", ").append(operandType).append(" ")
                .append(arg1).append(", i32 ").append(negReg).append("\n");
    }
}
```

#### ptr1 - ptr2 的处理

```java
if (arg1Type.isPointer() && arg2Type.isPointer()) {
    // ptr - ptr: 指针差值
    String ptrToInt1 = getRegister();
    String ptrToInt2 = getRegister();
    
    // 转换指针为整数
    llvmCode.append("  ").append(ptrToInt1)
            .append(" = ptrtoint ").append(operandType)
            .append(" ").append(arg1).append(" to i64\n");
    llvmCode.append("  ").append(ptrToInt2)
            .append(" = ptrtoint ").append(operandType)
            .append(" ").append(arg2).append(" to i64\n");
    
    // 整数减法
    String diffReg = getRegister();
    llvmCode.append("  ").append(diffReg).append(" = sub i64 ")
            .append(ptrToInt1).append(", ").append(ptrToInt2).append("\n");
    
    // 除以元素大小
    llvmCode.append("  ").append(result).append(" = sdiv i64 ")
            .append(diffReg).append(", ").append(elementSize).append("\n");
}
```

---

## LLVM IR 示例

### 示例 1: 指针加整数

```c
int arr[5];
int* p = arr;
int* q = p + 2;  // 偏移 2 个元素
```

生成的 LLVM IR:

```llvm
; 分配数组
%arr = alloca [5 x i32], align 4
%arr_ptr = bitcast [5 x i32]* %arr to i32*

; p = arr
store i32* %arr_ptr, i32** %p, align 8

; q = p + 2
%p_val = load i32*, i32** %p, align 8
%q_val = getelementptr i32, i32* %p_val, i32 2
store i32* %q_val, i32** %q, align 8
```

### 示例 2: 指针差值

```c
int arr[5];
int* p1 = arr;
int* p2 = arr + 3;
int diff = p2 - p1;  // diff = 3
```

生成的 LLVM IR:

```llvm
%p1_val = load i32*, i32** %p1, align 8
%p2_val = load i32*, i32** %p2, align 8

; 转换指针为整数
%p1_int = ptrtoint i32* %p1_val to i64
%p2_int = ptrtoint i32* %p2_val to i64

; 计算差值
%diff_bytes = sub i64 %p2_int, %p1_int
%diff_elems = sdiv i64 %diff_bytes, 4  ; 除以 sizeof(int)

; 存储结果
%diff_result = trunc i64 %diff_elems to i32
store i32 %diff_result, i32* %diff, align 4
```

---

## 测试用例

### 测试文件: `example_pointer_arithmetic.gc`

```c
int main() {
    // 测试 1: 指针加整数 (ptr + int)
    int arr[5];
    arr[0] = 10;
    arr[1] = 20;
    arr[2] = 30;
    
    int* p1 = arr;
    int* p2 = p1 + 1;      // 指向 arr[1]
    int* p3 = p1 + 2;      // 指向 arr[2]
    
    // 测试 2: 整数加指针 (int + ptr)
    int* p4 = 1 + p1;      // 等同于 p1 + 1
    
    // 测试 3: 指针减整数 (ptr - int)
    int* p5 = p3 - 1;      // 指向 arr[1]
    
    // 测试 4: 指针差值 (ptr - ptr)
    int diff1 = p3 - p1;   // 应为 2
    int diff2 = p2 - p1;   // 应为 1
    int diff3 = p1 - p1;   // 应为 0
    
    return 0;
}
```

### 测试覆盖

| 运算类型 | 测试项目 | 验证内容 |
|---------|---------|---------|
| ptr + int | 指针偏移 | 生成正确的 getelementptr 指令 |
| int + ptr | 交换操作数 | 生成 getelementptr（操作数交换） |
| ptr - int | 向后偏移 | 使用负数偏移的 getelementptr |
| ptr - ptr | 计算差值 | ptrtoint + 减法 + 除法 |

---

## 设计要点

### 1. 类型检查的准确性

- 指针和整数运算有明确的类型结果
- 编译器在语义分析阶段确保操作合法
- 类型不匹配会报告编译错误

### 2. LLVM 指令选择

- **getelementptr**: 用于指针偏移计算，自动处理元素大小
- **ptrtoint**: 用于将指针转换为整数地址
- **sdiv**: 用于计算指针差值时的除法

### 3. 元素大小的处理

- 指针运算自动按照指向类型的大小进行偏移
- 例如：`int* p; p + 1` 偏移 4 字节（int 大小）
- 指针差值时需要除以元素大小得到元素个数

### 4. 指针的内存布局

- 指针本身使用 `i32*` 或 `i64*` 的 LLVM 类型
- 指向的元素类型决定了 getelementptr 的偏移计算
- 支持指向基本类型（int, float, etc）和复合类型（array, struct）

---

## 限制和注意事项

### 当前支持

✅ 基本指针算术运算  
✅ 指针和整数的混合运算  
✅ 指针差值计算  
✅ 在数组和指针间相互转换  

### 当前不支持

❌ 指针解引用 (`*ptr`)  
❌ 指针取地址 (`&var`)  
❌ 指针赋值和比较（部分）  
❌ 空指针处理  
❌ 函数指针  
❌ void 指针  

### 已知问题

1. **指针解引用**: 未完全实现，不能访问指针指向的值
2. **取地址符**: 未支持，不能获取变量地址
3. **指针比较**: 暂不支持指针的相等性比较

---

## 改进方向

1. 完整指针支持
   - 实现指针解引用 (`*ptr`)
   - 实现取地址操作 (`&var`)
   
2. 指针安全
   - 实现空指针检测
   - 实现越界检测
   - 实现对齐检查

3. 高级特性
   - 函数指针
   - void 指针
   - 指针强制转换

---

<div align="center">

**🔗 指针运算支持**

Made with ❤️ by wei-C Compiler Team

</div>
