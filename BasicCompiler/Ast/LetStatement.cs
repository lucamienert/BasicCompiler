using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class LetStatement : Statement
{
    public string Name { get; set; }
    public Expression Value { get; set; }

    public LetStatement(string name, Expression value)
    {
        Name = name;
        Value = value;
    }

    public override void Compile(InstructionSequence instructionSequence)
    {
        Value.Compile(instructionSequence);
        instructionSequence.Append(new Instruction(OpCode.STORE, Name));
    }
}
