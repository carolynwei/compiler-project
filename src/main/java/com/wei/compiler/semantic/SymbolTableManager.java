package com.wei.compiler.semantic;

import java.util.*;

import com.wei.compiler.semantic.SemanticError;
import com.wei.compiler.semantic.SemanticErrorType;
import com.wei.compiler.semantic.SymbolEntry;
import com.wei.compiler.semantic.SymbolType;

/**
 * 符号表管理器
 * * 使用栈式结构管理作用域，支持动态展现符号表内容。
 * 作用域级别：0 代表全局作用域 (Global Scope)，1+ 代表局部作用域。
 */
public class SymbolTableManager {
    
    // 符号表栈，每个元素代表一个作用域
    // 栈底 (索引 0) 始终是全局作用域
    private final Stack<Map<String, SymbolEntry>> symbolTableStack;
    
    // 当前作用域级别 (0-based)
    private int currentScopeLevel;
    
    // 调试开关
    private boolean debugMode;
    
    // 错误收集器
    private final List<SemanticError> errors;
    
    public SymbolTableManager() {
        this.symbolTableStack = new Stack<>();
        this.currentScopeLevel = -1; // 初始化为 -1，第一次调用 enterScope() 后变为 0 (全局)
        this.debugMode = false;
        this.errors = new ArrayList<>();
        
        // 初始化全局作用域 (Scope Level 0)
        enterScope();
    }
    
    /**
     * 进入新的作用域
     */
    public void enterScope() {
        Map<String, SymbolEntry> newScope = new HashMap<>();
        symbolTableStack.push(newScope);
        currentScopeLevel++;
        
        if (debugMode) {
            System.out.println("--- 进入作用域 " + currentScopeLevel + " ---");
        }
    }
    
    /**
     * 退出当前作用域
     */
    public void exitScope() {
        // 必须保留全局作用域 (Level 0, 栈大小 > 1)
        if (symbolTableStack.size() > 1) { 
            Map<String, SymbolEntry> currentScope = symbolTableStack.pop();
            currentScopeLevel--;
            
            if (debugMode) {
                System.out.println("--- 退出作用域 " + (currentScopeLevel + 1) + " ---");
                System.out.println("当前作用域级别: " + currentScopeLevel);
                // 此时 peek() 是上一个作用域
                displayCurrentScope(); 
            }
        } else {
             // 错误：试图退出全局作用域
             if (debugMode) {
                System.err.println("警告: 试图退出全局作用域 (Level 0)。操作被阻止。");
             }
        }
    }
    
    /**
     * 插入符号到当前作用域
     * **注意：** 语义分析器在调用此方法前，应先通过 isDefinedInCurrentScope 检查重定义
     * 否则，此方法将自动记录重定义错误。
     */
    public boolean insertSymbol(SymbolEntry entry) {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        String name = entry.getName();
        
        // 检查重定义错误 (SymbolEntry 应该包含 Line/Column 信息)
        if (currentScope.containsKey(name)) {
            // 这里我们假设 SymbolEntry 有获取行号和列号的方法，否则错误信息不完整
            int line = 0; // 假设默认值
            int column = 0; // 假设默认值
            // 如果 SymbolEntry 没有 Line/Column 信息，需要从 AST 节点传入或通过 SymbolEntry 引用 AST 节点
            
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION,
                "标识符 '" + name + "' 在当前作用域中已定义",
                entry.getName(),
                currentScopeLevel,
                line, // 使用实际的行号
                column // 使用实际的列号
            ));
            return false;
        }
        
        currentScope.put(name, entry);
        
        if (debugMode) {
            System.out.println("[Scope " + currentScopeLevel + "] 插入符号: " + entry);
        }
        
        return true;
    }
    
    /**
     * 查找符号（从当前作用域开始向上查找），如果找不到则记录错误。
     */
    public SymbolEntry lookupSymbol(String name) {
        SymbolEntry entry = lookupSymbolWithoutError(name);
        
        if (entry == null) {
            // 未找到符号，记录错误
            addError(new SemanticError(
                SemanticErrorType.UNDEFINED_IDENTIFIER,
                "未定义的标识符 '" + name + "'",
                name,
                currentScopeLevel,
                0, // 查找时通常不知道引用处的行/列，需要在 TypeAnalyzer 中补充
                0
            ));
        }
        return entry;
    }
    
    /**
     * 查找符号（从当前作用域开始向上查找），内部使用或外部使用，不记录错误。
     */
    public SymbolEntry lookupSymbolWithoutError(String name) {
        // 从栈顶开始查找 (当前作用域 -> 全局作用域)
        if (debugMode) {
            System.out.println("[DEBUG] 查找符号: '" + name + "' (当前作用域级别: " + currentScopeLevel + ", 符号表栈大小: " + symbolTableStack.size() + ")");
        }
        
        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            if (debugMode) {
                System.out.println("  [DEBUG] 检查 Scope " + i + " 的符号: " + scope.keySet());
            }
            if (scope.containsKey(name)) {
                SymbolEntry found = scope.get(name);
                if (debugMode) {
                    System.out.println("  [DEBUG] ✓ 在 Scope " + i + " 找到符号: " + found);
                }
                return found;
            }
        }
        
        if (debugMode) {
            System.out.println("  [DEBUG] ✗ 符号 '" + name + "' 未找到");
        }
        return null;
    }
    
    /**
     * 查找符号（仅在当前作用域）
     */
    public SymbolEntry lookupSymbolInCurrentScope(String name) {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        return currentScope.get(name);
    }
    
    /**
     * 查找结构体定义 (通常从全局作用域或所有作用域中查找)
     * 假设结构体名查找遵循通常的符号查找规则 (先局部后全局)，但检查其 SymbolType。
     */
    public SymbolEntry lookupStruct(String structName) {
        SymbolEntry entry = lookupSymbolWithoutError(structName);
        
        // ⚠️ 假设 SymbolEntry.getSymbolType() 和 SymbolType.STRUCT_DEFINITION 已定义
        if (entry != null && entry.getSymbolType() == SymbolType.STRUCT_DEFINITION) {
            return entry;
        }
        return null;
    }
    
    /**
     * 检查符号是否在当前作用域中定义
     */
    public boolean isDefinedInCurrentScope(String name) {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        return currentScope.containsKey(name);
    }
    
    // --- Getters / Debug Methods (保持不变或微调) ---
    
    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }
    
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    public Map<String, SymbolEntry> getCurrentScope() {
        return symbolTableStack.peek();
    }
    
    public Map<String, SymbolEntry> getGlobalScope() {
        // 始终是栈底
        if (!symbolTableStack.isEmpty()) {
            return symbolTableStack.get(0);
        }
        return new HashMap<>(); // 返回空 Map 以防止 NullPointerException
    }
    
    public void displayCurrentScope() {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        System.out.println("作用域 " + currentScopeLevel + " 的符号:");
        for (Map.Entry<String, SymbolEntry> entry : currentScope.entrySet()) {
            System.out.println("  " + entry.getValue());
        }
    }
    
    public void displaySymbolTable() {
        System.out.println("=== 完整符号表 ===");
        // 修正：作用域级别从 0 开始
        for (int i = 0; i < symbolTableStack.size(); i++) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            System.out.println("作用域 " + i + " (级别 " + (i == 0 ? "全局" : "局部") + "):");
            for (Map.Entry<String, SymbolEntry> entry : scope.entrySet()) {
                System.out.println("  " + entry.getValue());
            }
            System.out.println();
        }
    }
    
    // --- Error Management ---
    
    public void addSemanticError(SemanticError error) {
        errors.add(error);
    }
    
    // 内部错误添加方法（供 lookupSymbol 等使用）
    private void addError(SemanticError error) {
        errors.add(error);
    }
    
    public List<SemanticError> getErrors() {
        return new ArrayList<>(errors);
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    public void printErrors() {
        if (hasErrors()) {
            System.err.println("=== 语义错误 (" + errors.size() + " 个) ===");
            for (SemanticError error : errors) {
                System.err.println(error);
            }
        } else {
            System.out.println("无语义错误");
        }
    }

    /**
     * 将符号表转换为字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 完整符号表 ===\n");
        for (int i = 0; i < symbolTableStack.size(); i++) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            sb.append("作用域 ").append(i + 1).append(":\n");
            for (Map.Entry<String, SymbolEntry> entry : scope.entrySet()) {
                sb.append("  ").append(entry.getValue()).append("\n");
            }
            sb.append("\n");
        }
        if (hasErrors()) {
            sb.append("=== 语义错误 ===\n");
            for (SemanticError error : errors) {
                sb.append(error).append("\n");
            }
        }
        return sb.toString();
    }
    
    
    /**
     * 清除所有错误
     */
    public void clearErrors() {
        errors.clear();
    }
    
    
    
    /**
     * 获取符号表深度
     */
    public int getSymbolTableDepth() {
        return symbolTableStack.size();
    }
    
    /**
     * 检查符号表是否为空
     */
    public boolean isEmpty() {
        return symbolTableStack.isEmpty();
    }
    
    /**
     * 获取指定作用域的符号数量
     */
    public int getScopeSize(int scopeLevel) {
        if (scopeLevel >= 0 && scopeLevel < symbolTableStack.size()) {
            return symbolTableStack.get(scopeLevel).size();
        }
        return 0;
    }
    
    /**
     * 获取所有作用域的符号数量
     */
    public int getTotalSymbolCount() {
        int total = 0;
        for (Map<String, SymbolEntry> scope : symbolTableStack) {
            total += scope.size();
        }
        return total;
    }
}
