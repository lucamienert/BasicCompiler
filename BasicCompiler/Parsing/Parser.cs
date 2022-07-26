using BasicCompiler.Ast;
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

    public ProgramAst Parse()
    {
        var lines = new List<Line>();
        while (!Accept(TokenType.EOF))
            lines.Add(NextLine());

        return new ProgramAst(lines.ToArray());
    }

    private Line NextLine()
    {
        if (_token.Type != TokenType.NUMBER)
            throw new UnexpectedTokenException(_token.Typ);

        var lineNumber = int.Parse(_token.Value.ToString()!);
        Consume();

        var statement = NextStatement();

        if (_token.Type != TokenType.LF && _token.Type != TokenType.EOF)
            throw new UnexpectedTokenException(_token.Type);

        Consume();
        return new Line(lineNumber, statement);
    }

    private Statement NextStatement()
    {
        if (_token.Type != TokenType.KEYWORD)
            throw new UnexpectedTokenException(_token.Type);

        var keyword = _token.Value.ToString()!;

        switch (keyword)
        {
            case "PRINT":
                {
                    Consume();
                    var values = new List<StringExpression>();
                    do
                    {
                        if (_token.Type == TokenType.STRING)
                        {
                            values.Add(new ImmediateString(_token.Value.ToString()!));
                            Consume();
                        }
                        else values.Add(NextExpression());
                    }
                    while (Accept(TokenType.COMMA));

                    return new PrintStatement(values.ToArray());
                }
            case "IF":
                {
                    Consume();

                    var left = NextExpression();
                    var op = _token.Type switch
                    {
                        TokenType.EQ => RelationalOperator.EQ,
                        TokenType.NE => RelationalOperator.NE,
                        TokenType.LT => RelationalOperator.LT,
                        TokenType.GT => RelationalOperator.GT,
                        _ => throw new UnexpectedTokenException(_token.Type),
                    };

                    Consume();

                    var right = NextExpression();

                    if (_token.Type != TokenType.KEYWORD)
                        throw new UnexpectedTokenException(_token.Type);

                    var thenKeyword = _token.Value.ToString()!;
                    if(thenKeyword.Equals("THEN"))
                        throw new UnexpectedTokenException(TokenType.KEYWORD);

                    Consume();

                    var statement = NextStatement();
                    return new IfStatement(op, left, right, statement);
                }
            case "GOTO":
            case "GOSUB":
                {
                    Consume();

                    if (_token.Type != TokenType.NUMBER)
                        throw new UnexpectedTokenException(_token.Type);

                    var target = int.Parse(_token.Value.ToString()!);
                    Consume();

                    var type = keyword.Equals("GOTO") ? BranchType.GOTO : BranchType.GOSUB;
                    return new BranchStatement(type, target);
                }
            case "INPUT":
                {
                    Consume();

                    var names = new List<string>();
                    do
                    {
                        if (_token.Type != TokenType.VAR)
                            throw new UnexpectedTokenException(_token.Type);

                        names.Add(_token.Value.ToString()!);
                        Consume();
                    }
                    while (Accept(TokenType.COMMA));

                    return new InputStatement(names.ToArray());
                }
            case "LET":
                {
                    Consume();

                    if(_token.Type != TokenType.VAR)
                        throw new UnexpectedTokenException(_token.Type);

                    var name = _token.Value.ToString()!;
                    Consume();

                    Expect(TokenType.EQ);

                    return new LetStatement(name, NextExpression());
                }
            case "RETURN":
                {
                    Consume();
                    return new ReturnStatement();
                }
            case "END":
                {
                    Consume();
                    return new EndStatement();
                }
            default: throw new UnexpectedTokenException();
        }
    }

    private Expression NextExpression()
    {
        Expression left;

        if (_token.Type == TokenType.PLUS || _token.Type == TokenType.MINUS)
        {
            var op = _token.Type == TokenType.PLUS ? UnaryOperator.PLUS : UnaryOperator.MINUS;
            Consume();

            left = new UnaryExpression(op, NextTerm());
        }
        else
            left = NextTerm();

        while(_token.Type == TokenType.PLUS || _token.Type == TokenType.MINUS)
        {
            var op = _token.Type == TokenType.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS;
            Consume();

            var right = NextTerm();
            left = new BinaryExpression(op, left, right);
        }

        return left;
    }

    private Expression NextTerm()
    {
        var left = NextFactor();

        while (_token.Type == TokenType.MULT || _token.Type == TokenType.DIV) 
        {
            var op = _token.Type == TokenType.MULT? BinaryOperator.MULT : BinaryOperator.DIV;
            Consume();

            left = new BinaryExpression(op, left, NextFactor());
        }

        return left;
    }

    private Expression NextFactor()
    {
        Expression expr;
        switch (_token.Type)
        {
            case TokenType.VAR:
                {
                    expr = new VariableExpression(_token.Value.ToString()!);
                    Consume();
                    return expr;
                }
            case TokenType.NUMBER:
                {
                    expr = new ImmediateExpression(int.Parse(_token.Value.ToString()!));
                    Consume();
                    return expr;
                }
            case TokenType.LPAREN:
                {
                    Consume();
                    expr = NextExpression();
                    Expect(TokenType.RPAREN);
                    return expr;
                }
            default: throw new UnexpectedTokenException(_token.Type);
        }
    }

    public void Dispose()
    {
    }
}
