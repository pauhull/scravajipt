package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import org.json.JSONObject;

public class PrintLnInstruction extends PrintInstruction {

    public PrintLnInstruction() {
    }

    public PrintLnInstruction(int line, String print) {
        super(line, print);
    }

    @Override
    public void run(Program program) {

        super.run(program);
        System.out.print("\n");
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = new JSONObject();

        object.put("type", "PrintLnInstruction");
        object.put("line", line);
        object.put("print", print);

        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        this.line = object.getInt("line");
        this.print = object.getString("print");

        return this;
    }
}
