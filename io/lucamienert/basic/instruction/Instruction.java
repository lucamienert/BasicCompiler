package io.lucamienert.basic.instruction;

import java.util.Objects;
import java.util.Optional;

public class Instruction {

    private final OpCode opcode;
    private final Optional<string> stringOperand;
    private final Optional<string> intOperand;

    public Instruction(OpCode opcode) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.intOperand = Optional.empty();
    }

    public Instruction(OpCode opcode, String operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.intOperand = Optional.empty();
    }

    public Instruction(OpCode opcode, int operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.intOperand = Optional.of(operand);
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Optional<String> getStringOperand() {
        return stringOperand;
    }

    public Optional<Integer> getIntOperand() {
        return intOperand;
    }
}