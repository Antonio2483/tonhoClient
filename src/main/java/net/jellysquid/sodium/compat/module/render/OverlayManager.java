package net.jellysquid.sodium.compat.module.render;

import net.jellysquid.sodium.compat.module.Module;
import org.lwjgl.glfw.GLFW;

public class OverlayManager extends Module {
    // Definimos a tecla padrão como P
    public OverlayManager() {
        super("NoPumpkin", GLFW.GLFW_KEY_P);
    }
    // Como é um hack visual passivo, não precisa de lógica no onEnable
}