package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.IRProgram;
import com.gemini.compiler.ir.TACInstruction;
import java.util.ArrayList;
import java.util.List;

/**
 * 顶层优化调度器。负责按顺序执行各个优化传递。
 */
public class IROptimizer {

    private boolean debugMode;
    private boolean enabled;

    public IROptimizer() {
        this(true);
    }

    public IROptimizer(boolean enabled) {
        this.enabled = enabled;
        this.debugMode = false;
    }

    public IRProgram optimize(IRProgram irProgram) {
        if (!enabled) {
            return irProgram;
        }

        System.out.println("\n--- 阶段五：中间代码优化 ---");

        List<TACInstruction> instructions = new ArrayList<>(irProgram.getInstructions());
        for (OptimizerPass pass : buildPassPipeline()) {
            instructions = pass.run(instructions);
        }

        IRProgram optimizedProgram = new IRProgram();
        instructions.forEach(optimizedProgram::addInstruction);

        if (debugMode) {
            System.out.println("优化前指令数: " + irProgram.getInstructions().size());
            System.out.println("优化后指令数: " + optimizedProgram.getInstructions().size());
        }

        System.out.println("中间代码优化完成");
        return optimizedProgram;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private List<OptimizerPass> buildPassPipeline() {
        List<OptimizerPass> passes = new ArrayList<>();
        passes.add(new ConstantPropagationPass(debugMode));
        passes.add(new LoopInvariantHoistPass(debugMode));
        passes.add(new DeadCodeEliminationPass(debugMode));
        passes.add(new Mem2RegPass());
        passes.add(new CommonSubexpressionEliminationPass(debugMode));
        return passes;
    }
}
