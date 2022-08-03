using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public abstract class Statement
{
    public abstract void Compile(InstructionSequence instructionSequence);
}
