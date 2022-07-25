namespace BasicCompiler.Ast;

public class AssertionException : Exception
{
    public AssertionException() : base("[Compiler]: Unexpected behavior") { }
    public AssertionException(BinaryOperator @operator) : base($"[Compiler]: Unexpeced operator: {@operator}") { }
}