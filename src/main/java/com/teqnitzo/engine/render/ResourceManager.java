package com.teqnitzo.engine.render;

import java.util.HashMap;
import java.util.Map;

public final class ResourceManager {

    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();

    private ResourceManager() {
    }

    public static Shader getShader(String name, String vertexPath, String fragmentPath) {
        Shader shader = shaders.get(name);
        if (shader == null) {
            shader = Shader.fromResources(vertexPath, fragmentPath);
            shaders.put(name, shader);
        }
        return shader;
    }

    public static Texture getTexture(String name, String resourcePath) {
        Texture texture = textures.get(name);
        if (texture == null) {
            texture = new Texture(resourcePath);
            textures.put(name, texture);
        }
        return texture;
    }

    public static void clear() {

        for (Shader shader : shaders.values()) {
            shader.destroy();
        }

        for (Texture texture : textures.values()) {
            texture.destroy();
        }

        shaders.clear();
        textures.clear();
    }
}