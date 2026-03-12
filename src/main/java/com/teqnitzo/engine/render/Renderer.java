package com.teqnitzo.engine.render;

import com.teqnitzo.engine.scene.GameObject;
import com.teqnitzo.engine.scene.Scene;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Renderer {

    private final Camera camera;

    public Renderer() {
        this.camera = new Camera((float) Math.toRadians(60.0f), 1280.0f / 720.0f, 0.1f, 100.0f);
    }

    public void init() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void render(Scene scene) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        Matrix4f view = camera.getViewMatrix();
        Matrix4f projection = camera.getProjectionMatrix();

        for (GameObject gameObject : scene.getGameObjects()) {
            Matrix4f model = gameObject.getTransform().getModelMatrix();

            Matrix4f mvp = new Matrix4f(projection)
                    .mul(view)
                    .mul(model);

            Material material = gameObject.getMaterial();
            Shader shader = material.getShader();

            material.bind();

            shader.setUniform("uMVP", mvp);
            shader.setUniform("uLightDir", -0.5f, -1.0f, -0.3f);

            gameObject.getMesh().render();

            material.unbind();
        }
    }

    public Camera getCamera() {
        return camera;
    }
}