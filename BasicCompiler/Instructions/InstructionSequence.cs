namespace BasicCompiler.Instructions;

public class InstructionSequence
{
    private readonly List<Instruction> _instructions = new List<Instruction>();
    private int _labelCounter = 0;

    public string CreateLineLabel(int line) => $"line_{line}";
    public string CreateGeneratedLabel() => $"generated_{_labelCounter++}";
    public void Append(Instruction instruction) => _instructions.Add(instruction);
    public List<Instruction> GetInstructions() => _instructions;
}
