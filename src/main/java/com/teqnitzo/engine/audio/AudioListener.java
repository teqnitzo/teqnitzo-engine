package com.teqnitzo.engine.audio;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioListener {

    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }

    public void setVelocity(Vector3f velocity) {
        alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }

    public void setOrientation(Vector3f at, Vector3f up) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(6);
        buffer.put(at.x).put(at.y).put(at.z);
        buffer.put(up.x).put(up.y).put(up.z);
        buffer.flip();

        alListenerfv(AL_ORIENTATION, buffer);
    }
}