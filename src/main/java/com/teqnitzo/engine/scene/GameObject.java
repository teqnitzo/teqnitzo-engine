package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.math.Transform;
import com.teqnitzo.engine.render.Material;
import com.teqnitzo.engine.render.Mesh;

public class GameObject {

    private final Transform transform;
    private final Mesh mesh;
    private final Material material;

    public GameObject(Mesh mesh, Material material) {
        this.transform = new Transform();
        this.mesh = mesh;
        this.material = material;
    }

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}