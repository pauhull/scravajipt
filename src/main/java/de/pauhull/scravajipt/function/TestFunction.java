package de.pauhull.scravajipt.function;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.Variable;

public class TestFunction extends Function {

    public TestFunction() {
        super("testFunction", "parameter1");
    }

    @Override
    public Variable run(Program program, String[] parameters) {

        System.out.println("Test function called");
        System.out.println("Parameter: " + parameters[0]);
        Variable temp = new Variable();
        program.evaluator.evaluate(parameters[0], temp, 0);
        System.out.println("Parameter evaluated: " + temp.value);

        return null;
    }
}
