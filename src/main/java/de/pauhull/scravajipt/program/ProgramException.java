package de.pauhull.scravajipt.program;

public class ProgramException extends RuntimeException {

    public ProgramException(int line, String message) {

        super(String.format("Runtime error at line %d: %s", line, message));
    }

}
