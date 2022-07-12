package io.lucamienert.basic;

import java.io.IOException;
import java.nio.file.Paths;

import io.lucamienert.basic.exceptions.CLIException;

public class Main {

    private static final String NOCLI = "-nocli"; 

    public static void main(String[] args) throws IOException, CLIException {
        Compiler compiler = new Compiler();

        if(args[0].equals(NOCLI))
            compiler.compile(Paths.get(args[1]));

        CLI cli = new CLI(compiler);
        cli.run();
    }
}