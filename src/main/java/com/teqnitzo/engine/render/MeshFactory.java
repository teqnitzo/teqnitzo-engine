package com.teqnitzo.engine.render;

public final class MeshFactory {

    private MeshFactory() {
    }

    public static Model createTexturedLitCube() {
        float[] vertices = {
                // front
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 1f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 1f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f,

                // back
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, -1f,
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, -1f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, -1f,
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, -1f,

                // left
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 1f, -1f, 0f, 0f,
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, -1f, 0f, 0f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 0f, -1f, 0f, 0f,
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, -1f, 0f, 0f,

                // right
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 1f, 0f, 0f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 0f, 1f, 0f, 0f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f,

                // top
                -0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, 1f, 0f,
                -0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, 1f, 0f,
                0.5f,  0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, 1f, 0f,
                0.5f,  0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 0f,

                // bottom
                -0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 1f, 0f, 0f, -1f, 0f,
                0.5f, -0.5f, -0.5f, 1f, 1f, 1f, 0f, 0f, 0f, -1f, 0f,
                0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 0f, 1f, 0f, -1f, 0f,
                -0.5f, -0.5f,  0.5f, 1f, 1f, 1f, 1f, 1f, 0f, -1f, 0f
        };

        int[] indices = {
                0, 1, 2, 2, 3, 0,
                4, 5, 6, 6, 7, 4,
                8, 9, 10, 10, 11, 8,
                12, 13, 14, 14, 15, 12,
                16, 17, 18, 18, 19, 16,
                20, 21, 22, 22, 23, 20
        };

        Model model = new Model();
        model.addMesh(new Mesh(vertices, indices));
        return model;
    }
}