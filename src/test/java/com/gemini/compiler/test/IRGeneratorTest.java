package com.gemini.compiler.test;

import com.gemini.compiler.ir.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 中间代码生成器测试类
 */
public class IRGeneratorTest {
    
    private IRProgram irProgram;
    
    @BeforeEach
    public void setUp() {
        irProgram = new IRProgram();
    }
    
    @Test
    public void testTACInstructionCreation() {
        // 测试TAC指令创建
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        
        assertEquals(TACOpcode.ADD, instruction.getOpcode(), "操作码应该匹配");
        assertEquals("t1", instruction.getArg1(), "参数1应该匹配");
        assertEquals("t2", instruction.getArg2(), "参数2应该匹配");
        assertEquals("t3", instruction.getResult(), "结果应该匹配");
    }
    
    @Test
    public void testTACInstructionToString() {
        // 测试TAC指令字符串表示
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        String str = instruction.toString();
        
        assertTrue(str.contains("t3 = t1 + t2"), "字符串表示应该包含加法运算");
    }
    
    @Test
    public void testIRProgramManagement() {
        // 测试IR程序管理
        TACInstruction instruction1 = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        TACInstruction instruction2 = new TACInstruction(TACOpcode.SUB, "t3", "t4", "t5");
        
        irProgram.addInstruction(instruction1);
        irProgram.addInstruction(instruction2);
        
        assertEquals(2, irProgram.getInstructions().size(), "指令数量应该是2");
    }
    
    @Test
    public void testTempVarGeneration() {
        // 测试临时变量生成
        String temp1 = irProgram.generateTempVar();
        String temp2 = irProgram.generateTempVar();
        
        assertEquals("t1", temp1, "第一个临时变量应该是t1");
        assertEquals("t2", temp2, "第二个临时变量应该是t2");
    }
    
    @Test
    public void testLabelGeneration() {
        // 测试标签生成
        String label1 = irProgram.generateLabel();
        String label2 = irProgram.generateLabel("test");
        
        assertEquals("L1", label1, "第一个标签应该是L1");
        assertEquals("test2", label2, "第二个标签应该是test2");
    }
    
    @Test
    public void testBasicBlockCreation() {
        // 测试基本块创建
        BasicBlock block = new BasicBlock("L1");
        
        assertEquals("L1", block.getLabel(), "标签应该匹配");
        assertTrue(block.getInstructions().isEmpty(), "指令列表应该为空");
        assertTrue(block.getPredecessors().isEmpty(), "前驱列表应该为空");
        assertTrue(block.getSuccessors().isEmpty(), "后继列表应该为空");
    }
    
    @Test
    public void testBasicBlockManagement() {
        // 测试基本块管理
        BasicBlock block = new BasicBlock("L1");
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        
        block.addInstruction(instruction);
        block.addPredecessor("L0");
        block.addSuccessor("L2");
        
        assertEquals(1, block.getInstructions().size(), "指令数量应该是1");
        assertTrue(block.getPredecessors().contains("L0"), "应该包含前驱L0");
        assertTrue(block.getSuccessors().contains("L2"), "应该包含后继L2");
    }
    
    @Test
    public void testBasicBlockToString() {
        // 测试基本块字符串表示
        BasicBlock block = new BasicBlock("L1");
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        
        block.addInstruction(instruction);
        String str = block.toString();
        
        assertTrue(str.contains("L1:"), "应该包含标签");
        assertTrue(str.contains("t3 = t1 + t2"), "应该包含指令");
    }
    
    @Test
    public void testIRProgramToString() {
        // 测试IR程序字符串表示
        TACInstruction instruction1 = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        TACInstruction instruction2 = new TACInstruction(TACOpcode.SUB, "t3", "t4", "t5");
        
        irProgram.addInstruction(instruction1);
        irProgram.addInstruction(instruction2);
        
        String str = irProgram.toString();
        
        assertTrue(str.contains("=== 中间代码 (TAC) ==="), "应该包含标题");
        assertTrue(str.contains("1: t3 = t1 + t2"), "应该包含第一条指令");
        assertTrue(str.contains("2: t5 = t3 - t4"), "应该包含第二条指令");
    }
    
    @Test
    public void testBasicBlockDisplay() {
        // 测试基本块显示
        BasicBlock block = new BasicBlock("L1");
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3");
        
        block.addInstruction(instruction);
        irProgram.addBasicBlock(block);
        
        // 显示基本块不应该抛出异常
        assertDoesNotThrow(() -> {
            irProgram.displayBasicBlocks();
        });
    }
    
    @Test
    public void testDifferentTACOpcodes() {
        // 测试不同的TAC操作码
        TACInstruction[] instructions = {
            new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.SUB, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.MUL, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.DIV, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.MOD, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.EQ, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.NE, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.LT, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.GT, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.LE, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.GE, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.AND, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.OR, "t1", "t2", "t3"),
            new TACInstruction(TACOpcode.NOT, "t1", null, "t3"),
            new TACInstruction(TACOpcode.ASSIGN, "t1", null, "t3"),
            new TACInstruction(TACOpcode.GOTO, null, null, "L1"),
            new TACInstruction(TACOpcode.LABEL, null, null, "L1"),
            new TACInstruction(TACOpcode.CALL, "func", null, "t3"),
            new TACInstruction(TACOpcode.RETURN, "t1", null, null)
        };
        
        for (TACInstruction instruction : instructions) {
            irProgram.addInstruction(instruction);
        }
        
        assertEquals(instructions.length, irProgram.getInstructions().size(), "指令数量应该匹配");
    }
    
    @Test
    public void testInstructionWithLineNumbers() {
        // 测试带行号的指令
        TACInstruction instruction = new TACInstruction(TACOpcode.ADD, "t1", "t2", "t3", 10);
        
        assertEquals(10, instruction.getLine(), "行号应该匹配");
        
        instruction.setLine(20);
        assertEquals(20, instruction.getLine(), "行号应该更新");
    }
}
