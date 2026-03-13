package com.teqnitzo.engine.render;

public class MeshData {

    private final float[] vertices;
    private final int[] indices;

    public MeshData(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}