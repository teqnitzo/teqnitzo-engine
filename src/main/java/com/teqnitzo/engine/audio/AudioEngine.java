package com.teqnitzo.engine.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioEngine {

    private long device = NULL;
    private long context = NULL;
    private boolean initialized = false;

    public void init() {

        device = alcOpenDevice((java.nio.ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open default OpenAL device.");
        }

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);

        context = alcCreateContext(device, (java.nio.IntBuffer) null);
        if (context == NULL) {
            alcCloseDevice(device);
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        if (!alcMakeContextCurrent(context)) {
            alcDestroyContext(context);
            alcCloseDevice(device);
            throw new IllegalStateException("Failed to make OpenAL context current.");
        }

        AL.createCapabilities(alcCapabilities);

        initialized = true;

        System.out.println("[Audio] OpenAL initialized successfully.");
    }

    public void cleanup() {
        if (context != NULL) {
            alcMakeContextCurrent(NULL);
            alcDestroyContext(context);
            context = NULL;
        }

        if (device != NULL) {
            alcCloseDevice(device);
            device = NULL;
        }

        initialized = false;
        System.out.println("[Audio] OpenAL cleaned up.");
    }

    public boolean isInitialized() {
        return initialized;
    }
}