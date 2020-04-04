package me.geek.tom.testgame.common.networking;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import io.netty.buffer.ByteBuf;
import me.geek.tom.testgame.common.world.Chunk;
import me.geek.tom.testgame.common.world.ChunkPos;

import java.io.IOException;

public class ChunkData {

    private ChunkPos pos;
    private Integer[] blocks;

    public ChunkData(Chunk chunk) {
        this.pos = chunk.getChunkPos();
        this.blocks = new Integer[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
        chunk.writeToArray(blocks);
    }

    public ChunkData(NetInput buf) throws IOException {
        int x = buf.readInt();
        int z = buf.readInt();
        this.pos = new ChunkPos(x, z);
        int length = buf.readInt();
        this.blocks = new Integer[length];
        for (int i = 0; i < length; i++) {
            this.blocks[i] = buf.readInt();
        }
    }

    public Chunk toChunk() {
        return new Chunk(pos, blocks);
    }

    public void writeToBuf(NetOutput buf) throws IOException {
        buf.writeInt(this.pos.x);
        buf.writeInt(this.pos.z);
        buf.writeInt(blocks.length);
        for (int block : this.blocks) {
            buf.writeInt(block);
        }
    }
}
