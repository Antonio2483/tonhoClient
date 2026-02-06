package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.NoPumpkin;
import net.minecraft.client.gui.DrawContext; // <--- Substitui GuiGraphics
import net.minecraft.client.gui.hud.InGameHud; // <--- Substitui Gui
import net.minecraft.util.Identifier; // <--- Yarn usa Identifier (pacote util)
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class) // <--- Apontamos para o HUD do jogo
public class NoPumpkinMixin {

    // O método agora se chama "renderOverlay" e recebe um DrawContext
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderOverlay(DrawContext context, Identifier texture, float opacity, CallbackInfo info) {

        // A lógica é a mesma: se o caminho da textura tiver "pumpkinblur", a gente cancela.
        if (texture.getPath().contains("pumpkinblur")) {
            NoPumpkin mod = ModuleManager.get(NoPumpkin.class);

            if (mod != null && mod.isEnabled()) {
                info.cancel(); // Tchau abóbora!
            }
        }
    }
}