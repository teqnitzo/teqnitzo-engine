package com.teqnitzo.engine.render;

public final class ModelLoader {

    private ModelLoader() {
    }

    public static Model loadObj(String resourcePath) {
        MeshData meshData = ObjLoader.load(resourcePath);

        Model model = new Model();
        model.addMesh(new Mesh(meshData));

        return model;
    }
}