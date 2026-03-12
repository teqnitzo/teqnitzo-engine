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
                // front - red
                -0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f,

                // back - green
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,

                // left - blue
                -0.5f,  0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 0.0f, 1.0f,

                // right - yellow
                0.5f,  0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 1.0f, 0.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f,

                // top - magenta
                -0.5f,  0.5f, -0.5f, 1.0f, 0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f, -0.5f, 1.0f, 0.0f, 1.0f,

                // bottom - cyan
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 0.0f, 1.0f, 1.0f
        };

        int[] indices = {
                // front
                0, 1, 2,
                2, 3, 0,

                // back
                4, 5, 6,
                6, 7, 4,

                // left
                8, 9, 10,
                10, 11, 8,

                // right
                12, 13, 14,
                14, 15, 12,

                // top
                16, 17, 18,
                18, 19, 16,

                // bottom
                20, 21, 22,
                22, 23, 20
        };

        triangle = new Mesh(vertices, indices);
        transform = new Transform();
        camera = new Camera((float) Math.toRadians(60.0f), 1280.0f / 720.0f, 0.1f, 100.0f);

        String vertexShader = """
                #version 330 core
                layout (location = 0) in vec3 aPos;
                layout (location = 1) in vec3 aColor;
                
                out vec3 vColor;
                
                uniform mat4 uMVP;
                
                void main() {
                    vColor = aColor;
                    gl_Position = uMVP * vec4(aPos, 1.0);
                }
                """;

        String fragmentShader = """
                #version 330 core
                in vec3 vColor;
                
                out vec4 FragColor;
                
                void main() {
                    FragColor = vec4(vColor, 1.0);
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