package com.teqnitzo.engine.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final int id;

    public Texture(String resourcePath) {
        ByteBuffer imageBuffer = loadResource(resourcePath);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer pixels = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);
        if (pixels == null) {
            throw new RuntimeException("Failed to load texture: " + resourcePath + " | " + STBImage.stbi_failure_reason());
        }

        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA8,
                width.get(0),
                height.get(0),
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                pixels
        );

        GL30Compat.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        STBImage.stbi_image_free(pixels);
    }

    public void bind(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void destroy() {
        GL11.glDeleteTextures(id);
    }

    private ByteBuffer loadResource(String resourcePath) {
        try (InputStream inputStream = Texture.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Texture resource not found: " + resourcePath);
            }

            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read texture resource: " + resourcePath, e);
        }
    }
}