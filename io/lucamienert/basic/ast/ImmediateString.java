package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class ImmediateString extends StringExpression {

    private final String value;

    public ImmediateString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(OpCode.PUSHS, value));
    }

}
