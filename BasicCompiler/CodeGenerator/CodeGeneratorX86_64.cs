using BasicCompiler.Helper;
using BasicCompiler.Instructions;

namespace BasicCompiler.CodeGenerator;

public class CodeGeneratorX86_64 : ICodeGenerator
{
    private readonly TextWriter _writer;

    public CodeGeneratorX86_64(TextWriter writer)
        => _writer = writer;

    private void WriteHeader()
    {
        _writer.WriteLine("[extern exit]\n");
        _writer.WriteLine("[extern printf]\n");
        _writer.WriteLine("[extern scanf]\n");
        _writer.WriteLine("[section .code]\n");
        _writer.WriteLine("[global main]\n");
        _writer.WriteLine("main:\n");
        _writer.WriteLine("  push rbp\n");
        _writer.WriteLine("  mov rbp, rsp\n");
        _writer.WriteLine("  sub rsp, " + (8 * 27) + "\n");
    }

    private void WriteFooter()
    {
        _writer.WriteLine("  mov rax, 0\n");
        _writer.WriteLine("  mov rsp, rbp\n");
        _writer.WriteLine("  pop rbp\n");
        _writer.WriteLine("  ret\n");
    }

    private int VarIndex(Instruction instruction)
        => (instruction.StringOperand.ToString()!.ElementAt(0) - 'A') * 8;

    public void Generate(InstructionSequence instructionSequence)
    {
        WriteHeader();

        var strings = new Dictionary<string, string>();

        foreach(var instruction in instructionSequence.GetInstructions())
        {
            switch(instruction.OpCode)
            {
                case OpCode.LABEL: _writer.WriteLine($"{instruction.StringOperand}:\n"); break;
                case OpCode.PUSHI: _writer.WriteLine($"  push 0x{instruction.IntOperand}\n"); break;
                case OpCode.PUSHS:
                    {
                        var label = instructionSequence.CreateGeneratedLabel();
                        strings.Add(label, instruction.StringOperand.ToString()!);
                        _writer.WriteLine($"  push {label}\n");
                        break;
                    }
                case OpCode.LOAD: break;
                case OpCode.STORE: break;
                case OpCode.ADD: break;
                case OpCode.SUB: break;
                case OpCode.MUL: break;
                case OpCode.DIV: break;
                case OpCode.CALL: break;
                case OpCode.RET: break;
                case OpCode.JMP: break;
                case OpCode.JMPGT: break;
                case OpCode.JMPLT: break;
                case OpCode.JMPEQ: break;
                case OpCode.JMPNE: break;
                case OpCode.OUTS: break;
                case OpCode.IN: break;
                case OpCode.OUTI: break;
                case OpCode.HLT: break;
            }
        }

        WriteFooter();

        _writer.WriteLine("[section .rodata]\n");

        foreach(var str in strings)
        {
            _writer.WriteLine($"{str.Key}:\n");
            _writer.WriteLine($"  db \"{StringHelper.Escape(str.Value)}\", 0\n");
        }
    }

    public void Dispose() => _writer.Dispose();
}
