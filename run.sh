#!/bin/bash
# Gemini-C 编译器运行脚本

# 使用 Maven 运行编译器
if [ $# -lt 2 ]; then
    echo "Usage: ./run.sh <input.gc> <output.ll> [--debug]"
    echo "Example: ./run.sh src/test/examples/example1.gc output.ll"
    exit 1
fi

INPUT_FILE=$1
OUTPUT_FILE=$2
DEBUG_FLAG=$3

# 构建参数
ARGS="$INPUT_FILE $OUTPUT_FILE"
if [ "$DEBUG_FLAG" == "--debug" ]; then
    ARGS="$ARGS --debug"
fi

# 使用 Maven exec 插件运行
mvn -q exec:java \
    -Dexec.mainClass="com.gemini.compiler.GeminiCompiler" \
    -Dexec.args="$ARGS"

echo "Compilation completed. Output written to: $OUTPUT_FILE"

