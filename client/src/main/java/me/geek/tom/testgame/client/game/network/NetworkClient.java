package me.geek.tom.testgame.client.game.network;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import me.geek.tom.testgame.client.game.Game;
import me.geek.tom.testgame.common.networking.ProtocolDefinition;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class NetworkClient implements Runnable {

    //public static final String SERVER_HOSTNAME = "gameserver.tomthegeek.ml";
    public static final String SERVER_HOSTNAME = "127.0.0.1";
    public static final int SERVER_PORT = 34474;

    private Client client;
    private String key;
    public NetworkClient(String key) {
        this.key = key;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void run() {
        SecretKey key = new SecretKeySpec(this.key.getBytes(), "AES");

        ProtocolDefinition definition = new ProtocolDefinition(key);
        client = new Client(SERVER_HOSTNAME, SERVER_PORT, definition, new TcpSessionFactory());
        client.getSession().addListener(new GameClientListener());
        client.getSession().connect(true);

        Game.INSTANCE.client = client;

        while (client.getSession().isConnected()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
