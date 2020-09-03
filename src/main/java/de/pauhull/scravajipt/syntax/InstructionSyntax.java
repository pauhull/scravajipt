package de.pauhull.scravajipt.syntax;

import java.util.ArrayList;
import java.util.List;

public class InstructionSyntax {

    public String name;
    public Parameter[] parameters;

    public static List<InstructionSyntax> syntaxLookup = new ArrayList<>();

    static {
        syntaxLookup.add(new InstructionSyntax("else"));
        syntaxLookup.add(new InstructionSyntax("if", Parameter.STATEMENT));
        syntaxLookup.add(new InstructionSyntax("input", Parameter.VARIABLE_NAME));
        syntaxLookup.add(new InstructionSyntax("print", Parameter.STATEMENT));
        syntaxLookup.add(new InstructionSyntax("println", Parameter.STATEMENT));
        syntaxLookup.add(new InstructionSyntax("set", Parameter.VARIABLE_NAME, Parameter.STATEMENT));
        syntaxLookup.add(new InstructionSyntax("while", Parameter.STATEMENT));
        syntaxLookup.add(new InstructionSyntax("end"));
    }

    public InstructionSyntax(String name, Parameter... parameters) {

        this.name = name;
        this.parameters = parameters;
    }

    public enum Parameter {

        VARIABLE_NAME, STATEMENT
    }
}
