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
  store i32 10, i32* %14, align 4
  store i32 5, i32* %15, align 4
  store i32 3, i32* %16, align 4
  %19 = load i32, i32* %15, align 4
  %20 = load i32, i32* %16, align 4
  %18 = mul i32 %19, %20
  %22 = load i32, i32* %14, align 4
  %21 = add i32 %22, %18
  store i32 %21, i32* %17, align 4
  %24 = load i32, i32* %14, align 4
  %25 = load i32, i32* %15, align 4
  %23 = add i32 %24, %25
  %27 = load i32, i32* %16, align 4
  %26 = mul i32 %23, %27
  store i32 %26, i32* %17, align 4
  %29 = load i32, i32* %14, align 4
  %30 = load i32, i32* %15, align 4
  %28 = sdiv i32 %29, %30
  %32 = load i32, i32* %16, align 4
  %31 = add i32 %28, %32
  store i32 %31, i32* %17, align 4
  %34 = load i32, i32* %14, align 4
  %35 = load i32, i32* %15, align 4
  %33 = srem i32 %34, %35
  store i32 %33, i32* %17, align 4
  %37 = load i32, i32* %14, align 4
  %38 = load i32, i32* %15, align 4
  %39 = icmp eq i32 %37, %38
  %40 = zext i1 %39 to i32
  store i32 %40, i32* %36, align 4
  %42 = load i32, i32* %14, align 4
  %43 = load i32, i32* %15, align 4
  %44 = icmp ne i32 %42, %43
  %45 = zext i1 %44 to i32
  store i32 %45, i32* %41, align 4
  %47 = load i32, i32* %14, align 4
  %48 = load i32, i32* %15, align 4
  %49 = icmp slt i32 %47, %48
  %50 = zext i1 %49 to i32
  store i32 %50, i32* %46, align 4
  %52 = load i32, i32* %14, align 4
  %53 = load i32, i32* %15, align 4
  %54 = icmp sgt i32 %52, %53
  %55 = zext i1 %54 to i32
  store i32 %55, i32* %51, align 4
  %57 = load i32, i32* %14, align 4
  %58 = load i32, i32* %15, align 4
  %59 = icmp sle i32 %57, %58
  %60 = zext i1 %59 to i32
  store i32 %60, i32* %56, align 4
  %62 = load i32, i32* %14, align 4
  %63 = load i32, i32* %15, align 4
  %64 = icmp sge i32 %62, %63
  %65 = zext i1 %64 to i32
  store i32 %65, i32* %61, align 4
  %67 = load i32, i32* %14, align 4
  %68 = icmp sgt i32 %67, 0
  %69 = zext i1 %68 to i32
  %70 = load i32, i32* %15, align 4
  %71 = icmp sgt i32 %70, 0
  %72 = zext i1 %71 to i32
  br i1 %68, label %check_next_1, label %false1
check_next_1:
  br i1 %71, label %check_next_2, label %false1
check_next_2:
  store i32 1, i32* %73, align 4
  br label %end2
false1:
  store i32 0, i32* %73, align 4
end2:
  %74 = load i32, i32* %73, align 4
  store i32 %74, i32* %66, align 4
  %76 = load i32, i32* %14, align 4
  %77 = icmp slt i32 %76, 0
  %78 = zext i1 %77 to i32
  %79 = load i32, i32* %15, align 4
  %80 = icmp sgt i32 %79, 0
  %81 = zext i1 %80 to i32
  br i1 %77, label %true3, label %check_next_3
check_next_3:
  br i1 %80, label %true3, label %check_next_4
check_next_4:
  store i32 0, i32* %82, align 4
  br label %end4
true3:
  store i32 1, i32* %82, align 4
end4:
  %83 = load i32, i32* %82, align 4
  store i32 %83, i32* %75, align 4
  %85 = load i32, i32* %14, align 4
  %86 = icmp sgt i32 %85, 0
  %87 = zext i1 %86 to i32
  %88 = xor i1 %86, 1
  %89 = zext i1 %88 to i32
  store i32 %89, i32* %84, align 4
  %90 = load i32, i32* %14, align 4
  %91 = add i32 %90, 1
  store i32 %91, i32* %14, align 4
  %92 = load i32, i32* %14, align 4
  %93 = add i32 %92, 1
  store i32 %93, i32* %14, align 4
  %94 = load i32, i32* %15, align 4
  %95 = sub i32 %94, 1
  store i32 %95, i32* %15, align 4
  %96 = load i32, i32* %15, align 4
  %97 = sub i32 %96, 1
  store i32 %97, i32* %15, align 4
  %99 = load i32, i32* %14, align 4
  %100 = load i32, i32* %15, align 4
  %98 = add i32 %99, %100
  store i32 %98, i32* %14, align 4
  %102 = load i32, i32* %14, align 4
  %103 = load i32, i32* %16, align 4
  %101 = sub i32 %102, %103
  store i32 %101, i32* %14, align 4
  %105 = load i32, i32* %14, align 4
  %104 = mul i32 %105, 2
  store i32 %104, i32* %14, align 4
  %107 = load i32, i32* %14, align 4
  %106 = sdiv i32 %107, 2
  store i32 %106, i32* %14, align 4
  %109 = load i32, i32* %14, align 4
  %108 = srem i32 %109, 3
  store i32 %108, i32* %14, align 4
  %110 = load i32, i32* %14, align 4
  %111 = load i32, i32* %15, align 4
  %112 = icmp sgt i32 %110, %111
  %113 = zext i1 %112 to i32
  %114 = load i32, i32* %14, align 4
  %115 = load i32, i32* %15, align 4
  %116 = select i1 %112, i32 %114, i32 %115
  store i32 %116, i32* %17, align 4
  %117 = load i32, i32* %17, align 4
  ret i32 %117
}

