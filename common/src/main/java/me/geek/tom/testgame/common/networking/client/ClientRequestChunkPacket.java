package me.geek.tom.testgame.common.networking.client;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
import me.geek.tom.testgame.common.world.ChunkPos;

import java.io.IOException;

public class ClientRequestChunkPacket implements Packet {

    private ChunkPos pos;

    public ClientRequestChunkPacket() {}
    public ClientRequestChunkPacket(ChunkPos pos) {
        this.pos = pos;
    }

    @Override
    public void read(NetInput in) throws IOException {
        int x = in.readInt();
        int z = in.readInt();
        this.pos = new ChunkPos(x, z);
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeInt(this.pos.x);
        out.writeInt(this.pos.z);
    }

    @Override
    public boolean isPriority() {
        return false;
    }

    public ChunkPos getPos() {
        return this.pos;
    }
}
