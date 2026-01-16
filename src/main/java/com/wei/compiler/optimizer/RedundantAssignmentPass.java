package com.wei.compiler.optimizer;

import com.wei.compiler.ir.TACInstruction;
import com.wei.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.List;

/**
 * 冗余赋值消除（Redundant Assignment Elimination）
 *
 * 消除对同一变量的连续赋值，只保留最后一次赋值。
 * 例如：
 *   result = a
 *   result = b  // 前一个赋值是冗余的，因为result被立即覆盖
 *   result = c  // 前面的赋值都是冗余的
 *
 * 这在多个表达式赋值到同一变量时很常见。
 */
public final class RedundantAssignmentPass implements OptimizerPass {

    private final boolean debug;

    public RedundantAssignmentPass() {
        this(false);
    }

    public RedundantAssignmentPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        List<TACInstruction> optimized = new ArrayList<>(instructions.size());
        int removedCount = 0;
        
        for (int i = 0; i < instructions.size(); i++) {
            TACInstruction current = instructions.get(i);
            
            // 检查是否是简单赋值指令
            if (current.getOpcode() == TACOpcode.ASSIGN && current.getResult() != null) {
                String varName = current.getResult();
                
                // 查找下一个对该变量的定义
                int nextDefIndex = findNextDefinition(instructions, i + 1, varName);
                
                // 如果下一个定义存在且在LABEL之前，则当前赋值是冗余的
                if (nextDefIndex != -1 && !hasLabelBetween(instructions, i, nextDefIndex)) {
                    if (debug) {
                        System.out.println("移除冗余赋值: " + current);
                    }
                    removedCount++;
                    continue;  // 跳过当前指令
                }
            }
            
            optimized.add(current);
        }
        
        if (debug) {
            System.out.println("冗余赋值消除Pass完成: 删除了" + removedCount + "个冗余赋值");
        }
        
        return optimized;
    }

    /**
     * 查找下一个对变量varName进行定义的指令位置
     * @return 指令索引，或-1如果没有找到
     */
    private int findNextDefinition(List<TACInstruction> instructions, int startIndex, String varName) {
        for (int i = startIndex; i < instructions.size(); i++) {
            TACInstruction instr = instructions.get(i);
            
            // 遇到LABEL，停止搜索
            if (instr.getOpcode() == TACOpcode.LABEL) {
                return -1;
            }
            
            // 检查是否定义了该变量
            if (definesVariable(instr, varName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 检查两个指令之间是否有LABEL
     */
    private boolean hasLabelBetween(List<TACInstruction> instructions, int fromIndex, int toIndex) {
        for (int i = fromIndex + 1; i < toIndex; i++) {
            if (instructions.get(i).getOpcode() == TACOpcode.LABEL) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查指令是否定义了指定的变量
     */
    private boolean definesVariable(TACInstruction instruction, String varName) {
        String result = instruction.getResult();
        if (result == null) {
            return false;
        }
        
        // 检查opcode是否会定义变量
        switch (instruction.getOpcode()) {
            case ASSIGN:
            case CAST:
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case EQ:
            case NE:
            case LT:
            case GT:
            case LE:
            case GE:
            case AND:
            case OR:
            case NOT:
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case MUL_ASSIGN:
            case DIV_ASSIGN:
            case MOD_ASSIGN:
            case INCREMENT:
            case DECREMENT:
            case ARRAY_ACCESS:
            case MEMBER_ACCESS:
            case ALLOC:
            case LOAD:
            case CALL:
                return result.equals(varName);
            default:
                return false;
        }
    }
}
