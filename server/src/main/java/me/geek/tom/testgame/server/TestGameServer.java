package me.geek.tom.testgame.server;

import me.geek.tom.testgame.server.console.ConsoleHandler;
import me.geek.tom.testgame.server.network.ServerInboundThread;

public class TestGameServer {

    private void run(String[] args) {
        new ServerInboundThread().start();
        ConsoleHandler.inputLoop();
    }

    public static void main(String[] args) {
        new TestGameServer().run(args);
    }
}
