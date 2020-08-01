import de.pauhull.scravajipt.compiler.Compiler;
import de.pauhull.scravajipt.compiler.CompilerException;
import de.pauhull.scravajipt.program.Program;
import de.pauhull.scravajipt.program.ProgramException;
import org.json.JSONObject;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Usage:");
            System.out.println("- \"compile <File>\" to compile a program");
            System.out.println("- \"run <File>\" to run");
            return;
        }

        if(args[0].equalsIgnoreCase("compile")) {

            if(args.length != 2) {
                System.out.println("Usage: \"compile <File>\"");
                return;
            }

            File sourceFile = new File(args[1]);
            File targetFile = new File(sourceFile.getParent(), sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf('.')) + ".prog");

            StringBuilder sourceBuilder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {

                String line;
                while((line = reader.readLine()) != null) {
                    sourceBuilder.append(line).append('\n');
                }
            } catch (IOException e) {
                System.err.println(String.format("Couldn't read file \"%s\"", args[1]));
                return;
            }

            Compiler compiler = new Compiler();
            Program program;

            try {
                program = compiler.compile(sourceBuilder.toString());
            } catch (CompilerException e) {
                System.err.println("Couldn't compile program. " + e.getMessage());
                return;
            }

            JSONObject compiled = program.toJson();

            try(PrintWriter writer = new PrintWriter(new FileWriter(targetFile))) {
                writer.print(compiled.toString());
            } catch (IOException e) {
                System.err.println(String.format("Couldn't write to file \"%s\"", targetFile.toPath()));
                return;
            }

            System.out.println(String.format("Successfully compiled program to \"%s\"", targetFile.toPath()));
        }

        if(args[0].equalsIgnoreCase("run")) {

            if(args.length != 2) {
                System.out.println("Usage: \"run <Target File>\"");
                return;
            }

            File targetFile = new File(args[1]);

            StringBuilder builder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(targetFile))) {

                String line;
                while((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }
            } catch (IOException e) {
                System.err.println(String.format("Couldn't read file \"%s\"", args[1]));
                return;
            }

            JSONObject object = new JSONObject(builder.toString());
            Program program = new Program().fromJson(object);

            try {
                program.run();
            } catch (ProgramException e) {
                System.err.println("Error while running program. " + e.getMessage());
            }
        }
    }
}
