package com.gemini.compiler.ir;

import java.util.*;

/**
 * 中间代码程序
 * 
 * 包含整个程序的中间代码表示
 */
public class IRProgram {
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