package io.lucamienert.basic.ast;

import io.lucamienert.basic.instruction.Instruction;
import io.lucamienert.basic.instruction.InstructionSequence;
import io.lucamienert.basic.instruction.OpCode;

public final class BranchStatement extends Statement {

    private final BranchType type;
    private final int target;

    public BranchStatement(BranchType type, int target) {
        this.type = type;
        this.target = target;
    }

    public BranchType getType() {
        return type;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public void compile(InstructionSequence seq) {
        OpCode opcode;
        switch (type) {
            case GOTO:
                opcode = OpCode.JMP;
                break;
            case GOSUB:
                opcode = OpCode.CALL;
                break;
            default:
                throw new AssertionError();
        }
        seq.append(new Instruction(opcode, seq.createLineLabel(target)));
    }
}