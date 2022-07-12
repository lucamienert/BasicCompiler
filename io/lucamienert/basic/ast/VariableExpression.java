package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class VariableExpression extends Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(OpCode.LOAD, name));
    }
    
}
