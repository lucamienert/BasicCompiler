package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class Line {

    private final int number;
    private final Statement statement;

    public Line(int number, Statement statement) {
        this.number = number;
        this.statement = statement;
    }

    public int getNumber() {
        return number;
    }

    public Statement getStatement() {
        return statement;
    }

    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(OpCode.LABEL, seq.createLineLabel(number)));
        statement.compile(seq);
    }
    
}
