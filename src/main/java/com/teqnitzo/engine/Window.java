package com.teqnitzo.engine;

import com.teqnitzo.engine.input.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Window {

    private final String title;
    private int width;
    private int height;

    private long handle;
    private boolean resized;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resized = false;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        handle = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (handle == 0) {
            GLFW.glfwTerminate();
            throw new IllegalStateException("Failed to create GLFW window");
        }

        GLFW.glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                Input.setKey(key, true);
            } else if (action == GLFW.GLFW_RELEASE) {
                Input.setKey(key, false);
            }
        });

        GLFW.glfwSetCursorPosCallback(handle, (window, xpos, ypos) -> {
            Input.setMousePosition(xpos, ypos);
        });

        GLFW.glfwSetFramebufferSizeCallback(handle, (window, newWidth, newHeight) -> {
            this.width = newWidth;
            this.height = newHeight;
            this.resized = true;
        });

        GLFW.glfwSetInputMode(handle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        GLFW.glfwMakeContextCurrent(handle);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(handle);

        GL.createCapabilities();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(handle);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(handle);
    }

    public void destroy() {
        GLFW.glfwDestroyWindow(handle);
        GLFW.glfwTerminate();

        GLFWErrorCallback callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public long getHandle() {
        return handle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }
}