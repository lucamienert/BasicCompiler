package io.lucamienert.basic.utils;

public class TokenizerUtils {
    
    public static boolean isWhitespace(int ch) {
        return Character.isWhitespace(ch);
    }

    public static boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isAlpha(int ch) {
        return ch >= 'A' && ch <= 'Z';
    }
}