package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class EndStatement extends Statement {
    
    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(OpCode.HLT));
    }

}