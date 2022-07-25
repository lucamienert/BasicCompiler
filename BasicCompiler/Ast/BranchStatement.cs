using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class BranchStatement : Statement
{
    public BranchType BranchType { get; set; }
    public int Target { get; set; }

    public BranchStatement(BranchType type, int target)
    {
        BranchType = type;
        Target = target;
    }

    public override void Compile(InstructionSequence instructionSequence)
    {
        var opcode = BranchType switch
        {
            BranchType.GOTO => OpCode.JMP,
            BranchType.GOSUB => OpCode.CALL,
            _ => throw new AssertionException(),
        };

        instructionSequence.Append(new Instruction(opcode, InstructionSequence.CreateLineLabel(Target)));
    }
}
