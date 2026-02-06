package me.antonio.tonhoclient.module.render;

import me.antonio.tonhoclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class NoPumpkin extends Module {
    // Definimos a tecla padrão como P
    public NoPumpkin() {
        super("NoPumpkin", GLFW.GLFW_KEY_P);
    }
    // Como é um hack visual passivo, não precisa de lógica no onEnable
}