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
  %6 = add i32 %5, 0
for_update5:
  %7 = load i32, i32* %1, align 4
  %8 = add i32 %7, 0
  %10 = load i32, i32* %1, align 4
  %9 = add i32 %10, 1
  store i32 %9, i32* %1, align 4
  br label %for_condition3
for_end6:
while7:
  %11 = icmp eq i32 0, 0
  br i1 %11, label %while_end9, label %while_end9_else
while_body8:
  %12 = icmp eq i32 0, 0
  br i1 %12, label %else10, label %else10_else
  %13 = add i32 count, 0
  %14 = sub i32 count, 1
  %15 = add i32 %14, 0
  br label %endif11
else10:
endif11:
  %16 = icmp eq i32 5, 0
  br i1 %16, label %else12, label %else12_else
  br label %endif13
else12:
endif13:
  %17 = sub i32 count, 1
  %18 = add i32 %17, 0
  br label %while7
while_end9:
  ret i32 0
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  store i32 1, i32* 0
  store i32 2, i32* 0
for_init2:
  store i32 0, i32* %2, align 4
for_condition3:
  %4 = load i32, i32* %1, align 4
  %5 = icmp eq i32 %4, 0
  br i1 %5, label %for_end6, label %for_end6_else
for_body4:
  %6 = load i32, i32* %7
  store i32 %2, i32* 0
  %9 = load i32, i32* %3, align 4
  %8 = add i32 %9, 0
  store i32 %8, i32* %3, align 4
for_update5:
  %10 = load i32, i32* %2, align 4
  %11 = add i32 %10, 0
  %13 = load i32, i32* %2, align 4
  %12 = add i32 %13, 1
  store i32 %12, i32* %2, align 4
  br label %for_condition3
for_end6:
while7:
  %14 = icmp eq i32 0, 0
  br i1 %14, label %while_end9, label %while_end9_else
while_body8:
  %15 = icmp eq i32 0, 0
  br i1 %15, label %else10, label %else10_else
  %16 = load i32, i32* %1, align 4
  %17 = add i32 %16, 0
  %19 = load i32, i32* %1, align 4
  %18 = sub i32 %19, 1
  store i32 %18, i32* %1, align 4
  br label %endif11
else10:
endif11:
  %20 = icmp eq i32 5, 0
  br i1 %20, label %else12, label %else12_else
  br label %endif13
else12:
endif13:
  %22 = load i32, i32* %1, align 4
  %21 = sub i32 %22, 1
  store i32 %21, i32* %1, align 4
  br label %while7
while_end9:
  ret i32 0
  ret i32 0
}
