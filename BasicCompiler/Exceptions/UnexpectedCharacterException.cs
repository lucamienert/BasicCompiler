namespace BasicCompiler.Exceptions;

public class UnexpectedCharacterException : Exception
{
    public UnexpectedCharacterException() : base("[Compiler]: Unexpected Character!") { }
    public UnexpectedCharacterException(char character) : base($"[Compiler]: Unexpected Character: {character}") { }
}
