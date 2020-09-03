package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONObject;

import java.util.List;

public class IfInstruction extends Instruction implements InstructionContainer {

    public String condition;
    public List<Instruction> instructions;

    public IfInstruction() {
    }

    public IfInstruction(int line, String condition, List<Instruction> instructions) {

        this.line = line;
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public void run(Program program) {

        Variable temp = new Variable();
        program.evaluator.evaluate(condition, temp, line);

        if(temp.type != Variable.Type.BOOL) {
            throw new ProgramException(line, "Invalid condition");
        }

        if((boolean) temp.value)
            instructions.forEach(i -> i.run(program));
    }

    @Override
    public List<Instruction> getContaining() {
        return instructions;
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("condition", condition);
        object.put("instructions", instructionListToJson(instructions));
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.condition = object.getString("condition");
        this.instructions = jsonToInstructionList(object.getJSONArray("instructions"));
        return this;
    }
}
