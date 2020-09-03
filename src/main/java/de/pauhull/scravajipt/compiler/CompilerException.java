package de.pauhull.scravajipt.compiler;

public class CompilerException extends RuntimeException {

    public int line;
    public String message;

    public CompilerException(int line, String message) {

        super(String.format("Compilation error at line %d: %s", line, message));

        this.line = line;
        this.message = message;
        this.message = message;
    }

}
