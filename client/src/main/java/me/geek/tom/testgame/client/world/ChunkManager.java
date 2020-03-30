package me.geek.tom.testgame.client.world;

import me.geek.tom.testgame.client.display.models.ChunkMeshBuilder;
import me.geek.tom.testgame.client.display.models.GameItem;
import me.geek.tom.testgame.client.display.models.Mesh;
import me.geek.tom.testgame.client.display.models.Texture;

import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    public static final int WORLD_SIZE = 16; // Size of the world in chunks (width/length)

    private Map<ChunkPos, Chunk> chunks;
    private int builtChunkCount = 0;

    public ChunkManager() {
        chunks = new HashMap<>();
        generateChunks();
    }

    private void generateChunks() {
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int z = 0; z < WORLD_SIZE; z++) {
                ChunkPos pos = new ChunkPos(x, z);
                Chunk chunk = new Chunk(pos);
                chunks.put(pos, chunk);
                chunk.generate(chunk::perlinSimpleGenerator);
            }
        }
    }

    private ChunkPos fromIndex(int idx) {
        int x = idx / WORLD_SIZE;
        int z = idx % WORLD_SIZE;

        return new ChunkPos(x, z);
    }

    public Chunk getChunk(ChunkPos pos) {
        return chunks.get(pos);
    }

    public GameItem buildNextChunk(Texture texture) {
        if (builtChunkCount >= (WORLD_SIZE * WORLD_SIZE))
            return null;

        ChunkPos pos = fromIndex(builtChunkCount);
        builtChunkCount++;
        return buildChunk(getChunk(pos), texture);
    }

    private GameItem buildChunk(Chunk chunk, Texture texture) {
        ChunkMeshBuilder builder = new ChunkMeshBuilder(chunk);
        Mesh mesh = builder.toMesh(texture);
        GameItem gameItem = new GameItem(mesh);
        ChunkPos pos = chunk.getChunkPos();
        gameItem.setPosition(Chunk.CHUNK_SIZE * pos.x, 0, Chunk.CHUNK_SIZE * pos.z);
        return gameItem;
    }
}
