package me.geek.tom.testgame.client.display.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final int id;

    public Texture(String fileName) throws Exception {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    private static int loadTexture(String filename) throws Exception {
        int width;
        int height;
        ByteBuffer buf;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = STBImage.stbi_load(filename, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + filename  + "] not loaded: " + STBImage.stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        int textureId = GL33.glGenTextures();
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, textureId);

        GL33.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL33.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

        GL33.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        STBImage.stbi_image_free(buf);

        return textureId;
    }

    public void cleanup() {
        GL33.glDeleteTextures(id);
    }
}
