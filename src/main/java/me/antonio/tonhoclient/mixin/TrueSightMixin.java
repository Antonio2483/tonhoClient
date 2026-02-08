package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.SeeInvis;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class TrueSightMixin {

    /**
     * O método getMixColor é perfeito porque ele retorna um 'int' (cor ARGB)
     * e recebe o 'state', permitindo checar se a entidade é invisível.
     */
    @Inject(method = "getMixColor", at = @At("HEAD"), cancellable = true)
    private void onGetMixColor(LivingEntityRenderState state, CallbackInfoReturnable<Integer> cir) {
        SeeInvis mod = ModuleManager.get(SeeInvis.class);

        // Se o mod estiver ligado e a entidade for invisível
        if (mod != null && mod.isEnabled() && state.invisible) {

            // Convertemos seu slider (0.0 a 1.0) para 0-255
            int alpha = (int) (SeeInvis.OPACITY * 255);
            alpha = Math.max(10, Math.min(255, alpha));

            // Montamos a cor: Alpha customizado + Branco (FFFFFF)
            // Isso vai sobrescrever a cor padrão do jogo por uma com o seu Alpha
            int customColor = (alpha << 24) | 0xFFFFFF;

            cir.setReturnValue(customColor);
        }
    }
}