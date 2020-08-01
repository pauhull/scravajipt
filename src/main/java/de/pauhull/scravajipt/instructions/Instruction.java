package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.program.Program;
import org.json.JSONObject;

public interface Instruction {

    void run(Program program);

    int getLine();

    JSONObject toJson();

    Instruction fromJson(JSONObject object);

}
