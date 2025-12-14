package com.wei.compiler.ir;

import com.wei.compiler.ast.*;
import com.wei.compiler.semantic.*;
import com.wei.compiler.type.DataType;
import com.wei.compiler.type.ArrayType;
import com.wei.compiler.type.PointerType;
import com.wei.compiler.type.StructType;
import java.util.*;

/**
 * IR 生成器
 * * 将 AST 转换为三地址代码 (TAC)。
 */
public class IRGenerator {
    
    private IRProgram irProgram;
    private SymbolTableManager symbolTableManager;
    private boolean debugMode;
    
    // 当前上下文
    private String currentFunction;
    private Stack<String> breakLabels;
    private Stack<String> continueLabels;
    private Map<String, String> switchLabels;
    
    public IRGenerator() {
        this.irProgram = new IRProgram();
        this.symbolTableManager = new SymbolTableManager();
        this.debugMode = false;
        this.currentFunction = null;
        this.breakLabels = new Stack<>();
        this.continueLabels = new Stack<>();
        this.switchLabels = new HashMap<>();
    }
    
    public IRGenerator(SymbolTableManager symbolTableManager) {
        this.irProgram = new IRProgram();
        this.symbolTableManager = symbolTableManager;
        this.debugMode = false;
        this.currentFunction = null;
        this.breakLabels = new Stack<>();
        this.continueLabels = new Stack<>();
        this.switchLabels = new HashMap<>();
    }
    
    /**
     * 生成中间代码
     */
    public IRProgram generate(ASTNode ast) {
        System.out.println("\n--- 阶段三：中间代码生成 ---");
        
        // 遍历 AST 生成中间代码
        if (ast instanceof ProgramNode) {
            visitProgram((ProgramNode) ast);
        }
        
        // 显示中间代码
        if (debugMode) {
            System.out.println(irProgram);
            irProgram.displayBasicBlocks();
        }
        
        System.out.println("中间代码生成完成");
        return irProgram;
    }
    
    /**
     * 设置调试模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * 生成表达式代码并返回结果变量
     */
    private String generateExpression(ExpressionNode expression) {
        if (expression == null) {
            return null;
        }
        
        return expression.accept(new ExpressionIRGenerator());
    }
    
    // ==================== AST 访问者方法 ====================
    
    @Deprecated // 不使用visitor模式，直接调用具体方法
    public Void visitProgram(ProgramNode node) {
        // 生成所有声明的代码
        for (ASTNode declaration : node.getDeclarations()) {
            visitDeclaration(declaration);
        }
        return null;
    }
    
    private void visitDeclaration(ASTNode node) {
        if (node instanceof FunctionDeclarationNode) {
            visitFunctionDeclaration((FunctionDeclarationNode) node);
        } else if (node instanceof VariableDeclarationNode) {
            visitVariableDeclaration((VariableDeclarationNode) node);
        } else if (node instanceof StructDeclarationNode) {
            visitStructDeclaration((StructDeclarationNode) node);
        }
    }
    
    // @Override
    public Void visitStructDeclaration(StructDeclarationNode node) {
        // 结构体声明不生成代码，只记录类型信息
        return null;
    }
    
    // @Override
    public Void visitFieldDeclaration(FieldDeclarationNode node) {
        // 字段声明不生成代码
        return null;
    }
    
    // @Override
    public Void visitFunctionDeclaration(FunctionDeclarationNode node) {
        String functionName = node.getFunctionName();
        currentFunction = functionName;
        
        // 生成函数标签
        String functionLabel = irProgram.generateLabel("func_" + functionName);
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, functionLabel));
        
        // 进入函数作用域
        symbolTableManager.enterScope();
        
        // 函数体代码由visitBlock处理
        if (node.getBody() instanceof BlockNode) {
            BlockNode body = (BlockNode) node.getBody();
            for (StatementNode statement : body.getStatements()) {
                if (statement instanceof ExpressionStatementNode) {
                    visitExpressionStatement((ExpressionStatementNode) statement);
                } else if (statement instanceof IfStatementNode) {
                    visitIfStatement((IfStatementNode) statement);
                } else if (statement instanceof WhileStatementNode) {
                    visitWhileStatement((WhileStatementNode) statement);
                } else if (statement instanceof ForStatementNode) {
                    visitForStatement((ForStatementNode) statement);
                } else if (statement instanceof ReturnStatementNode) {
                    visitReturnStatement((ReturnStatementNode) statement);
                }
            }
        }
        
        // 退出函数作用域
        symbolTableManager.exitScope();
        currentFunction = null;
        
        return null;
    }
    
    // @Override
    public Void visitParameter(ParameterNode node) {
        // 参数已经在函数声明中处理
        return null;
    }
    
    // @Override
    public Void visitVariableDeclaration(VariableDeclarationNode node) {
        // 生成变量声明代码
        for (VariableDeclaratorNode declarator : node.getDeclarators()) {
            String variableName = declarator.getVariableName();
            DataType dataType = node.getType().getDataType();
            
            // 计算空间大小（字节数）
            String sizeStr = calculateTypeSize(dataType, declarator);
            
            // 创建 TACType 对象
            TACType tacType = createTACType(dataType, declarator);
            
            // 根据数据类型构建 ALLOC 指令，并保存类型信息
            TACInstruction allocInst = new TACInstruction(TACOpcode.ALLOC, sizeStr, null, variableName);
            allocInst.setResultType(dataType.toString());
            allocInst.setResultTypeObj(tacType);
            allocInst.setMetadata(declarator.getArrayDimensions() != null ? "array" : "scalar");
            irProgram.addInstruction(allocInst);
            
            // 如果有初始化表达式，生成赋值代码
            if (declarator.getInitializer() != null) {
                String initValue = generateExpression(declarator.getInitializer());
                TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, initValue, null, variableName);
                assignInst.setResultTypeObj(tacType);
                irProgram.addInstruction(assignInst);
            }
        }
        
        return null;
    }
    
    // @Override
    public Void visitVariableDeclarator(VariableDeclaratorNode node) {
        // 变量声明符已经在变量声明中处理
        return null;
    }
    
    // @Override
    public Void visitBlock(BlockNode node) {
        // 进入新的作用域
        symbolTableManager.enterScope();
        
        // 生成所有语句的代码
        for (StatementNode statement : node.getStatements()) {
            if (statement instanceof ExpressionStatementNode) {
                visitExpressionStatement((ExpressionStatementNode) statement);
            } else if (statement instanceof IfStatementNode) {
                visitIfStatement((IfStatementNode) statement);
            } else if (statement instanceof WhileStatementNode) {
                visitWhileStatement((WhileStatementNode) statement);
            } else if (statement instanceof ForStatementNode) {
                visitForStatement((ForStatementNode) statement);
            } else if (statement instanceof ReturnStatementNode) {
                visitReturnStatement((ReturnStatementNode) statement);
            }
        }
        
        // 退出作用域
        symbolTableManager.exitScope();
        
        return null;
    }
    
    // @Override
    public Void visitExpressionStatement(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            generateExpression(node.getExpression());
        }
        return null;
    }
    
    // @Override
    public Void visitIfStatement(IfStatementNode node) {
        // 生成条件表达式代码
        String condition = generateExpression(node.getCondition());
        
        // 生成跳转标签
        String elseLabel = irProgram.generateLabel("else");
        String endLabel = irProgram.generateLabel("endif");
        
        // 生成条件跳转
        irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, elseLabel));
        
        // 生成 then 语句代码
        if (node.getThenStatement() instanceof ExpressionStatementNode) {
            visitExpressionStatement((ExpressionStatementNode) node.getThenStatement());
        } else if (node.getThenStatement() instanceof BlockNode) {
            visitBlock((BlockNode) node.getThenStatement());
        } else if (node.getThenStatement() instanceof IfStatementNode) {
            visitIfStatement((IfStatementNode) node.getThenStatement());
        }
        
        // 生成跳转到结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
        
        // 生成 else 标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, elseLabel));
        
        // 生成 else 语句代码
        if (node.getElseStatement() != null) {
            if (node.getElseStatement() instanceof ExpressionStatementNode) {
                visitExpressionStatement((ExpressionStatementNode) node.getElseStatement());
            } else if (node.getElseStatement() instanceof BlockNode) {
                visitBlock((BlockNode) node.getElseStatement());
            } else if (node.getElseStatement() instanceof IfStatementNode) {
                visitIfStatement((IfStatementNode) node.getElseStatement());
            }
        }
        
        // 生成结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    // @Override
    public Void visitWhileStatement(WhileStatementNode node) {
        // 生成循环标签
        String loopLabel = irProgram.generateLabel("while");
        String bodyLabel = irProgram.generateLabel("while_body");
        String endLabel = irProgram.generateLabel("while_end");
        
        // 生成循环开始标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, loopLabel));
        
        // 生成条件表达式代码
        String condition = generateExpression(node.getCondition());
        
        // 生成条件跳转
        irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, endLabel));
        
        // 生成循环体标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, bodyLabel));
        
        // 设置 break 和 continue 标签
        breakLabels.push(endLabel);
        continueLabels.push(loopLabel);
        
        // 生成循环体代码
        if (node.getBody() instanceof BlockNode) {
            visitBlock((BlockNode) node.getBody());
        } else if (node.getBody() instanceof ExpressionStatementNode) {
            visitExpressionStatement((ExpressionStatementNode) node.getBody());
        }
        
        // 恢复 break 和 continue 标签
        breakLabels.pop();
        continueLabels.pop();
        
        // 生成跳转到循环开始
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, loopLabel));
        
        // 生成循环结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    // @Override
    public Void visitForStatement(ForStatementNode node) {
        // 生成循环标签
        String initLabel = irProgram.generateLabel("for_init");
        String conditionLabel = irProgram.generateLabel("for_condition");
        String bodyLabel = irProgram.generateLabel("for_body");
        String updateLabel = irProgram.generateLabel("for_update");
        String endLabel = irProgram.generateLabel("for_end");
        
        // 生成初始化标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, initLabel));
        
        // 生成初始化代码
        if (node.getInitialization() != null) {
            if (node.getInitialization() instanceof ExpressionStatementNode) {
                visitExpressionStatement((ExpressionStatementNode) node.getInitialization());
            } else if (node.getInitialization() instanceof VariableDeclarationNode) {
                visitVariableDeclaration((VariableDeclarationNode) node.getInitialization());
            }
        }
        
        // 生成条件标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, conditionLabel));
        
        // 生成条件表达式代码
        if (node.getCondition() != null) {
            String condition = generateExpression(node.getCondition());
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, condition, null, endLabel));
        }
        
        // 生成循环体标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, bodyLabel));
        
        // 设置 break 和 continue 标签
        breakLabels.push(endLabel);
        continueLabels.push(updateLabel);
        
        // 生成循环体代码
        if (node.getBody() instanceof BlockNode) {
            visitBlock((BlockNode) node.getBody());
        } else if (node.getBody() instanceof ExpressionStatementNode) {
            visitExpressionStatement((ExpressionStatementNode) node.getBody());
        }
        
        // 恢复 break 和 continue 标签
        breakLabels.pop();
        continueLabels.pop();
        
        // 生成更新标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, updateLabel));
        
        // 生成更新表达式代码
        if (node.getUpdate() != null) {
            generateExpression(node.getUpdate());
        }
        
        // 生成跳转到条件检查
        irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, conditionLabel));
        
        // 生成循环结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    // @Override
    public Void visitSwitchStatement(SwitchStatementNode node) {
        // 生成 switch 表达式代码
        String switchValue = generateExpression(node.getExpression());
        
        // 生成 switch 标签
        String switchLabel = irProgram.generateLabel("switch");
        String endLabel = irProgram.generateLabel("switch_end");
        
        // 生成 switch 开始标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, switchLabel));
        
        // 设置 break 标签
        breakLabels.push(endLabel);
        
        // 生成所有 case 代码
        for (CaseStatementNode caseNode : node.getCases()) {
            visitCaseStatement(caseNode);
        }
        
        // 生成 default case 代码
        if (node.getDefaultCase() != null) {
            visitDefaultStatement(node.getDefaultCase());
        }
        
        // 恢复 break 标签
        breakLabels.pop();
        
        // 生成 switch 结束标签
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
        
        return null;
    }
    
    // @Override
    public Void visitCaseStatement(CaseStatementNode node) {
        // 生成 case 值代码
        String caseValue = generateExpression(node.getValue());
        
        // 生成 case 标签
        String caseLabel = irProgram.generateLabel("case");
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, caseLabel));
        
        // 生成 case 语句代码
        for (StatementNode statement : node.getStatements()) {
            // 直接处理具体的语句类型，避免使用accept
            if (statement instanceof BlockNode) {
                visitBlock((BlockNode) statement);
            } else if (statement instanceof IfStatementNode) {
                visitIfStatement((IfStatementNode) statement);
            }
            // 其他语句类型根据需要添加
        }
        
        return null;
    }
    
    // @Override
    public Void visitDefaultStatement(DefaultStatementNode node) {
        // 生成 default 标签
        String defaultLabel = irProgram.generateLabel("default");
        irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, defaultLabel));
        
        // 生成 default 语句代码
        for (StatementNode statement : node.getStatements()) {
            // 直接处理具体的语句类型，避免使用accept
            if (statement instanceof BlockNode) {
                visitBlock((BlockNode) statement);
            } else if (statement instanceof IfStatementNode) {
                visitIfStatement((IfStatementNode) statement);
            }
            // 其他语句类型根据需要添加
        }
        
        return null;
    }
    
    // @Override
    public Void visitBreakStatement(BreakStatementNode node) {
        if (!breakLabels.isEmpty()) {
            String breakLabel = breakLabels.peek();
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, breakLabel));
        }
        return null;
    }
    
    // @Override
    public Void visitContinueStatement(ContinueStatementNode node) {
        if (!continueLabels.isEmpty()) {
            String continueLabel = continueLabels.peek();
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, continueLabel));
        }
        return null;
    }
    
    // @Override
    public Void visitReturnStatement(ReturnStatementNode node) {
        if (node.getExpression() != null) {
            String returnValue = generateExpression(node.getExpression());
            irProgram.addInstruction(new TACInstruction(TACOpcode.RETURN, returnValue, null, null));
        } else {
            irProgram.addInstruction(new TACInstruction(TACOpcode.RETURN, null, null, null));
        }
        return null;
    }
    
    // @Override
    public Void visitType(TypeNode node) {
        // 类型节点通常不需要生成代码，主要用于语义分析
        return null;
    }
    
    // @Override
    public Void visitMemberAccess(MemberAccessNode node) {
        // 结构体成员访问，需要生成相应代码
        generateExpression(node);
        return null;
    }
    
    // @Override
    public Void visitArrayAccess(ArrayAccessNode node) {
        // 数组访问，需要生成相应代码
        generateExpression(node);
        return null;
    }
    
    // @Override
    public Void visitFunctionCall(FunctionCallNode node) {
        // 函数调用，需要生成相应代码
        generateExpression(node);
        return null;
    }
    
    // @Override
    public Void visitStringLiteral(StringLiteralNode node) {
        // 字符串字面量，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitCharLiteral(CharLiteralNode node) {
        // 字符字面量，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitFloatLiteral(FloatLiteralNode node) {
        // 浮点数字面量，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitIntLiteral(IntLiteralNode node) {
        // 整数字面量，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitIdentifier(IdentifierNode node) {
        // 标识符，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitCastExpression(CastExpressionNode node) {
        // 类型转换表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitPrimaryExpression(PrimaryExpressionNode node) {
        // 主表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitPostfixExpression(PostfixExpressionNode node) {
        // 后缀表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitUnaryExpression(UnaryExpressionNode node) {
        // 一元表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
        // 乘法表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitAdditiveExpression(AdditiveExpressionNode node) {
        // 加法表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitRelationalExpression(RelationalExpressionNode node) {
        // 关系表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitEqualityExpression(EqualityExpressionNode node) {
        // 相等性表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitLogicalAndExpression(LogicalAndExpressionNode node) {
        // 逻辑与表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitLogicalOrExpression(LogicalOrExpressionNode node) {
        // 逻辑或表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitConditionalExpression(ConditionalExpressionNode node) {
        // 束件表达式，不需要额外处理
        return null;
    }
    
    // @Override
    public Void visitAssignmentExpression(AssignmentExpressionNode node) {
        // 赋值表达式，不需要额外处理
        return null;
    }
    
    /**
     * 创建 TACType 对象的辅助方法
     */
    private TACType createTACType(DataType dataType, VariableDeclaratorNode declarator) {
        // 处理基本类型
        if (dataType == DataType.INT) {
            return new TACType(TACType.TypeKind.INT);
        } else if (dataType == DataType.FLOAT) {
            return new TACType(TACType.TypeKind.FLOAT);
        } else if (dataType == DataType.CHAR) {
            return new TACType(TACType.TypeKind.CHAR);
        } else if (dataType == DataType.STRING) {
            return new TACType(TACType.TypeKind.STRING);
        } else if (dataType instanceof PointerType) {
            // ✅ 新增：处理指针类型
            PointerType pointerType = (PointerType) dataType;
            DataType targetType = pointerType.getTargetType();
            TACType tacTargetType = createTACType(targetType, null);
            return new TACType(TACType.TypeKind.POINTER, tacTargetType);
        } else if (dataType instanceof ArrayType) {
            // 对于数组类型，创建对应的 TAC 数组类型
            ArrayType arrayType = (ArrayType) dataType;
            DataType elementType = arrayType.getElementType();
            TACType tacElementType = createTACType(elementType, null);
            
            // 获取数组维度信息
            // 注意：这里的实现是简化的，实际应用中可能需要更复杂的处理
            int arraySize = 1; // 默认大小
            if (declarator != null && declarator.getArrayDimensions() != null) {
                ExpressionNode[] dimensionExprs = declarator.getArrayDimensions();
                if (dimensionExprs.length > 0 && dimensionExprs[0] instanceof IntLiteralNode) {
                    arraySize = ((IntLiteralNode) dimensionExprs[0]).getValue();
                }
            }
            
            return new TACType(tacElementType, arraySize);
        } else if (dataType instanceof StructType) {
            // 对于结构体类型，创建对应的 TAC 结构体类型
            StructType structType = (StructType) dataType;
            String structName = structType.getName();
            StructInfo structInfo = structType.getStructInfo();
            
            // 构建结构体字段信息
            List<TACType.StructField> fields = new ArrayList<>();
            if (structInfo != null) {
                Map<String, SymbolEntry> structFields = structInfo.getFields();
                int offset = 0;
                for (Map.Entry<String, SymbolEntry> entry : structFields.entrySet()) {
                    String fieldName = entry.getKey();
                    SymbolEntry fieldEntry = entry.getValue();
                    if (fieldEntry != null && fieldEntry.getDataType() != null) {
                        TACType fieldType = createTACType(fieldEntry.getDataType(), null);
                        fields.add(new TACType.StructField(fieldName, fieldType, offset));
                        // 简化处理：假设每个字段占4字节
                        offset += 4;
                    }
                }
            }
            
            return new TACType(structName, fields);
        }
        
        // 默认返回 int 类型
        return new TACType(TACType.TypeKind.INT);
    }
    
    /**
     * 计算数据类型的大小（字节数）
     */
    private String calculateTypeSize(DataType dataType, VariableDeclaratorNode declarator) {
        if (dataType == DataType.FLOAT) {
            return "8";  // float为8字节
        } else if (dataType == DataType.INT) {
            return "4";  // int为4字节
        } else if (dataType == DataType.CHAR) {
            return "1";  // char为1字节
        } else if (dataType == DataType.STRING) {
            return "256";  // string简化处理，分配256字节
        } else if (dataType instanceof ArrayType) {
            // 数组：需要计算每个元素的大小乘以总个数
            ArrayType arrayType = (ArrayType) dataType;
            DataType elementType = arrayType.getElementType();
            String elementSizeStr = calculateTypeSize(elementType, null);
            
            // 获取数组维度信息
            int totalElements = 1;
            if (declarator != null && declarator.getArrayDimensions() != null) {
                ExpressionNode[] dimensionExprs = declarator.getArrayDimensions();
                for (ExpressionNode dimExpr : dimensionExprs) {
                    if (dimExpr instanceof IntLiteralNode) {
                        totalElements *= ((IntLiteralNode) dimExpr).getValue();
                    }
                }
            }
            
            try {
                int elementSize = Integer.parseInt(elementSizeStr);
                return String.valueOf(elementSize * totalElements);
            } catch (NumberFormatException e) {
                return "4";  // 默认值
            }
        } else if (dataType instanceof StructType) {
            // struct：从StructInfo获取实际大小
            StructType structType = (StructType) dataType;
            StructInfo structInfo = structType.getStructInfo();
            if (structInfo != null) {
                int size = structInfo.getSizeInBytes();
                if (size > 0) {
                    return String.valueOf(size);
                }
            }
            // 如果无法获取实际大小，则根据字段数估算
            return "32";  // 预设值，简化处理
        }
        return "4";  // 默认值
    }

    
    /**
     * 表达式结果类，包含值、类型和地址信息
     */
    public static class ExpressionResult {
        public String value;           // 临时变量或常量
        public TACType type;           // 类型信息
        public AddressTAC addressInfo; // 地址信息（用于数组元素、结构体字段）
        public boolean isLValue;       // 是否是左值
        
        public ExpressionResult(String value, TACType type) {
            this.value = value;
            this.type = type;
            this.addressInfo = null;
            this.isLValue = false;
        }
        
        public ExpressionResult(String value, TACType type, boolean isLValue) {
            this.value = value;
            this.type = type;
            this.addressInfo = null;
            this.isLValue = isLValue;
        }
        
        public ExpressionResult(String value, TACType type, AddressTAC addressInfo, boolean isLValue) {
            this.value = value;
            this.type = type;
            this.addressInfo = addressInfo;
            this.isLValue = isLValue;
        }
    }
    
    /**
     * 获取表达式的数据类型（从符号表）
     */
    private TACType getExpressionType(ExpressionNode expr) {
        if (expr instanceof IntLiteralNode) {
            return new TACType(TACType.TypeKind.INT);
        } else if (expr instanceof FloatLiteralNode) {
            return new TACType(TACType.TypeKind.FLOAT);
        } else if (expr instanceof CharLiteralNode) {
            return new TACType(TACType.TypeKind.CHAR);
        } else if (expr instanceof StringLiteralNode) {
            return new TACType(TACType.TypeKind.STRING);
        } else if (expr instanceof IdentifierNode) {
            // 从符号表查询类型
            IdentifierNode id = (IdentifierNode) expr;
            SymbolEntry symbol = symbolTableManager.lookupSymbol(id.getName());
            if (symbol != null && symbol.getDataType() != null) {
                TACType tacType = createTACType(symbol.getDataType(), null);
                // ✅ 数组退化为指针：在表达式上下文中，数组名视为指向首元素的指针
                if (tacType.isArray()) {
                    return new TACType(TACType.TypeKind.POINTER, tacType.getElementType());
                }
                return tacType;
            }
            return new TACType(TACType.TypeKind.INT); // 默认值
        } else if (expr instanceof ArrayAccessNode) {
            ArrayAccessNode arrayAccess = (ArrayAccessNode) expr;
            TACType baseType = getExpressionType(arrayAccess.getArray());
            // 数组访问返回元素类型
            if (baseType.isArray()) {
                return baseType.getElementType();
            }
            return baseType;
        } else if (expr instanceof AdditiveExpressionNode) {
            // 添减法需要下附部指针运算
            AdditiveExpressionNode binOp = (AdditiveExpressionNode) expr;
            TACType left = getExpressionType(binOp.getLeft());
            TACType right = getExpressionType(binOp.getRight());
                    
            // 检查是否是指针运算
            if (binOp.getOperator() == AdditiveOperator.ADD) {
                // ptr + int 或 int + ptr → 返回指针类型
                if ((left.isPointer() || left.isAddress()) && right.isInt()) {
                    return left;  // 结果是指针类型
                }
                if (left.isInt() && (right.isPointer() || right.isAddress())) {
                    return right;  // 结果是指针类型
                }
            } else if (binOp.getOperator() == AdditiveOperator.SUBTRACT) {
                // ptr - int → 返回指针类型
                if ((left.isPointer() || left.isAddress()) && right.isInt()) {
                    return left;  // 结果是指针类型
                }
                // ptr - ptr → 返回整数类型
                if ((left.isPointer() || left.isAddress()) && (right.isPointer() || right.isAddress())) {
                    return new TACType(TACType.TypeKind.INT);
                }
            }
                    
            // 普通数值运算：简化，有float则返回float
            if (left.isFloat() || right.isFloat()) {
                return new TACType(TACType.TypeKind.FLOAT);
            }
            return new TACType(TACType.TypeKind.INT);
        }
        // 默认为int
        return new TACType(TACType.TypeKind.INT);
    }
    
    /**
     * 生成类型转换指令（如果需要）
     */
    private String generateCastIfNeeded(String value, TACType fromType, TACType toType) {
        if (fromType.equals(toType)) {
            return value;
        }
        
        // 需要类型转换
        String result = irProgram.generateTempVar();
        String castOpcode;
        if (fromType.isInt() && toType.isFloat()) {
            castOpcode = "cast_i2f";
        } else if (fromType.isFloat() && toType.isInt()) {
            castOpcode = "cast_f2i";
        } else {
            // 其他类型转换
            castOpcode = "cast";
        }
        
        TACInstruction castInst = new TACInstruction(TACOpcode.CAST, value, null, result);
        castInst.setMetadata(castOpcode);
        irProgram.addInstruction(castInst);
        
        return result;
    }
    
    /**
     * 生成 i++ 的展开式
     */
    private ExpressionResult generatePostIncrement(String operand, TACType type) {
        // i++ 被展开为：
        // t1 = i        // 保存原始值
        // i = i + 1     // 自增
        // 返回 t1
        
        String oldValue = irProgram.generateTempVar();
        String tempOne = "1";
        String newValue = irProgram.generateTempVar();
        
        // t1 = i
        TACInstruction loadInst = new TACInstruction(TACOpcode.ASSIGN, operand, null, oldValue);
        loadInst.setResultTypeObj(type);
        irProgram.addInstruction(loadInst);
        
        // t2 = i + 1
        TACInstruction addInst = new TACInstruction(TACOpcode.ADD, operand, tempOne, newValue);
        addInst.setResultTypeObj(type);
        irProgram.addInstruction(addInst);
        
        // i = t2
        TACInstruction storeInst = new TACInstruction(TACOpcode.ASSIGN, newValue, null, operand);
        storeInst.setResultTypeObj(type);
        irProgram.addInstruction(storeInst);
        
        // 返回旧值
        return new ExpressionResult(oldValue, type);
    }
    
    /**
     * 生成 ++i 的展开式
     */
    private ExpressionResult generatePreIncrement(String operand, TACType type) {
        // ++i 被展开为：
        // i = i + 1
        // 返回 i
        
        String tempOne = "1";
        String newValue = irProgram.generateTempVar();
        
        // t1 = i + 1
        TACInstruction addInst = new TACInstruction(TACOpcode.ADD, operand, tempOne, newValue);
        addInst.setResultTypeObj(type);
        irProgram.addInstruction(addInst);
        
        // i = t1
        TACInstruction storeInst = new TACInstruction(TACOpcode.ASSIGN, newValue, null, operand);
        storeInst.setResultTypeObj(type);
        irProgram.addInstruction(storeInst);
        
        // 返回新值
        return new ExpressionResult(newValue, type);
    }
    
    private class ExpressionIRGenerator implements ASTVisitor<String> {
        
        // @Override
        public String visitProgram(ProgramNode node) {
            return null;
        }
        
        // @Override
        public String visitStructDeclaration(StructDeclarationNode node) {
            return null;
        }
        
        // @Override
        public String visitFieldDeclaration(FieldDeclarationNode node) {
            return null;
        }
        
        // @Override
        public String visitFunctionDeclaration(FunctionDeclarationNode node) {
            return null;
        }
        
        // @Override
        public String visitParameter(ParameterNode node) {
            return null;
        }
        
        // @Override
        public String visitVariableDeclaration(VariableDeclarationNode node) {
            return null;
        }
        
        // @Override
        public String visitVariableDeclarator(VariableDeclaratorNode node) {
            return null;
        }
        
        // @Override
        public String visitBlock(BlockNode node) {
            return null;
        }
        
        // @Override
        public String visitExpressionStatement(ExpressionStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitIfStatement(IfStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitWhileStatement(WhileStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitForStatement(ForStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitSwitchStatement(SwitchStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitCaseStatement(CaseStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitDefaultStatement(DefaultStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitBreakStatement(BreakStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitContinueStatement(ContinueStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitReturnStatement(ReturnStatementNode node) {
            return null;
        }
        
        // @Override
        public String visitAssignmentExpression(AssignmentExpressionNode node) {
            ExpressionNode leftExpr = node.getLeft();
            String rightValue = node.getRight().accept(this);
            TACType rightType = getExpressionType(node.getRight());
            
            // 检查左侧是否是数组访问或结构体字段，需要特殊处理
            if (leftExpr instanceof ArrayAccessNode) {
                // 处理 matrix[i][j] = value
                ArrayAccessNode arrayAccess = (ArrayAccessNode) leftExpr;
                String array = arrayAccess.getArray().accept(this);
                String index = arrayAccess.getIndex().accept(this);
                
                TACType arrayType = getExpressionType(arrayAccess.getArray());
                TACType elementType = arrayType.isArray() ? arrayType.getElementType() : new TACType(TACType.TypeKind.INT);
                
                // 转换类型（如果需要）
                rightValue = generateCastIfNeeded(rightValue, rightType, elementType);
                
                // 计算地址
                String addrVar = irProgram.generateTempVar();
                TACInstruction addrInst = new TACInstruction(TACOpcode.ARRAY_INDEX, array, index, addrVar);
                AddressTAC addressInfo = new AddressTAC(array, new String[]{index}, elementType);
                addrInst.setAddressInfo(addressInfo);
                addrInst.setResultTypeObj(new TACType(TACType.TypeKind.POINTER));
                irProgram.addInstruction(addrInst);
                
                // 忽略val，简化为直接 STORE
                // 实际应该是 store(rightValue, *addrVar)
                TACInstruction storeInst = new TACInstruction(TACOpcode.STORE, rightValue, addrVar, null);
                storeInst.setResultTypeObj(elementType);
                irProgram.addInstruction(storeInst);
                
                return array; // 或者返回 rightValue
                
            } else if (leftExpr instanceof MemberAccessNode) {
                // 处理 p.x = value
                MemberAccessNode memberAccess = (MemberAccessNode) leftExpr;
                String object = memberAccess.getObject().accept(this);
                String memberName = memberAccess.getMemberName();
                
                // 简化处理：假设字段类型为int
                TACType fieldType = new TACType(TACType.TypeKind.INT);
                rightValue = generateCastIfNeeded(rightValue, rightType, fieldType);
                
                // 计算地址
                String addrVar = irProgram.generateTempVar();
                TACInstruction addrInst = new TACInstruction(TACOpcode.GET_FIELD_ADDR, object, memberName, addrVar);
                AddressTAC addressInfo = new AddressTAC(object, memberName, 0, fieldType);
                addrInst.setAddressInfo(addressInfo);
                addrInst.setResultTypeObj(new TACType(TACType.TypeKind.POINTER));
                irProgram.addInstruction(addrInst);
                
                // STORE
                TACInstruction storeInst = new TACInstruction(TACOpcode.STORE, rightValue, addrVar, null);
                storeInst.setResultTypeObj(fieldType);
                irProgram.addInstruction(storeInst);
                
                return object;
                
            } else {
                // 正常的変量赋值
                String leftValue = leftExpr.accept(this);
                TACType leftType = getExpressionType(leftExpr);
                
                // 转换类型（如果需要）
                rightValue = generateCastIfNeeded(rightValue, rightType, leftType);
                
                // 根据赋值算法符的正常处理
                switch (node.getOperator()) {
                    case ASSIGN: {
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, rightValue, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                    case PLUS_ASSIGN: {
                        String temp = irProgram.generateTempVar();
                        TACInstruction addInst = new TACInstruction(TACOpcode.ADD, leftValue, rightValue, temp);
                        addInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(addInst);
                        
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, temp, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                    case MINUS_ASSIGN: {
                        String temp = irProgram.generateTempVar();
                        TACInstruction subInst = new TACInstruction(TACOpcode.SUB, leftValue, rightValue, temp);
                        subInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(subInst);
                        
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, temp, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                    case MULTIPLY_ASSIGN: {
                        String temp = irProgram.generateTempVar();
                        TACInstruction mulInst = new TACInstruction(TACOpcode.MUL, leftValue, rightValue, temp);
                        mulInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(mulInst);
                        
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, temp, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                    case DIVIDE_ASSIGN: {
                        String temp = irProgram.generateTempVar();
                        TACInstruction divInst = new TACInstruction(TACOpcode.DIV, leftValue, rightValue, temp);
                        divInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(divInst);
                        
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, temp, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                    case MODULO_ASSIGN: {
                        String temp = irProgram.generateTempVar();
                        TACInstruction modInst = new TACInstruction(TACOpcode.MOD, leftValue, rightValue, temp);
                        modInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(modInst);
                        
                        TACInstruction assignInst = new TACInstruction(TACOpcode.ASSIGN, temp, null, leftValue);
                        assignInst.setResultTypeObj(leftType);
                        irProgram.addInstruction(assignInst);
                        break;
                    }
                }
                
                return leftValue;
            }
        }
        
        // @Override
        public String visitConditionalExpression(ConditionalExpressionNode node) {
            String condition = node.getCondition().accept(this);
            String trueValue = node.getTrueExpression().accept(this);
            String falseValue = node.getFalseExpression().accept(this);
            
            String result = irProgram.generateTempVar();
            String trueLabel = irProgram.generateLabel("true");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成条件跳转
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, condition, null, trueLabel));
            
            // 生成 false 分支
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, falseValue, null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 true 分支
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, trueLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, trueValue, null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        // @Override
        public String visitLogicalOrExpression(LogicalOrExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            String trueLabel = irProgram.generateLabel("true");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成左操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, left, null, trueLabel));
            
            // 生成右操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_NONZERO, right, null, trueLabel));
            
            // 生成 false 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "0", null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 true 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, trueLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "1", null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        // @Override
        public String visitLogicalAndExpression(LogicalAndExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            String falseLabel = irProgram.generateLabel("false");
            String endLabel = irProgram.generateLabel("end");
            
            // 生成左操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, left, null, falseLabel));
            
            // 生成右操作数检查
            irProgram.addInstruction(new TACInstruction(TACOpcode.IF_ZERO, right, null, falseLabel));
            
            // 生成 true 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "1", null, result));
            irProgram.addInstruction(new TACInstruction(TACOpcode.GOTO, null, null, endLabel));
            
            // 生成 false 结果
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, falseLabel));
            irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, "0", null, result));
            
            // 生成结束标签
            irProgram.addInstruction(new TACInstruction(TACOpcode.LABEL, null, null, endLabel));
            
            return result;
        }
        
        // @Override
        public String visitEqualityExpression(EqualityExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode = (node.getOperator() == EqualityOperator.EQUAL) ? TACOpcode.EQ : TACOpcode.NE;
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        // @Override
        public String visitRelationalExpression(RelationalExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode;
            switch (node.getOperator()) {
                case LESS_THAN: opcode = TACOpcode.LT; break;
                case GREATER_THAN: opcode = TACOpcode.GT; break;
                case LESS_EQUAL: opcode = TACOpcode.LE; break;
                case GREATER_EQUAL: opcode = TACOpcode.GE; break;
                default: opcode = TACOpcode.LT; break;
            }
            
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        // @Override
        public String visitAdditiveExpression(AdditiveExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            // 获取左右操作数的类型
            TACType leftType = getExpressionType(node.getLeft());
            TACType rightType = getExpressionType(node.getRight());
            AdditiveOperator op = node.getOperator();
            
            // 检查是否是指针运算
            if ((leftType.isPointer() || leftType.isAddress()) && rightType.isInt()) {
                // 情况 1: ptr + int 或 ptr - int
                String result = irProgram.generateTempVar();
                // 指针运算需要考虑指针大小
                TACOpcode opcode = (op == AdditiveOperator.ADD) ? TACOpcode.ADD : TACOpcode.SUB;
                TACInstruction inst = new TACInstruction(opcode, left, right, result);
                inst.setArg1Type(leftType);
                inst.setArg2Type(rightType);
                inst.setResultTypeObj(leftType);
                irProgram.addInstruction(inst);
                return result;
            } else if (op == AdditiveOperator.ADD && rightType.isPointer() && leftType.isInt()) {
                // 情况 2: int + ptr
                String result = irProgram.generateTempVar();
                TACInstruction inst = new TACInstruction(TACOpcode.ADD, right, left, result);
                inst.setArg1Type(rightType);
                inst.setArg2Type(leftType);
                inst.setResultTypeObj(rightType);
                irProgram.addInstruction(inst);
                return result;
            } else if (op == AdditiveOperator.SUBTRACT && (leftType.isPointer() || leftType.isAddress()) && 
                       (rightType.isPointer() || rightType.isAddress())) {
                // 情况 3: ptr - ptr (指针差值)
                String result = irProgram.generateTempVar();
                TACInstruction inst = new TACInstruction(TACOpcode.SUB, left, right, result);
                inst.setArg1Type(leftType);
                inst.setArg2Type(rightType);
                inst.setResultTypeObj(new TACType(TACType.TypeKind.INT));
                irProgram.addInstruction(inst);
                return result;
            }
            
            // 普通数值运算
            // 不一致的类型需要转换（例如 float + int）
            TACType resultType;
            if (leftType.isFloat() || rightType.isFloat()) {
                resultType = new TACType(TACType.TypeKind.FLOAT);
                left = generateCastIfNeeded(left, leftType, resultType);
                right = generateCastIfNeeded(right, rightType, resultType);
            } else {
                resultType = new TACType(TACType.TypeKind.INT);
            }
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode = (op == AdditiveOperator.ADD) ? TACOpcode.ADD : TACOpcode.SUB;
            TACInstruction inst = new TACInstruction(opcode, left, right, result);
            inst.setResultTypeObj(resultType);
            irProgram.addInstruction(inst);
            
            return result;
        }
        
        // @Override
        public String visitMultiplicativeExpression(MultiplicativeExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            
            String result = irProgram.generateTempVar();
            
            TACOpcode opcode;
            switch (node.getOperator()) {
                case MULTIPLY: opcode = TACOpcode.MUL; break;
                case DIVIDE: opcode = TACOpcode.DIV; break;
                case MODULO: opcode = TACOpcode.MOD; break;
                default: opcode = TACOpcode.MUL; break;
            }
            
            irProgram.addInstruction(new TACInstruction(opcode, left, right, result));
            
            return result;
        }
        
        // @Override
        public String visitUnaryExpression(UnaryExpressionNode node) {
            ExpressionNode operandExpr = node.getOperand();
            
            switch (node.getOperator()) {
                case PRE_INCREMENT:
                    // ++i 的展开
                    if (operandExpr instanceof IdentifierNode) {
                        String operand = ((IdentifierNode) operandExpr).getName();
                        TACType type = getExpressionType(operandExpr);
                        ExpressionResult preIncResult = generatePreIncrement(operand, type);
                        return preIncResult.value;
                    }
                    return operandExpr.accept(this);
                    
                case PRE_DECREMENT:
                    // --i 的展开
                    if (operandExpr instanceof IdentifierNode) {
                        String operand = ((IdentifierNode) operandExpr).getName();
                        TACType type = getExpressionType(operandExpr);
                        
                        String tempOne = "1";
                        String newValue = irProgram.generateTempVar();
                        
                        // t1 = i - 1
                        TACInstruction subInst = new TACInstruction(TACOpcode.SUB, operand, tempOne, newValue);
                        subInst.setResultTypeObj(type);
                        irProgram.addInstruction(subInst);
                        
                        // i = t1
                        TACInstruction storeInst = new TACInstruction(TACOpcode.ASSIGN, newValue, null, operand);
                        storeInst.setResultTypeObj(type);
                        irProgram.addInstruction(storeInst);
                        
                        return newValue;
                    }
                    return operandExpr.accept(this);
                    
                case PLUS:
                    String operand = operandExpr.accept(this);
                    String result = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.ASSIGN, operand, null, result));
                    return result;
                    
                case MINUS:
                    operand = operandExpr.accept(this);
                    result = irProgram.generateTempVar();
                    TACType opType = getExpressionType(operandExpr);
                    TACInstruction negInst = new TACInstruction(TACOpcode.SUB, "0", operand, result);
                    negInst.setResultTypeObj(opType);
                    irProgram.addInstruction(negInst);
                    return result;
                    
                case NOT:
                    operand = operandExpr.accept(this);
                    result = irProgram.generateTempVar();
                    irProgram.addInstruction(new TACInstruction(TACOpcode.NOT, operand, null, result));
                    return result;
                    
                default:
                    return operandExpr.accept(this);
            }
        }
        
        // @Override
        public String visitPostfixExpression(PostfixExpressionNode node) {
            // 应该是一个后缀表达式
            ExpressionNode operandExpr = node.getOperand();
            
            // 仅支持对标识符的操作
            if (!(operandExpr instanceof IdentifierNode)) {
                return operandExpr.accept(this); // 预估只返回值
            }
            
            String operand = ((IdentifierNode) operandExpr).getName();
            TACType type = getExpressionType(operandExpr);
            
            switch (node.getOperator()) {
                case INCREMENT:
                    // i++ 展开式
                    ExpressionResult postIncResult = generatePostIncrement(operand, type);
                    return postIncResult.value;
                case DECREMENT:
                    // i-- 展开式（类似）
                    String oldValue = irProgram.generateTempVar();
                    String tempOne = "1";
                    String newValue = irProgram.generateTempVar();
                    
                    TACInstruction loadInst = new TACInstruction(TACOpcode.ASSIGN, operand, null, oldValue);
                    loadInst.setResultTypeObj(type);
                    irProgram.addInstruction(loadInst);
                    
                    TACInstruction subInst = new TACInstruction(TACOpcode.SUB, operand, tempOne, newValue);
                    subInst.setResultTypeObj(type);
                    irProgram.addInstruction(subInst);
                    
                    TACInstruction storeInst = new TACInstruction(TACOpcode.ASSIGN, newValue, null, operand);
                    storeInst.setResultTypeObj(type);
                    irProgram.addInstruction(storeInst);
                    
                    return oldValue;
                default:
                    return operand;
            }
        }
        
        // @Override
        public String visitPrimaryExpression(PrimaryExpressionNode node) {
            // 主表达式的处理
            return null;
        }
        
        // @Override
        public String visitCastExpression(CastExpressionNode node) {
            // 类型转换表达式的处理
            return null;
        }
        
        // @Override
        public String visitIdentifier(IdentifierNode node) {
            return node.getName();
        }
        
        // @Override
        public String visitIntLiteral(IntLiteralNode node) {
            return String.valueOf(node.getValue());
        }
        
        // @Override
        public String visitFloatLiteral(FloatLiteralNode node) {
            return String.valueOf(node.getValue());
        }
        
        // @Override
        public String visitCharLiteral(CharLiteralNode node) {
            return String.valueOf((int) node.getValue());
        }
        
        // @Override
        public String visitStringLiteral(StringLiteralNode node) {
            return "\"" + node.getValue() + "\"";
        }
        
        // @Override
        public String visitFunctionCall(FunctionCallNode node) {
            String functionName = node.getFunctionName();
            
            // 生成参数代码
            for (ExpressionNode arg : node.getArguments()) {
                String argValue = arg.accept(this);
                irProgram.addInstruction(new TACInstruction(TACOpcode.ARG, argValue, null, null));
            }
            
            // 生成函数调用
            String result = irProgram.generateTempVar();
            irProgram.addInstruction(new TACInstruction(TACOpcode.CALL, functionName, null, result));
            
            return result;
        }
        
        // @Override
        public String visitArrayAccess(ArrayAccessNode node) {
            String array = node.getArray().accept(this);
            String index = node.getIndex().accept(this);
            
            // 获取数组类型信息
            TACType arrayType = getExpressionType(node.getArray());
            TACType elementType = arrayType.isArray() ? arrayType.getElementType() : new TACType(TACType.TypeKind.INT);
            
            // 模拟 LLVM IR 的 getelementptr(matrix, i, j) + load 操作
            // 简化处理：特别执行 ARRAY_INDEX_ADDR 指令（类似 LLVM IR 的 GEP）
            String addrVar = irProgram.generateTempVar();
            
            // 生成数组素素地址计算（预类 GEP）
            TACInstruction addrInst = new TACInstruction(TACOpcode.ARRAY_INDEX, array, index, addrVar);
            AddressTAC addressInfo = new AddressTAC(array, new String[]{index}, elementType);
            addrInst.setAddressInfo(addressInfo);
            addrInst.setResultTypeObj(new TACType(TACType.TypeKind.POINTER)); // 地址的类型是指针
            irProgram.addInstruction(addrInst);
            
            // 简化为直接返回访问的值（不释放）
            // 实际应该是 load(*addrVar, elementType) → result
            String result = irProgram.generateTempVar();
            TACInstruction loadInst = new TACInstruction(TACOpcode.LOAD, addrVar, null, result);
            loadInst.setResultTypeObj(elementType);
            irProgram.addInstruction(loadInst);
            
            return result;
        }
        
        // @Override
        public String visitMemberAccess(MemberAccessNode node) {
            String object = node.getObject().accept(this);
            String memberName = node.getMemberName();
            
            // 获取结构体类型，查找字段信息
            TACType objectType = getExpressionType(node.getObject());
            // 从符号表查找字段的值位位移和类型（简化处理：假设字段位位移为0，类型为int）
            int fieldOffset = 0;
            TACType fieldType = new TACType(TACType.TypeKind.INT);
            
            // 模拟 LLVM IR 的 getelementptr(p, 0) + load 操作
            // 简化处理：特别执行 GET_FIELD_ADDR 指令（类似 LLVM IR 的 GEP）
            String addrVar = irProgram.generateTempVar();
            
            // 生成结构体字段地址计算（预类 GEP）
            TACInstruction addrInst = new TACInstruction(TACOpcode.GET_FIELD_ADDR, object, memberName, addrVar);
            AddressTAC addressInfo = new AddressTAC(object, memberName, fieldOffset, fieldType);
            addrInst.setAddressInfo(addressInfo);
            addrInst.setResultTypeObj(new TACType(TACType.TypeKind.POINTER));
            irProgram.addInstruction(addrInst);
            
            // 简化为直接返回访问的值
            // 实际应该是 load(*addrVar, fieldType) → result
            String result = irProgram.generateTempVar();
            TACInstruction loadInst = new TACInstruction(TACOpcode.LOAD, addrVar, null, result);
            loadInst.setResultTypeObj(fieldType);
            irProgram.addInstruction(loadInst);
            
            return result;
        }
        
        // @Override
        public String visitType(TypeNode node) {
            return null;
        }
        
        @Override
        public String visitTypeNameNode(TypeNameNode node) {
            return null;
        }
        
        @Override
        public String visitShiftExpressionNode(ShiftExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            String result = irProgram.generateTempVar();
            // 不结记整个蒸发：仅返回结果
            return result;
        }
        
        @Override
        public String visitBitwiseExpressionNode(BitwiseExpressionNode node) {
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            String result = irProgram.generateTempVar();
            // 不结记整个蒸发：仅返回结果
            return result;
        }
        
        @Override
        public String visitDoWhileStatementNode(DoWhileStatementNode node) {
            return null;
        }
        
        @Override
        public String visitDeclaratorNode(DeclaratorNode node) {
            return null;
        }
    }
}
