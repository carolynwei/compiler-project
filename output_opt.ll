; Gemini-C 编译器生成的 LLVM IR 代码
; 目标架构: x86-64
; 优化级别: -O2

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i1)
declare i8* @malloc(i64)
declare void @free(i8*)

; 全局变量声明

; 函数定义
define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i32, align 4
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  %10 = alloca i32, align 4
  %11 = alloca i32, align 4
  %12 = alloca i32, align 4
  %13 = alloca i32, align 4
  %14 = alloca i32, align 4
  %15 = alloca i32, align 4
  store i32 5, i32* %15, align 4
  %16 = alloca i32, align 4
  store i32 5, i32* %16, align 4
  %17 = alloca i32, align 4
  store i32 24, i32* %17, align 4
  %18 = alloca i32, align 4
  store i32 5, i32* %18, align 4
  %19 = alloca i32, align 4
  store i32 5, i32* %19, align 4
  %20 = alloca i32, align 4
  store i32 10, i32* %20, align 4
  %21 = alloca i32, align 4
  %23 = load i32, i32* %19, align 4
  %24 = load i32, i32* %20, align 4
  %22 = mul i32 %23, %24
  store i32 %22, i32* %21, align 4
  %25 = alloca i32, align 4
  %27 = load i32, i32* %19, align 4
  %28 = load i32, i32* %20, align 4
  %26 = mul i32 %27, %28
  store i32 %26, i32* %25, align 4
  %29 = alloca i32, align 4
  store i32 42, i32* %29, align 4
  %30 = alloca i32, align 4
  %32 = load i32, i32* %29, align 4
  %31 = add i32 %32, 8
  store i32 %31, i32* %30, align 4
  br label %else1
for_init3:
  store i32 0, i32* %7, align 4
for_condition4:
  %33 = load i32, i32* %7, align 4
  %34 = load i32, i32* %9, align 4
  %35 = icmp slt i32 %33, %34
  %36 = zext i1 %35 to i32
  br i1 %35, label %if_zero_fallthrough_1, label %for_end7
if_zero_fallthrough_1:
for_body5:
  %38 = alloca i32, align 4
  %40 = load i32, i32* %10, align 4
  %41 = load i32, i32* %7, align 4
  %39 = add i32 %40, %41
  store i32 %39, i32* %38, align 4
for_update6:
  %42 = load i32, i32* %7, align 4
  %43 = add i32 %42, 1
  store i32 %43, i32* %7, align 4
  br label %for_condition4
for_end7:
  ret i32 0
}

