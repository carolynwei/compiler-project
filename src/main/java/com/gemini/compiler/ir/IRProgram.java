package com.gemini.compiler.ir;

import java.util.*;

/**
 * 三地址代码 (TAC) 指令类型
 */
enum TACOpcode {
    // 算术运算
    ADD, SUB, MUL, DIV, MOD,
    
    // 比较运算
    EQ, NE, LT, GT, LE, GE,
    
    // 逻辑运算
    AND, OR, NOT,
    
    // 赋值
    ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN,
    
    // 自增自减
    INCREMENT, DECREMENT,
    
    // 跳转
    GOTO, IF_TRUE, IF_FALSE, IF_ZERO, IF_NONZERO,
    
    // 标签
    LABEL,
    
    // 函数调用
    CALL, RETURN,
    
    // 数组操作
    ARRAY_ACCESS, ARRAY_ASSIGN,
    
    // 结构体操作
    MEMBER_ACCESS, MEMBER_ASSIGN,
    
    // 其他
    PARAM, ARG, ALLOC, LOAD, STORE
}

/**
 * 三地址代码指令
 * 
 * 使用四元式表示：(操作码, 操作数1, 操作数2, 结果)
 */
class TACInstruction {
    private TACOpcode opcode;
    private String arg1;
    private String arg2;
    private String result;
    private int line;
    
    public TACInstruction(TACOpcode opcode, String arg1, String arg2, String result) {
        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
        this.line = -1;
    }
    
    public TACInstruction(TACOpcode opcode, String arg1, String arg2, String result, int line) {
        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
        this.line = line;
    }
    
    // Getters
    public TACOpcode getOpcode() { return opcode; }
    public String getArg1() { return arg1; }
    public String getArg2() { return arg2; }
    public String getResult() { return result; }
    public int getLine() { return line; }
    
    public void setLine(int line) { this.line = line; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        switch (opcode) {
            case ADD:
                sb.append(result).append(" = ").append(arg1).append(" + ").append(arg2);
                break;
            case SUB:
                sb.append(result).append(" = ").append(arg1).append(" - ").append(arg2);
                break;
            case MUL:
                sb.append(result).append(" = ").append(arg1).append(" * ").append(arg2);
                break;
            case DIV:
                sb.append(result).append(" = ").append(arg1).append(" / ").append(arg2);
                break;
            case MOD:
                sb.append(result).append(" = ").append(arg1).append(" % ").append(arg2);
                break;
            case EQ:
                sb.append(result).append(" = ").append(arg1).append(" == ").append(arg2);
                break;
            case NE:
                sb.append(result).append(" = ").append(arg1).append(" != ").append(arg2);
                break;
            case LT:
                sb.append(result).append(" = ").append(arg1).append(" < ").append(arg2);
                break;
            case GT:
                sb.append(result).append(" = ").append(arg1).append(" > ").append(arg2);
                break;
            case LE:
                sb.append(result).append(" = ").append(arg1).append(" <= ").append(arg2);
                break;
            case GE:
                sb.append(result).append(" = ").append(arg1).append(" >= ").append(arg2);
                break;
            case AND:
                sb.append(result).append(" = ").append(arg1).append(" && ").append(arg2);
                break;
            case OR:
                sb.append(result).append(" = ").append(arg1).append(" || ").append(arg2);
                break;
            case NOT:
                sb.append(result).append(" = !").append(arg1);
                break;
            case ASSIGN:
                sb.append(result).append(" = ").append(arg1);
                break;
            case PLUS_ASSIGN:
                sb.append(result).append(" += ").append(arg1);
                break;
            case MINUS_ASSIGN:
                sb.append(result).append(" -= ").append(arg1);
                break;
            case MUL_ASSIGN:
                sb.append(result).append(" *= ").append(arg1);
                break;
            case DIV_ASSIGN:
                sb.append(result).append(" /= ").append(arg1);
                break;
            case MOD_ASSIGN:
                sb.append(result).append(" %= ").append(arg1);
                break;
            case INCREMENT:
                sb.append(result).append("++");
                break;
            case DECREMENT:
                sb.append(result).append("--");
                break;
            case GOTO:
                sb.append("goto ").append(result);
                break;
            case IF_TRUE:
                sb.append("if ").append(arg1).append(" goto ").append(result);
                break;
            case IF_FALSE:
                sb.append("if !").append(arg1).append(" goto ").append(result);
                break;
            case IF_ZERO:
                sb.append("if ").append(arg1).append(" == 0 goto ").append(result);
                break;
            case IF_NONZERO:
                sb.append("if ").append(arg1).append(" != 0 goto ").append(result);
                break;
            case LABEL:
                sb.append(result).append(":");
                break;
            case CALL:
                sb.append(result).append(" = call ").append(arg1);
                break;
            case RETURN:
                if (arg1 != null) {
                    sb.append("return ").append(arg1);
                } else {
                    sb.append("return");
                }
                break;
            case ARRAY_ACCESS:
                sb.append(result).append(" = ").append(arg1).append("[").append(arg2).append("]");
                break;
            case ARRAY_ASSIGN:
                sb.append(arg1).append("[").append(arg2).append("] = ").append(result);
                break;
            case MEMBER_ACCESS:
                sb.append(result).append(" = ").append(arg1).append(".").append(arg2);
                break;
            case MEMBER_ASSIGN:
                sb.append(arg1).append(".").append(arg2).append(" = ").append(result);
                break;
            case PARAM:
                sb.append("param ").append(arg1);
                break;
            case ARG:
                sb.append("arg ").append(arg1);
                break;
            case ALLOC:
                sb.append(result).append(" = alloc ").append(arg1);
                break;
            case LOAD:
                sb.append(result).append(" = load ").append(arg1);
                break;
            case STORE:
                sb.append("store ").append(arg1).append(" -> ").append(result);
                break;
            default:
                sb.append(opcode.toString());
        }
        
        return sb.toString();
    }
}

/**
 * 基本块
 * 
 * 表示一个基本块，包含一系列顺序执行的指令
 */
class BasicBlock {
    private String label;
    private List<TACInstruction> instructions;
    private Set<String> predecessors;
    private Set<String> successors;
    
    public BasicBlock(String label) {
        this.label = label;
        this.instructions = new ArrayList<>();
        this.predecessors = new HashSet<>();
        this.successors = new HashSet<>();
    }
    
    public void addInstruction(TACInstruction instruction) {
        instructions.add(instruction);
    }
    
    public void addPredecessor(String predLabel) {
        predecessors.add(predLabel);
    }
    
    public void addSuccessor(String succLabel) {
        successors.add(succLabel);
    }
    
    // Getters
    public String getLabel() { return label; }
    public List<TACInstruction> getInstructions() { return instructions; }
    public Set<String> getPredecessors() { return predecessors; }
    public Set<String> getSuccessors() { return successors; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(":\n");
        for (TACInstruction instruction : instructions) {
            sb.append("  ").append(instruction).append("\n");
        }
        return sb.toString();
    }
}

/**
 * 中间代码程序
 * 
 * 包含整个程序的中间代码表示
 */
class IRProgram {
    private List<TACInstruction> instructions;
    private Map<String, BasicBlock> basicBlocks;
    private Map<String, String> labels;
    private int tempVarCounter;
    private int labelCounter;
    
    public IRProgram() {
        this.instructions = new ArrayList<>();
        this.basicBlocks = new HashMap<>();
        this.labels = new HashMap<>();
        this.tempVarCounter = 0;
        this.labelCounter = 0;
    }
    
    public void addInstruction(TACInstruction instruction) {
        instructions.add(instruction);
    }
    
    public void addBasicBlock(BasicBlock block) {
        basicBlocks.put(block.getLabel(), block);
    }
    
    public String generateTempVar() {
        return "t" + (++tempVarCounter);
    }
    
    public String generateLabel() {
        return "L" + (++labelCounter);
    }
    
    public String generateLabel(String prefix) {
        return prefix + (++labelCounter);
    }
    
    // Getters
    public List<TACInstruction> getInstructions() { return instructions; }
    public Map<String, BasicBlock> getBasicBlocks() { return basicBlocks; }
    public Map<String, String> getLabels() { return labels; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 中间代码 (TAC) ===\n");
        
        for (int i = 0; i < instructions.size(); i++) {
            TACInstruction instruction = instructions.get(i);
            sb.append(String.format("%3d: %s\n", i + 1, instruction));
        }
        
        return sb.toString();
    }
    
    /**
     * 显示基本块
     */
    public void displayBasicBlocks() {
        System.out.println("=== 基本块 ===");
        for (BasicBlock block : basicBlocks.values()) {
            System.out.println(block);
        }
    }
}
