using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class ImmediateString : StringExpression
{
    public string Value { get; set; }

    public ImmediateString(string value)
        => Value = value;

    public override void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.PUSHS, Value));
    }
}
