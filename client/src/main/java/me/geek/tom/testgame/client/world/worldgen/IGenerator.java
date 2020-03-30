package me.geek.tom.testgame.client.world.worldgen;

import java.util.Random;

public interface IGenerator {


    /**
     *
     * @param x coordinate of current column
     * @param z coordinate of current column
     * @param random A preconfigured random for RNG
     * @return The height of that chunk column, from zero to {@link me.geek.tom.testgame.client.world.Chunk#CHUNK_SIZE}
     */
    int getHeight(int x, int z, Random random);
}
