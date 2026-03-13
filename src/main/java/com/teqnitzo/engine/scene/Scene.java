package com.teqnitzo.engine.scene;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<GameObject> gameObjects = new ArrayList<>();

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void update(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}