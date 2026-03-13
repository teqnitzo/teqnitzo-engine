package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.render.Material;
import com.teqnitzo.engine.render.Mesh;

public class RotatingCube extends GameObject {

    public RotatingCube(Mesh mesh, Material material) {
        super(mesh, material);
    }

    @Override
    public void update(float deltaTime) {
        getTransform().rotation.y += deltaTime;
        getTransform().rotation.x += deltaTime * 0.5f;
    }
}