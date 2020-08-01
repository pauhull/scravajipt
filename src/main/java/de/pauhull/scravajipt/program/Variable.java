package de.pauhull.scravajipt.program;

public class Variable {

    public String name;
    public Type type;
    public Object value;

    public Variable() {
    }

    public Variable(String name, Type type, Object value) {

        this.name = name;
        this.type = type;
        this.value = value;
    }

    public enum Type {

        NUMBER, BOOL, STRING;
    }
}
