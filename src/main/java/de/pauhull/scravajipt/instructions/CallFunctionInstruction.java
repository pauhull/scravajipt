package de.pauhull.scravajipt.instructions;

import de.pauhull.scravajipt.function.Function;
import de.pauhull.scravajipt.program.Program;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class CallFunctionInstruction extends Instruction {

    public String functionToCall;
    public String[] parameters;

    public CallFunctionInstruction() {
    }

    public CallFunctionInstruction(int line, String functionToCall, String[] parameters) {

        this.line = line;
        this.functionToCall = functionToCall;
        this.parameters = parameters;
    }

    @Override
    public void run(Program program) {

        for(Function function : program.functions) {
            if(function.name.equalsIgnoreCase(functionToCall)) {
                function.run(program, parameters);
                return;
            }
        }
    }

    @Override
    public JSONObject toJson() {

        JSONObject object = super.toJson();
        object.put("functionToCall", functionToCall);
        object.put("parameters", parameters);
        return object;
    }

    @Override
    public Instruction fromJson(JSONObject object) {

        super.fromJson(object);
        this.functionToCall = object.getString("functionToCall");

        AtomicInteger i = new AtomicInteger();
        JSONArray parametersJson = object.getJSONArray("parameters");
        this.parameters = new String[parametersJson.length()];
        parametersJson.forEach(o -> parameters[i.getAndIncrement()] = o.toString());

        return this;
    }
}
