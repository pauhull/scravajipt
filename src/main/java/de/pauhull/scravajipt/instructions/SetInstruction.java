package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONObject;

public class SetInstruction extends Instruction {

    public String varName;
    public String assignment;

    public SetInstruction() {
    }

    public SetInstruction(int line, String varName, String assignment) {

        this.line = line;
        this.varName = varName;
        this.assignment = assignment;
    }

    @Override
    public void run(Program program) {

        Variable variable = null;

        for(Variable programVar : program.variables) {
            if(programVar.name.equals(varName)) {
                variable = programVar;
            }
        }

        if(variable == null) {
            variable = new Variable();
            variable.name = varName;
            program.variables.add(variable);
        }

        program.evaluator.evaluate(assignment, variable, line);
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("varName", varName);
        object.put("assignment", assignment);
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.varName = object.getString("varName");
        this.assignment = object.getString("assignment");
        return this;
    }
}
