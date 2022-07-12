package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryExpression(UnaryOperator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void compile(InstructionSequence seq) {
        switch (operator) {
            case PLUS:
                expression.compile(seq);
                break;
            case MINUS:
                seq.append(new Instruction(OpCode.PUSHI, 0));
                expression.compile(seq);
                seq.append(new Instruction(OpCode.SUB));
                break;
            default:
                throw new AssertionError();
        }
    }
    
}
