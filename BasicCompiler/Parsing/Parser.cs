using BasicCompiler.Exceptions;
using BasicCompiler.Token;

namespace BasicCompiler.Parsing;

public class Parser : IDisposable
{
    private readonly Tokenizer _tokenizer;
    private Token.Token _token;

    public Parser(Tokenizer tokenizer)
    {
        _tokenizer = tokenizer;
        _token = tokenizer.NextToken();
    }

    private void Consume() => _token = _tokenizer.NextToken();

    private bool Accept(TokenType type)
    {
        if(_token.Type != type)
            return false;

        Consume();
        return true;
    }

    private void Expect(TokenType type)
    {
        if (!Accept(type))
            throw new UnexpectedTokenException(type);
    }

    public void Dispose()
    {
    }
}
