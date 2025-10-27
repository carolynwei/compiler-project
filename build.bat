@echo off
REM Gemini-C 编译器构建脚本 (Windows)
REM 用于快速编译和运行编译器

echo === Gemini-C 编译器构建脚本 ===

REM 检查 Java 版本
echo 检查 Java 版本...
java -version

REM 检查 Maven 版本
echo 检查 Maven 版本...
mvn -version

REM 清理项目
echo 清理项目...
mvn clean

REM 编译项目
echo 编译项目...
mvn compile

REM 运行测试
echo 运行测试...
mvn test

REM 打包项目
echo 打包项目...
mvn package

echo === 构建完成 ===
echo 使用方法:
echo   java -jar target\gemini-c-compiler-1.0.0.jar ^<输入文件^> [输出文件] [选项]
echo.
echo 示例:
echo   java -jar target\gemini-c-compiler-1.0.0.jar src\test\examples\example1.gc output.ll
echo   java -jar target\gemini-c-compiler-1.0.0.jar src\test\examples\example1.gc output.ll --debug-ast
echo.
echo 选项:
echo   --debug-ast      显示抽象语法树
echo   --debug-symtable 显示符号表
echo   --debug-ir       显示中间代码
echo   --debug-codegen  显示目标代码生成过程
echo   --optimize       启用优化

pause
