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
define i32 @add1() {
entry:
  ret i32 b
}

define i32 @multiply2() {
entry:
  ret i32 b
}

define i32 @factorial3() {
entry:
  %1 = icmp eq i32 1, 0
  br i1 %1, label %else4, label %else4_else
  ret i32 1
  br label %endif5
else4:
  %2 = call i32 @factorial()
  ret i32 %3
endif5:
}

define i32 @main6() {
entry:
for_init7:
  %4 = alloca i32
  store i32 0, i32* %4, align 4
for_condition8:
  %5 = icmp eq i32 10, 0
  br i1 %5, label %for_end11, label %for_end11_else
for_body9:
  store i32 1, i32* 0
for_update10:
  %6 = load i32, i32* %4, align 4
  %7 = add i32 %6, 0
  %9 = load i32, i32* %4, align 4
  %8 = add i32 %9, 1
  store i32 %8, i32* %4, align 4
  br label %for_condition8
for_end11:
  %10 = call i32 @add()
  %12 = add i32 %11, 0
  %13 = call i32 @multiply()
  %15 = add i32 %14, 0
  %16 = call i32 @factorial()
  %18 = add i32 %17, 0
for_init12:
  %19 = alloca i32
  store i32 0, i32* %19, align 4
for_condition13:
  %20 = icmp eq i32 10, 0
  br i1 %20, label %for_end16, label %for_end16_else
for_body14:
  %21 = load i32, i32* %22
  %23 = add i32 sum, %21
  %24 = add i32 %23, 0
for_update15:
  %25 = load i32, i32* %19, align 4
  %26 = add i32 %25, 0
  %28 = load i32, i32* %19, align 4
  %27 = add i32 %28, 1
  store i32 %27, i32* %19, align 4
  br label %for_condition13
for_end16:
  ret i32 sum
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  ret i32 b
  ret i32 b
  %4 = icmp eq i32 1, 0
  br i1 %4, label %else4, label %else4_else
  ret i32 1
  br label %endif5
else4:
  %5 = call i32 @factorial()
  ret i32 %6
endif5:
for_init7:
  store i32 0, i32* %2, align 4
for_condition8:
  %7 = icmp eq i32 10, 0
  br i1 %7, label %for_end11, label %for_end11_else
for_body9:
  store i32 1, i32* 0
for_update10:
  %8 = load i32, i32* %2, align 4
  %9 = add i32 %8, 0
  %11 = load i32, i32* %2, align 4
  %10 = add i32 %11, 1
  store i32 %10, i32* %2, align 4
  br label %for_condition8
for_end11:
  %12 = call i32 @add()
  store i32 %13, i32* %1, align 4
  %14 = call i32 @multiply()
  store i32 %15, i32* %1, align 4
  %16 = call i32 @factorial()
  store i32 %17, i32* %1, align 4
for_init12:
  store i32 0, i32* %2, align 4
for_condition13:
  %18 = icmp eq i32 10, 0
  br i1 %18, label %for_end16, label %for_end16_else
for_body14:
  %19 = load i32, i32* %20
  %22 = load i32, i32* %3, align 4
  %21 = add i32 %22, %19
  store i32 %21, i32* %3, align 4
for_update15:
  %23 = load i32, i32* %2, align 4
  %24 = add i32 %23, 0
  %26 = load i32, i32* %2, align 4
  %25 = add i32 %26, 1
  store i32 %25, i32* %2, align 4
  br label %for_condition13
for_end16:
  %27 = load i32, i32* %3, align 4
  ret i32 %27
  ret i32 0
}
