package com.gemini.compiler.semantic;

import java.util.*;

/**
 * 符号表管理器
 * 
 * 使用栈式结构管理作用域，支持动态展现符号表内容
 */
public class SymbolTableManager {
    
    // 符号表栈，每个元素代表一个作用域
    private Stack<Map<String, SymbolEntry>> symbolTableStack;
    
    // 当前作用域级别
    private int currentScopeLevel;
    
    // 调试开关
    private boolean debugMode;
    
    // 错误收集器
    private List<SemanticError> errors;
    
    public SymbolTableManager() {
        this.symbolTableStack = new Stack<>();
        this.currentScopeLevel = 0;
        this.debugMode = false;
        this.errors = new ArrayList<>();
        
        // 初始化全局作用域
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
            displayCurrentScope();
        }
    }
    
    /**
     * 退出当前作用域
     */
    public void exitScope() {
        if (symbolTableStack.size() > 1) { // 保留全局作用域
            Map<String, SymbolEntry> currentScope = symbolTableStack.pop();
            currentScopeLevel--;
            
            if (debugMode) {
                System.out.println("--- 退出作用域 " + (currentScopeLevel + 1) + " ---");
                System.out.println("当前作用域: " + currentScopeLevel);
            }
        }
    }
    
    /**
     * 插入符号到当前作用域
     */
    public boolean insertSymbol(SymbolEntry entry) {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        String name = entry.getName();
        
        // 检查重定义错误
        if (currentScope.containsKey(name)) {
            addError(new SemanticError(
                SemanticErrorType.REDEFINITION,
                "标识符 '" + name + "' 在当前作用域中已定义",
                entry.getName(),
                currentScopeLevel
            ));
            return false;
        }
        
        currentScope.put(name, entry);
        
        if (debugMode) {
            System.out.println("插入符号: " + entry);
            displayCurrentScope();
        }
        
        return true;
    }
    
    /**
     * 查找符号（从当前作用域开始向上查找）
     */
    public SymbolEntry lookupSymbol(String name) {
        // 从栈顶开始查找
        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        
        // 未找到符号
        addError(new SemanticError(
            SemanticErrorType.UNDEFINED_IDENTIFIER,
            "未定义的标识符 '" + name + "'",
            name,
            currentScopeLevel
        ));
        
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
     * 查找结构体定义
     */
    public SymbolEntry lookupStruct(String structName) {
        // 结构体定义通常在全局作用域
        Map<String, SymbolEntry> globalScope = symbolTableStack.get(0);
        SymbolEntry entry = globalScope.get(structName);
        
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
    
    /**
     * 获取当前作用域的所有符号
     */
    public Map<String, SymbolEntry> getCurrentScope() {
        return symbolTableStack.peek();
    }
    
    /**
     * 获取全局作用域的所有符号
     */
    public Map<String, SymbolEntry> getGlobalScope() {
        return symbolTableStack.get(0);
    }
    
    /**
     * 获取当前作用域级别
     */
    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }
    
    /**
     * 设置调试模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * 显示当前作用域
     */
    public void displayCurrentScope() {
        Map<String, SymbolEntry> currentScope = symbolTableStack.peek();
        System.out.println("作用域 " + currentScopeLevel + " 的符号:");
        for (Map.Entry<String, SymbolEntry> entry : currentScope.entrySet()) {
            System.out.println("  " + entry.getValue());
        }
    }
    
    /**
     * 显示整个符号表
     */
    public void displaySymbolTable() {
        System.out.println("=== 完整符号表 ===");
        for (int i = 0; i < symbolTableStack.size(); i++) {
            Map<String, SymbolEntry> scope = symbolTableStack.get(i);
            System.out.println("作用域 " + (i + 1) + ":");
            for (Map.Entry<String, SymbolEntry> entry : scope.entrySet()) {
                System.out.println("  " + entry.getValue());
            }
            System.out.println();
        }
    }
    
    /**
     * 添加语义错误
     */
    private void addError(SemanticError error) {
        errors.add(error);
    }
    
    /**
     * 获取所有错误
     */
    public List<SemanticError> getErrors() {
        return new ArrayList<>(errors);
    }
    
    /**
     * 清除所有错误
     */
    public void clearErrors() {
        errors.clear();
    }
    
    /**
     * 检查是否有错误
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    /**
     * 打印所有错误
     */
    public void printErrors() {
        if (hasErrors()) {
            System.out.println("=== 语义错误 ===");
            for (SemanticError error : errors) {
                System.out.println(error);
            }
        } else {
            System.out.println("语义分析通过，无错误");
        }
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
