package me.geek.tom.testgame.client.display.models;

import static me.geek.tom.testgame.client.display.models.ChunkMeshBuilder.*;

public class Cube {
    public static Mesh createCube(Texture texture) {
        float[] positions = new float[] {
                // front
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                // back
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                // top
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                //
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                //
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                // bottom
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                0.0f, 0.0f,
                0.0f, 0.5f,

                0.5f, 0.0f,
                0.5f, 0.5f,

                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7
        };
        float[] light = new float[]{
                SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT,

                SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT,

                TOP_LIGHT, TOP_LIGHT, TOP_LIGHT, TOP_LIGHT,

                SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT, SIDE_LIGHT,

                BOTTOM_LIGHT, BOTTOM_LIGHT, BOTTOM_LIGHT, BOTTOM_LIGHT
        };

        return new Mesh(positions, indices, textCoords, texture, light);
    }
}
