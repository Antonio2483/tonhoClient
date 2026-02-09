package net.jellysquid.sodium.compat.mixin;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.FogDisabler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class LevelDarknessBrightnessMixin {

    @Inject(method = "getFadeFactor", at = @At("HEAD"), cancellable = true)
    private void onGetFadeFactor(LivingEntity entity, float tickProgress, CallbackInfoReturnable<Float> cir) {
        FogDisabler mod = ModuleManager.get(FogDisabler.class);

        if (mod != null && mod.isEnabled()) {
            // Se o hack estiver ligado, retornamos 0 para o fator de escuridão/pulsação
            cir.setReturnValue(0.0f);
        }
    }
}