package me.geek.tom.testgame.client;

import me.geek.tom.testgame.client.display.DisplayUtil;
import me.geek.tom.testgame.client.game.Game;
import me.geek.tom.testgame.client.game.exceptions.TerminateException;
import org.lwjgl.Version;

public class TestGameClient {

    private Game game;

    private void run(String[] args) {
        System.out.println("Using LWJGL version " + Version.getVersion());

        try {
            this.init();
            this.loop();
        } catch (TerminateException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Stopping...");
            game.cleanup();
            DisplayUtil.terminate();
        }
    }

    private void init() throws Exception {
        game = new Game();
        game.init();
    }

    private void loop() throws Exception {
        game.gameLoop();
    }



    public static void main(String[] args) {
        new TestGameClient().run(args);
    }
}
