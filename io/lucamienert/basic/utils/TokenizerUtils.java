package io.lucamienert.basic.utils;

import java.io.IOException;
import java.io.Reader;

public class TokenizerUtils {
    private static boolean isWhitespace(int ch) {
        return Character.isWhitespace(ch);
    }

    private static boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    private static boolean isAlpha(int ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private int peek(Reader reader) throws IOException {
        reader.mark(1);
        try {
            return reader.read();
        } finally {
            reader.reset();
        }
    }
}