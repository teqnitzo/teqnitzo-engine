package com.teqnitzo.engine.scene;

import org.joml.Vector3f;

public class DirectionalLight {

    private final Vector3f direction;
    private final Vector3f color;
    private float ambientStrength;

    public DirectionalLight(Vector3f direction, Vector3f color, float ambientStrength) {
        this.direction = direction;
        this.color = color;
        this.ambientStrength = ambientStrength;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getAmbientStrength() {
        return ambientStrength;
    }

    public void setAmbientStrength(float ambientStrength) {
        this.ambientStrength = ambientStrength;
    }
}