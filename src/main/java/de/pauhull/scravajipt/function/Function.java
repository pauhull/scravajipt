package de.pauhull.scravajipt.function;

import de.pauhull.scravajipt.instructions.Instruction;
import de.pauhull.scravajipt.instructions.InstructionContainer;
import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.Variable;

import java.util.ArrayList;
import java.util.List;

public class Function implements InstructionContainer {

    public List<Instruction> instructions;
    public String name;
    public String[] parameterNames;

    public Function(String name, String... parameterNames) {

        this.instructions = new ArrayList<>();
        this.name = name;
        this.parameterNames = parameterNames;
    }

    public Variable run(Program program, String[] parameters) {

        for(Instruction instruction : instructions) {
            instruction.run(program);
        }

        return null;
    }

    @Override
    public List<Instruction> getContaining() {
        return instructions;
    }
}
