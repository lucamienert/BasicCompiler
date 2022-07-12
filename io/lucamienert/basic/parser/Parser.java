package io.lucamienert.basic.parser;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.lucamienert.basic.ast.*;
import io.lucamienert.basic.token.*;

public class Parser implements Closeable {

    private final Tokenizer tokenizer;
    private Token token;

    public Parser(Tokenizer tokenizer) throws IOException {
        this.tokenizer = tokenizer;
        this.token = tokenizer.nextToken();
    }

    private void consume() throws IOException {
        token = tokenizer.nextToken();
    }

    private boolean accept(TokenType type) throws IOException {
        if (token.getTokenType() == type) {
            consume();
            return true;
        }

        return false;
    }

    private void expect(TokenType type) throws IOException {
        if (!accept(type))
            throw new IOException("Unexpected " + token.getTokenType() + ", expecting " + type);
    }

    public Program parse() throws IOException {
        List<Line> lines = new ArrayList<>();
        while (!accept(TokenType.EOF))
            lines.add(nextLine());

        return new Program(lines);
    }

    Line nextLine() throws IOException {
        if (token.getTokenType() != TokenType.NUMBER)
            throw new IOException("Unexpected " + token.getTokenType() + ", expecting NUMBER");

        int lineNumber = Integer.parseInt(token.getValue().get());
        consume();

        Statement stmt = nextStatement();

        if (token.getTokenType() != TokenType.LF && token.getTokenType() != TokenType.EOF)
            throw new IOException("Unexpected " + token.getTokenType() + ", expecting LF or EOF");

        consume();
        return new Line(lineNumber, stmt);
    }

    Statement nextStatement() throws IOException {
        if (token.getTokenType() != TokenType.KEYWORD)
            throw new IOException("Unexpected " + token.getTokenType() + ", expecting KEYWORD");

        String keyword = token.getValue().get();
        switch (keyword) {
            case "PRINT":
                consume();

                List<StringExpression> values = new ArrayList<>();
                do {
                    if (token.getTokenType() == TokenType.STRING) {
                        values.add(new ImmediateString(token.getValue().get()));
                        consume();
                    } else {
                        values.add(nextExpression());
                    }
                } while (accept(TokenType.COMMA));

                return new PrintStatement(values);

            case "IF":
                consume();

                Expression left = nextExpression();

                RelationalOperator operator;
                switch (token.getTokenType()) {
                    case EQ:
                        operator = RelationalOperator.EQ;
                        break;
                    case NE:
                        operator = RelationalOperator.NE;
                        break;
                    case LT:
                        operator = RelationalOperator.LT;
                        break;
                    case LTE:
                        operator = RelationalOperator.LTE;
                        break;
                    case GT:
                        operator = RelationalOperator.GT;
                        break;
                    case GTE:
                        operator = RelationalOperator.GTE;
                        break;
                    default:
                        throw new IOException("Unexpected " + token.getTokenType() + ", expecting EQ, NE, LT, LTE, GT or GTE");
                }
                consume();

                Expression right = nextExpression();

                if (token.getTokenType() != TokenType.KEYWORD)
                    throw new IOException("Unexpected " + token.getTokenType() + ", expecting KEYWORD");

                String thenKeyword = token.getValue().get();
                if (!thenKeyword.equals("THEN"))
                    throw new IOException("Unexpected keyword " + keyword + ", expecting THEN");

                consume();

                Statement statement = nextStatement();
                return new IfStatement(operator, left, right, statement);

            case "GOTO":
            case "GOSUB":
                consume();

                if (token.getTokenType() != TokenType.NUMBER)
                    throw new IOException("Unexpected " + token.getTokenType() + ", expecting NUMBER");

                int target = Integer.parseInt(token.getValue().get());
                consume();

                BranchType type = keyword.equals("GOTO") ? BranchType.GOTO : BranchType.GOSUB;
                return new BranchStatement(type, target);

            case "INPUT":
                consume();

                List<String> names = new ArrayList<>();
                do {
                    if (token.getTokenType() != TokenType.VAR)
                        throw new IOException("Unexpected " + token.getTokenType() + ", expecting VAR");

                    names.add(token.getValue().get());
                    consume();
                } while (accept(TokenType.COMMA));

                return new InputStatement(names);

            case "LET":
                consume();

                if (token.getTokenType() != TokenType.VAR)
                    throw new IOException("Unexpected " + token.getTokenType() + ", expecting VAR");

                String name = token.getValue().get();
                consume();

                expect(TokenType.EQ);

                return new LetStatement(name, nextExpression());

            case "RETURN":
                consume();
                return new ReturnStatement();

            case "END":
                consume();
                return new EndStatement();

            default:
                throw new IOException("Unknown keyword: " + keyword);
        }
    }

    Expression nextExpression() throws IOException {
        Expression left;

        if (token.getTokenType() == TokenType.PLUS || token.getTokenType() == TokenType.MINUS) {
            UnaryOperator operator = token.getTokenType() == TokenType.PLUS ? UnaryOperator.PLUS : UnaryOperator.MINUS;
            consume();

            left = new UnaryExpression(operator, nextTerm());
        } else {
            left = nextTerm();
        }

        while (token.getTokenType() == TokenType.PLUS || token.getTokenType() == TokenType.MINUS) {
            BinaryOperator operator = token.getTokenType() == TokenType.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS;
            consume();

            Expression right = nextTerm();
            left = new BinaryExpression(operator, left, right);
        }

        return left;
    }

    private Expression nextTerm() throws IOException {
        Expression left = nextFactor();

        while (token.getTokenType() == TokenType.MULT || token.getTokenType() == TokenType.DIV) {
            BinaryOperator operator = token.getTokenType() == TokenType.MULT ? BinaryOperator.MULT : BinaryOperator.DIV;
            consume();

            left = new BinaryExpression(operator, left, nextFactor());
        }

        return left;
    }

    private Expression nextFactor() throws IOException {
        switch (token.getTokenType()) {
            case VAR:
                Expression expr = new VariableExpression(token.getValue().get());
                consume();
                return expr;

            case NUMBER:
                expr = new ImmediateExpression(Integer.parseInt(token.getValue().get()));
                consume();
                return expr;

            case LPAREN:
                consume();
                expr = nextExpression();
                expect(TokenType.RPAREN);
                return expr;

            default:
                throw new IOException("Unexpected " + token.getTokenType() + ", expecting VAR, NUMBER or LPAREN");
        }
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
    
}
