package me.geek.tom.testgame.server.console;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;

public class ConsoleHandler {
    static {
        InputStream stream = ConsoleHandler.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputLoop() {
        Scanner input = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String cmd = input.nextLine();
            if (cmd.equals("exit")) {
                running = false;
            }
        }
    }
}
