package com.teqnitzo.engine.audio;

import org.joml.Vector3f;

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

    public void setPosition(float x, float y, float z) {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }

    public void setPosition(Vector3f position) {
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }

    public void setVelocity(float x, float y, float z) {
        alSource3f(sourceId, AL_VELOCITY, x, y, z);
    }

    public void setVelocity(Vector3f velocity) {
        alSource3f(sourceId, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }

    public void setRelative(boolean relative) {
        alSourcei(sourceId, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }

    public void setReferenceDistance(float distance) {
        alSourcef(sourceId, AL_REFERENCE_DISTANCE, distance);
    }

    public void setMaxDistance(float distance) {
        alSourcef(sourceId, AL_MAX_DISTANCE, distance);
    }

    public void setRolloffFactor(float factor) {
        alSourcef(sourceId, AL_ROLLOFF_FACTOR, factor);
    }

    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
    }

    public int getId() {
        return sourceId;
    }
}