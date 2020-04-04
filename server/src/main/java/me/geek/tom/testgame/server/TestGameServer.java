package me.geek.tom.testgame.server;

import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import me.geek.tom.testgame.common.networking.ProtocolDefinition;
import me.geek.tom.testgame.common.world.ChunkManager;
import me.geek.tom.testgame.server.console.ConsoleHandler;
import me.geek.tom.testgame.server.server.GameServerListener;
import me.geek.tom.testgame.server.world.ServerChunkManager;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class TestGameServer {

    static {
        InputStream stream = ConsoleHandler.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int SERVER_PORT = 34474;
    public static TestGameServer INSTANCE;

    private ChunkManager chunkManager;

    private void run(String[] args) {

        if (args.length == 0) {
            System.err.println("Please define the secret key as the first argument!");
            return;
        }

        SecretKey key = new SecretKeySpec(args[0].getBytes(), "AES");

        chunkManager = new ServerChunkManager();

        Server server = new Server("0.0.0.0", SERVER_PORT, ProtocolDefinition.class, new TcpSessionFactory());
        server.addListener(new GameServerListener(key));
        server.bind(true);

        ConsoleHandler.inputLoop();
        server.close(true);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public static void main(String[] args) {
        INSTANCE = new TestGameServer();
        INSTANCE.run(args);
    }
}
