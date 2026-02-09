package net.jellysquid.sodium.compat.mixin;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.EntityProcessor;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public class EntityPredicateValidator {

    /**
     * Alvo: Método render
     * At: STORE (Logo após o Minecraft salvar o valor da cor na variável local)
     * ordinal: 1 (Geralmente a variável 'j' ou 'k' que guarda a cor final)
     */
    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 1)
    private int modifyInvisColor(int originalColor, LivingEntityRenderState state) {
        EntityProcessor mod = ModuleManager.get(EntityProcessor.class);

        // Só aplicamos se o mod estiver ON e a entidade for invisível
        if (mod != null && mod.isEnabled() && state.invisible) {

            // Lógica do seu slider (0.0 a 1.0)
            int alpha = (int) (EntityProcessor.OPACITY * 255);
            alpha = Math.max(5, Math.min(255, alpha));

            // Retorna a cor com seu Alpha + Branco
            return (alpha << 24) | 0xFFFFFF;
        }

        return originalColor;
    }
}