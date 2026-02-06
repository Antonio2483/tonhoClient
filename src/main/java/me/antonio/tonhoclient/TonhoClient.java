package me.antonio.tonhoclient;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.ui.TonhoMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient; // <--- Mudou de Minecraft para MinecraftClient
import net.minecraft.client.option.KeyBinding; // <--- Mudou de KeyMapping para KeyBinding
import net.minecraft.client.util.InputUtil; // <--- Mudou de InputConstants para InputUtil
import org.lwjgl.glfw.GLFW;

public class TonhoClient implements ClientModInitializer {

	// Variável estática da tecla
	public static KeyBinding menuKey; // Mudou o tipo aqui também

	@Override
	public void onInitializeClient() {
		// 1. Inicializa os módulos
		ModuleManager instance = ModuleManager.INSTANCE;
		System.out.println("!!! TONHO CLIENT: INICIANDO NA 1.21.4 (YARN) !!!");

		// 2. CRIAÇÃO DA TECLA
		menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.tonhoclient.menu",       // Tradução
				InputUtil.Type.KEYSYM,        // Tipo (Mudou InputConstants para InputUtil)
				GLFW.GLFW_KEY_LEFT_ALT,       // Tecla
				"key.categories.misc"         // Categoria
		));

		// 3. EVENTO DE TICK
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Nota: 'client' aqui já é um MinecraftClient
			while (menuKey.wasPressed()) { // Yarn usa 'wasPressed()' em vez de 'consumeClick()'
				client.setScreen(new TonhoMenu());
			}
		});

		// 4. COMANDO DE BACKUP (/tonho)
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("tonho")
					.executes(context -> {
						// execute() roda na thread principal
						MinecraftClient.getInstance().execute(() -> {
							MinecraftClient.getInstance().setScreen(new TonhoMenu());
						});
						return 1;
					}));
		});
	}
}