using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public abstract class StringExpression
{
    public abstract void Compile(InstructionSequence instructionSequence);
}
