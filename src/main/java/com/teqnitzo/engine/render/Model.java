package com.teqnitzo.engine.render;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private final List<Mesh> meshes = new ArrayList<>();

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public void destroy() {
        for (Mesh mesh : meshes) {
            mesh.destroy();
        }
    }
}