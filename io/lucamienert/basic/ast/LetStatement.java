package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class LetStatement extends Statement {

    private final String name;
    private final Expression value;

    public LetStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public void compile(InstructionSequence seq) {
        value.compile(seq);
        seq.append(new Instruction(OpCode.STORE, name));
    }
    
}
