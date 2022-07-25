using BasicCompiler.Token;

namespace BasicCompiler.Exceptions;

public class UnexpectedTokenException : Exception
{
    public UnexpectedTokenException() : base("[Compiler]: Unexpected Token!") { }
    public UnexpectedTokenException(TokenType type) : base($"[Compiler]: Unexpected Token: {type}") { }
}