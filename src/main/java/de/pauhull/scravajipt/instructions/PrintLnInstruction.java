package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;

public class PrintLnInstruction extends PrintInstruction {

    public PrintLnInstruction() {
    }

    public PrintLnInstruction(int line, String print) {
        super(line, print);
    }

    @Override
    public void run(Program program) {

        super.run(program);
        program.ioAdapter.output("\n");
    }
}
