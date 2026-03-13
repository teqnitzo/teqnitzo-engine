package com.teqnitzo.engine.render;

public class Material {

    private final Shader shader;
    private final Texture texture;

    public Material(Shader shader, Texture texture) {
        this.shader = shader;
        this.texture = texture;
    }

    public Shader getShader() {
        return shader;
    }

    public Texture getTexture() {
        return texture;
    }

    public void bind() {
        shader.bind();
        texture.bind(0);
        shader.setUniform("uTexture", 0);
    }

    public void unbind() {
        shader.unbind();
    }

    public void destroy() {
        // пока пусто
    }
}