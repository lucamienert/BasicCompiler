package io.lucamienert.basic.instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InstructionSequence {

    private final List<Instruction> instructions = new ArrayList<>();
    private int labelCounter = 0;

    public String createLineLabel(int line) {
        return "line_" + line;
    }

    public String createGeneratedLabel() {
        return "generated_" + (labelCounter++);
    }

    public void append(Instruction... instructions) {
        this.instructions.addAll(Arrays.asList(instructions));
    }

    public List<Instruction> getInstructions() {
        return Collections.unmodifiableList(instructions);
    }

}