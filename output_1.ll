; Gemini-C 编译器生成的 LLVM IR 代码
; 目标架构: x86-64
; 优化级别: -O2

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i1)
declare i8* @malloc(i64)
declare void @free(i8*)

; 全局变量声明
%struct.Point = type { i32, i32 }

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
  %8 = alloca float, align 4
  %9 = alloca i32, align 4
  %10 = alloca i8*, align 4
  store i32 10, i32* %11, align 4
  store float 0.0, float* %12, align 4
  store i8* @str.1, i8** %15, align 4
  %16 = getelementptr inbounds %struct.Point, %struct.Point* %14, i32 0, i32 0
  store i32 1, i32* %16, align 4
  %17 = getelementptr inbounds %struct.Point, %struct.Point* %14, i32 0, i32 1
  store i32 2, i32* %17, align 4
for_init1:
  store i32 0, i32* %18, align 4
for_condition2:
  %19 = load i32, i32* %18, align 4
  %20 = load i32, i32* %11, align 4
  %21 = icmp slt i32 %19, %20
  %22 = zext i1 %21 to i32
  br i1 %21, label %check_next_1, label %for_end5
check_next_1:
for_body3:
  %23 = load i32, i32* %18, align 4
  %24 = getelementptr inbounds [3 x [3 x i32]], [3 x [3 x i32]]* %13, i64 0, i64 %23
  %26 = load i32, i32* %18, align 4
  %25 = mul i32 %26, %26
  %27 = load i32, i32* %18, align 4
  %28 = getelementptr inbounds [3 x i32], [3 x i32]* %24, i64 0, i64 %27
  store i32 %25, i32* %28, align 4
  %29 = load i32, i32* %18, align 4
  %30 = sitofp i32 %29 to float
  %32 = load float, float* %12, align 4
  %31 = fadd float %32, %30
  store float %31, float* %12, align 4
for_update4:
  %33 = load i32, i32* %18, align 4
  %34 = add i32 %33, 1
  store i32 %34, i32* %18, align 4
  br label %for_condition2
  br label %for_end5
for_end5:
switch6:
  %35 = load i32, i32* %11, align 4
  switch i32 %35, label %default9 [
    i32 10, label %case8
  ]
case8:
  %36 = load i32, i32* %11, align 4
  %37 = add i32 %36, 1
  store i32 %37, i32* %11, align 4
  br label %switch_end7
default9:
  %39 = load i32, i32* %11, align 4
  %38 = mul i32 %39, 2
  store i32 %38, i32* %11, align 4
switch_end7:
while10:
  %40 = load i32, i32* %11, align 4
  %41 = icmp sgt i32 %40, 0
  %42 = zext i1 %41 to i32
  br i1 %41, label %check_next_2, label %while_end12
check_next_2:
while_body11:
  %44 = load i32, i32* %11, align 4
  %43 = srem i32 %44, 2
  %45 = icmp eq i32 %43, 0
  %46 = zext i1 %45 to i32
  br i1 %45, label %check_next_3, label %else13
check_next_3:
  %47 = load i32, i32* %11, align 4
  %48 = sub i32 %47, 1
  store i32 %48, i32* %11, align 4
  br label %while10
while_end12:
  ret i32 0
}

