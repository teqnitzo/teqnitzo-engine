package com.teqnitzo.engine.render;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {

    private final int vao;
    private final int vbo;
    private final int vertexCount;

    public Mesh(float[] vertices) {
        vertexCount = vertices.length / 6;

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        int stride = 6 * Float.BYTES;

        GL20.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, stride, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, stride, 3L * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        MemoryUtil.memFree(buffer);

        GL30.glBindVertexArray(0);
    }

    public void render() {
        GL30.glBindVertexArray(vao);
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, vertexCount);
        GL30.glBindVertexArray(0);
    }
}