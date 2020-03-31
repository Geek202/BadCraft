package me.geek.tom.testgame.common.world;

public class WorldPos {

    public int x, y, z;

    public WorldPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static WorldPos fromChunkBlockPos(ChunkBlockPos pos, ChunkPos chunkPos) {
        int x = (chunkPos.x * Chunk.CHUNK_SIZE) + pos.x;
        int z = (chunkPos.z * Chunk.CHUNK_SIZE) + pos.z;
        return new WorldPos(x, pos.y, z);
    }
}
