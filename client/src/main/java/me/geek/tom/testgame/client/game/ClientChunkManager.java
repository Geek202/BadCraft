package me.geek.tom.testgame.client.game;

import me.geek.tom.testgame.client.display.models.ChunkMeshBuilder;
import me.geek.tom.testgame.client.display.models.GameItem;
import me.geek.tom.testgame.client.display.models.Mesh;
import me.geek.tom.testgame.client.display.models.Texture;
import me.geek.tom.testgame.common.world.Chunk;
import me.geek.tom.testgame.common.world.ChunkManager;
import me.geek.tom.testgame.common.world.ChunkPos;

public class ClientChunkManager extends ChunkManager {

    private int builtChunkCount = 0;

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
