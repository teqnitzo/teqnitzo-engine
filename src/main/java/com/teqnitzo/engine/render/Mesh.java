package com.teqnitzo.engine.render;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private final int vao;
    private final int vbo;
    private final int ebo;
    private final int indexCount;

    public Mesh(float[] vertices, int[] indices) {
        this.indexCount = indices.length;

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        ebo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);

        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);

        int stride = 11 * Float.BYTES;

        GL20.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, stride, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, stride, 3L * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        GL20.glVertexAttribPointer(2, 2, GL30.GL_FLOAT, false, stride, 6L * Float.BYTES);
        GL20.glEnableVertexAttribArray(2);

        GL20.glVertexAttribPointer(3, 3, GL30.GL_FLOAT, false, stride, 8L * Float.BYTES);
        GL20.glEnableVertexAttribArray(3);

        MemoryUtil.memFree(vertexBuffer);
        MemoryUtil.memFree(indexBuffer);

        GL30.glBindVertexArray(0);
    }

    public Mesh(MeshData meshData) {
        this(meshData.getVertices(), meshData.getIndices());
    }

    public void render() {
        GL30.glBindVertexArray(vao);
        GL30.glDrawElements(GL30.GL_TRIANGLES, indexCount, GL30.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
    }

    public void destroy() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
        GL15.glDeleteBuffers(ebo);
    }
}