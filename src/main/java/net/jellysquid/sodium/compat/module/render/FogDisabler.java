package net.jellysquid.sodium.compat.module.render;

import net.jellysquid.sodium.compat.module.Module; // Ou 'Module' se não renomeou
import org.lwjgl.glfw.GLFW;

public class FogDisabler extends Module {
    public FogDisabler() {
        super("NoFog", GLFW.GLFW_KEY_N);
    }
}