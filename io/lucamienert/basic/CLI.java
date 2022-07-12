package io.lucamienert.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.lucamienert.basic.exceptions.CLIException;

@SuppressWarnings("all")
public class CLI {

    private Compiler compiler;

    public CLI(Compiler compiler) {
        this.compiler = compiler;
    }

    public void run() throws CLIException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String line = null;
            while((line = in.readLine()) != null) {
                executeCommand(line);
            }
        } catch(IOException exception) {
            System.err.println(exception.toString());
        }
    }

    private void executeCommand(String input) throws CLIException {
        if(input.isEmpty() || input == null)
            throw new CLIException("Input is empty or null!");

        // if(input.equals("compile")) {
        //     compiler.compile(null);
        // }
    }
}