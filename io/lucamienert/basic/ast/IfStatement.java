package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class IfStatement extends Statement {

    private final RelationalOperator operator;
    private final Expression leftExpression;
    private final Expression rightExpression;
    private final Statement statement;

    public IfStatement(RelationalOperator operator, Expression leftExpression, Expression rightExpression, Statement statement) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.statement = statement;
    }

    public RelationalOperator getOperator() {
        return operator;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void compile(InstructionSequence seq) {
        leftExpression.compile(seq);
        rightExpression.compile(seq);

        String thenLabel = seq.createGeneratedLabel();
        String endLabel = seq.createGeneratedLabel();

        OpCode opcode;
        switch (operator) {
            case EQ:
                opcode = OpCode.JMPEQ;
                break;
            case NE:
                opcode = OpCode.JMPNE;
                break;
            case LTE:
                opcode = OpCode.JMPLTE;
                break;
            case LT:
                opcode = OpCode.JMPLT;
                break;
            case GT:
                opcode = OpCode.JMPGT;
                break;
            case GTE:
                opcode = OpCode.JMPGTE;
                break;
            default:
                throw new AssertionError();
        }

        seq.append(
            new Instruction(opcode, thenLabel),
            new Instruction(OpCode.JMP, endLabel),
            new Instruction(OpCode.LABEL, thenLabel)
        );

        statement.compile(seq);

        seq.append(new Instruction(OpCode.LABEL, endLabel));
    }
    
}
