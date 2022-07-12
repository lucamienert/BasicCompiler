package io.lucamienert.basic;

public class CLI {

    private Compiler compiler;

    public CLI(Compiler compiler) {
        this.compiler = compiler;
    }

    public void run() {
        try (BufferdReader in = new BufferdReader(new InputStreamReader(System.in))) {
            String line = null;
            while((line = in.readLine()) != null) {
                executeCommand(line);
            }
        }
    }

    private void executeCommand(String input) {
        if(input.isEmpty() || input == null)
            throw new CLIException("Input is empty or null!");
    }
}