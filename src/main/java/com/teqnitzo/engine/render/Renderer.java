package com.teqnitzo.engine.render;

import com.teqnitzo.engine.scene.DirectionalLight;
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
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void resize(int width, int height) {
        if (height == 0) {
            height = 1;
        }

        GL11.glViewport(0, 0, width, height);
        camera.setAspectRatio((float) width / (float) height);
    }

    public void render(Scene scene) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        Matrix4f view = camera.getViewMatrix();
        Matrix4f projection = camera.getProjectionMatrix();

        DirectionalLight light = scene.getDirectionalLight();

        for (GameObject gameObject : scene.getGameObjects()) {
            Matrix4f model = gameObject.getTransform().getModelMatrix();

            Matrix4f mvp = new Matrix4f(projection)
                    .mul(view)
                    .mul(model);

            Material material = gameObject.getMaterial();
            Shader shader = material.getShader();

            material.bind();

            shader.setUniform("uMVP", mvp);

            if (light != null) {
                shader.setUniform(
                        "uLightDir",
                        light.getDirection().x,
                        light.getDirection().y,
                        light.getDirection().z
                );

                shader.setUniform(
                        "uLightColor",
                        light.getColor().x,
                        light.getColor().y,
                        light.getColor().z
                );

                shader.setUniform("uAmbientStrength", light.getAmbientStrength());
            }

            gameObject.getMesh().render();

            material.unbind();
        }
    }

    public Camera getCamera() {
        return camera;
    }
}