package com.gemini.compiler.test;

import com.gemini.compiler.semantic.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 符号表管理器测试类
 */
public class SymbolTableManagerTest {
    
    private SymbolTableManager symbolTableManager;
    
    @BeforeEach
    public void setUp() {
        symbolTableManager = new SymbolTableManager();
    }
    
    @Test
    public void testInsertAndLookupSymbol() {
        // 测试插入和查找符号
        SymbolEntry entry = new SymbolEntry("testVar", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.LOCAL);
        
        assertTrue(symbolTableManager.insertSymbol(entry), "应该成功插入符号");
        
        SymbolEntry found = symbolTableManager.lookupSymbol("testVar");
        assertNotNull(found, "应该找到符号");
        assertEquals("testVar", found.getName(), "符号名称应该匹配");
        assertEquals(SymbolType.VARIABLE, found.getSymbolType(), "符号类型应该匹配");
    }
    
    @Test
    public void testRedefinitionError() {
        // 测试重定义错误
        SymbolEntry entry1 = new SymbolEntry("testVar", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.LOCAL);
        SymbolEntry entry2 = new SymbolEntry("testVar", SymbolType.VARIABLE, DataType.FLOAT, 1, SymbolKind.LOCAL);
        
        assertTrue(symbolTableManager.insertSymbol(entry1), "第一次插入应该成功");
        assertFalse(symbolTableManager.insertSymbol(entry2), "重定义应该失败");
        
        assertTrue(symbolTableManager.hasErrors(), "应该有错误");
    }
    
    @Test
    public void testUndefinedIdentifier() {
        // 测试未定义标识符
        SymbolEntry found = symbolTableManager.lookupSymbol("undefinedVar");
        assertNull(found, "未定义的符号应该返回null");
        
        assertTrue(symbolTableManager.hasErrors(), "应该有错误");
    }
    
    @Test
    public void testScopeManagement() {
        // 测试作用域管理
        assertEquals(1, symbolTableManager.getCurrentScopeLevel(), "初始作用域级别应该是1");
        
        symbolTableManager.enterScope();
        assertEquals(2, symbolTableManager.getCurrentScopeLevel(), "进入新作用域后级别应该是2");
        
        symbolTableManager.exitScope();
        assertEquals(1, symbolTableManager.getCurrentScopeLevel(), "退出作用域后级别应该是1");
    }
    
    @Test
    public void testSymbolInDifferentScopes() {
        // 测试不同作用域中的符号
        SymbolEntry entry1 = new SymbolEntry("testVar", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.LOCAL);
        symbolTableManager.insertSymbol(entry1);
        
        symbolTableManager.enterScope();
        SymbolEntry entry2 = new SymbolEntry("testVar", SymbolType.VARIABLE, DataType.FLOAT, 2, SymbolKind.LOCAL);
        symbolTableManager.insertSymbol(entry2);
        
        // 在当前作用域查找应该找到内层的符号
        SymbolEntry found = symbolTableManager.lookupSymbol("testVar");
        assertNotNull(found, "应该找到符号");
        assertEquals(DataType.FLOAT, found.getDataType(), "应该找到内层的符号");
        
        symbolTableManager.exitScope();
        
        // 在外层作用域查找应该找到外层的符号
        found = symbolTableManager.lookupSymbol("testVar");
        assertNotNull(found, "应该找到符号");
        assertEquals(DataType.INT, found.getDataType(), "应该找到外层的符号");
    }
    
    @Test
    public void testStructDefinition() {
        // 测试结构体定义
        SymbolEntry structEntry = new SymbolEntry("Point", SymbolType.STRUCT_DEFINITION, DataType.STRUCT, 1, SymbolKind.GLOBAL);
        
        StructInfo structInfo = new StructInfo("Point");
        structInfo.addField("x", new SymbolEntry("x", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.STRUCT_MEMBER));
        structInfo.addField("y", new SymbolEntry("y", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.STRUCT_MEMBER));
        
        structEntry.setStructInfo(structInfo);
        symbolTableManager.insertSymbol(structEntry);
        
        SymbolEntry found = symbolTableManager.lookupStruct("Point");
        assertNotNull(found, "应该找到结构体定义");
        assertEquals("Point", found.getName(), "结构体名称应该匹配");
    }
    
    @Test
    public void testFunctionDefinition() {
        // 测试函数定义
        SymbolEntry functionEntry = new SymbolEntry("add", SymbolType.FUNCTION, DataType.INT, 1, SymbolKind.GLOBAL);
        
        FunctionInfo functionInfo = new FunctionInfo(DataType.INT);
        functionInfo.addParameter(new SymbolEntry("a", SymbolType.PARAMETER, DataType.INT, 1, SymbolKind.PARAMETER));
        functionInfo.addParameter(new SymbolEntry("b", SymbolType.PARAMETER, DataType.INT, 1, SymbolKind.PARAMETER));
        functionInfo.setDefined(true);
        
        functionEntry.setFunctionInfo(functionInfo);
        symbolTableManager.insertSymbol(functionEntry);
        
        SymbolEntry found = symbolTableManager.lookupSymbol("add");
        assertNotNull(found, "应该找到函数定义");
        assertEquals(SymbolType.FUNCTION, found.getSymbolType(), "符号类型应该是函数");
        assertTrue(found.getFunctionInfo().isDefined(), "函数应该已定义");
    }
    
    @Test
    public void testArrayInfo() {
        // 测试数组信息
        SymbolEntry arrayEntry = new SymbolEntry("arr", SymbolType.VARIABLE, DataType.ARRAY, 1, SymbolKind.LOCAL);
        
        ArrayInfo arrayInfo = new ArrayInfo(DataType.INT, new int[]{10, 20});
        arrayEntry.setArrayInfo(arrayInfo);
        
        symbolTableManager.insertSymbol(arrayEntry);
        
        SymbolEntry found = symbolTableManager.lookupSymbol("arr");
        assertNotNull(found, "应该找到数组符号");
        assertNotNull(found.getArrayInfo(), "应该有数组信息");
        assertEquals(2, found.getArrayInfo().getDimensionCount(), "应该是二维数组");
        assertEquals(200, found.getArrayInfo().getTotalSize(), "总大小应该是200");
    }
    
    @Test
    public void testDebugMode() {
        // 测试调试模式
        symbolTableManager.setDebugMode(true);
        
        SymbolEntry entry = new SymbolEntry("debugVar", SymbolType.VARIABLE, DataType.INT, 1, SymbolKind.LOCAL);
        symbolTableManager.insertSymbol(entry);
        
        // 调试模式不应该抛出异常
        assertDoesNotThrow(() -> {
            symbolTableManager.displayCurrentScope();
            symbolTableManager.displaySymbolTable();
        });
    }
    
    @Test
    public void testErrorCollection() {
        // 测试错误收集
        symbolTableManager.lookupSymbol("undefinedVar");
        
        assertTrue(symbolTableManager.hasErrors(), "应该有错误");
        assertTrue(symbolTableManager.getErrorCount() > 0, "错误数量应该大于0");
        
        List<SemanticError> errors = symbolTableManager.getErrors();
        assertFalse(errors.isEmpty(), "错误列表不应该为空");
        
        SemanticError error = errors.get(0);
        assertEquals(SemanticErrorType.UNDEFINED_IDENTIFIER, error.getErrorType(), "错误类型应该匹配");
    }
}
