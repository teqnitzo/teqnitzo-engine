package com.teqnitzo.engine.render;

import com.teqnitzo.engine.math.Transform;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Renderer {

    private Mesh triangle;
    private Shader shader;
    private Transform transform;
    private Camera camera;

    public void init() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        float[] vertices = {
                // front
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,

                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,

                // back
                -0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,

                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,

                // left
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f,  0.5f,

                -0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,

                // right
                0.5f,  0.5f, -0.5f,
                0.5f,  0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,

                0.5f, -0.5f,  0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,

                // top
                -0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,

                0.5f,  0.5f,  0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,

                // bottom
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f,  0.5f,

                0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f, -0.5f
        };

        triangle = new Mesh(vertices);
        transform = new Transform();
        camera = new Camera((float) Math.toRadians(60.0f), 1280.0f / 720.0f, 0.1f, 100.0f);

        String vertexShader = """
                #version 330 core
                layout (location = 0) in vec3 aPos;

                uniform mat4 uMVP;

                void main() {
                    gl_Position = uMVP * vec4(aPos, 1.0);
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

    public void update(float deltaTime) {
        transform.rotation.y += deltaTime;
        transform.rotation.x += deltaTime * 0.5f;
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        Matrix4f model = transform.getModelMatrix();
        Matrix4f view = camera.getViewMatrix();
        Matrix4f projection = camera.getProjectionMatrix();

        Matrix4f mvp = new Matrix4f(projection)
                .mul(view)
                .mul(model);

        shader.bind();
        shader.setUniform("uMVP", mvp);
        triangle.render();
        shader.unbind();
    }

    public Camera getCamera() {
        return camera;
    }
}