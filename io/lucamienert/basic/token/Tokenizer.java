package io.lucamienert.basic.token;

import java.io.Reader;
import java.io.StringReader;
import java.io.Closeable;
import java.io.IOException;

public class Tokenizer implements Closeable {

    private final Reader reader;

    public Tokenizer(Reader reader) {
        this.reader = reader;
    }

    public Tokenizer(Stirng str) {
        this.reader = new StringReader(str);
    }
}