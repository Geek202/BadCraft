package me.geek.tom.testgame.common.world;

import me.geek.tom.testgame.common.world.worldgen.IGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk {

    public static final int CHUNK_SIZE = 32;

    private List<Integer> blocks = new ArrayList<>(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE);

    private ChunkPos pos;

    public Chunk(ChunkPos pos) {
        this.pos = pos;
        for (int i = 0; i < (CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE); i++) {
            this.blocks.add(0);
        }
    }

    public void generate(IGenerator generator) {
        Random r = new Random(1234567890 * this.pos.hashCode()); // Different for each chunk, but the same across restarts
        ChunkBlockPos p = new ChunkBlockPos(0, 0, 0);
        for (int x = 0; x < CHUNK_SIZE; x++) {
            p.x = x;
            for (int z = 0; z < CHUNK_SIZE; z++) {
                p.z = z;
                int maxY = generator.getHeight(x, z, getChunkPos(), r);
                for (int y = 0; y <= maxY; y++) {
                    p.y = y;
                    this.setPos(p, 1);
                }
            }
        }
    }

    public int simpleRandomGenerate(int x, int z, ChunkPos pos, Random r) {
        return r.nextInt(Chunk.CHUNK_SIZE);
    }

    public void setPos(ChunkBlockPos pos, int blockId) {
        blocks.set(toIndexPos(pos), blockId);
    }

    public boolean isAir(ChunkBlockPos pos) {
        return getPos(pos) == 0;
    }

    public int getPos(ChunkBlockPos pos) {
        if (pos.x < 0 || pos.x >= CHUNK_SIZE
         || pos.y < 0 || pos.y >= CHUNK_SIZE
         || pos.z < 0 || pos.z >= CHUNK_SIZE) {
            return 0;
        }
        return blocks.get(toIndexPos(pos));
    }

    public int toIndexPos(ChunkBlockPos pos) {
        return pos.x + (pos.y * CHUNK_SIZE) + (pos.z * CHUNK_SIZE * CHUNK_SIZE);
    }

    public ChunkBlockPos fromIndexPos(int index) {
        int tmp = index;
        int x = tmp % (CHUNK_SIZE * CHUNK_SIZE);
        tmp -= x;
        tmp /= CHUNK_SIZE;
        int y = tmp % CHUNK_SIZE;
        tmp -= y;
        tmp /= CHUNK_SIZE;
        int z = tmp;
        return new ChunkBlockPos(x, y, z);
    }

    public ChunkPos getChunkPos() {
        return pos;
    }
}
