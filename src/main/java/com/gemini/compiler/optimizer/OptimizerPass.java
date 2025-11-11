package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import java.util.List;

/**
 * 单个优化传递的统一接口。
 * <p>
 * 每个 Pass 接受一组 TAC 指令并返回优化后的指令列表。
 */
public interface OptimizerPass {

    /**
     * 执行优化传递。
     *
     * @param instructions 输入的指令列表（同一函数或整个程序的顺序指令）
     * @return 优化后的指令列表
     */
    List<TACInstruction> run(List<TACInstruction> instructions);
}

