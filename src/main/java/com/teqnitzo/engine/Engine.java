package com.teqnitzo.engine;

import com.teqnitzo.engine.input.Input;
import com.teqnitzo.engine.render.*;
import com.teqnitzo.engine.scene.GameObject;
import com.teqnitzo.engine.scene.Scene;
import org.lwjgl.glfw.GLFW;

public class Engine {

    private final Window window;
    private final Renderer renderer;
    private final Scene scene;
    private boolean running;

    public Engine(String title, int width, int height) {
        this.window = new Window(title, width, height);
        this.renderer = new Renderer();
        this.scene = new Scene();
        this.running = false;
    }

    public void run() {
        init();
        loop();
        shutdown();
    }

    private void init() {
        window.init();
        renderer.init();
        createScene();
        running = true;
    }

    private void createScene() {
        float[] vertices = {
                // front
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 1f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 1f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f,

                // back
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, -1f,
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, -1f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, -1f,
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, -1f,

                // left
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 1f, -1f, 0f, 0f,
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, -1f, 0f, 0f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 0f, -1f, 0f, 0f,
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, -1f, 0f, 0f,

                // right
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 1f, 0f, 0f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 0f, 1f, 0f, 0f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f,

                // top
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 1f, 0f,
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 1f, 0f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 1f, 0f,
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 0f,

                // bottom
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, -1f, 0f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, -1f, 0f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, -1f, 0f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, -1f, 0f
        };

        int[] indices = {
                0, 1, 2, 2, 3, 0,
                4, 5, 6, 6, 7, 4,
                8, 9, 10, 10, 11, 8,
                12, 13, 14, 14, 15, 12,
                16, 17, 18, 18, 19, 16,
                20, 21, 22, 22, 23, 20
        };

        Mesh mesh = new Mesh(vertices, indices);
        Shader shader = Shader.fromResources("/shaders/basic.vert", "/shaders/basic.frag");
        Texture texture = new Texture("/textures/crate.png");
        Material material = new Material(shader, texture);

        GameObject cube = new GameObject(mesh, material);
        scene.addGameObject(cube);

        GameObject cube2 = new GameObject(mesh, material);
        cube2.getTransform().position.set(2.0f, 0.0f, 0.0f);
        scene.addGameObject(cube2);
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
        if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            running = false;
        }
    }

    private void update(float deltaTime) {
        float cameraSpeed = 2.0f * deltaTime;
        float mouseSensitivity = 0.002f;

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            renderer.getCamera().moveForward(cameraSpeed);
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            renderer.getCamera().moveBackward(cameraSpeed);
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            renderer.getCamera().moveLeft(cameraSpeed);
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            renderer.getCamera().moveRight(cameraSpeed);
        }

        renderer.getCamera().addYaw(Input.getDeltaX() * mouseSensitivity);
        renderer.getCamera().addPitch(Input.getDeltaY() * mouseSensitivity);

        for (GameObject gameObject : scene.getGameObjects()) {
            gameObject.getTransform().rotation.y += deltaTime;
            gameObject.getTransform().rotation.x += deltaTime * 0.5f;
        }

        Input.endFrame();
    }

    private void render() {
        renderer.render(scene);
    }

    private void shutdown() {
        window.destroy();
    }
}