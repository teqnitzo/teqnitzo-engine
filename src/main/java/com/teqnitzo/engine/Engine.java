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
        final double fixedTimeStep = 1.0 / 60.0;

        double previousTime = GLFW.glfwGetTime();
        double accumulator = 0.0;

        while (running && !window.shouldClose()) {
            double currentTime = GLFW.glfwGetTime();
            double frameTime = currentTime - previousTime;
            previousTime = currentTime;

            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += frameTime;

            input();

            while (accumulator >= fixedTimeStep) {
                update((float) fixedTimeStep);
                accumulator -= fixedTimeStep;
            }

            render();

            window.swapBuffers();
            window.pollEvents();
        }
    }

    private void input() {
        if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            running = false;
        }
    }

    private void update(float deltaTime) {
        // game logic here
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void shutdown() {
        window.destroy();
    }
}