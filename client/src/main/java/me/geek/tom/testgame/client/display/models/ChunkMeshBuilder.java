package me.geek.tom.testgame.client.display.models;


import me.geek.tom.testgame.common.world.Chunk;
import me.geek.tom.testgame.common.world.ChunkBlockPos;
import me.geek.tom.testgame.common.world.ChunkPos;
import me.geek.tom.testgame.common.world.Direction;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshBuilder {

    private Float[] positions;
    private Integer[] indices;
    private Float[] textCoords;
    private Float[] lightValues;
    private int tris = 0;

    // VERTICIES
    private float[] FRONT_VERTICES = new float[]{
            0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f
    };

    private float[] BACK_VERTICES = new float[]{
            1.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f
    };

    private float[] LEFT_VERTICES = new float[]{
            0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f
    };

    private float[] RIGHT_VERTICES = new float[]{
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f
    };

    private float[] TOP_VERTICES = new float[]{
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f
    };

    private float[] BOTTOM_VERTICES = new float[]{
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f
    };

    // TEXTURE COORDS
    private float[] SIDE_TEXT_Z = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f
    };

    private float[] SIDE_TEXT_X = new float[]{
            0.5f, 0.0f,
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f
    };

    private float[] BOTTOM_TEXT = new float[]{
            0.5f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 0.0f
    };

    private float[] TOP_TEXT = new float[]{
            0.0f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            0.5f, 0.5f
    };

    // LIGHTING
    public static final float TOP_LIGHT = 1.0f;
    public static final float SIDE_LIGHT = 0.8f;
    public static final float BOTTOM_LIGHT = 0.5f;

    private ChunkPos chunkPos;

    public ChunkMeshBuilder(Chunk chunk) {
        chunkPos = chunk.getChunkPos();

        System.out.println("Building mesh for: '" + chunkPos + "'");

        List<Float> positionList = new ArrayList<>();
        List<Integer> indiceList = new ArrayList<>();
        List<Float> textCoordList = new ArrayList<>();
        List<Float> lightList = new ArrayList<>();

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    ChunkBlockPos pos = new ChunkBlockPos(x, y, z);
                    if (!chunk.isAir(pos)) {
                        for (Direction d : Direction.values()) {
                            if (shouldAddFace(pos, d, chunk)) {
                                addFace(positionList, indiceList, textCoordList, lightList, d, pos);
                            }
                        }
                    }
                }
            }
        }
        positions = new Float[positionList.size()];
        positionList.toArray(positions);
        textCoords = new Float[textCoordList.size()];
        textCoordList.toArray(textCoords);
        indices = new Integer[indiceList.size()];
        indiceList.toArray(indices);
        lightValues = new Float[lightList.size()];
        lightList.toArray(lightValues);
    }

    public Mesh toMesh(Texture text) {
        float[] pos = new float[positions.length];
        float[] texts = new float[textCoords.length];
        int[] indice = new int[indices.length];
        float[] light = new float[lightValues.length];
        for (int i = 0; i < pos.length; i++)
            pos[i] = positions[i];
        for (int i = 0; i < texts.length; i++)
            texts[i] = textCoords[i];
        for (int i = 0; i < indice.length; i++)
            indice[i] = indices[i];
        for (int i = 0; i < light.length; i++)
            light[i] = lightValues[i];
        return new Mesh(pos, indice, texts, text, light);
    }

    private void addFace(List<Float> positions, List<Integer> indices, List<Float> textCoords, List<Float> lights, Direction face, ChunkBlockPos blockPos) {
        int[] indicies = new int[]{0, 1, 3, 3, 1, 2};

        float[] pos;
        float[] text;
        float light;
        switch (face) {
            case POSZ:
                light = SIDE_LIGHT;
                pos = FRONT_VERTICES;
                text = SIDE_TEXT_Z;
                break;
            case UP:
                light = TOP_LIGHT;
                pos = TOP_VERTICES;
                text = TOP_TEXT;
                break;
            case DOWN:
                light = BOTTOM_LIGHT;
                pos = BOTTOM_VERTICES;
                text = BOTTOM_TEXT;
                break;
            case NEGZ:
                light = SIDE_LIGHT;
                pos = BACK_VERTICES;
                text = SIDE_TEXT_Z;
                break;
            case NEGX:
                light = SIDE_LIGHT;
                pos = LEFT_VERTICES;
                text = SIDE_TEXT_X;
                break;
            case POSX:
                light = SIDE_LIGHT;
                pos = RIGHT_VERTICES;
                text = SIDE_TEXT_X;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + face); // Make it compile
        }

        for (int i = 0; i < 4; i++) {
            lights.add(light);
        }
        for (int i : indicies)
            indices.add(i + tris);
        for (float f : text)
            textCoords.add(f);

        for (int i = 0; i < pos.length; i++) {
            if (i % 3 == 0)
                positions.add(pos[i] + blockPos.x);
            else if (i % 3 == 1)
                positions.add(pos[i] + blockPos.y);
            else positions.add(pos[i] + blockPos.z);
        }

        tris += 4;
    }

    private boolean shouldAddFace(ChunkBlockPos pos, Direction direction, Chunk chunk) {
        return chunk.isAir(pos.inDirection(direction));
    }
}
