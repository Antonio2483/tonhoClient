package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.NoFog;
import net.minecraft.client.render.BackgroundRenderer; // Era FogRenderer/BackgroundRenderer
import net.minecraft.client.render.Fog; // Era FogParameters
import net.minecraft.client.render.FogShape; // Para o formato da neblina
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class NoFogMixin {

    @Redirect(
            method = "applyFog",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/client/render/Fog" // O alvo é o construtor da neblina
            )
    )
    private static Fog onCmputeFog(float start, float end, FogShape shape, float red, float green, float blue, float alpha) {

        NoFog mod = ModuleManager.get(NoFog.class);

        if (mod != null && mod.isEnabled()) {
            // Se o mod tá ligado, criamos uma neblina que começa no 0 e termina no 1 milhão.
            // O resto (cor, formato) mantemos o original.
            return new Fog(0.0f, 1_000_000.0f, shape, red, green, blue, alpha);
        }

        // Se o mod tá desligado, deixa criar a neblina normal do jogo
        return new Fog(start, end, shape, red, green, blue, alpha);
    }
}