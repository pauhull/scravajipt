package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.syntax.InstructionSyntax;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Instruction {

    public int line;
    public InstructionContainer container;
    public InstructionSyntax syntax;

    public Instruction() {
    }

    public Instruction(String name, InstructionSyntax.Parameter... parameters) {

        this(new InstructionSyntax(name, parameters));
    }

    public Instruction(InstructionSyntax syntax) {

        this.syntax = syntax;
    }

    public abstract void run(Program program);

    public JSONObject toJson() {

        JSONObject object = new JSONObject();
        object.put("type", getClass().getSimpleName());
        object.put("line", line);

        return object;
    }

    public Instruction fromJson(JSONObject object) {

        this.line = object.getInt("line");

        return this;
    }

    protected JSONArray instructionListToJson(List<Instruction> instructions) {

        JSONArray array = new JSONArray();
        instructions.forEach(i -> array.put(i.toJson()));
        return array;
    }

    protected List<Instruction> jsonToInstructionList(JSONArray array) {

        List<Instruction> instructions = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {

            JSONObject arrayObject = array.getJSONObject(i);

            try {
                Class<?> clazz = Class.forName("de.pauhull.scravajipt.instructions." + arrayObject.getString("type"));
                Instruction instruction = (Instruction) clazz.getMethod("fromJson", JSONObject.class).invoke(clazz.newInstance(), arrayObject);
                instructions.add(instruction);
            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return instructions;
    }
}
