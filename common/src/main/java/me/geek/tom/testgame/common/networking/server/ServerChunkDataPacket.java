package me.geek.tom.testgame.common.networking.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
import me.geek.tom.testgame.common.networking.ChunkData;

import java.io.IOException;

public class ServerChunkDataPacket implements Packet {

    private ChunkData chunk;

    public ServerChunkDataPacket(ChunkData chunk) {
        this.chunk = chunk;
    }

    public ServerChunkDataPacket() { }

    public ChunkData getChunkData() {
        return chunk;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.chunk = new ChunkData(in);
    }

    @Override
    public void write(NetOutput out) throws IOException {
        this.chunk.writeToBuf(out);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
