package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.Fullbright;
import net.minecraft.client.MinecraftClient; // Era Minecraft
import net.minecraft.client.option.SimpleOption; // Era OptionInstance
import net.minecraft.client.render.LightmapTextureManager; // Era LightTexture
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class FullbrightMixin {

    // No Yarn, o método que atualiza a luz chama "update"
    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    // O alvo mudou de OptionInstance;get para SimpleOption;getValue
                    target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"
            )
    )
    private Object getGamma(SimpleOption<Double> option) {
        // Pega o valor original (Double)
        Double originalValue = option.getValue();

        // Verifica se a opção que o jogo está pedindo é o Gamma
        if (option == MinecraftClient.getInstance().options.getGamma()) {
            Fullbright mod = ModuleManager.get(Fullbright.class);

            // Se o módulo estiver ligado, retornamos 100.0 (super claro)
            if (mod != null && mod.isEnabled()) {
                return 100.0;
            }
        }

        // Vida normal se não for gamma ou mod desligado
        return originalValue;
    }
}