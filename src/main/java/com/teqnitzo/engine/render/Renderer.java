package com.teqnitzo.engine.render;

import org.lwjgl.opengl.GL11;

public class Renderer {

    private Mesh triangle;
    private Shader shader;

    public void init() {

        float[] vertices = {
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        triangle = new Mesh(vertices);

        String vertexShader = """
                #version 330 core
                layout (location = 0) in vec3 aPos;

                void main() {
                    gl_Position = vec4(aPos, 1.0);
                }
                """;

        String fragmentShader = """
                #version 330 core
                out vec4 FragColor;

                void main() {
                    FragColor = vec4(0.2, 0.7, 1.0, 1.0);
                }
                """;

        shader = new Shader(vertexShader, fragmentShader);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        shader.bind();

        triangle.render();

        shader.unbind();
    }
}