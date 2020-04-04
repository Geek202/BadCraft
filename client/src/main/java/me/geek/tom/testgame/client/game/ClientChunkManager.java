package me.geek.tom.testgame.client.game;

import me.geek.tom.testgame.client.display.models.ChunkMeshBuilder;
import me.geek.tom.testgame.client.display.models.GameItem;
import me.geek.tom.testgame.client.display.models.Mesh;
import me.geek.tom.testgame.client.display.models.Texture;
import me.geek.tom.testgame.common.networking.ChunkData;
import me.geek.tom.testgame.common.networking.client.ClientRequestChunkPacket;
import me.geek.tom.testgame.common.world.Chunk;
import me.geek.tom.testgame.common.world.ChunkManager;
import me.geek.tom.testgame.common.world.ChunkPos;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class ClientChunkManager extends ChunkManager {

    private Queue<Chunk> pendingUpdates = new ArrayDeque<>();

    public ClientChunkManager() {
        chunks = new HashMap<>();
        //generateChunks();
    }

    public void requestChunks() {
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int z = 0; z < WORLD_SIZE; z++) {
                this.getChunk(new ChunkPos(x, z));
            }
        }
    }

    @Override
    public Chunk getChunk(ChunkPos pos) {
        if (pos.isWithinWorld() && !this.chunks.containsKey(pos)) {
            this.requestChunk(pos);
        }
        return super.getChunk(pos);
    }

    private void requestChunk(ChunkPos pos) {
        while (Game.INSTANCE.client == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Game.INSTANCE.client.getSession().send(new ClientRequestChunkPacket(pos));
    }

    public void onChunkRecieved(ChunkData chunkData) {
        Chunk chunk = chunkData.toChunk();
        this.chunks.put(chunk.getChunkPos(), chunk);
        pendingUpdates.add(chunk);
    }

    public GameItem buildNextChunk(Texture texture) {
        if (!pendingUpdates.isEmpty()) {
            Chunk chunk = pendingUpdates.poll();
            return buildChunk(chunk, texture);
        }
        return null;
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
