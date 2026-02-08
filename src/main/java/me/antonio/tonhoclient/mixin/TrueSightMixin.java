package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.SeeInvis;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public class TrueSightMixin {

    /**
     * Alvo: Método render
     * At: STORE (Logo após o Minecraft salvar o valor da cor na variável local)
     * ordinal: 1 (Geralmente a variável 'j' ou 'k' que guarda a cor final)
     */
    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 1)
    private int modifyInvisColor(int originalColor, LivingEntityRenderState state) {
        SeeInvis mod = ModuleManager.get(SeeInvis.class);

        // Só aplicamos se o mod estiver ON e a entidade for invisível
        if (mod != null && mod.isEnabled() && state.invisible) {

            // Lógica do seu slider (0.0 a 1.0)
            int alpha = (int) (SeeInvis.OPACITY * 255);
            alpha = Math.max(5, Math.min(255, alpha));

            // Retorna a cor com seu Alpha + Branco
            return (alpha << 24) | 0xFFFFFF;
        }

        return originalColor;
    }
}