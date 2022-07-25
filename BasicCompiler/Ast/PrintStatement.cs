using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class PrintStatement : Statement
{
    public List<StringExpression> Values { get; set; }

    public PrintStatement(params StringExpression[] values)
        => Values = new List<StringExpression>(values);

    public override void Compile(InstructionSequence instructionSequence)
    {
        foreach(var value in Values)
        {
            value.Compile(instructionSequence);
            instructionSequence.Append(new Instruction(value is ImmediateString ? OpCode.OUTS : OpCode.OUTI));
        }

        instructionSequence.Append(
            new Instruction(OpCode.PUSHS, "\n"), 
            new Instruction(OpCode.OUTS)
        );
    }
}
