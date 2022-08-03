using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class ImmediateExpression : Expression
{
    public int Value { get; set; }

    public ImmediateExpression(int value)
        => Value = value;

    public override void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.PUSHI, Value));
    }
}
