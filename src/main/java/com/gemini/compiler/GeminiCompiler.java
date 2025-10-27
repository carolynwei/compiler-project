package com.gemini.compiler;

import com.gemini.compiler.ast.*;
import com.gemini.compiler.semantic.*;
import com.gemini.compiler.ir.*;
import com.gemini.compiler.codegen.*;
import com.gemini.grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.nio.file.*;

/**
 * Gemini-C 编译器主类
 * 
 * 实现完整的编译流程：
 * 1. 词法分析 (Lexical Analysis)
 * 2. 语法分析 (Syntax Analysis) 
 * 3. 语义分析 (Semantic Analysis)
 * 4. 中间代码生成 (Intermediate Code Generation)
 * 5. 目标代码生成 (Target Code Generation)
 */
public class GeminiCompiler {
    
    // 调试开关
    public static boolean DEBUG_AST = false;
    public static boolean DEBUG_SYMTABLE = false;
    public static boolean DEBUG_IR = false;
    public static boolean DEBUG_CODEGEN = false;
    
    // 编译器配置
    private CompilerConfig config;
    
    public GeminiCompiler() {
        this.config = new CompilerConfig();
    }
    
    public GeminiCompiler(CompilerConfig config) {
        this.config = config;
    }
    
    /**
     * 编译 Gemini-C 源文件
     * @param inputFile 输入文件路径
     * @param outputFile 输出文件路径
     * @throws IOException 文件操作异常
     */
    public void compile(String inputFile, String outputFile) throws IOException {
        System.out.println("=== Gemini-C 编译器启动 ===");
        System.out.println("输入文件: " + inputFile);
        System.out.println("输出文件: " + outputFile);
        
        // 阶段一：词法分析和语法分析
        ASTNode ast = parseFile(inputFile);
        
        // 阶段二：语义分析
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyze(ast);
        
        // 阶段三：中间代码生成
        IRGenerator irGenerator = new IRGenerator();
        IRProgram irProgram = irGenerator.generate(ast);
        
        // 阶段四：目标代码生成
        CodeGenerator codeGenerator = new CodeGenerator();
        String targetCode = codeGenerator.generate(irProgram);
        
        // 输出目标代码
        writeToFile(targetCode, outputFile);
        
        System.out.println("=== 编译完成 ===");
    }
    
    /**
     * 解析文件，生成抽象语法树
     */
    private ASTNode parseFile(String inputFile) throws IOException {
        System.out.println("\n--- 阶段一：词法分析和语法分析 ---");
        
        // 读取源文件
        String sourceCode = Files.readString(Paths.get(inputFile));
        
        // 创建词法分析器
        GeminiCLexer lexer = new GeminiCLexer(CharStreams.fromString(sourceCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // 创建语法分析器
        GeminiCParser parser = new GeminiCParser(tokens);
        
        // 移除默认错误监听器，使用自定义错误处理
        parser.removeErrorListeners();
        parser.addErrorListener(new CompilerErrorListener());
        
        // 解析程序
        ParseTree parseTree = parser.program();
        
        // 构建抽象语法树
        ASTBuilder astBuilder = new ASTBuilder();
        ASTNode ast = astBuilder.build(parseTree);
        
        // 调试：显示 AST
        if (DEBUG_AST) {
            System.out.println("\n--- 抽象语法树 (AST) ---");
            ASTPrinter printer = new ASTPrinter();
            printer.print(ast);
        }
        
        return ast;
    }
    
    /**
     * 将内容写入文件
     */
    private void writeToFile(String content, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.print(content);
        }
    }
    
    /**
     * 设置调试开关
     */
    public void setDebugFlags(boolean ast, boolean symtable, boolean ir, boolean codegen) {
        DEBUG_AST = ast;
        DEBUG_SYMTABLE = symtable;
        DEBUG_IR = ir;
        DEBUG_CODEGEN = codegen;
    }
    
    /**
     * 编译器配置类
     */
    public static class CompilerConfig {
        private boolean optimize = false;
        private String targetArchitecture = "x86-64";
        private boolean verbose = false;
        
        // Getters and Setters
        public boolean isOptimize() { return optimize; }
        public void setOptimize(boolean optimize) { this.optimize = optimize; }
        
        public String getTargetArchitecture() { return targetArchitecture; }
        public void setTargetArchitecture(String targetArchitecture) { this.targetArchitecture = targetArchitecture; }
        
        public boolean isVerbose() { return verbose; }
        public void setVerbose(boolean verbose) { this.verbose = verbose; }
    }
    
    /**
     * 编译器错误监听器
     */
    private static class CompilerErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                              int line, int charPositionInLine, String msg, RecognitionException e) {
            System.err.println("语法错误 [" + line + ":" + charPositionInLine + "] " + msg);
            System.exit(1);
        }
    }
    
    /**
     * 主程序入口
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("用法: java GeminiCompiler <输入文件> [输出文件] [选项]");
            System.out.println("选项:");
            System.out.println("  --debug-ast      显示抽象语法树");
            System.out.println("  --debug-symtable 显示符号表");
            System.out.println("  --debug-ir       显示中间代码");
            System.out.println("  --debug-codegen  显示目标代码生成过程");
            System.out.println("  --optimize       启用优化");
            System.exit(1);
        }
        
        String inputFile = args[0];
        String outputFile = args.length > 1 ? args[1] : "output.ll";
        
        // 解析命令行选项
        boolean debugAst = false, debugSymtable = false, debugIr = false, debugCodegen = false;
        boolean optimize = false;
        
        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {
                case "--debug-ast": debugAst = true; break;
                case "--debug-symtable": debugSymtable = true; break;
                case "--debug-ir": debugIr = true; break;
                case "--debug-codegen": debugCodegen = true; break;
                case "--optimize": optimize = true; break;
            }
        }
        
        try {
            GeminiCompiler compiler = new GeminiCompiler();
            compiler.setDebugFlags(debugAst, debugSymtable, debugIr, debugCodegen);
            
            CompilerConfig config = new CompilerConfig();
            config.setOptimize(optimize);
            compiler.config = config;
            
            compiler.compile(inputFile, outputFile);
            
        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("编译错误: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
