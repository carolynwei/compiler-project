; Gemini-C 编译器生成的 LLVM IR 代码
; 目标架构: x86-64
; 优化级别: -O2

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i1)
declare i8* @malloc(i64)
declare void @free(i8*)

; 全局变量声明
%struct.Student = type { i32, i32, float }

; 函数定义
define i32 @findMax(i32* %arr_arg, i32 %size_arg) {
entry:
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = getelementptr inbounds i32, i32* %arr_arg, i64 0
  %8 = load i32, i32* %7, align 4
  store i32 %8, i32* %6, align 4
for_init1:
  %10 = alloca i32, align 4
  store i32 1, i32* %10, align 4
for_condition2:
  %11 = load i32, i32* %10, align 4
  %12 = icmp slt i32 %11, %size_arg
  %13 = zext i1 %12 to i32
  br i1 %12, label %if_zero_fallthrough_1, label %for_end5
if_zero_fallthrough_1:
for_body3:
  %14 = load i32, i32* %10, align 4
  %15 = getelementptr inbounds i32, i32* %arr_arg, i64 %14
  %16 = load i32, i32* %15, align 4
  %17 = load i32, i32* %6, align 4
  %18 = icmp sgt i32 %16, %17
  %19 = zext i1 %18 to i32
  br i1 %18, label %if_zero_fallthrough_2, label %else6
if_zero_fallthrough_2:
  %20 = load i32, i32* %10, align 4
  %21 = getelementptr inbounds i32, i32* %arr_arg, i64 %20
  %22 = load i32, i32* %21, align 4
  store i32 %22, i32* %6, align 4
  br label %endif7
else6:
endif7:
for_update4:
  %23 = load i32, i32* %10, align 4
  %24 = add i32 %23, 1
  store i32 %24, i32* %10, align 4
  br label %for_condition2
for_end5:
  %25 = load i32, i32* %6, align 4
  ret i32 %25
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
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  %10 = alloca i32, align 4
  %11 = alloca i32, align 4
  %12 = alloca i32, align 4
  %13 = alloca i32, align 4
  %14 = alloca i32, align 4
  %15 = alloca i32, align 4
  %16 = alloca i32, align 4
  %17 = alloca i32, align 4
  %18 = alloca i32, align 4
  %19 = alloca i32, align 4
  %20 = alloca i32, align 4
  %21 = alloca [5 x %struct.Student], align 4
  %22 = alloca [5 x i32], align 4
  %23 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 0
  store i32 85, i32* %23, align 4
  %24 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 1
  store i32 92, i32* %24, align 4
  %25 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 2
  store i32 78, i32* %25, align 4
  %26 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 3
  store i32 96, i32* %26, align 4
  %27 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 4
  store i32 88, i32* %27, align 4
for_init8:
  %29 = alloca i32, align 4
  store i32 0, i32* %29, align 4
for_condition9:
  %30 = load i32, i32* %29, align 4
  %31 = icmp slt i32 %30, 5
  %32 = zext i1 %31 to i32
  br i1 %31, label %if_zero_fallthrough_3, label %for_end12
if_zero_fallthrough_3:
for_body10:
  %33 = load i32, i32* %29, align 4
  %34 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %33
  %36 = load i32, i32* %29, align 4
  %35 = add i32 %36, 1
  %37 = load i32, i32* %35, align 4
  %38 = getelementptr inbounds %struct.tudent, %struct.tudent* %34, i32 0, i32 0
  store i32 %37, i32* %38, align 4
  %39 = load i32, i32* %29, align 4
  %40 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %39
  %42 = load i32, i32* %29, align 4
  %41 = add i32 18, %42
  %43 = load i32, i32* %41, align 4
  %44 = getelementptr inbounds %struct.tudent, %struct.tudent* %40, i32 0, i32 0
  store i32 %43, i32* %44, align 4
  %45 = load i32, i32* %29, align 4
  %46 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %45
  %47 = load i32, i32* %29, align 4
  %48 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 %47
  %49 = load i32, i32* %48, align 4
  %50 = getelementptr inbounds %struct.tudent, %struct.tudent* %46, i32 0, i32 0
  store i32 %49, i32* %50, align 4
for_update11:
  %51 = load i32, i32* %29, align 4
  %52 = add i32 %51, 1
  store i32 %52, i32* %29, align 4
  br label %for_condition9
for_end12:
  %54 = alloca i32, align 4
  %55 = getelementptr inbounds [5 x i32], [5 x i32]* %22, i64 0, i64 0
  %56 = call i32 @findMax(i32* %55, i32 5)
  %57 = load i32, i32* %56, align 4
  store i32 %57, i32* %54, align 4
for_init13:
  store i32 0, i32* %58, align 4
for_condition14:
  %59 = load i32, i32* %58, align 4
  %60 = icmp slt i32 %59, 5
  %61 = zext i1 %60 to i32
  br i1 %60, label %if_zero_fallthrough_4, label %for_end17
if_zero_fallthrough_4:
for_body15:
for_init18:
  %63 = alloca i32, align 4
  %65 = load i32, i32* %58, align 4
  %64 = add i32 %65, 1
  store i32 %64, i32* %63, align 4
for_condition19:
  %66 = load i32, i32* %63, align 4
  %67 = icmp slt i32 %66, 5
  %68 = zext i1 %67 to i32
  br i1 %67, label %if_zero_fallthrough_5, label %for_end22
if_zero_fallthrough_5:
for_body20:
  %69 = load i32, i32* %58, align 4
  %70 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %69
  %71 = getelementptr inbounds %struct.tudent, %struct.tudent* %70, i32 0, i32 0
  %72 = load i32, i32* %63, align 4
  %73 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %72
  %74 = getelementptr inbounds %struct.tudent, %struct.tudent* %73, i32 0, i32 0
  %75 = load i32, i32* %71, align 4
  %76 = load i32, i32* %74, align 4
  %77 = icmp slt i32 %75, %76
  %78 = zext i1 %77 to i32
  br i1 %77, label %if_zero_fallthrough_6, label %else23
if_zero_fallthrough_6:
  %80 = alloca %struct.Student, align 4
  %81 = load i32, i32* %58, align 4
  %82 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %81
  %83 = bitcast %struct.Student* %80 to i8*
  %84 = bitcast %struct.Student* %82 to i8*
  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %83, i8* %84, i64 12, i1 false)
  %85 = load i32, i32* %63, align 4
  %86 = getelementptr inbounds [5 x %struct.Student], [5 x %struct.Student]* %21, i64 0, i64 %85
  %87 = load i32, i32* %58, align 4
  %88 = getelementptr inbounds [5 x i32], [5 x i32]* %21, i64 0, i64 %87
  %89 = load i32, i32* %86, align 4
  store i32 %89, i32* %88, align 4
  %90 = load i32, i32* %63, align 4
  %91 = getelementptr inbounds [5 x i32], [5 x i32]* %21, i64 0, i64 %90
  %92 = load i32, i32* %80, align 4
  store i32 %92, i32* %91, align 4
  br label %endif24
else23:
endif24:
for_update21:
  %93 = load i32, i32* %63, align 4
  %94 = add i32 %93, 1
  store i32 %94, i32* %63, align 4
  br label %for_condition19
for_end22:
for_update16:
  %95 = load i32, i32* %58, align 4
  %96 = add i32 %95, 1
  store i32 %96, i32* %58, align 4
  br label %for_condition14
for_end17:
  %98 = alloca i32, align 4
  store i32 3, i32* %98, align 4
switch25:
  %99 = load i32, i32* %98, align 4
  switch i32 %99, label %default30 [
    i32 1, label %case27
    i32 2, label %case28
    i32 3, label %case29
  ]
case27:
  ret i32 1
case28:
  ret i32 2
case29:
  ret i32 3
default30:
  ret i32 0
switch_end26:
}

