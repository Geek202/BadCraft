package me.geek.tom.testgame.client.display;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public class DisplayUtil {
    public static void setupGl() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialise GLFW!");
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public static void terminate() {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
