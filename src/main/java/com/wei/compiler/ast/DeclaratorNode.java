package com.wei.compiler.ast;

public class DeclaratorNode extends ASTNode {
    private final String name;
    private final int pointerLevel;
    private final int referenceCount;
    private final ExpressionNode[] arrayDimensions; // 数组维度可以是表达式

    public DeclaratorNode(String name, int pointerLevel, int referenceCount, 
                          ExpressionNode[] arrayDimensions, int line, int col) {
        super(ASTNodeType.TYPE, line, col);
        this.name = name;
        this.pointerLevel = pointerLevel;
        this.referenceCount = referenceCount;
        this.arrayDimensions = arrayDimensions;
    }

    public String getName() {
        return name;
    }

    public int getPointerLevel() {
        return pointerLevel;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public ExpressionNode[] getArrayDimensions() {
        return arrayDimensions;
    }
    
    @Override
    public ASTNode[] getChildren() {
        // DeclaratorNode 本身不直接包含子 ASTNode，数组维度是 ExpressionNode
        // 如果需要遍历表达式，可以在访问者中特殊处理
        return new ASTNode[0];
    }
    
    // 假设您已在 ASTVisitor 中定义了 visitDeclaratorNode 方法
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDeclaratorNode(this);
    }
}