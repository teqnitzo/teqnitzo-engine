package com.teqnitzo.engine.audio;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AudioSystem {

    private final List<SoundSource> activeSources = new ArrayList<>();

    public void play(SoundBuffer soundBuffer) {
        SoundSource source = new SoundSource();
        source.setBuffer(soundBuffer.getId());
        source.setRelative(true);
        source.play();

        activeSources.add(source);
    }

    public void playAt(SoundBuffer soundBuffer, Vector3f position) {
        SoundSource source = new SoundSource();
        source.setBuffer(soundBuffer.getId());
        source.setRelative(false);
        source.setPosition(position);
        source.setReferenceDistance(1.0f);
        source.setMaxDistance(30.0f);
        source.setRolloffFactor(1.0f);
        source.play();

        activeSources.add(source);
    }

    public void update() {
        Iterator<SoundSource> iterator = activeSources.iterator();

        while (iterator.hasNext()) {
            SoundSource source = iterator.next();

            if (!source.isPlaying()) {
                source.cleanup();
                iterator.remove();
            }
        }
    }

    public void cleanup() {
        for (SoundSource source : activeSources) {
            source.cleanup();
        }

        activeSources.clear();
    }
}