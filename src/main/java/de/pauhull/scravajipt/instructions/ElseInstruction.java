package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONObject;

import java.util.List;

public class ElseInstruction extends Instruction implements InstructionContainer {

    public String originalCondition;
    public List<Instruction> instructions;

    public ElseInstruction() {
    }

    public ElseInstruction(int line, String originalCondition, List<Instruction> instructions) {

        this.line = line;
        this.originalCondition = originalCondition;
        this.instructions = instructions;
    }

    @Override
    public void run(Program program) {

        Variable temp = new Variable();
        program.evaluator.evaluate(originalCondition, temp, line);

        if(temp.type != Variable.Type.BOOL) {
            throw new ProgramException(line, "Invalid condition");
        }

        if(!((boolean) temp.value))
            instructions.forEach(i -> i.run(program));
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("originalCondition", originalCondition);
        object.put("instructions", instructionListToJson(instructions));
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.originalCondition = object.getString("originalCondition");
        this.instructions = jsonToInstructionList(object.getJSONArray("instructions"));
        return this;
    }

    @Override
    public List<Instruction> getContaining() {
        return instructions;
    }
}
