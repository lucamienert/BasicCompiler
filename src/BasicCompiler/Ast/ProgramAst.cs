using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class ProgramAst
{
    public List<Line> Lines { get; set; }

    public ProgramAst(params Line[] lines)
        => Lines = new List<Line>(lines);

    public InstructionSequence Compile()
    {
        var instructionSequence = new InstructionSequence();

        foreach(var line in Lines)
            line.Compile(instructionSequence);

        return instructionSequence;
    }
}
