package me.geek.tom.testgame.client.display;

import me.geek.tom.testgame.client.Utils;
import me.geek.tom.testgame.client.display.math.Camera;
import me.geek.tom.testgame.client.display.math.Transformations;
import me.geek.tom.testgame.client.display.models.GameItem;
import me.geek.tom.testgame.client.display.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.util.List;

public class Renderer {
    private Window window;
    private ShaderProgram shaderProgram;

    private Transformations transformations;

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    public Renderer(Window window) throws Exception {
        this.window = window;
        this.window.setShowMouse(false);

        this.transformations = new Transformations();

        GL.createCapabilities();

        GL33.glEnable(GL33.GL_DEPTH_TEST);
        GL33.glClearColor(1.0f, 0.0f, 1.0f, 0.0f);

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        Matrix4f projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public void init() throws Exception {
        // Create uniforms for world and projection matrices
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");

        // Create texture uniform
        shaderProgram.createUniform("texture_sampler");
    }

    public void render(List<GameItem> gameItems, Camera camera) {
        GL33.glClear(GL33.GL_COLOR_BUFFER_BIT | GL33.GL_DEPTH_BUFFER_BIT);

        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformations.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformations.getViewMatrix(camera);

        shaderProgram.setUniform("texture_sampler", 0);

        // Draw!
        for(GameItem gameItem : gameItems) {
            // Set model view matrix for this item
            Matrix4f modelViewMatrix = transformations.getModelViewMatrix(gameItem, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mesh for this game item
            gameItem.getMesh().render();
        }

        // Restore state
        GL33.glDisableVertexAttribArray(0);
        GL33.glBindVertexArray(0);

        shaderProgram.unbind();

        window.swap();

        DisplayUtil.pollEvents();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }

        // Destroy the window.
        window.destroy();
    }
}
