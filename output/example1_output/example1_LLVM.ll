; wei-C 编译器生成的 LLVM IR 代码
; 目标架构: x86-64
; 优化级别: -O2

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i1)
declare i8* @malloc(i64)
declare void @free(i8*)

; 全局变量声明

; 函数定义
define i32 @main1() {
entry:
  store i32 1, i32* 0
  store i32 2, i32* 0
for_init2:
  %1 = alloca i32
  store i32 0, i32* %1, align 4
for_condition3:
  %2 = icmp eq i32 count, 0
  br i1 %2, label %for_end6, label %for_end6_else
for_body4:
  %3 = load i32, i32* %4
  store i32 %1, i32* 0
  %5 = add i32 sum, 0
for_update5:
  %7 = load i32, i32* %1, align 4
  %6 = add i32 %7, 1
  br label %for_condition3
for_end6:
while7:
  br label %while_end9
while_end9:
  ret i32 0
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  store i32 1, i32* 0
  store i32 2, i32* 0
for_init2:
  store i32 0, i32* %1, align 4
for_condition3:
  %2 = icmp eq i32 count, 0
  br i1 %2, label %for_end6, label %for_end6_else
for_body4:
  %3 = load i32, i32* %4
  store i32 %1, i32* 0
  %5 = add i32 sum, 0
for_update5:
  %7 = load i32, i32* %1, align 4
  %6 = add i32 %7, 1
  br label %for_condition3
for_end6:
while7:
  br label %while_end9
while_end9:
  ret i32 0
  ret i32 0
}
