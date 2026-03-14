package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.render.Material;
import com.teqnitzo.engine.render.Model;

public class RotatingObject extends GameObject {

    public RotatingObject(Model model, Material material) {
        super(model, material);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        getTransform().rotation.y += deltaTime;
        getTransform().rotation.x += deltaTime * 0.5f;
    }
}