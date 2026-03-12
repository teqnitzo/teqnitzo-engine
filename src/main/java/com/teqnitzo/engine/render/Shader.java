package com.teqnitzo.engine.render;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryStack;

public class Shader {

    private final int programId;

    public Shader(String vertexSrc, String fragmentSrc) {
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexSrc);
        GL20.glCompileShader(vertexShader);

        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Vertex shader error: " + GL20.glGetShaderInfoLog(vertexShader));
        }

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentSrc);
        GL20.glCompileShader(fragmentShader);

        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Fragment shader error: " + GL20.glGetShaderInfoLog(fragmentShader));
        }

        programId = GL20.glCreateProgram();

        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Program link error: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void setUniform(String name, Matrix4f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location < 0) {
            throw new IllegalStateException("Uniform not found: " + name);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }
}