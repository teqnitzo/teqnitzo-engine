package com.teqnitzo.engine.scene;

import org.joml.Vector3f;

public class DirectionalLight {

    private final Vector3f direction;

    public DirectionalLight(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getDirection() {
        return direction;
    }
}