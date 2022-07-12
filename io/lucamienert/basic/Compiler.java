package io.lucamienert.basic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import io.lucamienert.basic.codegen.CodeGenerator;
import io.lucamienert.basic.codegen.CodeGeneratorX86_64;
import io.lucamienert.basic.parser.Parser;
import io.lucamienert.basic.token.Tokenizer;

public class Compiler {
    
    public Compiler() {}

    public void compile(Path path) throws IOException {
        try (Tokenizer tokenizer = new Tokenizer(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            try (Parser parser = new Parser(tokenizer)) {
                try (CodeGenerator generator = new CodeGeneratorX86_64(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
                    generator.generate(parser.parse().compile());
                }
            }
        }
    }
}