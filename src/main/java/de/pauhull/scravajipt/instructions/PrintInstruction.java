package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONObject;

public class PrintInstruction extends Instruction {

    public String print;

    public PrintInstruction() {
    }

    public PrintInstruction(int line, String print) {

        this.line = line;
        this.print = print;
    }

    @Override
    public void run(Program program) {

        Variable temp = new Variable();
        program.evaluator.evaluate(print, temp, line);
        System.out.print(temp.value.toString());
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("print", print);
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.print = object.getString("print");
        return this;
    }
}
