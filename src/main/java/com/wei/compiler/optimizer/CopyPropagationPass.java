package com.wei.compiler.optimizer;

import com.wei.compiler.ir.TACInstruction;
import com.wei.compiler.ir.TACOpcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 副本传播优化（Copy Propagation）
 * 
 * 追踪形如 x = y 的赋值，并将所有 x 的使用替换为 y
 * 这样可以减少不必要的临时变量，为死代码消除提供更多机会
 * 
 * 例如：
 * t1 = 5
 * result = t1  // 可以替换为 result = 5
 * use(result)  // 实际上使用的是 5
 */
public final class CopyPropagationPass implements OptimizerPass {

    private final boolean debug;

    public CopyPropagationPass() {
        this(false);
    }

    public CopyPropagationPass(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<TACInstruction> run(List<TACInstruction> instructions) {
        Map<String, String> copyMap = new HashMap<>();
        List<TACInstruction> optimized = new ArrayList<>(instructions.size());

        for (TACInstruction instruction : instructions) {
            TACOpcode opcode = instruction.getOpcode();

            // 标签处理：清除之前的副本映射
            if (opcode == TACOpcode.LABEL) {
                copyMap.clear();
                optimized.add(instruction);
                continue;
            }

            // 副本传播：替换指令中的操作数
            String arg1 = propagateCopy(instruction.getArg1(), copyMap);
            String arg2 = propagateCopy(instruction.getArg2(), copyMap);
            String metadata = propagateCopy(instruction.getMetadata(), copyMap);
            String result = instruction.getResult();

            TACInstruction propagated = OptimizerUtils.cloneInstruction(
                instruction, opcode, arg1, arg2, result
            );
            propagated.setMetadata(metadata);
            optimized.add(propagated);

            // 更新副本映射
            if (opcode == TACOpcode.ASSIGN && result != null && arg1 != null) {
                // 只追踪简单的副本赋值 (x = y)，不追踪涉及计算的赋值
                if (!OptimizerUtils.isComplexExpression(arg1)) {
                    copyMap.put(result, arg1);
                    if (debug) {
                        System.out.println("副本传播: " + result + " -> " + arg1);
                    }
                } else {
                    copyMap.remove(result);
                }
            } else if (result != null) {
                // 其他类型的赋值会破坏副本关系
                copyMap.remove(result);
            }
        }

        return optimized;
    }

    /**
     * 通过副本映射进行递归传播
     */
    private String propagateCopy(String operand, Map<String, String> copyMap) {
        if (operand == null) {
            return null;
        }

        String current = operand;
        Set<String> visited = new HashSet<>();

        // 防止循环引用（虽然正常情况不会发生）
        while (copyMap.containsKey(current) && visited.add(current)) {
            String next = copyMap.get(current);
            current = next;
        }

        return current;
    }
}
