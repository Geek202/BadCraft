package me.geek.tom.testgame.common.world.worldgen;

import me.geek.tom.testgame.common.world.ChunkBlockPos;
import me.geek.tom.testgame.common.world.ChunkPos;
import me.geek.tom.testgame.common.world.WorldPos;
import org.lwjgl.stb.STBPerlin;

import java.util.Random;

public class PerlinGenerator implements IGenerator {

    public static final float COORD_SCALE = 128.0f;
    public static final float HEIGHT_SCALE = 22.0f;

    public static final float LACUNARITY = 2.0f;
    public static final float GAIN = 0.5f;
    //public static final float OFFSET = 1.0f;
    public static final int OCTAVES = 6;

    public int getHeight(int x, int z, ChunkPos chunk, Random r) {
        WorldPos worldPos = WorldPos.fromChunkBlockPos(new ChunkBlockPos(x, 0, z), chunk);
        float val = (STBPerlin.stb_perlin_fbm_noise3(worldPos.x / COORD_SCALE, 0, worldPos.z / COORD_SCALE, LACUNARITY, GAIN, OCTAVES) +1) * HEIGHT_SCALE / 2;
        return ((int)val);
    }
}
