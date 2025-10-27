package com.gemini.compiler.codegen;

import com.gemini.compiler.ir.*;
import com.gemini.compiler.semantic.*;
import java.util.*;

/**
 * LLVM IR 代码生成器
 * 
 * 将三地址代码 (TAC) 转换为 LLVM IR 代码
 */
public class CodeGenerator {
    
    private boolean debugMode;
    private StringBuilder llvmCode;
    private Map<String, String> variableMap;
    private Map<String, String> functionMap;
    private int registerCounter;
    private int labelCounter;
    
    public CodeGenerator() {
        this.debugMode = false;
        this.llvmCode = new StringBuilder();
        this.variableMap = new HashMap<>();
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
        llvmCode.append("; Gemini-C 编译器生成的 LLVM IR 代码\n");
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
        
        // 生成函数声明
        llvmCode.append("define i32 @").append(functionName).append("() {\n");
        llvmCode.append("entry:\n");
        
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
        
        // 生成主函数体
        generateMainFunctionBody(irProgram);
        
        llvmCode.append("  ret i32 0\n");
        llvmCode.append("}\n");
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
        }
    }
    
    /**
     * 生成加法指令
     */
    private void generateAdd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成减法指令
     */
    private void generateSub(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = sub i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成乘法指令
     */
    private void generateMul(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = mul i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成除法指令
     */
    private void generateDiv(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = sdiv i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成取模指令
     */
    private void generateMod(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = srem i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成相等比较指令
     */
    private void generateEq(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp eq i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成不等比较指令
     */
    private void generateNe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp ne i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成小于比较指令
     */
    private void generateLt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp slt i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成大于比较指令
     */
    private void generateGt(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp sgt i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成小于等于比较指令
     */
    private void generateLe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp sle i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成大于等于比较指令
     */
    private void generateGe(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = icmp sge i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成逻辑与指令
     */
    private void generateAnd(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = and i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成逻辑或指令
     */
    private void generateOr(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = or i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成逻辑非指令
     */
    private void generateNot(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        llvmCode.append("  ").append(result).append(" = xor i32 ").append(arg1).append(", 1\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成赋值指令
     */
    private void generateAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", 0\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成复合赋值指令
     */
    private void generatePlusAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    private void generateMinusAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = sub i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    private void generateMulAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = mul i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    private void generateDivAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = sdiv i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    private void generateModAssign(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        String arg2 = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = srem i32 ").append(arg1).append(", ").append(arg2).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成自增指令
     */
    private void generateIncrement(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        llvmCode.append("  ").append(result).append(" = add i32 ").append(arg1).append(", 1\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成自减指令
     */
    private void generateDecrement(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        llvmCode.append("  ").append(result).append(" = sub i32 ").append(arg1).append(", 1\n");
        
        variableMap.put(instruction.getResult(), result);
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
        String arg1 = getOperand(instruction.getArg1());
        llvmCode.append("  br i1 ").append(arg1).append(", label %").append(instruction.getResult()).append("_else, label %").append(instruction.getResult()).append("\n");
    }
    
    private void generateIfNonzero(TACInstruction instruction) {
        String arg1 = getOperand(instruction.getArg1());
        llvmCode.append("  br i1 ").append(arg1).append(", label %").append(instruction.getResult()).append(", label %").append(instruction.getResult()).append("_else\n");
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
        
        llvmCode.append("  ").append(result).append(" = call i32 @").append(functionName).append("()\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成返回指令
     */
    private void generateReturn(TACInstruction instruction) {
        if (instruction.getArg1() != null) {
            String arg1 = getOperand(instruction.getArg1());
            llvmCode.append("  ret i32 ").append(arg1).append("\n");
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
        String index = getOperand(instruction.getArg2());
        
        llvmCode.append("  ").append(result).append(" = getelementptr i32, i32* ").append(array).append(", i32 ").append(index).append("\n");
        llvmCode.append("  ").append(result).append(" = load i32, i32* ").append(result).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成数组赋值指令
     */
    private void generateArrayAssign(TACInstruction instruction) {
        String array = getOperand(instruction.getArg1());
        String index = getOperand(instruction.getArg2());
        String value = getOperand(instruction.getResult());
        
        String ptr = getRegister();
        llvmCode.append("  ").append(ptr).append(" = getelementptr i32, i32* ").append(array).append(", i32 ").append(index).append("\n");
        llvmCode.append("  store i32 ").append(value).append(", i32* ").append(ptr).append("\n");
    }
    
    /**
     * 生成成员访问指令
     */
    private void generateMemberAccess(TACInstruction instruction) {
        String result = getRegister();
        String object = getOperand(instruction.getArg1());
        String memberName = instruction.getArg2();
        
        llvmCode.append("  ").append(result).append(" = getelementptr inbounds %struct.").append(object).append(", %struct.").append(object).append("* ").append(object).append(", i32 0, i32 0\n");
        llvmCode.append("  ").append(result).append(" = load i32, i32* ").append(result).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成成员赋值指令
     */
    private void generateMemberAssign(TACInstruction instruction) {
        String object = getOperand(instruction.getArg1());
        String memberName = instruction.getArg2();
        String value = getOperand(instruction.getResult());
        
        String ptr = getRegister();
        llvmCode.append("  ").append(ptr).append(" = getelementptr inbounds %struct.").append(object).append(", %struct.").append(object).append("* ").append(object).append(", i32 0, i32 0\n");
        llvmCode.append("  store i32 ").append(value).append(", i32* ").append(ptr).append("\n");
    }
    
    /**
     * 生成参数指令
     */
    private void generateParam(TACInstruction instruction) {
        // 参数指令在函数调用中处理
    }
    
    /**
     * 生成参数指令
     */
    private void generateArg(TACInstruction instruction) {
        // 参数指令在函数调用中处理
    }
    
    /**
     * 生成分配指令
     */
    private void generateAlloc(TACInstruction instruction) {
        String result = getRegister();
        String size = instruction.getArg1();
        
        llvmCode.append("  ").append(result).append(" = alloca i32\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成加载指令
     */
    private void generateLoad(TACInstruction instruction) {
        String result = getRegister();
        String arg1 = getOperand(instruction.getArg1());
        
        llvmCode.append("  ").append(result).append(" = load i32, i32* ").append(arg1).append("\n");
        
        variableMap.put(instruction.getResult(), result);
    }
    
    /**
     * 生成存储指令
     */
    private void generateStore(TACInstruction instruction) {
        String arg1 = getOperand(instruction.getArg1());
        String result = getOperand(instruction.getResult());
        
        llvmCode.append("  store i32 ").append(arg1).append(", i32* ").append(result).append("\n");
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
        if (operand.matches("-?\\d+")) {
            return operand;
        }
        
        // 如果是变量，查找对应的寄存器
        if (variableMap.containsKey(operand)) {
            return variableMap.get(operand);
        }
        
        // 如果是临时变量，返回寄存器名称
        if (operand.startsWith("t")) {
            return "%" + operand;
        }
        
        // 默认返回操作数
        return operand;
    }
}
