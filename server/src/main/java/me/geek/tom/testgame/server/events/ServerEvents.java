package me.geek.tom.testgame.server.events;

import com.google.common.net.InetAddresses;
import me.geek.tom.testgame.server.network.ClientConnection;
import me.geek.tom.testgame.server.network.Server;

@SuppressWarnings("UnstableApiUsage")
public class ServerEvents {
    public static void clientJoin(int clientId) {
        ClientConnection conn = Server.INSTANCE.getConnectionInfo(clientId);
        System.out.println("[Server] Client from " + InetAddresses.fromInteger(Math.toIntExact(conn.getIp())).getHostAddress() + " joined with client ID " + clientId);
    }

    public static void clientDisconnect(int clientId) {
        System.out.println("[Server] Client ID " + clientId + " disconnected by request.");
    }
}
