using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class ReturnStatement : Statement
{
    public override void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.RET));
    }
}
