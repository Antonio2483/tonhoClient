package net.jellysquid.sodium.compat.module.render;

import net.jellysquid.sodium.compat.module.Module;
import org.lwjgl.glfw.GLFW;

public class EntityProcessor extends Module {

    public static float OPACITY = 0.5f;

    public EntityProcessor() {
        // Tecla I de "Invisible" (ou a que você preferir)
        super("SeeInvis", GLFW.GLFW_KEY_I);

        if (OPACITY > 0.01f) {
            this.setEnabled(true);
        }
    }
}