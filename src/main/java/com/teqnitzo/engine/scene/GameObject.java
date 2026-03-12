package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.math.Transform;
import com.teqnitzo.engine.render.Mesh;
import com.teqnitzo.engine.render.Shader;
import com.teqnitzo.engine.render.Texture;

public class GameObject {

    private final Transform transform;
    private final Mesh mesh;
    private final Shader shader;
    private final Texture texture;

    public GameObject(Mesh mesh, Shader shader, Texture texture) {
        this.transform = new Transform();
        this.mesh = mesh;
        this.shader = shader;
        this.texture = texture;
    }

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Shader getShader() {
        return shader;
    }

    public Texture getTexture() {
        return texture;
    }
}