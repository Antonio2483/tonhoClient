package me.antonio.tonhoclient.module.render;

import me.antonio.tonhoclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class SeeInvis extends Module {

    public static float OPACITY = 0.5f;

    public SeeInvis() {
        // Tecla I de "Invisible" (ou a que você preferir)
        super("SeeInvis", GLFW.GLFW_KEY_I);

        if (OPACITY > 0.01f) {
            this.setEnabled(true);
        }
    }
}