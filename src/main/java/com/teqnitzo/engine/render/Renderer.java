package com.teqnitzo.engine.render;

import com.teqnitzo.engine.math.Transform;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Renderer {

    private Mesh triangle;
    private Shader shader;
    private Transform transform;
    private Camera camera;
    private Texture texture;

    public void init() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);

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

        triangle = new Mesh(vertices, indices);
        transform = new Transform();
        camera = new Camera((float) Math.toRadians(60.0f), 1280.0f / 720.0f, 0.1f, 100.0f);

        String vertexShader = """
                #version 330 core
                layout (location = 0) in vec3 aPos;
                layout (location = 1) in vec3 aColor;
                layout (location = 2) in vec2 aTexCoord;
                layout (location = 3) in vec3 aNormal;
        
                out vec3 vColor;
                out vec2 vTexCoord;
                out vec3 vNormal;
        
                uniform mat4 uMVP;
        
                void main() {
                    vColor = aColor;
                    vTexCoord = aTexCoord;
                    vNormal = aNormal;
                    gl_Position = uMVP * vec4(aPos, 1.0);
                }
                """;

        String fragmentShader = """
                #version 330 core
                in vec3 vColor;
                in vec2 vTexCoord;
                in vec3 vNormal;
        
                out vec4 FragColor;
        
                uniform sampler2D uTexture;
                uniform vec3 uLightDir;
        
                void main() {
                    vec3 normal = normalize(vNormal);
                    vec3 lightDir = normalize(-uLightDir);
        
                    float diffuse = max(dot(normal, lightDir), 0.2);
        
                    vec4 texColor = texture(uTexture, vTexCoord);
                    FragColor = texColor * vec4(vColor, 1.0) * diffuse;
                }
                """;

        shader = new Shader(vertexShader, fragmentShader);
        texture = new Texture("/textures/crate.png");
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
        texture.bind(0);
        shader.setUniform("uTexture", 0);
        shader.setUniform("uLightDir", -0.5f, -1.0f, -0.3f);
        triangle.render();
        shader.unbind();
    }

    public Camera getCamera() {
        return camera;
    }
}