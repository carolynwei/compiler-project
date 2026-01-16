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
define i32 @findMax1() {
entry:
for_init2:
  %1 = alloca i32
  store i32 1, i32* %1, align 4
for_condition3:
  %2 = icmp eq i32 size, 0
  br i1 %2, label %for_end6, label %for_end6_else
for_body4:
  %3 = icmp eq i32 max, 0
  br i1 %3, label %else7, label %else7_else
  %4 = load i32, i32* %5
  %6 = add i32 %4, 0
  br label %endif8
else7:
endif8:
for_update5:
  %8 = load i32, i32* %1, align 4
  %7 = add i32 %8, 1
  br label %for_condition3
for_end6:
  ret i32 max
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
for_init2:
  store i32 1, i32* %2, align 4
for_condition3:
  %3 = icmp eq i32 size, 0
  br i1 %3, label %for_end6, label %for_end6_else
for_body4:
  %4 = load i32, i32* %1, align 4
  %5 = icmp eq i32 %4, 0
  br i1 %5, label %else7, label %else7_else
  %6 = load i32, i32* %7
  store i32 %6, i32* %1, align 4
  br label %endif8
else7:
endif8:
for_update5:
  %9 = load i32, i32* %2, align 4
  %8 = add i32 %9, 1
  br label %for_condition3
for_end6:
  %10 = load i32, i32* %1, align 4
  ret i32 %10
  ret i32 0
}
