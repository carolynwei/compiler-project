package com.wei.compiler.codegen;

import com.wei.compiler.ir.*;
import com.wei.compiler.semantic.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 * LLVM IR 代码生成器
 * 
 * 将三地址代码 (TAC) 转换为 LLVM IR 代码
 */
public class CodeGenerator {
    
    private boolean debugMode;
    private StringBuilder llvmCode;
    private Map<String, String> variableMap;  // 变量 -> 指针寄存器映射
    private Map<String, String> tempVarMap;    // 临时变量 -> 值寄存器映射
    private Map<String, String> variableTypeMap;  // 变量 -> 类型映射（关键修复）
    private Map<String, String> functionMap;
    private int registerCounter;
    private int labelCounter;
    private List<String> callArgs;

    public CodeGenerator() {
        this.debugMode = false;
        this.llvmCode = new StringBuilder();
        this.variableMap = new HashMap<>();
        this.tempVarMap = new HashMap<>();
        this.variableTypeMap = new HashMap<>();  // 初始化类型映射
        this.functionMap = new HashMap<>();
        this.registerCounter = 0;
        this.labelCounter = 0;
    }
    
    /**
     * 生成 LLVM IR 代码
     */
    public String generate(IRProgram irProgram) {
        System.out.println("\n--- 阶段四：目标代码生成 (LLVM IR) ---");
        
        // 生成 LLVM IR 头部
        generateHeader();
        
        // 生成全局变量声明
        generateGlobalDeclarations();
        
        // 生成函数代码
        generateFunctions(irProgram);
        
        // 生成主函数
        generateMainFunction(irProgram);
        
        if (debugMode) {
            System.out.println("=== 生成的 LLVM IR 代码 ===");
            System.out.println(llvmCode.toString());
        }
        
        System.out.println("LLVM IR 代码生成完成");
        return llvmCode.toString();
    }
    
    /**
     * 设置调试模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * 生成 LLVM IR 头部
     */
    private void generateHeader() {
        llvmCode.append("; wei-C 编译器生成的 LLVM IR 代码\n");
        llvmCode.append("; 目标架构: x86-64\n");
        llvmCode.append("; 优化级别: -O2\n\n");
        
        // 添加必要的声明
        llvmCode.append("declare i32 @printf(i8*, ...)\n");
        llvmCode.append("declare i32 @scanf(i8*, ...)\n");
        llvmCode.append("declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i1)\n");
        llvmCode.append("declare i8* @malloc(i64)\n");
        llvmCode.append("declare void @free(i8*)\n\n");
    }
    
    /**
     * 生成全局变量声明
     */
    private void generateGlobalDeclarations() {
        llvmCode.append("; 全局变量声明\n");
        // 这里可以添加全局变量的声明
        llvmCode.append("\n");
    }
    
    /**
     * 生成函数代码
     */
    private void generateFunctions(IRProgram irProgram) {
        llvmCode.append("; 函数定义\n");
        
        // 遍历中间代码，生成函数
        for (TACInstruction instruction : irProgram.getInstructions()) {
            if (instruction.getOpcode() == TACOpcode.LABEL && 
                instruction.getResult().startsWith("func_")) {
                generateFunction(instruction.getResult(), irProgram);
            }
        }
    }
    
    /**
     * 生成单个函数
     */
    private void generateFunction(String functionLabel, IRProgram irProgram) {
        String functionName = functionLabel.substring(5); // 移除 "func_" 前缀
        
        // 收集函数参数
        List<String> functionParams = new ArrayList<>();
        boolean inFunction = false;
        for (TACInstruction instruction : irProgram.getInstructions()) {
            if (instruction.getOpcode() == TACOpcode.LABEL && 
                instruction.getResult().equals(functionLabel)) {
                inFunction = true;
                continue;
            }
            
            if (inFunction) {
                if (instruction.getOpcode() == TACOpcode.LABEL && 
                    instruction.getResult().startsWith("func_")) {
                    break; // 遇到下一个函数
                }
                
                // 处理PARAM指令（函数参数）
                if (instruction.getOpcode() == TACOpcode.PARAM) {
                    String paramName = instruction.getArg1();
                    if (paramName != null) {
                        functionParams.add(paramName);
                    }
                }
            }
        }
        
        // 生成函数声明（包含参数）
        llvmCode.append("define i32 @").append(functionName).append("(");
        for (int i = 0; i < functionParams.size(); i++) {
            if (i > 0) {
                llvmCode.append(", ");
            }
            // 默认参数类型为i32，实际应该从TACInstruction中获取
            llvmCode.append("i32 %").append(functionParams.get(i)).append("_arg");
        }
        llvmCode.append(") {\n");
        
        llvmCode.append("entry:\n");
        
        // 为每个函数参数生成alloca指令和store指令
        for (String param : functionParams) {
            String reg = getRegister();
            // 默认参数类型为i32，实际应该从TACInstruction中获取
            llvmCode.append("  ").append(reg).append(" = alloca i32, align 4\n");
            variableMap.put(param, reg);
            // 存储参数值到分配的内存中
            llvmCode.append("  store i32 %").append(param).append("_arg, i32* ").append(reg).append(", align 4\n");
        }
        
        // 生成函数体
        generateFunctionBody(functionLabel, irProgram);
        
        llvmCode.append("}\n\n");
    }
    
    /**
     * 生成函数体
     */
    private void generateFunctionBody(String functionLabel, IRProgram irProgram) {
        boolean inFunction = false;
        
        for (TACInstruction instruction : irProgram.getInstructions()) {
            if (instruction.getOpcode() == TACOpcode.LABEL && 
                instruction.getResult().equals(functionLabel)) {
                inFunction = true;
                continue;
            }
            
            if (inFunction) {
                if (instruction.getOpcode() == TACOpcode.LABEL && 
                    instruction.getResult().startsWith("func_")) {
                    break; // 遇到下一个函数
                }
                
                generateInstruction(instruction);
            }
        }
    }
    
    /**
     * 生成主函数
     */
    private void generateMainFunction(IRProgram irProgram) {
        llvmCode.append("define i32 @main() {\n");
        llvmCode.append("entry:\n");
        
        // 重置计数器
        registerCounter = 0;
        tempVarMap.clear();
        variableMap.clear();
        variableTypeMap.clear();  // 🔥 重置类型映射
        
        // 首先预扫描所有变量声明（ALLOC 指令）
        preAllocateVariables(irProgram);
        
        // 生成主函数体
        generateMainFunctionBody(irProgram);
        
        llvmCode.append("  ret i32 0\n");
        llvmCode.append("}\n");
    }
    
    /**
     * 预先分配所有变量
     * 这个方法不仅处理 ALLOC 指令，还要处理被优化器（如 Mem2RegPass）转换后的变量
     */
    private void preAllocateVariables(IRProgram irProgram) {
        Set<String> allocatedVars = new HashSet<>();
        Set<String> needsAllocation = new HashSet<>();
        Map<String, String> typeMap = new HashMap<>();  // 变量 -> 类型
        Map<String, TACType> tacTypeMap = new HashMap<>();  // 变量 -> TACType
        
        // 第一遍：收集所有需要分配的变量和类型信息
        for (TACInstruction instruction : irProgram.getInstructions()) {
            if (instruction.getOpcode() == TACOpcode.LABEL && 
                instruction.getResult().startsWith("func_")) {
                continue; // 跳过函数定义
            }
            
            // 收集所有 ALLOC 指令的变量和类型
            if (instruction.getOpcode() == TACOpcode.ALLOC) {
                String varName = instruction.getResult();
                needsAllocation.add(varName);
                // 保存类型信息
                TACType tacType = instruction.getResultTypeObj();
                if (tacType != null) {
                    tacTypeMap.put(varName, tacType);
                    typeMap.put(varName, tacType.getTypeName());
                } else {
                    // 默认为int
                    typeMap.put(varName, instruction.getResultType() != null ? instruction.getResultType() : "INT");
                }
            }
            
            // 收集所有 ASSIGN 的目标变量（可能是被优化器处理后的变量）
            // 但不包括临时变量
            if (instruction.getOpcode() == TACOpcode.ASSIGN) {
                String resultVar = instruction.getResult();
                if (resultVar != null && !resultVar.startsWith("t") && !resultVar.matches("\\d+")) {
                    needsAllocation.add(resultVar);
                    // ASSIGN 需要保存类型，取双方的类型
                    if (!typeMap.containsKey(resultVar)) {
                        TACType tacType = instruction.getResultTypeObj();
                        if (tacType != null) {
                            tacTypeMap.put(resultVar, tacType);
                            typeMap.put(resultVar, tacType.getTypeName());
                        } else {
                            typeMap.put(resultVar, "INT");
                        }
                    }
                }
            }
        }
        
        // 第二遭：为所有需要分配的变量生成 alloca 指令
        for (String varName : needsAllocation) {
            if (!allocatedVars.contains(varName)) {
                allocatedVars.add(varName);
                String reg = getRegister();
                String llvmType = getLLVMTypeFromTACType(tacTypeMap.get(varName));
                llvmCode.append("  ").append(reg).append(" = alloca ").append(llvmType).append(", align 4\n");
                variableMap.put(varName, reg);
                variableTypeMap.put(varName, llvmType);  // 🔥 保存类型信息
            }
        }
    }
    
    /**
     * 生成主函数体
     */
    private void generateMainFunctionBody(IRProgram irProgram) {
        for (TACInstruction instruction : irProgram.getInstructions()) {
            if (instruction.getOpcode() == TACOpcode.LABEL && 
                instruction.getResult().startsWith("func_")) {
                continue; // 跳过函数定义
            }
            
            // 跳过 ALLOC 指令，因为已经在 preAllocateVariables 中处理
            if (instruction.getOpcode() == TACOpcode.ALLOC) {
                continue;
            }
            
            generateInstruction(instruction);
        }
    }
    
    /**
     * 生成单个指令
     */
    private void generateInstruction(TACInstruction instruction) {
        switch (instruction.getOpcode()) {
            case ADD:
                generateAdd(instruction);
                break;
            case SUB:
                generateSub(instruction);
                break;
            case MUL:
                generateMul(instruction);
                break;
            case DIV:
                generateDiv(instruction);
                break;
            case MOD:
                generateMod(instruction);
                break;
            case FADD:
                generateFadd(instruction);
                break;
            case FSUB:
                generateFsub(instruction);
                break;
            case FMUL:
                generateFmul(instruction);
                break;
            case FDIV:
                generateFdiv(instruction);
                break;
            case FNEG:
                generateFneg(instruction);
                break;
            case EQ:
                generateEq(instruction);
                break;
            case NE:
                generateNe(instruction);
                break;
            case LT:
                generateLt(instruction);
                break;
            case GT:
                generateGt(instruction);
                break;
            case LE:
                generateLe(instruction);
                break;
            case GE:
                generateGe(instruction);
                break;
            case FEQ:
                generateFeq(instruction);
                break;
            case FNE:
                generateFne(instruction);
                break;
            case FLT:
                generateFlt(instruction);
                break;
            case FGT:
                generateFgt(instruction);
                break;
            case FLE:
                generateFle(instruction);
                break;
            case FGE:
                generateFge(instruction);
                break;
            case AND:
                generateAnd(instruction);
                break;
            case OR:
                generateOr(instruction);
                break;
            case NOT:
                generateNot(instruction);
                break;
            case ASSIGN:
                generateAssign(instruction);
                break;
            case PLUS_ASSIGN:
                generatePlusAssign(instruction);
                break;
            case MINUS_ASSIGN:
                generateMinusAssign(instruction);
                break;
            case MUL_ASSIGN:
                generateMulAssign(instruction);
                break;
            case DIV_ASSIGN:
                generateDivAssign(instruction);
                break;
            case MOD_ASSIGN:
                generateModAssign(instruction);
                break;
            case INCREMENT:
                generateIncrement(instruction);
                break;
            case DECREMENT:
                generateDecrement(instruction);
                break;
            case GOTO:
                generateGoto(instruction);
                break;
            case IF_TRUE:
                generateIfTrue(instruction);
                break;
            case IF_FALSE:
                generateIfFalse(instruction);
                break;
            case IF_ZERO:
                generateIfZero(instruction);
                break;
            case IF_NONZERO:
                generateIfNonzero(instruction);
                break;
            case LABEL:
                generateLabel(instruction);
                break;
            case CALL:
                generateCall(instruction);
                break;
            case RETURN:
                generateReturn(instruction);
                break;
            case ARRAY_ACCESS:
                generateArrayAccess(instruction);
                break;
            case ARRAY_ASSIGN:
                generateArrayAssign(instruction);
                break;
            case MEMBER_ACCESS:
                generateMemberAccess(instruction);
                break;
            case MEMBER_ASSIGN:
                generateMemberAssign(instruction);
                break;
            case PARAM:
                generateParam(instruction);
                break;
            case ARG:
                generateArg(instruction);
                break;
            case ALLOC:
                generateAlloc(instruction);
                break;
            case LOAD:
                generateLoad(instruction);
                break;
            case STORE:
                generateStore(instruction);
                break;
            case CAST:
            case SITOFP:
            case FPTOSI:
                generateCast(instruction);
                break;
        }
    }
    
    private void generateAdd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        TACType arg1Type = instruction.getArg1Type();
        TACType arg2Type = instruction.getArg2Type();
        TACType resultType = instruction.getResultTypeObj();
        
        String operandType = "i32"; // 默认为i32
        if (arg1Type != null) {
            operandType = getLLVMTypeFromTACType(arg1Type);
        }
        
        // 检查是否是指针运算
        if (resultType != null && (resultType.isPointer() || resultType.isAddress())) {
            // 指针运算：ptr + int，需要使用 getelementptr
            String elemType = operandType.endsWith("*") ? operandType.substring(0, operandType.length() - 1) : "i32";
            llvmCode.append("  ").append(result).append(" = getelementptr ").append(elemType).append(", ").append(operandType).append(" ").append(arg1).append(", i32 ").append(arg2).append("\n");
        } else {
            // 普通整数加法
            llvmCode.append("  ").append(result).append(" = add ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        }
        
        String resultVar = instruction.getResult();
        // 概程: 临时变量直接存到tempVarMap（值）；正常变量存到variableMap（指针）
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成减法指令
     */
    private void generateSub(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        TACType arg1Type = instruction.getArg1Type();
        TACType arg2Type = instruction.getArg2Type();
        TACType resultType = instruction.getResultTypeObj();
        
        String operandType = "i32"; // 默认为i32
        if (arg1Type != null) {
            operandType = getLLVMTypeFromTACType(arg1Type);
        }
        
        // 检查是否是指针运算
        if (resultType != null) {
            if ((arg1Type != null && (arg1Type.isPointer() || arg1Type.isAddress())) && 
                (arg2Type != null && arg2Type.isInt())) {
                // 指针减法：ptr - int，需要使用 getelementptr 带负数偏移
                String elemType = operandType.endsWith("*") ? operandType.substring(0, operandType.length() - 1) : "i32";
                // 计算负数偏移：0 - arg2
                String negOffset = getRegister();
                llvmCode.append("  ").append(negOffset).append(" = sub i32 0, ").append(arg2).append("\n");
                // 使用负数偏移的 getelementptr
                llvmCode.append("  ").append(result).append(" = getelementptr ").append(elemType).append(", ").append(operandType).append(" ").append(arg1).append(", i32 ").append(negOffset).append("\n");
            } else if ((arg1Type != null && (arg1Type.isPointer() || arg1Type.isAddress())) && 
                       (arg2Type != null && (arg2Type.isPointer() || arg2Type.isAddress()))) {
                // 指针差值：ptr - ptr，返回整数
                // 需要进行指针转换后计算差值
                String ptrToInt1 = getRegister();
                String ptrToInt2 = getRegister();
                llvmCode.append("  ").append(ptrToInt1).append(" = ptrtoint ").append(operandType).append(" ").append(arg1).append(" to i64\n");
                llvmCode.append("  ").append(ptrToInt2).append(" = ptrtoint ").append(operandType).append(" ").append(arg2).append(" to i64\n");
                
                // 计算字节差值
                String diffBytes = getRegister();
                llvmCode.append("  ").append(diffBytes).append(" = sub i64 ").append(ptrToInt1).append(", ").append(ptrToInt2).append("\n");
                
                // 获取元素大小，除以元素大小得到元素个数
                int elementSize = 4; // 默认为 int 的大小
                if (arg1Type.isPointer() && arg1Type.getElementType() != null) {
                    // 根据元素类型计算大小
                    TACType elemType = arg1Type.getElementType();
                    if (elemType.isInt() || elemType.isFloat()) {
                        elementSize = 4;
                    } else if (elemType.isChar()) {
                        elementSize = 1;
                    }
                }
                
                // 除以元素大小
                String diffElems = getRegister();
                llvmCode.append("  ").append(diffElems).append(" = sdiv i64 ").append(diffBytes).append(", ").append(elementSize).append("\n");
                
                // 转换为 i32
                llvmCode.append("  ").append(result).append(" = trunc i64 ").append(diffElems).append(" to i32\n");
            } else {
                // 普通整数减法
                llvmCode.append("  ").append(result).append(" = sub ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
            }
        } else {
            // 默认为普通减法
            llvmCode.append("  ").append(result).append(" = sub ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        }
        
        String resultVar = instruction.getResult();
        // 概程: 临时变量直接存到tempVarMap（值）；正常变量存到variableMap（指针）
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成乘法指令
     */
    private void generateMul(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = mul ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        // 概程: 临时变量直接存到tempVarMap（值）；正常变量存到variableMap（指针）
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成除法指令
     */
    private void generateDiv(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = sdiv ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成取模指令
     */
    private void generateMod(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = srem ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点加法指令
     */
    private void generateFadd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 浮点操作使用 double
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fadd ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 生成浮点减法指令
     */
    private void generateFsub(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fsub ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 生成浮点乘法指令
     */
    private void generateFmul(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fmul ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 生成浮点除法指令
     */
    private void generateFdiv(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fdiv ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 生成浮点负数指令
     */
    private void generateFneg(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fneg ").append(operandType).append(" ").append(arg1).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 生成整数相等比较指令
     */
    private void generateEq(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp eq ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点相等比较指令
     */
    private void generateFeq(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp oeq ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点不等比较指令
     */
    private void generateFne(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp one ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点小于比较指令
     */
    private void generateFlt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp olt ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点大于比较指令
     */
    private void generateFgt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp ogt ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点小于等于比较指令
     */
    private void generateFle(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp ole ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成浮点大于等于比较指令
     */
    private void generateFge(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        String operandType = "double";
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = fcmp oge ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成不等比较指令
     */
    private void generateNe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp ne ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成小于比较指令
     */
    private void generateLt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp slt ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成大于比较指令
     */
    private void generateGt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp sgt ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成小于等于比较指令
     */
    private void generateLe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp sle ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成大于等于比较指令
     */
    private void generateGe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = icmp sge ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i1 ").append(result).append(", i1* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成逻辑与指令
     */
    private void generateAnd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = and ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成逻辑或指令
     */
    private void generateOr(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = or ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成逻辑非指令
     */
    private void generateNot(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = xor ").append(operandType).append(" ").append(arg1).append(", 1\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成地址加载
     */
    private String generateAddressLoad(AddressTAC addressInfo) {
        if (addressInfo == null) {
            return "null";
        }
        
        String addressReg = getRegister();
        
        switch (addressInfo.getKind()) {
            case VARIABLE:
                // 简单变量地址
                if (variableMap.containsKey(addressInfo.getBase())) {
                    String basePtr = variableMap.get(addressInfo.getBase());
                    // 对于简单变量，直接返回其地址
                    return basePtr;
                }
                break;
            case ARRAY_ELEMENT:
                // 数组元素地址
                if (variableMap.containsKey(addressInfo.getBase())) {
                    String basePtr = variableMap.get(addressInfo.getBase());
                    String index = getValueOperand(addressInfo.getIndices()[0]); // 简化处理，只取第一个索引
                    // 获取数组元素类型
                    String elementType = "i32"; // 默认为i32
                    if (addressInfo.getAddressType() != null && addressInfo.getAddressType().getKind() == TACType.TypeKind.ARRAY) {
                        elementType = getLLVMTypeFromTACType(addressInfo.getAddressType().getElementType());
                    }
                    llvmCode.append("  ").append(addressReg).append(" = getelementptr inbounds ").append(elementType).append(", ").append(elementType).append("* ").append(basePtr).append(", i32 ").append(index).append("\n");
                }
                break;
            case STRUCT_FIELD:
                // 结构体字段地址
                if (variableMap.containsKey(addressInfo.getBase())) {
                    String basePtr = variableMap.get(addressInfo.getBase());
                    // 获取结构体类型名称
                    String structTypeName = addressInfo.getBase();
                    if (addressInfo.getAddressType() != null && addressInfo.getAddressType().getKind() == TACType.TypeKind.STRUCT) {
                        structTypeName = addressInfo.getAddressType().getTypeName();
                    }
                    llvmCode.append("  ").append(addressReg).append(" = getelementptr inbounds %struct.").append(structTypeName).append(", %struct.").append(structTypeName).append("* ").append(basePtr).append(", i32 0, i32 ").append(addressInfo.getFieldOffset()).append("\n");
                }
                break;
            case DEREFERENCE:
                // 指针解引用
                if (variableMap.containsKey(addressInfo.getBase())) {
                    String basePtr = variableMap.get(addressInfo.getBase());
                    llvmCode.append("  ").append(addressReg).append(" = load i32*, i32** ").append(basePtr).append("\n");
                }
                break;
        }
        
        return addressReg;
    }
    
    /**
     * 生成赋值指令
     */
    private void generateAssign(TACInstruction instruction) {
        String arg1 = getValueOperand(instruction.getArg1());
        String resultVar = instruction.getResult();
        
        // 检查是否有地址信息
        AddressTAC addressInfo = instruction.getAddressInfo();
        if (addressInfo != null) {
            // 这是一个地址操作，需要生成 store 指令
            String addressReg = generateAddressLoad(addressInfo);
            String llvmType = getLLVMTypeFromTACType(addressInfo.getAddressType());
            llvmCode.append("  store ").append(llvmType).append(" ").append(arg1).append(", ").append(llvmType).append("* ").append(addressReg).append(", align 4\n");
        } else if (variableMap.containsKey(resultVar)) {
            // 这是一个已分配的变量，需要生成 store 指令
            String ptr = variableMap.get(resultVar);
            // 获取变量的类型信息
            String llvmType = "i32"; // 默认为 i32
            TACType tacType = instruction.getResultTypeObj();
            if (tacType != null) {
                llvmType = getLLVMTypeFromTACType(tacType);
            }
            llvmCode.append("  store ").append(llvmType).append(" ").append(arg1).append(", ").append(llvmType).append("* ").append(ptr).append(", align 4\n");
        } else {
            // 这是一个临时变量或者未分配的变量，生成一个新的寄存器
            String result = getRegister();
            llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", 0\n");
            tempVarMap.put(resultVar, result);
        }
    }
    
    /**
     * 生成复合赋值指令
     */
    private void generatePlusAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = add ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        // 只有当结果不是已分配变量时，才将其添加到variableMap
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            // 如果是已分配的变量，生成store指令
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    private void generateMinusAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = sub ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    private void generateMulAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = mul ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    private void generateDivAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = sdiv ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    private void generateModAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        String arg2 = getValueOperand(instruction.getArg2());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = srem ").append(operandType).append(" ").append(arg1).append(", ").append(arg2).append("\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成自增指令
     */
    private void generateIncrement(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = add ").append(operandType).append(" ").append(arg1).append(", 1\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成自减指令
     */
    private void generateDecrement(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getValueOperand(instruction.getArg1());
        
        // 获取操作数类型
        String operandType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null) {
            operandType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = sub ").append(operandType).append(" ").append(arg1).append(", 1\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(operandType).append(" ").append(result).append(", ").append(operandType).append("* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成跳转指令
     */
    private void generateGoto(TACInstruction instruction) {
        llvmCode.append("  br label %").append(instruction.getResult()).append("\n");
    }
    
    /**
     * 生成条件跳转指令
     */
    private void generateIfTrue(TACInstruction instruction) {
        String arg1 = getOperand(instruction.getArg1());
        llvmCode.append("  br i1 ").append(arg1).append(", label %").append(instruction.getResult()).append(", label %").append(instruction.getResult()).append("_else\n");
    }
    
    private void generateIfFalse(TACInstruction instruction) {
        String arg1 = getOperand(instruction.getArg1());
        llvmCode.append("  br i1 ").append(arg1).append(", label %").append(instruction.getResult()).append("_else, label %").append(instruction.getResult()).append("\n");
    }
    
    private void generateIfZero(TACInstruction instruction) {
        String arg1 = getValueOperand(instruction.getArg1());  // 🔥 改为getValueOperand以正确加载变量
        String cond = getRegister();
        // 生成与零比较
        llvmCode.append("  ").append(cond).append(" = icmp eq i32 ").append(arg1).append(", 0\n");
        llvmCode.append("  br i1 ").append(cond).append(", label %").append(instruction.getResult()).append(", label %").append(instruction.getResult()).append("_else\n");
    }
    
    private void generateIfNonzero(TACInstruction instruction) {
        String arg1 = getValueOperand(instruction.getArg1());  // 🔥 改为getValueOperand以正确加载变量
        String cond = getRegister();
        // 生成与零比较
        llvmCode.append("  ").append(cond).append(" = icmp ne i32 ").append(arg1).append(", 0\n");
        llvmCode.append("  br i1 ").append(cond).append(", label %").append(instruction.getResult()).append(", label %").append(instruction.getResult()).append("_else\n");
    }
    
    /**
     * 生成标签
     */
    private void generateLabel(TACInstruction instruction) {
        llvmCode.append(instruction.getResult()).append(":\n");
    }
    
    /**
     * 生成函数调用指令
     */
    private void generateCall(TACInstruction instruction) {
        String result = getRegister();
        String functionName = instruction.getArg1();
        
        // 收集函数调用参数
        List<String> callArgs = new ArrayList<>();
        // 查找此调用之前的ARG指令
        // 这里我们需要一个更智能的方式来跟踪参数
        // 简化处理：假设参数已经通过其他方式准备好
        
        // 获取返回值类型
        String returnType = "i32"; // 默认为i32
        if (instruction.getResultTypeObj() != null) {
            returnType = getLLVMTypeFromTACType(instruction.getResultTypeObj());
        }
        
        // 生成函数调用指令
        llvmCode.append("  ").append(result).append(" = call ").append(returnType).append(" @").append(functionName).append("(");
        
        // 添加参数（这里需要根据实际的ARG指令来确定参数）
        // 暂时简化处理，后续需要改进
        String arg1 = instruction.getArg2();
        if (arg1 != null) {
            String argValue = getValueOperand(arg1);
            // 获取参数类型
            String argType = "i32"; // 默认为i32
            if (instruction.getArg2Type() != null) {
                argType = getLLVMTypeFromTACType(instruction.getArg2Type());
            }
            llvmCode.append(argType).append(" ").append(argValue);
        }
        
        llvmCode.append(")\n");
        
        String resultVar = instruction.getResult();
        if (!variableMap.containsKey(resultVar)) {
            variableMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store i32 ").append(result).append(", i32* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成返回指令
     */
    private void generateReturn(TACInstruction instruction) {
        if (instruction.getArg1() != null) {
            String arg1 = getValueOperand(instruction.getArg1());
            // 获取返回值类型
            String returnType = "i32"; // 默认为i32
            if (instruction.getArg1Type() != null) {
                returnType = getLLVMTypeFromTACType(instruction.getArg1Type());
            }
            llvmCode.append("  ret ").append(returnType).append(" ").append(arg1).append("\n");
        } else {
            llvmCode.append("  ret i32 0\n");
        }
    }
    
    /**
     * 生成数组访问指令
     */
    private void generateArrayAccess(TACInstruction instruction) {
        String result = getRegister();
        String array = getOperand(instruction.getArg1());
        String index = getValueOperand(instruction.getArg2());
        
        // 获取数组元素类型
        String elementType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null && instruction.getArg1Type().getKind() == TACType.TypeKind.ARRAY) {
            elementType = getLLVMTypeFromTACType(instruction.getArg1Type().getElementType());
        }
        
        llvmCode.append("  ").append(result).append(" = getelementptr inbounds ").append(elementType).append(", ").append(elementType).append("* ").append(array).append(", i32 ").append(index).append("\n");
        llvmCode.append("  ").append(result).append(" = load ").append(elementType).append(", ").append(elementType).append("* ").append(result).append(", align 4\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else {
            variableMap.put(resultVar, result);
        }
    }
    
    /**
     * 生成数组赋值指令
     */
    private void generateArrayAssign(TACInstruction instruction) {
        String array = getOperand(instruction.getArg1());
        String index = getValueOperand(instruction.getArg2());
        String value = getValueOperand(instruction.getResult());
        
        // 获取数组元素类型
        String elementType = "i32"; // 默认为i32
        if (instruction.getArg1Type() != null && instruction.getArg1Type().getKind() == TACType.TypeKind.ARRAY) {
            elementType = getLLVMTypeFromTACType(instruction.getArg1Type().getElementType());
        }
        
        String ptr = getRegister();
        llvmCode.append("  ").append(ptr).append(" = getelementptr inbounds ").append(elementType).append(", ").append(elementType).append("* ").append(array).append(", i32 ").append(index).append("\n");
        llvmCode.append("  store ").append(elementType).append(" ").append(value).append(", ").append(elementType).append("* ").append(ptr).append(", align 4\n");
    }
    
    /**
     * 生成成员访问指令
     */
    private void generateMemberAccess(TACInstruction instruction) {
        String result = getRegister();
        String object = getOperand(instruction.getArg1());
        String memberName = instruction.getArg2();
        
        // 获取对象的指针
        String objectPtr = variableMap.get(instruction.getArg1());
        if (objectPtr != null) {
            // 获取字段偏移量
            int fieldOffset = 0; // 默认为0，实际应该从类型信息中获取
            if (instruction.getResultTypeObj() != null) {
                fieldOffset = instruction.getResultTypeObj().getFieldOffset(memberName);
                if (fieldOffset < 0) {
                    fieldOffset = 0; // 如果找不到字段，使用默认偏移量
                }
            }
            llvmCode.append("  ").append(result).append(" = getelementptr inbounds %struct.").append(object).append(", %struct.").append(object).append("* ").append(objectPtr).append(", i32 0, i32 ").append(fieldOffset).append("\n");
            llvmCode.append("  ").append(result).append(" = load i32, i32* ").append(result).append(", align 4\n");
        }
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else {
            variableMap.put(resultVar, result);
        }
    }
    
    /**
     * 生成成员赋值指令
     */
    private void generateMemberAssign(TACInstruction instruction) {
        String object = getOperand(instruction.getArg1());
        String memberName = instruction.getArg2();
        String value = getValueOperand(instruction.getResult());
        
        // 获取对象的指针
        String objectPtr = variableMap.get(instruction.getArg1());
        if (objectPtr != null) {
            String ptr = getRegister();
            // 获取字段偏移量
            int fieldOffset = 0; // 默认为0，实际应该从类型信息中获取
            if (instruction.getResultTypeObj() != null) {
                fieldOffset = instruction.getResultTypeObj().getFieldOffset(memberName);
                if (fieldOffset < 0) {
                    fieldOffset = 0; // 如果找不到字段，使用默认偏移量
                }
            }
            llvmCode.append("  ").append(ptr).append(" = getelementptr inbounds %struct.").append(object).append(", %struct.").append(object).append("* ").append(objectPtr).append(", i32 0, i32 ").append(fieldOffset).append("\n");
            llvmCode.append("  store i32 ").append(value).append(", i32* ").append(ptr).append(", align 4\n");
        }
    }
    
    /**
     * 生成参数指令
     */
    private void generateParam(TACInstruction instruction) {
        // 参数指令在函数声明中处理
        // 在函数声明时，PARAM指令用于声明函数参数
        String paramName = instruction.getArg1();
        if (paramName != null) {
            // 为参数生成alloca指令
            String reg = getRegister();
            // 获取参数类型
            String llvmType = "i32"; // 默认为 i32
            if (instruction.getArg1Type() != null) {
                llvmType = getLLVMTypeFromTACType(instruction.getArg1Type());
            }
            llvmCode.append("  ").append(reg).append(" = alloca ").append(llvmType).append(", align 4\n");
            variableMap.put(paramName, reg);
        }
    }
    
    /**
     * 生成参数指令
     */
    private void generateArg(TACInstruction instruction) {
        // 参数指令在函数调用中处理
        // ARG指令用于传递函数调用的参数
        // 这些参数会在CALL指令之前收集
        // 暂时不做处理，参数会在函数调用时处理
    }
    
    /**
     * 生成分配指令
     */
    private void generateAlloc(TACInstruction instruction) {
        String result = getRegister();
        String size = instruction.getArg1();
        
        // 获取分配的类型
        String llvmType = "i32"; // 默认为 i32
        if (instruction.getResultTypeObj() != null) {
            llvmType = getLLVMTypeFromTACType(instruction.getResultTypeObj());
        }
        
        llvmCode.append("  ").append(result).append(" = alloca ").append(llvmType).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成加载指令
     */
    private void generateLoad(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        // 获取加载的类型
        String llvmType = "i32"; // 默认为 i32
        if (instruction.getArg1Type() != null) {
            llvmType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  ").append(result).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* ").append(arg1).append("\n");
        
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else {
            variableMap.put(resultVar, result);
        }
    }
    
    /**
     * 生成存储指令
     */
    private void generateStore(TACInstruction instruction) {
        String arg1 = getOperand(instruction.getArg1());
        String result = getOperand(instruction.getResult());
        
        // 获取存储的类型
        String llvmType = "i32"; // 默认为 i32
        if (instruction.getArg1Type() != null) {
            llvmType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        llvmCode.append("  store ").append(llvmType).append(" ").append(arg1).append(", ").append(llvmType).append("* ").append(result).append("\n");
    }
    
    /**
     * 生成类型转换指令
     */
    private void generateCast(TACInstruction instruction) {
        String arg1 = getValueOperand(instruction.getArg1());
        String result = getRegister();
        
        // 获取源类型和目标类型
        String fromType = "i32"; // 默认为i32
        String toType = "i32";
        
        if (instruction.getArg1Type() != null) {
            fromType = getLLVMTypeFromTACType(instruction.getArg1Type());
        }
        
        if (instruction.getResultTypeObj() != null) {
            toType = getLLVMTypeFromTACType(instruction.getResultTypeObj());
        }
        
        // 选择正确的类型转换指令
        String castOpcode;
        switch (instruction.getOpcode()) {
            case SITOFP:  // signed int to floating point
                castOpcode = "sitofp";
                break;
            case FPTOSI:  // floating point to signed int
                castOpcode = "fptosi";
                break;
            case CAST:
            default:
                // 默认bitcast
                castOpcode = "bitcast";
        }
        
        llvmCode.append("  ").append(result).append(" = ").append(castOpcode).append(" ").append(fromType).append(" ").append(arg1).append(" to ").append(toType).append("\n");
        
        // 保存结果
        String resultVar = instruction.getResult();
        if (resultVar.startsWith("t")) {
            tempVarMap.put(resultVar, result);
        } else if (!variableMap.containsKey(resultVar)) {
            tempVarMap.put(resultVar, result);
        } else {
            String ptr = variableMap.get(resultVar);
            llvmCode.append("  store ").append(toType).append(" ").append(result).append(", ").append(toType).append("* ").append(ptr).append(", align 8\n");
        }
    }
    
    /**
     * 获取 LLVM 类型代码
     */
    private String getLLVMType(String dataType) {
        if (dataType == null || dataType.equals("INT")) {
            return "i32";
        } else if (dataType.equals("FLOAT")) {
            return "double";
        } else if (dataType.equals("CHAR")) {
            return "i8";
        } else if (dataType.equals("STRING")) {
            return "i8*";
        } else if (dataType.equals("ARRAY")) {
            return "i32";  // 简化：整个数组作为一个整体分配
        } else if (dataType.equals("STRUCT")) {
            return "i32";  // 简化： struct 的尺寸待定
        }
        return "i32";  // 默认值
    }
    
    // 根据 TACType 获取 LLVM 类型
    private String getLLVMTypeFromTACType(TACType tacType) {
        if (tacType == null) {
            return "i32";  // 默认为 i32
        }
        
        switch (tacType.getKind()) {
            case INT:
                return "i32";
            case FLOAT:
                return "double";
            case CHAR:
                return "i8";
            case STRING:
                return "i8*";
            case ARRAY:
                // 数组类型返回指向元素类型的指针
                return getLLVMTypeFromTACType(tacType.getElementType()) + "*";
            case STRUCT:
                // 结构体类型处理
                if (tacType.getTypeName() != null && !tacType.getTypeName().isEmpty()) {
                    return "%struct." + tacType.getTypeName();
                }
                return "i8*";  // 默认使用指针
            case POINTER:
                // 指针类型
                return getLLVMTypeFromTACType(tacType.getElementType()) + "*";
            case ADDRESS:
                // 地址类型
                return getLLVMTypeFromTACType(tacType.getElementType()) + "*";
            default:
                return "i32";  // 默认为 i32
        }
    }
    
    /**
     * 获取寄存器名称
     */
    private String getRegister() {
        return "%" + (++registerCounter);
    }
    
    /**
     * 获取操作数
     */
    private String getOperand(String operand) {
        if (operand == null) {
            return "0";
        }
        
        // 如果是数字，直接返回
        if (operand.matches("-?\\d+(\\.\\d+)?")) {
            return operand;
        }
        
        // 如果是临时变量，查找映射，没有则创建
        if (operand.startsWith("t")) {
            if (!tempVarMap.containsKey(operand)) {
                String reg = getRegister();
                tempVarMap.put(operand, reg);
            }
            return tempVarMap.get(operand);
        }
        
        // 如果是变量，查找对应的寄存器
        if (variableMap.containsKey(operand)) {
            return variableMap.get(operand);
        }
        
        // 默认返回操作数
        return operand;
    }
    
    /**
     * 获取作为值的操作数（若需要则加载）
     */
    private String getValueOperand(String operand) {
        if (operand == null) {
            return "0";
        }
        
        // 如果是数字，直接返回
        if (operand.matches("-?\\d+(\\.\\d+)?")) {
            return operand;
        }
        
        // 如果是临时变量，查找映射，没有则创建
        if (operand.startsWith("t")) {
            if (!tempVarMap.containsKey(operand)) {
                String reg = getRegister();
                tempVarMap.put(operand, reg);
            }
            return tempVarMap.get(operand);
        }
        
        // 如果是已分配的变量，要加载
        if (variableMap.containsKey(operand)) {
            String ptr = variableMap.get(operand);
            String tempReg = getRegister();
            // 🔥 关键修复：从类型映射获取正确的变量类型
            String llvmType = variableTypeMap.getOrDefault(operand, "i32");
            llvmCode.append("  ").append(tempReg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* ").append(ptr).append(", align 4\n");
            return tempReg;
        }
        
        // 默认返回操作数
        return operand;
    }
}
