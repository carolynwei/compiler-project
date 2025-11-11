package com.gemini.compiler.ir;

/**
 * 三地址代码指令
 * 
 * 使用四元式表示：(操作码, 操作数1, 操作数2, 结果)
 */
public class TACInstruction {
    private TACOpcode opcode;
    private String arg1;
    private String arg2;
    private String result;
    private int line;
    private String metadata;  // Added for optimizers
    private String resultType; // Added for optimizers
    
    public TACInstruction(TACOpcode opcode, String arg1, String arg2, String result) {
        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
        this.line = -1;
        this.metadata = null;
        this.resultType = null;
    }
    
    public TACInstruction(TACOpcode opcode, String arg1, String arg2, String result, int line) {
        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
        this.line = line;
        this.metadata = null;
        this.resultType = null;
    }
    
    // Getters
    public TACOpcode getOpcode() { return opcode; }
    public String getArg1() { return arg1; }
    public String getArg2() { return arg2; }
    public String getResult() { return result; }
    public int getLine() { return line; }
    public String getMetadata() { return metadata; }  // Added method
    public String getResultType() { return resultType; } // Added method

    public void setLine(int line) { this.line = line; }
    public void setMetadata(String metadata) { this.metadata = metadata; }  // Added method
    public void setResultType(String resultType) { this.resultType = resultType; } // Added method

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        switch (opcode) {
            case ADD:
                sb.append(result).append(" = ").append(arg1).append(" + ").append(arg2);
                break;
            case SUB:
                sb.append(result).append(" = ").append(arg1).append(" - ").append(arg2);
                break;
            case MUL:
                sb.append(result).append(" = ").append(arg1).append(" * ").append(arg2);
                break;
            case DIV:
                sb.append(result).append(" = ").append(arg1).append(" / ").append(arg2);
                break;
            case MOD:
                sb.append(result).append(" = ").append(arg1).append(" % ").append(arg2);
                break;
            case EQ:
                sb.append(result).append(" = ").append(arg1).append(" == ").append(arg2);
                break;
            case NE:
                sb.append(result).append(" = ").append(arg1).append(" != ").append(arg2);
                break;
            case LT:
                sb.append(result).append(" = ").append(arg1).append(" < ").append(arg2);
                break;
            case GT:
                sb.append(result).append(" = ").append(arg1).append(" > ").append(arg2);
                break;
            case LE:
                sb.append(result).append(" = ").append(arg1).append(" <= ").append(arg2);
                break;
            case GE:
                sb.append(result).append(" = ").append(arg1).append(" >= ").append(arg2);
                break;
            case AND:
                sb.append(result).append(" = ").append(arg1).append(" && ").append(arg2);
                break;
            case OR:
                sb.append(result).append(" = ").append(arg1).append(" || ").append(arg2);
                break;
            case NOT:
                sb.append(result).append(" = !").append(arg1);
                break;
            case ASSIGN:
                sb.append(result).append(" = ").append(arg1);
                break;
            case PLUS_ASSIGN:
                sb.append(result).append(" += ").append(arg1);
                break;
            case MINUS_ASSIGN:
                sb.append(result).append(" -= ").append(arg1);
                break;
            case MUL_ASSIGN:
                sb.append(result).append(" *= ").append(arg1);
                break;
            case DIV_ASSIGN:
                sb.append(result).append(" /= ").append(arg1);
                break;
            case MOD_ASSIGN:
                sb.append(result).append(" %= ").append(arg1);
                break;
            case INCREMENT:
                sb.append(result).append("++");
                break;
            case DECREMENT:
                sb.append(result).append("--");
                break;
            case GOTO:
                sb.append("goto ").append(result);
                break;
            case IF_TRUE:
                sb.append("if ").append(arg1).append(" goto ").append(result);
                break;
            case IF_FALSE:
                sb.append("if !").append(arg1).append(" goto ").append(result);
                break;
            case IF_ZERO:
                sb.append("if ").append(arg1).append(" == 0 goto ").append(result);
                break;
            case IF_NONZERO:
                sb.append("if ").append(arg1).append(" != 0 goto ").append(result);
                break;
            case LABEL:
                sb.append(result).append(":");
                break;
            case CALL:
                sb.append(result).append(" = call ").append(arg1);
                break;
            case RETURN:
                if (arg1 != null) {
                    sb.append("return ").append(arg1);
                } else {
                    sb.append("return");
                }
                break;
            case ARRAY_ACCESS:
                sb.append(result).append(" = ").append(arg1).append("[").append(arg2).append("]");
                break;
            case ARRAY_ASSIGN:
                sb.append(arg1).append("[").append(arg2).append("] = ").append(result);
                break;
            case MEMBER_ACCESS:
                sb.append(result).append(" = ").append(arg1).append(".").append(arg2);
                break;
            case MEMBER_ASSIGN:
                sb.append(arg1).append(".").append(arg2).append(" = ").append(result);
                break;
            case PARAM:
                sb.append("param ").append(arg1);
                break;
            case ARG:
                sb.append("arg ").append(arg1);
                break;
            case ALLOC:
                sb.append(result).append(" = alloc ").append(arg1);
                break;
            case LOAD:
                sb.append(result).append(" = load ").append(arg1);
                break;
            case STORE:
                sb.append("store ").append(arg1).append(" -> ").append(result);
                break;
            default:
                sb.append(opcode.toString());
        }
        
        return sb.toString();
    }
}