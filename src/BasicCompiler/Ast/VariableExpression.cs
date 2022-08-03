using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class VariableExpression : Expression
{
    public string Name { get; set; }

    public VariableExpression(string name) 
        => Name = name;

    public override void Compile(InstructionSequence instructionSequence)
    {
        instructionSequence.Append(new Instruction(OpCode.LOAD, Name));
    }
}
