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
  %1 = add i32 a, 1
  %2 = add i32 %1, 1
  %3 = sub i32 b, 1
  %4 = sub i32 %3, 1
  %5 = add i32 %2, %4
  %6 = sub i32 %5, c
  %7 = mul i32 %6, 2
  %8 = sdiv i32 %7, 2
  %9 = srem i32 %8, 3
  %10 = add i32 %9, 0
  %11 = icmp ne i32 %4, 0
  br i1 %11, label %true2, label %true2_else
  br label %end3
true2:
  %12 = add i32 a, 0
end3:
  ret i32 %12
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %3 = load i32, i32* %1, align 4
  %2 = add i32 %3, 1
  %4 = add i32 %2, 1
  %5 = sub i32 b, 1
  %6 = sub i32 %5, 1
  %7 = add i32 %4, %6
  %8 = sub i32 %7, c
  %9 = mul i32 %8, 2
  %10 = sdiv i32 %9, 2
  %11 = srem i32 %10, 3
  store i32 %11, i32* %1, align 4
  %12 = icmp ne i32 %6, 0
  br i1 %12, label %true2, label %true2_else
  br label %end3
true2:
  %13 = load i32, i32* %1, align 4
  %14 = add i32 %13, 0
end3:
  ret i32 %14
  ret i32 0
}
