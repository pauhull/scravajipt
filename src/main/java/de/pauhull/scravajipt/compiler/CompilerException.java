package de.pauhull.scravajipt.compiler;

public class CompilerException extends RuntimeException {

    public CompilerException(int line, String message) {

        super(String.format("Compilation error at line %d: %s", line, message));
    }

}
