package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import de.pauhull.scravajipt.program.Variable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class WhileInstruction implements Instruction, InstructionContainer {

    public int line;
    public String condition;
    public List<Instruction> instructions;

    public WhileInstruction() {
    }

    public WhileInstruction(int line, String condition, List<Instruction> instructions) {

        this.line = line;
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public void run(Program program) {

        while (true) {

            Variable temp = new Variable();
            program.evaluator.evaluate(condition, temp, line);

            if (temp.type != Variable.Type.BOOL) {
                throw new ProgramException(line, "Invalid condition");
            }

            if (!((boolean) temp.value)) {
                break;
            }

            for (Instruction instruction : instructions) {
                instruction.run(program);
            }
        }
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = new JSONObject();

        object.put("type", "WhileInstruction");
        object.put("line", line);
        object.put("condition", condition);

        JSONArray array = new JSONArray();
        for(Instruction instruction : instructions) {
            array.put(instruction.toJson());
        }
        object.put("instructions", array);

        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        this.line = object.getInt("line");
        this.condition = object.getString("condition");
        this.instructions = new ArrayList<>();

        JSONArray array = object.getJSONArray("instructions");

        for(int i = 0; i < array.length(); i++) {

            JSONObject arrayObject = array.getJSONObject(i);

            try {
                Class<?> clazz = Class.forName("de.pauhull.scravajipt.instructions." + arrayObject.getString("type"));
                Instruction instruction = (Instruction) clazz.getMethod("fromJson", JSONObject.class).invoke(clazz.newInstance(), arrayObject);
                this.instructions.add(instruction);
            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    @Override
    public List<Instruction> getContaining() {
        return instructions;
    }
}
