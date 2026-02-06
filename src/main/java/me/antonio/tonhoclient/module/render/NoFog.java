package me.antonio.tonhoclient.module.render;

import me.antonio.tonhoclient.module.Module; // Ou 'Module' se não renomeou
import org.lwjgl.glfw.GLFW;

public class NoFog extends Module {
    public NoFog() {
        super("NoFog", GLFW.GLFW_KEY_N);
    }
}