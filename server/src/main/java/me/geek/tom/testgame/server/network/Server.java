package me.geek.tom.testgame.server.network;

import me.geek.tom.testgame.server.events.ServerEvents;

public class Server {
    private ClientConnection[] clientConnections = new ClientConnection[4];

    public static Server INSTANCE = new Server();

    public ClientConnection getConnectionInfo(int clientId) {
        return clientConnections[clientId];
    }

    public int clientJoined(long ip) {
        for (int i = 0; i < clientConnections.length; i++) {
            if (clientConnections[i] == null) {
                clientConnections[i] = new ClientConnection(ip, i);
                ServerEvents.clientJoin(i);
                return i;
            }
            if (clientConnections[i].getIp() == ip)
                return i;
        }
        return -1;
    }

    public void clientDisconnected(int clientId) {
        if (clientConnections[clientId] != null) {
            ServerEvents.clientDisconnect(clientId);
            clientConnections[clientId] = null;
        }
    }
}
