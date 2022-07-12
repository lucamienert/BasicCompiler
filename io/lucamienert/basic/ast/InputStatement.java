package io.lucamienert.basic.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class InputStatement extends Statement {
    
    private final List<String> names;

    public InputStatement(String... names) {
        this.names = Collections.unmodifiableList(Arrays.asList(names));
    }

    public InputStatement(List<String> names) {
        this.names = Collections.unmodifiableList(new ArrayList<>(names));
    }

    public List<String> getNames() {
        return names;
    }

    @Override
    public void compile(InstructionSequence seq) {
        for (String name : names) {
            seq.append(
                new Instruction(OpCode.IN),
                new Instruction(OpCode.STORE, name)
            );
        }
    }
    
}
