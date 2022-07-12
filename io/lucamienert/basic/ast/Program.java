package io.lucamienert.basic.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.lucamienert.basic.instruction.InstructionSequence;

public class Program {

    private final List<Line> lines;

    public Program(Line... lines) {
        this.lines = Collections.unmodifiableList(Arrays.asList(lines));
    }

    public Program(List<Line> lines) {
        this.lines = Collections.unmodifiableList(new ArrayList<>(lines));
    }

    public List<Line> getLines() {
        return lines;
    }

    public InstructionSequence compile() {
        InstructionSequence seq = new InstructionSequence();
        for (Line line : lines)
            line.compile(seq);

        return seq;
    }
    
}
