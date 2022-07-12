package io.lucamienert.basic.token;

import io.lucamienert.basic.utils.TokenizerUtils;

import java.io.Reader;
import java.io.StringReader;
import java.io.Closeable;
import java.io.IOException;

public class Tokenizer implements Closeable {

    private final Reader reader;

    public Tokenizer(Reader reader) {
        this.reader = reader;
    }

    public Tokenizer(String str) {
        this.reader = new StringReader(str);
    }

    private int peek() throws IOException {
        reader.mark(1);
        try {
            return reader.read();
        } finally {
            reader.reset();
        }
    }

    public Token nextToken() throws IOException {
        for (;;) {
            int ch = reader.read();
            if (ch == -1)
                return new Token(TokenType.EOF);
            else if (ch == '\n')
                return new Token(TokenType.LF);
            else if (ch == '+')
                return new Token(TokenType.PLUS);
            else if (ch == '-')
                return new Token(TokenType.MINUS);
            else if (ch == '*')
                return new Token(TokenType.MULT);
            else if (ch == '/')
                return new Token(TokenType.DIV);
            else if (ch == '(')
                return new Token(TokenType.LPAREN);
            else if (ch == ')')
                return new Token(TokenType.RPAREN);
            else if (ch == ',')
                return new Token(TokenType.COMMA);
            else if (ch == '"')
                return nextStringToken();
            else if (ch == '=')
                return new Token(TokenType.EQ);
            else if (ch == '>' || ch == '<')
                return nextRelationalOperatorToken(ch);
            else if (TokenizerUtils.isAlpha(ch) && !TokenizerUtils.isAlpha(peek()))
                return new Token(TokenType.VAR, new String(new char[] { (char) ch }));
            else if (TokenizerUtils.isAlpha(ch))
                return nextKeywordToken(ch);
            else if (TokenizerUtils.isDigit(ch))
                return nextNumberToken(ch);
            else if (!TokenizerUtils.isWhitespace(ch))
                throw new IOException("Unexpected character: " + ch);
        }
    }

    private Token nextRelationalOperatorToken(int first) throws IOException {
        int second = peek();

        if (first == '>') {
            if (second == '<') {
                reader.skip(1);
                return new Token(TokenType.NE);
            } else if (second == '=') {
                reader.skip(1);
                return new Token(TokenType.GTE);
            } else {
                return new Token(TokenType.GT);
            }
        } else {
            assert first == '<';

            if (second == '>') {
                reader.skip(1);
                return new Token(TokenType.NE);
            } else if (second == '=') {
                reader.skip(1);
                return new Token(TokenType.LTE);
            } else {
                return new Token(TokenType.LT);
            }
        }
    }

    private Token nextStringToken() throws IOException {
        StringBuilder buf = new StringBuilder();

        for (;;) {
            int ch = reader.read();
            if (ch == -1)
                throw new IOException("Unexpected EOF within string");
            else if (ch == '"')
                break;

            buf.append((char) ch);
        }
        return new Token(TokenType.STRING, buf.toString());
    }

    private Token nextKeywordToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();
            if (!TokenizerUtils.isAlpha(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.KEYWORD, buf.toString());
    }

    private Token nextNumberToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();
            if (!TokenizerUtils.isDigit(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.NUMBER, buf.toString());
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}