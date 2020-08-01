package de.pauhull.scravajipt.compiler;

import de.pauhull.scravajipt.instructions.*;
import de.pauhull.scravajipt.program.Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Compiler {

    private final Pattern variableNamePattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);

    public Program compile(String source) throws CompilerException {

        List<String> lines = new ArrayList<>();
        Arrays.asList(source.split("\n")).forEach(line -> lines.add(removeSpacesInFront(line)));

        Program program = new Program();
        LinkedList<InstructionContainer> containers = new LinkedList<>();
        containers.add(program);

        for(int lineNum = 0; lineNum < lines.size(); lineNum++) {

            String line = lines.get(lineNum);

            Instruction instructionToAdd;

            if(line.toLowerCase().startsWith("set ")) {

                String[] args = line.split(" ");
                if(args.length < 3) {
                    throw new CompilerException(lineNum, "Expected variable name and value");
                }

                String varName = args[1];
                if(variableNamePattern.matcher(varName).find()) {
                    throw new CompilerException(lineNum, String.format("Variable name \"%s\" is invalid", varName));
                }

                StringBuilder builder = new StringBuilder();
                for(int i = 2; i < args.length; i++) {
                    if(builder.length() > 0) {
                        builder.append(" ");
                    }
                    builder.append(args[i]);
                }
                String assignment = builder.toString();

                instructionToAdd = new SetInstruction(lineNum, varName, assignment);
            } else if(line.toLowerCase().startsWith("while ")) {

                String[] args = line.split(" ");

                if (args.length < 2) {
                    throw new CompilerException(lineNum, "Expected condition");
                }

                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    if (builder.length() > 0) {
                        builder.append(" ");
                    }
                    builder.append(args[i]);
                }
                String condition = builder.toString();

                List<Instruction> instructions = new ArrayList<>();
                instructionToAdd = new WhileInstruction(lineNum, condition, instructions);
            } else if(line.toLowerCase().startsWith("if ")) {

                String[] args = line.split(" ");

                if (args.length < 2) {
                    throw new CompilerException(lineNum, "Expected condition");
                }

                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    if (builder.length() > 0) {
                        builder.append(" ");
                    }
                    builder.append(args[i]);
                }
                String condition = builder.toString();

                List<Instruction> instructions = new ArrayList<>();
                instructionToAdd = new IfInstruction(lineNum, condition, instructions);

            } else if(line.toLowerCase().startsWith("end")) {

                if (containers.size() <= 1) {
                    throw new CompilerException(lineNum, "No container to end");
                }

                containers.removeLast();
                instructionToAdd = null;
            } else if(line.startsWith("input ")) {

                String[] args = line.split(" ");

                if(args.length < 2) {
                    throw new CompilerException(lineNum, "Expected variable");
                }

                String writeTo = args[1];
                instructionToAdd = new InputInstruction(lineNum, writeTo);

            } else if(line.startsWith("print ") || line.startsWith("println ")) {

                String[] args = line.split(" ");

                if (args.length < 2) {
                    throw new CompilerException(lineNum, "Expected parameter");
                }

                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    if (builder.length() > 0) {
                        builder.append(" ");
                    }
                    builder.append(args[i]);
                }

                String toPrint = builder.toString();

                if(line.startsWith("print ")) {
                    instructionToAdd = new PrintInstruction(lineNum, toPrint);
                } else {
                    instructionToAdd = new PrintLnInstruction(lineNum, toPrint);
                }
            } else if(line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {

                instructionToAdd = null;
            } else {

                throw new CompilerException(lineNum, "Unexpected expression");
            }

            if(instructionToAdd != null) {
                containers.getLast().getContaining().add(instructionToAdd);
            }

            if(instructionToAdd instanceof InstructionContainer) {
                containers.addLast((InstructionContainer) instructionToAdd);
            }
        }

        if(containers.size() != 1) {
            throw new CompilerException(lines.size(), "Expected container end");
        }

        return program;
    }

    private String removeSpacesInFront(String s) {

        StringBuilder builder = new StringBuilder();

        boolean copy = false;
        for(char c : s.toCharArray()) {

            if(c != ' ' && c != '\t')
                copy = true;

            if(copy)
                builder.append(c);
        }

        return builder.toString();
    }

}
