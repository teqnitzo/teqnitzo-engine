package com.teqnitzo.engine.scene;

import org.joml.Vector3f;

public class RotatorComponent extends ScriptComponent {

    private final Vector3f rotationSpeed;

    public RotatorComponent(Vector3f rotationSpeed) {
        this.rotationSpeed = new Vector3f(rotationSpeed);
    }

    @Override
    public void update(float deltaTime) {
        gameObject.getTransform().rotation.x += rotationSpeed.x * deltaTime;
        gameObject.getTransform().rotation.y += rotationSpeed.y * deltaTime;
        gameObject.getTransform().rotation.z += rotationSpeed.z * deltaTime;
    }
}