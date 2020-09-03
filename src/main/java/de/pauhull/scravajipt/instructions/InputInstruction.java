package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONObject;

import java.util.Scanner;

public class InputInstruction extends Instruction {

    public String writeTo;

    public InputInstruction() {
    }

    public InputInstruction(int line, String writeTo) {

        this.line = line;
        this.writeTo = writeTo;
    }

    @Override
    public void run(Program program) {

        Variable variable = null;

        for(Variable temp : program.variables) {
            if(temp.name.equals(writeTo)) {
                variable = temp;
            }
        }

        if(variable == null) {
            throw new ProgramException(line, String.format("Cant find variable \"%s\"", writeTo));
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        variable.type = Variable.Type.STRING;
        variable.value = input;
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("writeTo", writeTo);
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.writeTo = object.getString("writeTo");
        return this;
    }
}
