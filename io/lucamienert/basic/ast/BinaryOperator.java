package io.lucamienert.basic.ast;

public enum BinaryOperator {
    PLUS('+'),
    MINUS('-'),
    MULT('*'),
    DIV('/');

    private final char character;

    private BinaryOperator(char character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}
