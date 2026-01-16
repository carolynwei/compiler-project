package com.wei.compiler;

import com.wei.compiler.ast.*;
import com.wei.compiler.semantic.*;
import com.wei.compiler.ir.*;
import com.wei.compiler.codegen.*;
import com.wei.compiler.optimizer.*;
import com.wei.WeiCLexer;
import com.wei.WeiCParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.nio.file.*;

/**
 * wei-C 编译器主类
 * 
 * 实现完整的编译流程：
 * 1. 词法分析 (Lexical Analysis)
 * 2. 语法分析 (Syntax Analysis) 
 * 3. 语义分析 (Semantic Analysis)
 * 4. 中间代码生成 (Intermediate Code Generation)
 * 5. 目标代码生成 (Target Code Generation)
 */
public class WeiCompiler {
    
    // 编译器配置
    private CompilerConfig config;
    
    public WeiCompiler() {
        this.config = new CompilerConfig();
    }
    
    public WeiCompiler(CompilerConfig config) {
        // 建议增加非空检查
        this.config = config != null ? config : new CompilerConfig();
    }
    
    // 添加 Setter 来替换直接访问
    public void setConfig(CompilerConfig config) {
        this.config = config;
    }
    
    /**
     * 编译 wei-C 源文件
     * @param inputFile 输入文件路径
     * @param outputFile 输出文件路径
     * @throws IOException 文件操作异常
     * @throws RuntimeException 编译错误
     */
    public void compile(String inputFile, String outputFile) throws IOException {
        System.out.println("=== wei-C 编译器启动 ===");
        System.out.println("输入文件: " + inputFile);
        System.out.println("输出文件: " + outputFile);
        
        // 🔥 修复：从输入文件提取源文件名（而不是输出文件名）
        // 这样可以避免 output_output 这样的冗余目录名
        String inputFileName = new File(inputFile).getName();
        String baseName = inputFileName.replaceAll("\\.[^.]*$", "");
        
        // 创建输出目录结构：output/<baseName>_output/
        Path outputDirPath = Paths.get("output", baseName + "_output");
        String outputDir = outputDirPath.toString();
        
        // 确保输出目录存在
        Files.createDirectories(outputDirPath);
        
        // 生成完整输出文件路径
        String llvmOutputFile = Paths.get(outputDir, baseName + "_LLVM.ll").toString();
        String astOutputFile = Paths.get(outputDir, baseName + "_AST.txt").toString();
        String symTableOutputFile = Paths.get(outputDir, baseName + "_SymbolTable.txt").toString();
        String irOutputFile = Paths.get(outputDir, baseName + "_IR_TAC.tac").toString();
        
        System.out.println("📁 编译输出目录: " + new File(outputDir).getAbsolutePath());
        
        // 创建共享的符号表管理器
        SymbolTableManager symbolTableManager = new SymbolTableManager();
        
        // 阶段一：词法分析和语法分析
        ASTNode ast = parseFile(inputFile, symbolTableManager);
        
        if (this.config.isDebugAst()) { 
            ASTPrinter printer = new ASTPrinter();
            
            // 打印到控制台
            System.out.println("\n--- 抽象语法树 (AST) ---");
            printer.print(ast);

            // 写入文件
            System.out.println("\n将 AST 输出到: " + astOutputFile);
            String astContent = printer.toString(ast); 
            writeToFile(astContent, astOutputFile);
        }
        
        // 阶段二：语义分析
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(symbolTableManager, new TypeAnalyzer(symbolTableManager));
        semanticAnalyzer.setDebugMode(this.config.isDebugSymtable());
        semanticAnalyzer.analyze(ast);
        
        // 如果启用符号表 debug
        if (this.config.isDebugSymtable()) {
            System.out.println("将符号表输出到: " + symTableOutputFile);
            String symTableContent = semanticAnalyzer.getSymbolTableManager().toString();
            writeToFile(symTableContent, symTableOutputFile);
        }
        
        // 🔥 关键修复：如果语义分析失败，停止编译过程
        if (semanticAnalyzer.hasErrors()) {
            System.err.println("❌ 语义分析失败，中止编译。");
            return;
        }
        
        // 阶段三：中间代码生成
        IRGenerator irGenerator = new IRGenerator();
        irGenerator.setDebugMode(this.config.isDebugIr());
        IRProgram irProgram = irGenerator.generate(ast);
        
        // 阶段三点五：中间代码优化（新增）
        IROptimizer optimizer = new IROptimizer(this.config.isOptimize());
        optimizer.setDebugMode(this.config.isDebugIr());
        irProgram = optimizer.optimize(irProgram);
        
        // 如果启用 IR debug，保存中间代码到文件
        if (this.config.isDebugIr()) { 
            System.out.println("将中间代码输出到: " + irOutputFile);
            String irContent = irProgram.toString();
            writeToFile(irContent, irOutputFile);
        }
        
        // 阶段四：目标代码生成
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.setDebugMode(this.config.isDebugCodegen());
        String targetCode = codeGenerator.generate(irProgram);
        
        // 输出 LLVM IR 代码
        System.out.println("将 LLVM IR 输出到: " + llvmOutputFile);
        writeToFile(targetCode, llvmOutputFile);
        
        System.out.println("=== 编译完成 ===");
    }
    
    /**
     * 解析文件，生成抽象语法树
     */
    private ASTNode parseFile(String inputFile, SymbolTableManager symbolTableManager) throws IOException {
        System.out.println("\n--- 阶段一：词法分析和语法分析 ---");
        
        // 读取源文件
        String sourceCode = java.nio.file.Files.readString(java.nio.file.Paths.get(inputFile));
        
        // 创建词法分析器
        CharStream charStream = CharStreams.fromString(sourceCode);
        WeiCLexer lexer = new WeiCLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // 创建语法分析器
        WeiCParser parser = new WeiCParser(tokens);
        
        // 移除默认错误监听器，使用自定义错误处理
        parser.removeErrorListeners();
        parser.addErrorListener(new CompilerErrorListener());
        
        // 解析程序
        ParseTree parseTree = parser.program();
        
        // 构建抽象语法树 (使用共享的符号表管理器)
        ASTBuilder astBuilder = new ASTBuilder(symbolTableManager);
        ASTNode ast = astBuilder.build(parseTree);
        
        return ast;
    }
    
    /**
     * 将内容写入文件
     */
    private void writeToFile(String content, String outputFile) throws IOException {
        // 确保输出目录存在
        File file = new File(outputFile);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {
            writer.print(content);
        }
    }
    
    /**
     * 编译器配置类
     */
    public static class CompilerConfig {
        private boolean optimize = true;  // 默认启用优化
        private String targetArchitecture = "x86-64";
        private boolean verbose = false;

        // A. 新增调试标志的实例字段
        private boolean debugAst = false;
        private boolean debugSymtable = false;
        private boolean debugIr = false;
        private boolean debugCodegen = false;
        
        // Getters and Setters
        public boolean isOptimize() { return optimize; }
        public void setOptimize(boolean optimize) { this.optimize = optimize; }
        
        public String getTargetArchitecture() { return targetArchitecture; }
        public void setTargetArchitecture(String targetArchitecture) { this.targetArchitecture = targetArchitecture; }
        
        public boolean isVerbose() { return verbose; }
        public void setVerbose(boolean verbose) { this.verbose = verbose; }

        // A. 新增 Getter/Setter
        public boolean isDebugAst() { return debugAst; }
        public void setDebugAst(boolean debugAst) { this.debugAst = debugAst; }

        public boolean isDebugSymtable() { return debugSymtable; }
        public void setDebugSymtable(boolean debugSymtable) { this.debugSymtable = debugSymtable; }

        public boolean isDebugIr() { return debugIr; }
        public void setDebugIr(boolean debugIr) { this.debugIr = debugIr; }

        public boolean isDebugCodegen() { return debugCodegen; }
        public void setDebugCodegen(boolean debugCodegen) { this.debugCodegen = debugCodegen; }

        public CompilerConfig() {
            // 设置默认值
            this.optimize = false;
            this.targetArchitecture = "x86-64";
            this.verbose = false;
            this.debugAst = false;
            this.debugSymtable = false;
            this.debugIr = false;
            this.debugCodegen = false;
        }
        
        public CompilerConfig(boolean optimize, String targetArchitecture, boolean verbose, boolean debugAst, boolean debugSymtable, boolean debugIr, boolean debugCodegen) {
            this.optimize = optimize;
            this.targetArchitecture = targetArchitecture;
            this.verbose = verbose;
            this.debugAst = debugAst;
            this.debugSymtable = debugSymtable;
            this.debugIr = debugIr;
            this.debugCodegen = debugCodegen;
        }
    }
    
    /**
     * 编译器错误监听器
     */
    private static class CompilerErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                              int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new RuntimeException("语法错误 [" + line + ":" + charPositionInLine + "] " + msg);
        }
    }
    
    /**
     * 主程序入口
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("用法: java WeiCompiler <输入文件> [输出文件] [选项]");
            System.out.println("选项:");
            System.out.println("  --debug-ast      显示抽象语法树");
            System.out.println("  --debug-symtable 显示符号表");
            System.out.println("  --debug-ir       显示中间代码");
            System.out.println("  --debug-codegen  显示目标代码生成过程");
            System.out.println("  --debug-all      显示所有调试信息（等同于上述四个选项）");
            System.out.println("  --optimize       启用优化");
            System.exit(1);
        }
        
        String inputFile = args[0];
        
        // 检查第二个参数是否是输出文件或标志
        int flagStartIndex = 1;
        String outputFile = "output.ll";
        
        if (args.length > 1 && !args[1].startsWith("--")) {
            outputFile = args[1];
            flagStartIndex = 2;
        }
        
        // 解析命令行选项
        boolean debugAst = false, debugSymtable = false, debugIr = false, debugCodegen = false;
        boolean optimize = true;  // 默认启用优化
        
        for (int i = flagStartIndex; i < args.length; i++) {
            switch (args[i]) {
                case "--debug-ast": debugAst = true; break;
                case "--debug-symtable": debugSymtable = true; break;
                case "--debug-ir": debugIr = true; break;
                case "--debug-codegen": debugCodegen = true; break;
                case "--debug-all": 
                    debugAst = true;
                    debugSymtable = true;
                    debugIr = true;
                    debugCodegen = true;
                    break;
                case "--optimize": optimize = true; break;
            }
        }
        
        CompilerConfig config = null;

        try {
            config = new CompilerConfig(
                optimize,
                "x86-64", 
                false, 
                debugAst, 
                debugSymtable, 
                debugIr, 
                debugCodegen
            );

            // 使用带配置的构造函数创建编译器实例
            WeiCompiler compiler = new WeiCompiler(config); 
            compiler.compile(inputFile, outputFile);
            
        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
            // 在测试环境中不调用System.exit，让异常正常抛出
            if (System.getProperty("sun.java.command", "").contains("org.apache.maven.surefire")) {
                throw new RuntimeException("文件操作错误: " + e.getMessage(), e);
            } else {
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("编译错误: " + e.getMessage());

            if (config != null && config.isVerbose()) { 
                e.printStackTrace();
            }

            if (System.getProperty("sun.java.command", "").contains("org.apache.maven.surefire")) {
                throw new RuntimeException("编译错误: " + e.getMessage(), e);
            } else {
                System.exit(1);
            }
        }
    }
}