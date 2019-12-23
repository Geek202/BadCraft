package me.geek.tom.testgame.server.network;

public class ClientConnection {
    private final long ip;
    private final int clientId;

    public long getIp() {
        return ip;
    }

    public int getClientId() {
        return clientId;
    }

    public ClientConnection(long ip, int clientId) {
        this.ip = ip;
        this.clientId = clientId;
    }
}
