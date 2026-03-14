package com.teqnitzo.engine.audio;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class AudioLoader {

    public static ByteBuffer load(String resourcePath) throws IOException {
        try (InputStream stream = AudioLoader.class.getResourceAsStream(resourcePath)) {

            if (stream == null) {
                throw new IOException("Sound file not found: " + resourcePath);
            }

            byte[] bytes = stream.readAllBytes();

            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();

            return buffer;
        }
    }
}