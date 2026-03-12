package com.teqnitzo.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private final Vector3f position;
    private final Vector3f rotation;

    private final float fov;
    private final float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera(float fov, float aspectRatio, float nearPlane, float farPlane) {
        this.position = new Vector3f(0.0f, 0.0f, 2.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);

        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .translate(-position.x, -position.y, -position.z);
    }

    public void moveForward(float amount) {
        position.z -= amount;
    }

    public void moveBackward(float amount) {
        position.z += amount;
    }

    public void moveLeft(float amount) {
        position.x -= amount;
    }

    public void moveRight(float amount) {
        position.x += amount;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}