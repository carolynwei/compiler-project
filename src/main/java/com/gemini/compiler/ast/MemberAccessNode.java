package com.gemini.compiler.ast;

/**
 * 成员访问节点
 */
public class MemberAccessNode extends ExpressionNode {
    private ExpressionNode object;
    private String memberName;
    
    public MemberAccessNode(ExpressionNode object, String memberName, int line, int column) {
        super(ASTNodeType.MEMBER_ACCESS, line, column);
        this.object = object;
        this.memberName = memberName;
    }
    
    public ExpressionNode getObject() { return object; }
    public String getMemberName() { return memberName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMemberAccess(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{object};
    }
}
