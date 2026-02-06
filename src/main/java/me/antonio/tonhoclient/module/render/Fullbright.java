package me.antonio.tonhoclient.module.render;

import me.antonio.tonhoclient.module.Module; // (Ou 'Mod', se você renomeou)
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", GLFW.GLFW_KEY_G); // Tecla G pra ativar (se um dia usarmos tecla)
    }

    // Esse módulo é só um "interruptor", não precisa de lógica complexa aqui dentro.
    // O trabalho pesado será feito pelo Mixin, que vai checar o isEnabled() daqui.
}