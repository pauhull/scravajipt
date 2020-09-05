package de.pauhull.scravajipt.io;

import java.util.Scanner;

public class ConsoleAdapter implements IOAdapter {

    @Override
    public void output(String s) {
        System.out.print(s);
    }

    @Override
    public String input() {
        return new Scanner(System.in).nextLine();
    }

}
