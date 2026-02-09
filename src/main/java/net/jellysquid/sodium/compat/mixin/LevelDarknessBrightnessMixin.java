package net.jellysquid.sodium.compat.mixin;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.FogDisabler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects; // Import necessário
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow; // Para acessar o tipo do efeito
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.effect.StatusEffect;

@Mixin(StatusEffectInstance.class)
public abstract class LevelDarknessBrightnessMixin {

    @Shadow public abstract RegistryEntry<StatusEffect> getEffectType();

    @Inject(method = "getFadeFactor", at = @At("HEAD"), cancellable = true)
    private void onGetFadeFactor(LivingEntity entity, float tickProgress, CallbackInfoReturnable<Float> cir) {
        // Lógica para Darkness (seu FogDisabler)
        FogDisabler mod = ModuleManager.get(FogDisabler.class);
        if (mod != null && mod.isEnabled()) {
            if (this.getEffectType().equals(StatusEffects.DARKNESS)) {
                cir.setReturnValue(0.0f);
                return;
            }
        }

        // Lógica para Náusea (Sempre ativo, como você queria)
        if (this.getEffectType().equals(StatusEffects.NAUSEA)) {
            cir.setReturnValue(0.0f);
        }
    }
}