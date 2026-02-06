package me.antonio.tonhoclient.module;

import net.minecraft.client.MinecraftClient;


public abstract class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final String name;
    private int key; // A tecla de atalho (GLFW code)
    private boolean enabled = false;
    private boolean wasPressed = false;

    public Module(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public boolean wasPressed() { return wasPressed; }
    public void setPressed(boolean pressed) { this.wasPressed = pressed; }
    // Métodos opcionais para sobrescrever
    public void onEnable() { }
    public void onDisable() { }
    public void onTick() { } // Útil para coisas que rodam todo frame (tipo killaura)

    public String getName() { return name; }
    public int getKey() { return key; }
    public boolean isEnabled() { return enabled; }
}