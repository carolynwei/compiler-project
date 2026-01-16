#!/bin/bash
# Test RedundantAssignmentPass logic
cd /workspaces/compiler-project

echo "测试冗余赋值消除优化器..."
echo ""

# 显示example3前几行TAC
echo "=== Example3 TAC (前20行) ===" 
cat output/example3_output/example3_IR_TAC.tac | head -22 | tail -20

echo ""
echo "=== 分析 ==="
echo "result在第2-4行被赋值为'c'"
echo "result在第5行被赋值为'b'"
echo ""
echo "按照RedundantAssignmentPass的逻辑："
echo "- 第2行: result = c, 查找下一个对result的定义在第3行, 所以第2行应该被删除"
echo "- 第3行: result = c, 查找下一个对result的定义在第4行, 所以第3行应该被删除"
echo "- 第4行: result = c, 查找下一个对result的定义在第5行, 所以第4行应该被删除"
echo "- 第5行: result = b, 下一个对result的定义在第32行, 所以第5行不应该被删除"
echo ""
echo "理想输出应该是："
echo "result = b"
echo "t1 = a"
echo "..."
