package com.teqnitzo.engine.audio;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {

    private final int sourceId;

    public SoundSource() {
        sourceId = alGenSources();

        alSourcef(sourceId, AL_GAIN, 1.0f);
        alSourcef(sourceId, AL_PITCH, 1.0f);
        alSource3f(sourceId, AL_POSITION, 0.0f, 0.0f, 0.0f);
        alSource3f(sourceId, AL_VELOCITY, 0.0f, 0.0f, 0.0f);
        alSourcei(sourceId, AL_LOOPING, AL_FALSE);
    }

    public void setBuffer(int bufferId) {
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    public void play() {
        alSourcePlay(sourceId);
    }

    public void pause() {
        alSourcePause(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void setLooping(boolean looping) {
        alSourcei(sourceId, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
    }

    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
    }

    public int getId() {
        return sourceId;
    }
}