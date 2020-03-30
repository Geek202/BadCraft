package me.geek.tom.testgame.client.world;

import java.util.Objects;

public class ChunkPos {

    public int x;
    public int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
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
