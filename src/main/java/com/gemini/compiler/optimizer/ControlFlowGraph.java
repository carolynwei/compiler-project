package com.gemini.compiler.optimizer;

import com.gemini.compiler.ir.TACInstruction;
import com.gemini.compiler.ir.TACOpcode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的指令级控制流图。
 */
final class ControlFlowGraph {

    private final List<List<Integer>> successors;
    private final List<List<Integer>> predecessors;

    private ControlFlowGraph(List<List<Integer>> successors, List<List<Integer>> predecessors) {
        this.successors = successors;
        this.predecessors = predecessors;
    }

    List<Integer> successors(int index) {
        return successors.get(index);
    }

    List<Integer> predecessors(int index) {
        return predecessors.get(index);
    }

    int size() {
        return successors.size();
    }

    static ControlFlowGraph fromInstructions(List<TACInstruction> instructions) {
        int n = instructions.size();
        Map<String, Integer> labelToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            TACInstruction instr = instructions.get(i);
            if (instr.getOpcode() == TACOpcode.LABEL && instr.getResult() != null) {
                labelToIndex.put(instr.getResult(), i);
            }
        }

        List<List<Integer>> successors = new ArrayList<>(n);
        List<List<Integer>> predecessors = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            successors.add(new ArrayList<>());
            predecessors.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            TACInstruction instr = instructions.get(i);
            TACOpcode opcode = instr.getOpcode();

            switch (opcode) {
                case GOTO: {
                    Integer target = labelToIndex.get(instr.getResult());
                    if (target != null) {
                        addEdge(successors, predecessors, i, target);
                    }
                    break;
                }
                case IF_TRUE:
                case IF_FALSE:
                case IF_ZERO:
                case IF_NONZERO: {
                    Integer target = labelToIndex.get(instr.getResult());
                    if (target != null) {
                        addEdge(successors, predecessors, i, target);
                    }
                    if (i + 1 < n) {
                        addEdge(successors, predecessors, i, i + 1);
                    }
                    break;
                }
                case RETURN:
                    break;
                default:
                    if (i + 1 < n) {
                        addEdge(successors, predecessors, i, i + 1);
                    }
                    break;
            }
        }

        return new ControlFlowGraph(successors, predecessors);
    }

    boolean[] computeReachable() {
        int n = size();
        boolean[] reachable = new boolean[n];
        if (n == 0) {
            return reachable;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        reachable[0] = true;

        while (!stack.isEmpty()) {
            int index = stack.pop();
            for (int succ : successors(index)) {
                if (!reachable[succ]) {
                    reachable[succ] = true;
                    stack.push(succ);
                }
            }
        }
        return reachable;
    }

    private static void addEdge(
        List<List<Integer>> successors,
        List<List<Integer>> predecessors,
        int from,
        int to
    ) {
        List<Integer> succ = successors.get(from);
        if (!succ.contains(to)) {
            succ.add(to);
            predecessors.get(to).add(from);
        }
    }
}

