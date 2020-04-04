package me.geek.tom.testgame.server.console;

import java.util.Scanner;

public class ConsoleHandler {
    public static void inputLoop() {
        Scanner input = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String cmd = input.nextLine();
            if (cmd.equals("end")) {
                running = false;
            }
        }
    }
}
