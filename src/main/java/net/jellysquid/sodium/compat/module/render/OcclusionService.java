package net.jellysquid.sodium.compat.module.render;

import net.jellysquid.sodium.compat.module.Module;
import org.lwjgl.glfw.GLFW;

public class OcclusionService extends Module {

    // Definindo os estados possíveis
    public enum Mode {
        OFF("§cDESLIGADO"),
        PLAYERS("§aPLAYERS"),
        MOBS("§6MOBS"),
        TUDO("§bTUDO");

        public final String nome;
        Mode(String nome) { this.nome = nome; }
    }

    public static Mode currentMode = Mode.OFF;

    public OcclusionService() {
        super("Wall", GLFW.GLFW_KEY_X);
    }

    @Override
    public void toggle() {
        // Cicla entre os modos: 0 -> 1 -> 2 -> 3 -> 0...
        int nextIndex = (currentMode.ordinal() + 1) % Mode.values().length;
        currentMode = Mode.values()[nextIndex];

        // Define se o módulo está "ativo" (qualquer coisa diferente de OFF)
        this.setEnabled(currentMode != Mode.OFF);
    }

    @Override
    public boolean isEnabled() {
        return currentMode != Mode.OFF;
    }

    @Override
    public void onDisable() {
        currentMode = Mode.OFF;
    }
}