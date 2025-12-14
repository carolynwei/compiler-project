package com.gemini.compiler.test;

import com.wei.compiler.WeiCompiler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

/**
 * Wei-C 编译器测试类
 */
public class GeminiCompilerTest {
    
    private WeiCompiler compiler;
    private String testDir;
    
    @BeforeEach
    public void setUp() {
        compiler = new WeiCompiler();
        testDir = "src/test/examples/";
    }
    
    @AfterEach
    public void tearDown() {
        // 清理测试文件
        try {
            Files.deleteIfExists(Paths.get("output.ll"));
            Files.deleteIfExists(Paths.get("empty.gc"));
            Files.deleteIfExists(Paths.get("large.gc"));
        } catch (IOException e) {
            // 忽略删除错误
        }
    }
    
    @Test
    public void testBasicSyntax() throws IOException {
        String inputFile = testDir + "example1.gc";
        String outputFile = "output.ll";
        
        // 测试基本语法编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
        
        // 验证输出文件不为空
        String content = Files.readString(Paths.get(outputFile));
        assertFalse(content.isEmpty(), "输出文件不应该为空");
        
        // 验证包含基本的LLVM IR结构
        assertTrue(content.contains("define i32 @main()"), "应该包含main函数定义");
        assertTrue(content.contains("ret i32"), "应该包含返回语句");
    }
    
    @Test
    public void testFunctionAndArray() throws IOException {
        String inputFile = testDir + "example2.gc";
        String outputFile = "output.ll";
        
        // 测试函数和数组编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
        
        // 验证包含函数定义
        String content = Files.readString(Paths.get(outputFile));
        assertTrue(content.contains("define i32 @add"), "应该包含add函数定义");
        assertTrue(content.contains("define i32 @multiply"), "应该包含multiply函数定义");
        assertTrue(content.contains("define i32 @factorial"), "应该包含factorial函数定义");
    }
    
    @Test
    public void testComplexExpressions() throws IOException {
        String inputFile = testDir + "example3.gc";
        String outputFile = "output.ll";
        
        // 测试复杂表达式编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
        
        // 验证包含算术运算
        String content = Files.readString(Paths.get(outputFile));
        assertTrue(content.contains("add i32"), "应该包含加法运算");
        assertTrue(content.contains("mul i32"), "应该包含乘法运算");
        assertTrue(content.contains("sdiv i32"), "应该包含除法运算");
    }
    
    @Test
    public void testStructAndControlFlow() throws IOException {
        String inputFile = testDir + "example4.gc";
        String outputFile = "output.ll";
        
        // 测试结构体和控制流编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
        
        // 验证包含结构体相关代码
        String content = Files.readString(Paths.get(outputFile));
        assertTrue(content.contains("define i32 @findMax"), "应该包含findMax函数定义");
    }
    
    @Test
    public void testErrorHandling() {
        String inputFile = testDir + "error_test.gc";
        String outputFile = "output.ll";
        
        // 测试错误处理
        assertThrows(Exception.class, () -> {
            compiler.compile(inputFile, outputFile);
        }, "应该抛出编译错误");
    }
    
    @Test
    public void testDebugMode() throws IOException {
        String inputFile = testDir + "example1.gc";
        String outputFile = "output.ll";
        
        // 启用调试模式
        WeiCompiler.CompilerConfig config = new WeiCompiler.CompilerConfig();
        config.setDebugAst(true);
        config.setDebugSymtable(true);
        config.setDebugIr(true);
        config.setDebugCodegen(true);
        compiler = new WeiCompiler(config);
        
        // 测试调试模式编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
    }
    
    @Test
    public void testCompilerConfig() {
        // 测试编译器配置
        WeiCompiler.CompilerConfig config = new WeiCompiler.CompilerConfig();
        config.setOptimize(true);
        config.setTargetArchitecture("x86-64");
        config.setVerbose(true);
        
        assertTrue(config.isOptimize(), "优化应该启用");
        assertEquals("x86-64", config.getTargetArchitecture(), "目标架构应该是x86-64");
        assertTrue(config.isVerbose(), "详细模式应该启用");
    }
    
    @Test
    public void testFileNotFound() {
        String inputFile = "nonexistent.gc";
        String outputFile = "output.ll";
        
        // 测试文件不存在的情况
        assertThrows(IOException.class, () -> {
            compiler.compile(inputFile, outputFile);
        }, "应该抛出文件不存在错误");
    }
    
    @Test
    public void testEmptyFile() throws IOException {
        // 创建空文件
        String inputFile = "empty.gc";
        String outputFile = "output.ll";
        
        Files.write(Paths.get(inputFile), "".getBytes());
        
        try {
            // 测试空文件编译（编译器会检测main函数）
            // 无论是成功还是失败，都应该能处理
            compiler.compile(inputFile, outputFile);
            
            // 即使有语义错误，编译器仍成查输出文件
            // 此测试只需测试编译过程不崩溃
            assertDoesNotThrow(() -> Files.readString(Paths.get(outputFile)), "编译应该完成");
            
        } finally {
            // 清理空文件
            Files.deleteIfExists(Paths.get(inputFile));
        }
    }
    
    @Test
    public void testLargeFile() throws IOException {
        // 创建大文件
        String inputFile = "large.gc";
        String outputFile = "output.ll";
        
        StringBuilder content = new StringBuilder();
        content.append("int main() {\n");
        for (int i = 0; i < 1000; i++) {
            content.append("    int var").append(i).append(" = ").append(i).append(";\n");
        }
        content.append("    return 0;\n");
        content.append("}\n");
        
        Files.write(Paths.get(inputFile), content.toString().getBytes());
        
        try {
            // 测试大文件编译
            compiler.compile(inputFile, outputFile);
            
            // 验证输出文件存在
            assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
            
        } finally {
            // 清理大文件
            Files.deleteIfExists(Paths.get(inputFile));
        }
    }
    
    @Test
    public void testPointerArithmetic() throws IOException {
        String inputFile = testDir + "example_pointer_arithmetic.gc";
        String outputFile = "output.ll";
        
        // 测试指针运算编译
        compiler.compile(inputFile, outputFile);
        
        // 验证输出文件存在
        assertTrue(Files.exists(Paths.get(outputFile)), "输出文件应该存在");
        
        // 验证包含指针运算相关的LLVM IR代码
        String content = Files.readString(Paths.get(outputFile));
        
        // 验证包含getelementptr指令（用于指针加减）
        assertTrue(content.contains("getelementptr"), "应该包含getelementptr指令用于指针偏移");
        
        // 验证包含ptrtoint指令（用于指针差值）
        assertTrue(content.contains("ptrtoint"), "应该包含ptrtoint指令用于指针转换");
        
        // 验证包含加法和减法运算
        assertTrue(content.contains("add"), "应该包含加法运算");
        assertTrue(content.contains("sub"), "应该包含减法运算");
        
        // 验证包含main函数
        assertTrue(content.contains("define i32 @main()"), "应该包含main函数定义");
    }
}
