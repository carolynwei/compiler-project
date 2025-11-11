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
define i32 @add(i32 %a_arg, i32 %b_arg) {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = add i32 %1, %2
  ret i32 %3
}

define i32 @multiply(i32 %a_arg, i32 %b_arg) {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = mul i32 %1, %2
  ret i32 %3
}

define i32 @factorial(i32 %n_arg) {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = icmp sle i32 %2, 1
  %4 = zext i1 %3 to i32
  br i1 %3, label %check_next_1, label %else1
check_next_1:
  ret i32 1
else1:
  %5 = sub i32 %2, 1
  %6 = call i32 @factorial(i32 %5)
  %7 = mul i32 %2, %6
  ret i32 %7
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i32, align 4
for_init3:
  store i32 0, i32* %10, align 4
for_condition4:
  %11 = load i32, i32* %10, align 4
  %12 = icmp slt i32 %11, 10
  %13 = zext i1 %12 to i32
  br i1 %12, label %check_next_2, label %for_end7
check_next_2:
for_body5:
  %15 = load i32, i32* %10, align 4
  %14 = add i32 %15, 1
  %16 = load i32, i32* %10, align 4
  %17 = getelementptr inbounds [10 x i32], [10 x i32]* %8, i64 0, i64 %16
  store i32 %14, i32* %17, align 4
for_update6:
  %18 = load i32, i32* %10, align 4
  %19 = add i32 %18, 1
  store i32 %19, i32* %10, align 4
  br label %for_condition4
  br label %for_end7
for_end7:
  store i32 8, i32* %9, align 4
  %20 = load i32, i32* %9, align 4
  %21 = call i32 @multiply(i32 %20, i32 2)
  store i32 %21, i32* %9, align 4
  %22 = call i32 @factorial(i32 5)
  store i32 %22, i32* %9, align 4
  store i32 0, i32* %23, align 4
for_init8:
  store i32 0, i32* %24, align 4
for_condition9:
  %25 = load i32, i32* %24, align 4
  %26 = icmp slt i32 %25, 10
  %27 = zext i1 %26 to i32
  br i1 %26, label %check_next_3, label %for_end12
check_next_3:
for_body10:
  %28 = load i32, i32* %24, align 4
  %29 = getelementptr inbounds [10 x i32], [10 x i32]* %8, i64 0, i64 %28
  %31 = load i32, i32* %23, align 4
  %32 = load i32, i32* %29, align 4
  %30 = add i32 %31, %32
  store i32 %30, i32* %23, align 4
for_update11:
  %33 = load i32, i32* %24, align 4
  %34 = add i32 %33, 1
  store i32 %34, i32* %24, align 4
  br label %for_condition9
  br label %for_end12
for_end12:
  %35 = load i32, i32* %23, align 4
  ret i32 %35
}

