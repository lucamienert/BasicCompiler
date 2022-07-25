using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class EndStatement : Statement
{
    public override void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.HLT));
    }
}
