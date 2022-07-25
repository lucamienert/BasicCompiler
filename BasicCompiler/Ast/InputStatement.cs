using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class InputStatement : Statement
{
    public List<string> Names { get; set; }

    public InputStatement(params string[] names)
        => Names = new List<string>(names);

    public override void Compile(InstructionSequence instructionSequence)
    {
        foreach (string name in Names)
        {
            instructionSequence.Append(
                new Instruction(OpCode.IN),
                new Instruction(OpCode.STORE, name))
            );
        }
    }
}
