package com.teqnitzo.engine.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);

    public Matrix4f getModelMatrix() {
        return new Matrix4f()
                .translate(position)
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .scale(scale);
    }
}