using BasicCompiler.Helper;

namespace BasicCompiler.Token;

public class Token
{
    public TokenType Type { get; }
    public Optional<string> Value { get; }

    public Token(TokenType type)
    {
        Type = type;
        Value = Optional<string>.Empty();
    }

    public Token(TokenType type, string value)
    {
        Type = type;
        Value = Optional<string>.Of(value);
    }
}
