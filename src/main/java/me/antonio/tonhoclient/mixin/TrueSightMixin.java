package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.SeeInvis;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntityRenderer.class)
public class TrueSightMixin {

    // Na 1.21.4, o método principal é 'render'. Dentro dele, ele chama 'model.render(...)'.
    // Nós vamos interceptar os ARGUMENTOS passados para o modelo.

    @ModifyArgs(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    // AQUI ESTAVA O ERRO: Troquei 'ru/color' por 'I' (que significa Inteiro)
                    // A assinatura correta termina com III (Light, Overlay, Color)
                    target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"
            )
    )
    private void modifyRenderColor(Args args, LivingEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        SeeInvis mod = ModuleManager.get(SeeInvis.class);

        // Verificamos se o mod está ligado E se a entidade está invisível
        // (Se não estiver invisível, não precisamos mexer na cor)
        if (mod != null && mod.isEnabled() && state.invisible) {

            // Cálculos matemáticos de cor (Sua lógica original preservada!)
            int alpha = (int) (SeeInvis.OPACITY * 255);
            alpha = Math.max(5, Math.min(255, alpha)); // Limita entre 5 e 255

            // Cria a cor nova: (Alpha << 24) + Branco (FFFFFF)
            int newColor = (alpha << 24) | 0xFFFFFF;

            // O argumento de cor geralmente é o último (índice 4 ou 5 dependendo da versão exata)
            // Na 1.21.4 padrão, a assinatura é: (matrices, vertexConsumer, light, overlay, COLOR)
            // Então é o índice 4.
            args.set(4, newColor);
        }
    }
}