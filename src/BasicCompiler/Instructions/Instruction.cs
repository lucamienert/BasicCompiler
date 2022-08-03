using BasicCompiler.Helper;

namespace BasicCompiler.Instructions;

public class Instruction
{
    public OpCode OpCode { get; set; }
    public Optional<string> StringOperand { get; set; }
    public Optional<int> IntOperand { get; set; }

    public Instruction(OpCode opCode)
    {
        OpCode = opCode;
        StringOperand = Optional<string>.Empty();
        IntOperand = Optional<int>.Empty();
    }

    public Instruction(OpCode opCode, string operand)
    {
        OpCode = opCode;
        StringOperand = Optional<string>.Of(operand);
        IntOperand = Optional<int>.Empty();
    }

    public Instruction(OpCode opCode, int operand)
    {
        OpCode = opCode;
        StringOperand = Optional<string>.Empty();
        IntOperand = Optional<int>.Of(operand);
    }
}
