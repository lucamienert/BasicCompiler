package io.lucamienert.basic.token;

import java.util.Objects;
import java.util.Optional;

public class Token {
    
    private final TokenType type;
    private final Optional<String> value;

    public Token(TokenType type) {
        this.type = type;
        this.value = Optional.empty();
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = Optional.of(value);
    }

    public TokenType getTokenType() {
        return type;
    }

    public Optional<String> getValue() {
        return value;
    }

}