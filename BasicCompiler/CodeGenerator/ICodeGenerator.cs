using BasicCompiler.Instructions;

namespace BasicCompiler.CodeGenerator;

public interface ICodeGenerator : IDisposable
{
    public void Generate(InstructionSequence instructionSequence);
    public new void Dispose();
}
