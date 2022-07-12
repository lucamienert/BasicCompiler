package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class BinaryExpression extends Expression {

    private final BinaryOperator operator;
    private final Expression leftExpression;
    private final Expression rightExpression;

    public BinaryExpression(BinaryOperator operator, Expression leftExpression, Expression rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void compile(InstructionSequence seq) {
        leftExpression.compile(seq);
        rightExpression.compile(seq);
        switch (operator) {
            case PLUS:
                seq.append(new Instruction(OpCode.ADD));
                break;
            case MINUS:
                seq.append(new Instruction(OpCode.SUB));
                break;
            case MULT:
                seq.append(new Instruction(OpCode.MUL));
                break;
            case DIV:
                seq.append(new Instruction(OpCode.DIV));
                break;
            default:
                throw new AssertionError();
        }
    }
}
