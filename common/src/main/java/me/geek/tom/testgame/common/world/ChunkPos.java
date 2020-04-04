package me.geek.tom.testgame.common.world;

import java.util.Objects;

public class ChunkPos {

    public int x;
    public int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkPos(ChunkPos other) {
        this.x = other.x;
        this.z = other.z;
    }

    public boolean isWithinWorld() {
        return this.x >= 0 && this.x < ChunkManager.WORLD_SIZE && this.z >= 0 && this.z < ChunkManager.WORLD_SIZE;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkPos))
            return false;
        ChunkPos other = (ChunkPos)obj;
        return other.x==x && other.z==z;
    }

    @Override
    public String toString() {
        return "Chunk at " + x + " / " + z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}
