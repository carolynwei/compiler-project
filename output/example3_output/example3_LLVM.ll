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
  %1 = add i32 c, 0
  %2 = add i32 c, 0
  %3 = add i32 c, 0
  %4 = add i32 b, 0
  %5 = add i32 a, 0
  %6 = add i32 a, 1
  %7 = add i32 %6, 0
  %8 = add i32 a, 1
  %9 = add i32 %8, 0
  %10 = add i32 b, 0
  %11 = sub i32 b, 1
  %12 = add i32 %11, 0
  %13 = sub i32 b, 1
  %14 = add i32 %13, 0
  %15 = add i32 a, b
  %16 = add i32 %15, 0
  %17 = sub i32 a, c
  %18 = add i32 %17, 0
  %19 = mul i32 a, 2
  %20 = add i32 %19, 0
  %21 = sdiv i32 a, 2
  %22 = add i32 %21, 0
  %23 = srem i32 a, 3
  %24 = add i32 %23, 0
  %25 = icmp ne i32 b, 0
  br i1 %25, label %true2, label %true2_else
  %26 = add i32 b, 0
  br label %end3
true2:
  %27 = add i32 a, 0
end3:
  %28 = add i32 %27, 0
  ret i32 result
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  store i32 c, i32* %1, align 4
  store i32 c, i32* %1, align 4
  store i32 c, i32* %1, align 4
  %4 = load i32, i32* %3, align 4
  store i32 %4, i32* %1, align 4
  %5 = load i32, i32* %2, align 4
  %6 = add i32 %5, 0
  %8 = load i32, i32* %2, align 4
  %7 = add i32 %8, 1
  store i32 %7, i32* %2, align 4
  %10 = load i32, i32* %2, align 4
  %9 = add i32 %10, 1
  store i32 %9, i32* %2, align 4
  %11 = load i32, i32* %3, align 4
  %12 = add i32 %11, 0
  %14 = load i32, i32* %3, align 4
  %13 = sub i32 %14, 1
  store i32 %13, i32* %3, align 4
  %16 = load i32, i32* %3, align 4
  %15 = sub i32 %16, 1
  store i32 %15, i32* %3, align 4
  %18 = load i32, i32* %2, align 4
  %19 = load i32, i32* %3, align 4
  %17 = add i32 %18, %19
  store i32 %17, i32* %2, align 4
  %21 = load i32, i32* %2, align 4
  %20 = sub i32 %21, c
  store i32 %20, i32* %2, align 4
  %23 = load i32, i32* %2, align 4
  %22 = mul i32 %23, 2
  store i32 %22, i32* %2, align 4
  %25 = load i32, i32* %2, align 4
  %24 = sdiv i32 %25, 2
  store i32 %24, i32* %2, align 4
  %27 = load i32, i32* %2, align 4
  %26 = srem i32 %27, 3
  store i32 %26, i32* %2, align 4
  %28 = load i32, i32* %3, align 4
  %29 = icmp ne i32 %28, 0
  br i1 %29, label %true2, label %true2_else
  %30 = load i32, i32* %3, align 4
  %31 = add i32 %30, 0
  br label %end3
true2:
  %32 = load i32, i32* %2, align 4
  %33 = add i32 %32, 0
end3:
  store i32 %33, i32* %1, align 4
  %34 = load i32, i32* %1, align 4
  ret i32 %34
  ret i32 0
}
