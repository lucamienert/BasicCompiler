package io.lucamienert.basic.codegen;

import java.io.Closeable;
import java.io.IOException;

import io.lucamienert.basic.instruction.InstructionSequence;

public abstract class CodeGenerator implements Closeable {

    public abstract void generate(InstructionSequence seq) throws IOException;

}