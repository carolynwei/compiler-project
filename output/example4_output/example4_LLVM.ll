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
  %7 = load i32, i32* %1, align 4
  %8 = add i32 %7, 0
  %10 = load i32, i32* %1, align 4
  %9 = add i32 %10, 1
  store i32 %9, i32* %1, align 4
  br label %for_condition3
for_end6:
  ret i32 max
}

define i32 @main9() {
entry:
  store i32 85, i32* 0
  store i32 92, i32* 0
  store i32 78, i32* 0
  store i32 96, i32* 0
  store i32 88, i32* 0
for_init10:
  %11 = alloca i32
  store i32 0, i32* %11, align 4
for_condition11:
  %12 = icmp eq i32 5, 0
  br i1 %12, label %for_end14, label %for_end14_else
for_body12:
  %13 = load i32, i32* %14
  store i32 1, i32* 0
  %15 = load i32, i32* %16
  store i32 %11, i32* 0
  %17 = load i32, i32* %18
  %19 = load i32, i32* %20
  store i32 %17, i32* 0
for_update13:
  %21 = load i32, i32* %11, align 4
  %22 = add i32 %21, 0
  %24 = load i32, i32* %11, align 4
  %23 = add i32 %24, 1
  store i32 %23, i32* %11, align 4
  br label %for_condition11
for_end14:
for_init15:
  %25 = alloca i32
  store i32 0, i32* %25, align 4
for_condition16:
  %26 = icmp eq i32 5, 0
  br i1 %26, label %for_end19, label %for_end19_else
for_body17:
for_init20:
  %27 = alloca i32
  store i32 1, i32* %27, align 4
for_condition21:
  %28 = icmp eq i32 5, 0
  br i1 %28, label %for_end24, label %for_end24_else
for_body22:
  %29 = load i32, i32* %30
  %31 = load i32, i32* %32
  %33 = icmp eq i32 %31, 0
  br i1 %33, label %else25, label %else25_else
  %34 = load i32, i32* %35
  store i32 %34, i32* 0
  store i32 %36, i32* 0
  br label %endif26
else25:
endif26:
for_update23:
  %37 = load i32, i32* %27, align 4
  %38 = add i32 %37, 0
  %40 = load i32, i32* %27, align 4
  %39 = add i32 %40, 1
  store i32 %39, i32* %27, align 4
  br label %for_condition21
for_end24:
for_update18:
  %41 = load i32, i32* %25, align 4
  %42 = add i32 %41, 0
  %44 = load i32, i32* %25, align 4
  %43 = add i32 %44, 1
  store i32 %43, i32* %25, align 4
  br label %for_condition16
for_end19:
}

define i32 @main() {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
for_init2:
  store i32 1, i32* %2, align 4
for_condition3:
  %4 = icmp eq i32 size, 0
  br i1 %4, label %for_end6, label %for_end6_else
for_body4:
  %5 = load i32, i32* %1, align 4
  %6 = icmp eq i32 %5, 0
  br i1 %6, label %else7, label %else7_else
  %7 = load i32, i32* %8
  store i32 %7, i32* %1, align 4
  br label %endif8
else7:
endif8:
for_update5:
  %9 = load i32, i32* %2, align 4
  %10 = add i32 %9, 0
  %12 = load i32, i32* %2, align 4
  %11 = add i32 %12, 1
  store i32 %11, i32* %2, align 4
  br label %for_condition3
for_end6:
  %13 = load i32, i32* %1, align 4
  ret i32 %13
  store i32 85, i32* 0
  store i32 92, i32* 0
  store i32 78, i32* 0
  store i32 96, i32* 0
  store i32 88, i32* 0
for_init10:
  store i32 0, i32* %2, align 4
for_condition11:
  %14 = icmp eq i32 5, 0
  br i1 %14, label %for_end14, label %for_end14_else
for_body12:
  %15 = load i32, i32* %16
  store i32 1, i32* 0
  %17 = load i32, i32* %18
  store i32 %2, i32* 0
  %19 = load i32, i32* %20
  %21 = load i32, i32* %22
  store i32 %19, i32* 0
for_update13:
  %23 = load i32, i32* %2, align 4
  %24 = add i32 %23, 0
  %26 = load i32, i32* %2, align 4
  %25 = add i32 %26, 1
  store i32 %25, i32* %2, align 4
  br label %for_condition11
for_end14:
for_init15:
  store i32 0, i32* %2, align 4
for_condition16:
  %27 = icmp eq i32 5, 0
  br i1 %27, label %for_end19, label %for_end19_else
for_body17:
for_init20:
  store i32 1, i32* %3, align 4
for_condition21:
  %28 = icmp eq i32 5, 0
  br i1 %28, label %for_end24, label %for_end24_else
for_body22:
  %29 = load i32, i32* %30
  %31 = load i32, i32* %32
  %33 = icmp eq i32 %31, 0
  br i1 %33, label %else25, label %else25_else
  %34 = load i32, i32* %35
  store i32 %34, i32* 0
  store i32 %36, i32* 0
  br label %endif26
else25:
endif26:
for_update23:
  %37 = load i32, i32* %3, align 4
  %38 = add i32 %37, 0
  %40 = load i32, i32* %3, align 4
  %39 = add i32 %40, 1
  store i32 %39, i32* %3, align 4
  br label %for_condition21
for_end24:
for_update18:
  %41 = load i32, i32* %2, align 4
  %42 = add i32 %41, 0
  %44 = load i32, i32* %2, align 4
  %43 = add i32 %44, 1
  store i32 %43, i32* %2, align 4
  br label %for_condition16
for_end19:
  ret i32 0
}
