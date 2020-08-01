package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import de.pauhull.scravajipt.program.Variable;
import de.pauhull.scravajipt.util.InstructionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ElseInstruction implements Instruction, InstructionContainer {

    public int line;
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
    public int getLine() {
        return line;
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = new JSONObject();

        object.put("type", "ElseInstruction");
        object.put("line", line);
        object.put("originalCondition", originalCondition);

        JSONArray array = new JSONArray();
        instructions.forEach(i -> array.put(i.toJson()));
        object.put("instructions", array);

        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        this.line = object.getInt("line");
        this.originalCondition = object.getString("originalCondition");
        this.instructions = InstructionUtil.instructionListFromJsonArray(object.getJSONArray("instructions"));

        return this;
    }

    @Override
    public List<Instruction> getContaining() {
        return instructions;
    }
}
