package com.teqnitzo.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Engine {

    private final Window window;
    private boolean running;

    public Engine(String title, int width, int height) {
        this.window = new Window(title, width, height);
        this.running = false;
    }

    public void run() {
        init();
        loop();
        shutdown();
    }

    private void init() {
        window.init();
        running = true;
    }

    private void loop() {
        while (running && !window.shouldClose()) {
            update();
            render();

            window.swapBuffers();
            window.pollEvents();
        }
    }

    private void update() {
        if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            running = false;
        }
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void shutdown() {
        window.destroy();
    }
}