package de.pauhull.scravajipt.util;

import de.pauhull.scravajipt.instructions.Instruction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class InstructionUtil {

    public static List<Instruction> instructionListFromJsonArray(JSONArray array) {

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
