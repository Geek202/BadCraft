package me.geek.tom.testgame.client;

import me.geek.tom.testgame.client.display.DisplayUtil;
import me.geek.tom.testgame.client.game.Game;
import me.geek.tom.testgame.client.game.exceptions.TerminateException;
import org.lwjgl.Version;

public class TestGameClient {

    private Game game;

    private void run(String[] args) {
        if (args.length == 0) {
            System.err.println("Please define the secret key as the first argument!");
            return;
        }

        System.out.println("Using LWJGL version " + Version.getVersion());

        try {
            this.init(args[0]);
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

    private void init(String key) throws Exception {
        game = new Game();
        game.init(key);
    }

    private void loop() throws Exception {
        game.gameLoop();
    }



    public static void main(String[] args) {
        new TestGameClient().run(args);
    }
}
