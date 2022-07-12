package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.InstructionSequence;

public abstract class Statement {

    public abstract void compile(InstructionSequence seq);

}