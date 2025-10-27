package com.gemini.compiler.ast;

/**
 * 语句节点基类
 */
abstract class StatementNode extends ASTNode {
    public StatementNode(ASTNodeType nodeType, int line, int column) {
        super(nodeType, line, column);
    }
}

/**
 * 代码块节点
 */
class BlockNode extends StatementNode {
    private StatementNode[] statements;
    
    public BlockNode(StatementNode[] statements, int line, int column) {
        super(ASTNodeType.BLOCK, line, column);
        this.statements = statements;
    }
    
    public StatementNode[] getStatements() {
        return statements;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBlock(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return statements;
    }
}

/**
 * 表达式语句节点
 */
class ExpressionStatementNode extends StatementNode {
    private ExpressionNode expression;
    
    public ExpressionStatementNode(ExpressionNode expression, int line, int column) {
        super(ASTNodeType.EXPRESSION_STATEMENT, line, column);
        this.expression = expression;
    }
    
    public ExpressionNode getExpression() {
        return expression;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitExpressionStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return expression != null ? new ASTNode[]{expression} : new ASTNode[0];
    }
}

/**
 * if 语句节点
 */
class IfStatementNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode thenStatement;
    private StatementNode elseStatement;
    
    public IfStatementNode(ExpressionNode condition, StatementNode thenStatement, 
                          StatementNode elseStatement, int line, int column) {
        super(ASTNodeType.IF_STATEMENT, line, column);
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    
    public ExpressionNode getCondition() { return condition; }
    public StatementNode getThenStatement() { return thenStatement; }
    public StatementNode getElseStatement() { return elseStatement; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIfStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        if (elseStatement != null) {
            return new ASTNode[]{condition, thenStatement, elseStatement};
        } else {
            return new ASTNode[]{condition, thenStatement};
        }
    }
}

/**
 * while 语句节点
 */
class WhileStatementNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode body;
    
    public WhileStatementNode(ExpressionNode condition, StatementNode body, int line, int column) {
        super(ASTNodeType.WHILE_STATEMENT, line, column);
        this.condition = condition;
        this.body = body;
    }
    
    public ExpressionNode getCondition() { return condition; }
    public StatementNode getBody() { return body; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitWhileStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{condition, body};
    }
}

/**
 * for 语句节点
 */
class ForStatementNode extends StatementNode {
    private StatementNode initialization;
    private ExpressionNode condition;
    private ExpressionNode update;
    private StatementNode body;
    
    public ForStatementNode(StatementNode initialization, ExpressionNode condition, 
                           ExpressionNode update, StatementNode body, int line, int column) {
        super(ASTNodeType.FOR_STATEMENT, line, column);
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }
    
    public StatementNode getInitialization() { return initialization; }
    public ExpressionNode getCondition() { return condition; }
    public ExpressionNode getUpdate() { return update; }
    public StatementNode getBody() { return body; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitForStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{initialization, condition, update, body};
    }
}

/**
 * switch 语句节点
 */
class SwitchStatementNode extends StatementNode {
    private ExpressionNode expression;
    private CaseStatementNode[] cases;
    private DefaultStatementNode defaultCase;
    
    public SwitchStatementNode(ExpressionNode expression, CaseStatementNode[] cases, 
                             DefaultStatementNode defaultCase, int line, int column) {
        super(ASTNodeType.SWITCH_STATEMENT, line, column);
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }
    
    public ExpressionNode getExpression() { return expression; }
    public CaseStatementNode[] getCases() { return cases; }
    public DefaultStatementNode getDefaultCase() { return defaultCase; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSwitchStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[cases.length + (defaultCase != null ? 2 : 1)];
        children[0] = expression;
        System.arraycopy(cases, 0, children, 1, cases.length);
        if (defaultCase != null) {
            children[children.length - 1] = defaultCase;
        }
        return children;
    }
}

/**
 * case 语句节点
 */
class CaseStatementNode extends StatementNode {
    private ExpressionNode value;
    private StatementNode[] statements;
    
    public CaseStatementNode(ExpressionNode value, StatementNode[] statements, int line, int column) {
        super(ASTNodeType.CASE_STATEMENT, line, column);
        this.value = value;
        this.statements = statements;
    }
    
    public ExpressionNode getValue() { return value; }
    public StatementNode[] getStatements() { return statements; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCaseStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        ASTNode[] children = new ASTNode[statements.length + 1];
        children[0] = value;
        System.arraycopy(statements, 0, children, 1, statements.length);
        return children;
    }
}

/**
 * default 语句节点
 */
class DefaultStatementNode extends StatementNode {
    private StatementNode[] statements;
    
    public DefaultStatementNode(StatementNode[] statements, int line, int column) {
        super(ASTNodeType.DEFAULT_STATEMENT, line, column);
        this.statements = statements;
    }
    
    public StatementNode[] getStatements() { return statements; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDefaultStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return statements;
    }
}

/**
 * break 语句节点
 */
class BreakStatementNode extends StatementNode {
    public BreakStatementNode(int line, int column) {
        super(ASTNodeType.BREAK_STATEMENT, line, column);
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBreakStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * continue 语句节点
 */
class ContinueStatementNode extends StatementNode {
    public ContinueStatementNode(int line, int column) {
        super(ASTNodeType.CONTINUE_STATEMENT, line, column);
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitContinueStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}

/**
 * return 语句节点
 */
class ReturnStatementNode extends StatementNode {
    private ExpressionNode expression;
    
    public ReturnStatementNode(ExpressionNode expression, int line, int column) {
        super(ASTNodeType.RETURN_STATEMENT, line, column);
        this.expression = expression;
    }
    
    public ExpressionNode getExpression() { return expression; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitReturnStatement(this);
    }
    
    @Override
    public ASTNode[] getChildren() {
        return expression != null ? new ASTNode[]{expression} : new ASTNode[0];
    }
}
