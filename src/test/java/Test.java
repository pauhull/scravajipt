import de.pauhull.scravajipt.compiler.Compiler;
import de.pauhull.scravajipt.program.Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {

        Compiler compiler = new Compiler();

        File file = new File("B:\\test.sj");
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Program program = compiler.compile(builder.toString());
        program.run();
    }

}
