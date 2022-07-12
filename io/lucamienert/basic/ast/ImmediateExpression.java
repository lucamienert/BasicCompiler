package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class ImmediateExpression extends Expression {

    private final int value;

    public ImmediateExpression(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(OpCode.PUSHI, value));
    }
    
}
