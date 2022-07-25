using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class Line
{
    public int Number { get; set; }
    public Statement Statement { get; set; }

    public Line(int number, Statement statement)
    {
        Number = number;
        Statement = statement;
    }

    public void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.LABEL, InstructionSequence.CreateLineLabel(Number)));
        Statement.Compile(instructionSequence);
    }
}
