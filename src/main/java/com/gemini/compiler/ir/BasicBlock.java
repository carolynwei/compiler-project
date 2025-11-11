package com.gemini.compiler.ir;

import java.util.*;
import java.util.Set;

/**
 * 基本块
 * 
 * 表示一个基本块，包含一系列顺序执行的指令
 */
public class BasicBlock {
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