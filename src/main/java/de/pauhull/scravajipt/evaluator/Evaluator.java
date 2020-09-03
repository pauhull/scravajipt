package de.pauhull.scravajipt.evaluator;

import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.Variable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Evaluator {

    private Program program;

    public Evaluator(Program program) {

        this.program = program;
    }

    public void evaluate(String toEvaluate, Variable writeTo, int line) {

        toEvaluate = toEvaluate.replace("\\n", "\n");

        List<Variable> varsSortedByLength = new ArrayList<>(program.variables);
        varsSortedByLength.sort(Comparator.comparingInt(o -> Integer.MAX_VALUE - o.name.length()));

        for(Variable programVar : varsSortedByLength) {
            if(programVar.value == null || programVar.type == Variable.Type.STRING) continue;
            toEvaluate = toEvaluate.replace("&" + programVar.name, programVar.value.toString());
        }

        if(toEvaluate.startsWith("\"") && toEvaluate.endsWith("\"")) {

            toEvaluate = toEvaluate.substring(1, toEvaluate.length() - 1);
        } else {

            toEvaluate = evaluateBrackets(toEvaluate, writeTo, line);

            if(evaluateBool(toEvaluate, writeTo, line)) {
                return;
            }

            if(evaluateMath(toEvaluate, writeTo, line)) {
                return;
            }
        }

        for(Variable programVar : varsSortedByLength) {
            if(programVar.value == null || programVar.type != Variable.Type.STRING) continue;
            toEvaluate = toEvaluate.replace("&" + programVar.name, programVar.value.toString());
        }

        writeTo.value = toEvaluate;
        writeTo.type = Variable.Type.STRING;
    }

    private String evaluateBrackets(String toEvaluate, Variable variable, int line) {

        int lastOpeningIndex = 0;

        for(int i = 0; i < toEvaluate.length(); i++) {

            char c = toEvaluate.charAt(i);

            if(c == '(') {
                lastOpeningIndex = i;
            }

            if(c == ')') {

                String leftToBracket = toEvaluate.substring(0, lastOpeningIndex);
                String bracketContent = toEvaluate.substring(lastOpeningIndex + 1, i);
                String rightToBracket = toEvaluate.substring(i + 1);

                Variable temp = new Variable();
                evaluate(bracketContent, temp, line);

                return evaluateBrackets(leftToBracket + temp.value.toString() + rightToBracket, variable, line);
            }
        }

        return toEvaluate;
    }

    private boolean evaluateBool(String toEvaluate, Variable variable, int line) {

        toEvaluate = toEvaluate.replace(" ", "");

        if(toEvaluate.contains("&&")) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf("&&")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf("&&") + 2), right, line);

            if(left.type == Variable.Type.BOOL && right.type == Variable.Type.BOOL) {
                toEvaluate = Boolean.toString((boolean) left.value && (boolean) right.value);
            }
        }

        if(toEvaluate.contains("||")) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf("||")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf("||") + 2), right, line);

            if(left.type == Variable.Type.BOOL && right.type == Variable.Type.BOOL) {
                toEvaluate = Boolean.toString((boolean) left.value || (boolean) right.value);
            }
        }

        if(toEvaluate.contains("!=")) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf("!=")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf("!=") + 2), right, line);

            toEvaluate = Boolean.toString(!left.value.equals(right.value));
        }

        if(toEvaluate.contains("==")) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf("==")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf("==") + 2), right, line);

            toEvaluate = Boolean.toString(left.value.equals(right.value));
        }

        if(toEvaluate.lastIndexOf(">=") != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf(">=")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf(">=") + 2), right, line);

            if(left.type == Variable.Type.NUMBER && right.type == Variable.Type.NUMBER) {
                toEvaluate = Boolean.toString((double) left.value >= (double) right.value);
            }
        }

        if(toEvaluate.lastIndexOf("<=") != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf("<=")), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf("<=") + 2), right, line);

            if(left.type == Variable.Type.NUMBER && right.type == Variable.Type.NUMBER) {
                toEvaluate = Boolean.toString((double) left.value <= (double) right.value);
            }
        }

        if(toEvaluate.lastIndexOf('>') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf('>')), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf('>') + 1), right, line);

            if(left.type == Variable.Type.NUMBER && right.type == Variable.Type.NUMBER) {
                toEvaluate = Boolean.toString((double) left.value > (double) right.value);
            }
        }

        if(toEvaluate.lastIndexOf('<') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            evaluate(toEvaluate.substring(0, toEvaluate.indexOf('<')), left, line);
            evaluate(toEvaluate.substring(toEvaluate.indexOf('<') + 1), right, line);

            if(left.type == Variable.Type.NUMBER && right.type == Variable.Type.NUMBER) {
                toEvaluate = Boolean.toString((double) left.value < (double) right.value);
            }
        }

        if(toEvaluate.equalsIgnoreCase("true")) {
            variable.value = true;
            variable.type = Variable.Type.BOOL;
            return true;
        } else if(toEvaluate.equalsIgnoreCase("false")) {
            variable.value = false;
            variable.type = Variable.Type.BOOL;
            return true;
        } else {
            return false;
        }
    }

    private boolean evaluateMath(String toEvaluate, Variable variable, int line) {

        toEvaluate = toEvaluate.replace(" ", "");

        if(toEvaluate.indexOf('%') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            if(evaluateMath(toEvaluate.substring(0, toEvaluate.indexOf('%')), left, line)
                    && evaluateMath(toEvaluate.substring(toEvaluate.indexOf('%') + 1), right, line)) {

                toEvaluate = String.valueOf((double) left.value % (double) right.value);
            } else {

                return false;
            }
        }

        for(int i = 1; i < toEvaluate.length(); i++) {

            char c = toEvaluate.charAt(i);
            if(c != '-') continue;

            char leftChar = toEvaluate.charAt(i - 1);
            if(leftChar == '+'
                    || leftChar == '-'
                    || leftChar == '*'
                    || leftChar == '/'
                    || leftChar == '%') {
                continue;
            }

            Variable left = new Variable(),
                    right = new Variable();

            if(evaluateMath(toEvaluate.substring(0, i), left, line)
                    && evaluateMath(toEvaluate.substring(i + 1), right, line)) {

                toEvaluate = String.valueOf((double) left.value - (double) right.value);
            } else {

                return false;
            }
        }

        if(toEvaluate.indexOf('+') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            if(evaluateMath(toEvaluate.substring(0, toEvaluate.indexOf('+')), left, line)
                    && evaluateMath(toEvaluate.substring(toEvaluate.indexOf('+') + 1), right, line)) {

                toEvaluate = String.valueOf((double) left.value + (double) right.value);
            } else {

                return false;
            }
        }

        if(toEvaluate.indexOf('*') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            if(evaluateMath(toEvaluate.substring(0, toEvaluate.indexOf('*')), left, line)
                    && evaluateMath(toEvaluate.substring(toEvaluate.indexOf('*') + 1), right, line)) {

                toEvaluate = String.valueOf((double) left.value * (double) right.value);
            } else {

                return false;
            }
        }

        if(toEvaluate.indexOf('/') != -1) {

            Variable left = new Variable(),
                    right = new Variable();

            if(evaluateMath(toEvaluate.substring(0, toEvaluate.indexOf('/')), left, line)
                    && evaluateMath(toEvaluate.substring(toEvaluate.indexOf('/') + 1), right, line)) {

                toEvaluate = String.valueOf((double) left.value / (double) right.value);
            } else {

                return false;
            }
        }

        try {
            variable.value = Double.valueOf(toEvaluate);
            variable.type = Variable.Type.NUMBER;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
