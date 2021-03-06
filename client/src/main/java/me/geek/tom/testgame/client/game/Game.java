package me.geek.tom.testgame.client.game;

import com.github.steveice10.packetlib.Client;
import me.geek.tom.testgame.client.display.DisplayUtil;
import me.geek.tom.testgame.client.display.Renderer;
import me.geek.tom.testgame.client.display.Window;
import me.geek.tom.testgame.client.display.math.Camera;
import me.geek.tom.testgame.client.display.models.*;
import me.geek.tom.testgame.client.game.exceptions.TerminateException;
import me.geek.tom.testgame.client.game.input.MouseInput;
import me.geek.tom.testgame.client.game.network.NetworkClient;
import me.geek.tom.testgame.common.world.ChunkManager;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private static final float CAMERA_POS_STEP = 0.5f;
    private static final float MOUSE_SENSITIVITY = 0.5f;

    private final Vector3f cameraInc;

    private MouseInput mouseInput;
    private Renderer renderer;

    private Window window;
    private Camera camera;

    private Timer timer;

    private Texture texture;

    private List<GameItem> gameItems;

    private ClientChunkManager chunkManager;
    public Client client;

    private GameItem skybox;

    public static Game INSTANCE;

    public Game() {
        cameraInc = new Vector3f(0, 0, 0);
        camera = new Camera();
        mouseInput = new MouseInput();
        timer = new Timer();
        INSTANCE = this;
    }

    public void init(String key) throws Exception {
        DisplayUtil.setupGl();
        window = new Window("BadCraft 2.0 v3 - Yeah...", 1024, 1024);
        renderer = new Renderer(window);
        mouseInput.init(window);
        renderer.init();
        gameItems = new ArrayList<>();

        chunkManager = new ClientChunkManager();

        NetworkClient clientRunnable = new NetworkClient(key);
        new Thread(clientRunnable).start(); // Start a client!

        this.chunkManager.requestChunks();
        this.createModels();
    }

    @SuppressWarnings("ConstantConditions")
    public void gameLoop() throws TerminateException {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;

        while (running && !window.shouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            renderer.render(gameItems, camera);

            sync();
        }
    }

    public void cleanup() {
        renderer.cleanup();
        for (GameItem item : gameItems) {
            item.getMesh().cleanUp();
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) { }
        }
    }

    private void input() throws TerminateException {
        mouseInput.input(window);

        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            throw new TerminateException();
        }
    }

    public void update(float interval) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP,
                cameraInc.y * CAMERA_POS_STEP,
                cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        buildNextChunk();

        Vector3f camPos = camera.getPosition();
        this.skybox.setPosition(camPos.x, camPos.y, camPos.z);
    }

    private void buildNextChunk() {
        GameItem item = chunkManager.buildNextChunk(texture);
        if (item != null)
            gameItems.add(item);
    }

    private void createModels() throws Exception {
        texture = new Texture("cube.png");
        Texture t = new Texture("skybox.png");
        System.out.println("Skybox is texture at: " + t.getId());
        Mesh m = Cube.createCube(t);
        GameItem gameItem = new GameItem(m, true);
        //gameItems.add(gameItem);
        gameItem.setPosition(0.0f, 0.0f, 0.0f);
        gameItem.setScale(50.0f);
        this.skybox = gameItem;
    }

    public ClientChunkManager getChunkManager() {
        return this.chunkManager;
    }
}
