package com.teqnitzo.engine.scene;

public abstract class Component {

    protected GameObject gameObject;

    void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {
    }

    public void update(float deltaTime) {
    }

    public void cleanup() {
    }
}