package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.InstructionSequence;

public abstract class StringExpression {

    public abstract void compile(InstructionSequence seq);

}