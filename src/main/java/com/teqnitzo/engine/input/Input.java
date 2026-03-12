package com.teqnitzo.engine.input;

import org.lwjgl.glfw.GLFW;

public class Input {

    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    public static void setKey(int key, boolean pressed) {
        if (key >= 0 && key < keys.length) {
            keys[key] = pressed;
        }
    }

    public static boolean isKeyDown(int key) {
        if (key >= 0 && key < keys.length) {
            return keys[key];
        }
        return false;
    }
}