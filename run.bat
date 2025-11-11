@echo off
REM Gemini-C 编译器运行脚本 (Windows)

if "%~2"=="" (
    echo Usage: run.bat ^<input.gc^> ^<output.ll^> [--debug]
    echo Example: run.bat src\test\examples\example1.gc output.ll
    exit /b 1
)

set INPUT_FILE=%~1
set OUTPUT_FILE=%~2
set DEBUG_FLAG=%~3

REM 构建参数
set ARGS=%INPUT_FILE% %OUTPUT_FILE%
if "%DEBUG_FLAG%"=="--debug" (
    set ARGS=%ARGS% --debug
)

REM 使用 Maven exec 插件运行
mvn -q exec:java -Dexec.mainClass="com.gemini.compiler.GeminiCompiler" -Dexec.args="%ARGS%"

echo Compilation completed. Output written to: %OUTPUT_FILE%

