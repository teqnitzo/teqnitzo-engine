package com.teqnitzo.engine.audio;

import com.teqnitzo.engine.scene.Component;

public class AudioComponent extends Component {

    private final SoundBuffer soundBuffer;
    private final SoundSource soundSource;
    private final boolean playOnStart;
    private boolean started = false;

    public AudioComponent(SoundBuffer soundBuffer, boolean loop, boolean playOnStart) {
        this.soundBuffer = soundBuffer;
        this.soundSource = new SoundSource();
        this.playOnStart = playOnStart;

        soundSource.setBuffer(soundBuffer.getId());
        soundSource.setLooping(loop);
        soundSource.setRelative(false);
        soundSource.setReferenceDistance(1.0f);
        soundSource.setMaxDistance(30.0f);
        soundSource.setRolloffFactor(1.0f);
    }

    @Override
    public void start() {
        if (playOnStart && !started) {
            soundSource.play();
            started = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        soundSource.setPosition(gameObject.getTransform().position);
    }

    public void play() {
        soundSource.play();
        started = true;
    }

    public void stop() {
        soundSource.stop();
    }

    public void pause() {
        soundSource.pause();
    }

    public boolean isPlaying() {
        return soundSource.isPlaying();
    }

    public SoundSource getSoundSource() {
        return soundSource;
    }

    public SoundBuffer getSoundBuffer() {
        return soundBuffer;
    }

    @Override
    public void cleanup() {
        soundSource.cleanup();
    }
}