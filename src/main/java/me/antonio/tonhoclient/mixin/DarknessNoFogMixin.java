package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.NoFog;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.DarknessEffectFogModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class DarknessNoFogMixin {

    @Inject(method = "getFadeFactor", at = @At("HEAD"), cancellable = true)
    private void onGetFadeFactor(LivingEntity entity, float tickProgress, CallbackInfoReturnable<Float> cir) {
        NoFog mod = ModuleManager.get(NoFog.class);

        if (mod != null && mod.isEnabled()) {
            // Se o hack estiver ligado, retornamos 0 para o fator de escuridão/pulsação
            cir.setReturnValue(0.0f);
        }
    }
}