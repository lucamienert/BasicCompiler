namespace BasicCompiler.Ast;

public class AssertionException : Exception
{
    public AssertionException() : base("Unexpected behavior") { }
    public AssertionException(BinaryOperator @operator) : base($"Unexpeced operator {@operator}") { }
}