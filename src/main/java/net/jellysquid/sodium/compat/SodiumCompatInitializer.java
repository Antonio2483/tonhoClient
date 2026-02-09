package net.jellysquid.sodium.compat;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.ui.SodiumOptionsGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SodiumCompatInitializer implements ClientModInitializer {

	public static KeyBinding menuKey;

	@Override
	public void onInitializeClient() {
		// 1. Inicializa os módulos de forma silenciosa
		ModuleManager instance = ModuleManager.INSTANCE;

		// Log camuflado: Se alguém vir seu log, parece apenas o Sodium carregando
		System.out.println("[Sodium-Compat] Initializing render pipeline optimization...");

		// 2. CRIAÇÃO DA TECLA (Corrigido para 1.21.1)
		menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.SodiumOptions.menu",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_KP_1,
				KeyBinding.Category.MISC
		));

		// 3. EVENTO DE TICK
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (menuKey.wasPressed()) {
				client.setScreen(new SodiumOptionsGUI());
			}
		});

		// 4. REMOVIDO: Comando /tonho por segurança (anti-detect)
	}
}