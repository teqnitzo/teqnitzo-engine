package com.teqnitzo.engine.render;

import org.lwjgl.opengl.GL11;

public class Renderer {

    public void init() {
        GL11.glClearColor(0.1f, 0.1f, 0.12f, 1.0f);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
}