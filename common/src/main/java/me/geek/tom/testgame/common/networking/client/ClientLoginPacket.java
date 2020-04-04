package me.geek.tom.testgame.common.networking.client;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * The packet used to signal a client joining
 */
public class ClientLoginPacket implements Packet {

    /**
     * The username of the client player.
     */
    private String username;

    /**
     * Constructor when recieved
     */
    public ClientLoginPacket() {}

    /**
     * Constructor on the client
     * @param username The player's username
     */
    public ClientLoginPacket(String username) {
        this.username = username;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.username = in.readString();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeString(this.username);
    }

    @Override
    public boolean isPriority() {
        return true;
    }

    public String getUsername() {
        return this.username;
    }
}
