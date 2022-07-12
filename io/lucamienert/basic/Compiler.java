package io.lucamienert.basic;

public class Compiler {
    
    public Compiler() {

    }

    public void compile() {
        try (Tokenizer tokenizer = new Tokenizer(Files.newBufferedReader("", StandardCharsets.UTF_8))) {
            try (Parser parser = new Parser(tokenizer)) {
                try (CodeGenerator generator = new X86_64CodeGenerator(Files.newBufferedWriter("user.home", StandardCharsets.UTF_8))) {
                    generator.generate(parser.parse().compile());
                }
            }
        }
    }
}