package com.teqnitzo.engine.input;

import org.lwjgl.glfw.GLFW;

public class Input {

    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST + 1];

    private static double mouseX;
    private static double mouseY;
    private static double deltaX;
    private static double deltaY;
    private static boolean firstMouseEvent = true;

    public static void setKey(int key, boolean pressed) {
        if (key >= 0 && key < keys.length) {
            keys[key] = pressed;
        }
    }

    public static boolean isKeyDown(int key) {
        return key >= 0 && key < keys.length && keys[key];
    }

    public static void setMousePosition(double x, double y) {
        if (firstMouseEvent) {
            mouseX = x;
            mouseY = y;
            firstMouseEvent = false;
        }

        deltaX += x - mouseX;
        deltaY += y - mouseY;

        mouseX = x;
        mouseY = y;
    }

    public static float getDeltaX() {
        return (float) deltaX;
    }

    public static float getDeltaY() {
        return (float) deltaY;
    }

    public static void endFrame() {
        deltaX = 0.0;
        deltaY = 0.0;
    }
}