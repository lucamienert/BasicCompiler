package io.lucamienert.basic.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public class PrintStatement extends Statement {

    private final List<StringExpression> values;

    public PrintStatement(StringExpression... values) {
        this.values = Collections.unmodifiableList(Arrays.asList(values));
    }

    public PrintStatement(List<StringExpression> values) {
        this.values = Collections.unmodifiableList(new ArrayList<>(values));
    }

    public List<StringExpression> getValues() {
        return values;
    }

    @Override
    public void compile(InstructionSequence seq) {
        for (StringExpression value : values) {
            value.compile(seq);

            seq.append(new Instruction(value instanceof ImmediateString ? OpCode.OUTS : OpCode.OUTI));
        }

        seq.append(new Instruction(OpCode.PUSHS, "\n"), new Instruction(OpCode.OUTS));
    }
    
}
