package com.teqnitzo.engine.render;

import java.util.HashMap;
import java.util.Map;

public final class ResourceManager {

    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, Model> models = new HashMap<>();

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

    public static Model getObjModel(String name, String resourcePath) {
        Model model = models.get(name);
        if (model == null) {
            model = ModelLoader.loadObj(resourcePath);
            models.put(name, model);
        }
        return model;
    }

    public static void clear() {
        for (Shader shader : shaders.values()) {
            shader.destroy();
        }

        for (Texture texture : textures.values()) {
            texture.destroy();
        }

        for (Model model : models.values()) {
            model.destroy();
        }

        shaders.clear();
        textures.clear();
        models.clear();
    }
}