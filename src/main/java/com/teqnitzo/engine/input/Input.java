package com.teqnitzo.engine.input;

import org.lwjgl.glfw.GLFW;

public class Input {

    private static final boolean[] currentKeys = new boolean[GLFW.GLFW_KEY_LAST + 1];
    private static final boolean[] previousKeys = new boolean[GLFW.GLFW_KEY_LAST + 1];

    private static double mouseX;
    private static double mouseY;
    private static double deltaX;
    private static double deltaY;
    private static boolean firstMouseEvent = true;

    private Input() {
    }

    public static void setKey(int key, boolean pressed) {
        if (key >= 0 && key < currentKeys.length) {
            currentKeys[key] = pressed;
        }
    }

    public static boolean isKeyDown(int key) {
        return key >= 0 && key < currentKeys.length && currentKeys[key];
    }

    public static boolean isKeyPressed(int key) {
        return key >= 0
                && key < currentKeys.length
                && currentKeys[key]
                && !previousKeys[key];
    }

    public static boolean isKeyReleased(int key) {
        return key >= 0
                && key < currentKeys.length
                && !currentKeys[key]
                && previousKeys[key];
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

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static void endFrame() {
        System.arraycopy(currentKeys, 0, previousKeys, 0, currentKeys.length);
        deltaX = 0.0;
        deltaY = 0.0;
    }

    public static void resetMouse() {
        firstMouseEvent = true;
        deltaX = 0.0;
        deltaY = 0.0;
    }
}