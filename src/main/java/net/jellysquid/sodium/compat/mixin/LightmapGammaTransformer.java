package net.jellysquid.sodium.compat.mixin;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.GammaOverride;
import net.minecraft.client.MinecraftClient; // Era Minecraft
import net.minecraft.client.option.SimpleOption; // Era OptionInstance
import net.minecraft.client.render.LightmapTextureManager; // Era LightTexture
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class LightmapGammaTransformer {

    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"
            )
    )
    private Object getGamma(SimpleOption<?> option) {
        // Primeiro, pegamos o valor como Object para não causar ClassCastException imediato
        Object value = option.getValue();

        // Verificamos se esta opção é realmente o Gamma
        if (option == MinecraftClient.getInstance().options.getGamma()) {
            GammaOverride mod = ModuleManager.get(GammaOverride.class);

            if (mod != null && mod.isEnabled()) {
                return 16.0D; // Retorna Double se ligado
            }
        }

        return value; // Retorna o valor original (seja ele o que for) sem tentar forçar cast
    }
}