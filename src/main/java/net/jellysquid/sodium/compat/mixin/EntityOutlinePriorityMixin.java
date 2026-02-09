package net.jellysquid.sodium.compat.mixin;

import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.EntityProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityOutlinePriorityMixin {

    // Método: isInvisibleTo(PlayerEntity player)
    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {

        EntityProcessor mod = ModuleManager.get(EntityProcessor.class);

        if (mod != null && mod.isEnabled() && EntityProcessor.OPACITY > 0) {
            info.setReturnValue(false);
        }
    }
}