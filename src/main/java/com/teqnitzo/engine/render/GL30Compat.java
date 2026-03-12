package com.teqnitzo.engine.render;

import org.lwjgl.opengl.GL30;

public class GL30Compat {

    public static void glGenerateMipmap(int target) {
        GL30.glGenerateMipmap(target);
    }
}