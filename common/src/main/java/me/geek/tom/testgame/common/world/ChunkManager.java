package me.geek.tom.testgame.common.world;

import me.geek.tom.testgame.common.world.worldgen.PerlinGenerator;

import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    public static final int WORLD_SIZE = 16; // Size of the world in chunks (width/length)

    protected Map<ChunkPos, Chunk> chunks;

    public ChunkManager() {
        chunks = new HashMap<>();
        generateChunks();
    }

    protected void generateChunks() {
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int z = 0; z < WORLD_SIZE; z++) {
                ChunkPos pos = new ChunkPos(x, z);
                Chunk chunk = new Chunk(pos);
                chunks.put(pos, chunk);
                chunk.generate(new PerlinGenerator());
                this.chunkGenerated(chunk);
            }
        }
    }

    public static ChunkPos fromIndex(int idx) {
        int x = idx / WORLD_SIZE;
        int z = idx % WORLD_SIZE;

        return new ChunkPos(x, z);
    }

    public Chunk getChunk(ChunkPos pos) {
        return chunks.get(pos);
    }

    protected void chunkGenerated(Chunk chunk) {}
}
