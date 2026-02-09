package net.jellysquid.sodium.compat.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameDistortionService {

    /**
     * Intercepta a renderização do overlay de náusea.
     * At HEAD com cancellable = true permite "pular" o método original.
     */
    @Inject(method = "renderNauseaOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderNausea(DrawContext context, float nauseaStrength, CallbackInfo ci) {
        // Cancela a execução do método original. Nada será desenhado.
        ci.cancel();
    }
}