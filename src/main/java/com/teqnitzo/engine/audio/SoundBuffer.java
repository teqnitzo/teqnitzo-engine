package com.teqnitzo.engine.audio;

import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;

public class SoundBuffer {

    private final int bufferId;

    public SoundBuffer(String path) throws IOException {

        bufferId = alGenBuffers();

        try (MemoryStack stack = MemoryStack.stackPush()) {

            IntBuffer channels = stack.mallocInt(1);
            IntBuffer sampleRate = stack.mallocInt(1);

            ByteBuffer rawAudioBuffer = WavLoader.load(path);

            ShortBuffer pcm = stb_vorbis_decode_memory(rawAudioBuffer, channels, sampleRate);

            int format = channels.get(0) == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;

            alBufferData(bufferId, format, pcm, sampleRate.get(0));
            org.lwjgl.system.MemoryUtil.memFree(pcm);
        }
    }

    public int getId() {
        return bufferId;
    }

    public void cleanup() {
        alDeleteBuffers(bufferId);
    }
}