package com.teqnitzo.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private final Vector3f position;

    private float pitch;
    private float yaw;

    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera(float fov, float aspectRatio, float nearPlane, float farPlane) {
        this.position = new Vector3f(0.0f, 0.0f, 2.0f);

        this.pitch = 0.0f;
        this.yaw = 0.0f;

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
                .rotateX(pitch)
                .rotateY(yaw)
                .translate(-position.x, -position.y, -position.z);
    }

    public Vector3f getForwardVector() {
        return new Vector3f(
                (float) Math.sin(yaw),
                0.0f,
                (float) -Math.cos(yaw)
        ).normalize();
    }

    public Vector3f getRightVector() {
        Vector3f forward = getForwardVector();
        return new Vector3f(-forward.z, 0.0f, forward.x).normalize();
    }

    public void moveForward(float amount) {
        position.add(getForwardVector().mul(amount));
    }

    public void moveBackward(float amount) {
        position.sub(getForwardVector().mul(amount));
    }

    public void moveLeft(float amount) {
        position.sub(getRightVector().mul(amount));
    }

    public void moveRight(float amount) {
        position.add(getRightVector().mul(amount));
    }

    public void addYaw(float amount) {
        yaw += amount;
    }

    public void addPitch(float amount) {
        pitch += amount;

        float limit = (float) Math.toRadians(89.0);
        if (pitch > limit) {
            pitch = limit;
        }
        if (pitch < -limit) {
            pitch = -limit;
        }
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getForward() {
        Vector3f forward = new Vector3f();

        forward.x = (float) (Math.cos(pitch) * Math.sin(yaw));
        forward.y = (float) Math.sin(pitch);
        forward.z = (float) -(Math.cos(pitch) * Math.cos(yaw));

        return forward.normalize();
    }

    public Vector3f getUp() {
        Vector3f forward = getForward();
        Vector3f right = new Vector3f(forward).cross(0.0f, 1.0f, 0.0f).normalize();
        return new Vector3f(right).cross(forward).normalize();
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }
}