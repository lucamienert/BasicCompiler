using BasicCompiler.Exceptions;
using BasicCompiler.Helper;

namespace BasicCompiler.Token;

public class Tokenizer
{
    private StringReader _reader;

    public Tokenizer(StringReader reader) => _reader = reader ?? throw new ArgumentNullException(nameof(reader));
    public Tokenizer(string str) => _reader = new StringReader(str);

    private int Peek()
    {
        _reader.Peek();
        return _reader.Read();
    }

    public Token NextToken()
    {
        for(;;)
        {
            var current = _reader.Read();

            switch (current)
            {
                case -1: return new Token(TokenType.EOF);
                case '\n': return new Token(TokenType.LF);
                case '+': return new Token(TokenType.PLUS);
                case '-': return new Token(TokenType.MINUS);
                case '*': return new Token(TokenType.MULT);
                case '/': return new Token(TokenType.DIV);
                case '(': return new Token(TokenType.LPAREN);
                case ')': return new Token(TokenType.RPAREN);
                case ',': return new Token(TokenType.COMMA);
                case '"': return new Token(TokenType.STRING);
                case '=': return new Token(TokenType.EQ);
                case '>': return NextRelationalOperatorToken(current);
                case '<': return NextRelationalOperatorToken(current);
                default: break;
            }

            if (char.IsWhiteSpace((char)current))
                 throw new UnexpectedCharacterException((char)current);


        }
    }

    private Token NextRelationalOperatorToken(int first)
    {
        return new Token(TokenType.GT);
    }

}
