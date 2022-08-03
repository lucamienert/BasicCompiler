using BasicCompiler.CodeGenerator;
using BasicCompiler.Helper;
using BasicCompiler.Parsing;
using BasicCompiler.Token;
using System.Text;

namespace BasicCompiler;

public class Compiler
{
    public Compiler() { }

    public void Compile(string path)
    {
        var input = FileHandler.ReadFile(path);
        var tokenizer = new Tokenizer(input);
        var parser = new Parser(tokenizer);
        var codegen = new CodeGeneratorX86_64();

        codegen.Generate(parser.Parse().Compile());
    }
}
