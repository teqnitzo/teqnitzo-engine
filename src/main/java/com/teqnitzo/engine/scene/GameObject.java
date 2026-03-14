package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.audio.AudioComponent;
import com.teqnitzo.engine.math.Transform;
import com.teqnitzo.engine.render.Material;
import com.teqnitzo.engine.render.Model;

public class GameObject {

    private final Transform transform;
    private final Model model;
    private final Material material;
    private AudioComponent audioComponent;

    public GameObject(Model model, Material material) {
        this.transform = new Transform();
        this.model = model;
        this.material = material;
    }

    public void update(float deltaTime) {
        if (audioComponent != null) {
            audioComponent.update(this);
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public Model getModel() {
        return model;
    }

    public Material getMaterial() {
        return material;
    }

    public void setAudioComponent(AudioComponent audioComponent) {
        this.audioComponent = audioComponent;
    }

    public AudioComponent getAudioComponent() {
        return audioComponent;
    }

    public void cleanup() {
        if (audioComponent != null) {
            audioComponent.cleanup();
        }
    }
}