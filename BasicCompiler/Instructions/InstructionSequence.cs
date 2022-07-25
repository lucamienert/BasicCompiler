namespace BasicCompiler.Instructions;

public class InstructionSequence
{
    private readonly List<Instruction> _instructions = new();
    private int _labelCounter = 0;

    public static string CreateLineLabel(int line) => $"line_{line}";
    public string CreateGeneratedLabel() => $"generated_{_labelCounter++}";
    public void Append(params Instruction[] instruction) => _instructions.AddRange(instruction);
    public List<Instruction> GetInstructions() => _instructions;
}
