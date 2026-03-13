package com.teqnitzo.engine;

import com.teqnitzo.engine.audio.AudioEngine;
import com.teqnitzo.engine.input.Input;
import com.teqnitzo.engine.render.*;
import com.teqnitzo.engine.scene.DirectionalLight;
import com.teqnitzo.engine.scene.GameObject;
import com.teqnitzo.engine.scene.RotatingObject;
import com.teqnitzo.engine.scene.Scene;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Engine {

    private final Window window;
    private final Renderer renderer;
    private final Scene scene;
    private final AudioEngine audioEngine;
    private boolean running;

    public Engine(String title, int width, int height) {
        this.window = new Window(title, width, height);
        this.renderer = new Renderer();
        this.scene = new Scene();
        this.audioEngine = new AudioEngine();
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
        audioEngine.init();
        renderer.resize(window.getWidth(), window.getHeight());
        createScene();
        running = true;
    }

    private void createScene() {
        Model cubeModel = MeshFactory.createTexturedLitCube();
        Model objModel = ResourceManager.getObjModel("cube", "/models/cube.obj");

        Shader shader = ResourceManager.getShader("basic", "/shaders/basic.vert", "/shaders/basic.frag");
        Texture texture = ResourceManager.getTexture("crate", "/textures/crate.png");
        Material material = new Material(shader, texture);

        scene.setDirectionalLight(
                new DirectionalLight(
                        new Vector3f(-0.5f, -1.0f, -0.3f),
                        new Vector3f(1.0f, 1.0f, 1.0f),
                        0.2f
                )
        );

        GameObject cube = new RotatingObject(cubeModel, material);
        cube.getTransform().position.set(-2.0f, 0.0f, 0.0f);
        scene.addGameObject(cube);

        GameObject cubeObj = new RotatingObject(objModel, material);
        cubeObj.getTransform().position.set(2.0f, 0.0f, 0.0f);
        scene.addGameObject(cubeObj);
    }

    private void loop() {
        final double fixedTimeStep = 1.0 / 60.0;

        double previousTime = GLFW.glfwGetTime();
        double accumulator = 0.0;

        while (running && !window.shouldClose()) {
            if (window.isResized()) {
                renderer.resize(window.getWidth(), window.getHeight());
                window.setResized(false);
            }

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

        scene.update(deltaTime);

        Input.endFrame();
    }

    private void render() {
        renderer.render(scene);
    }

    private void shutdown() {
        audioEngine.cleanup();
        ResourceManager.clear();
        window.destroy();
    }
}