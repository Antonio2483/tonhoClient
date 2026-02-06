package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.ModuleManager;
import me.antonio.tonhoclient.module.render.SeeInvis;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class SeeInvisMixin {

    // Método: isInvisibleTo(PlayerEntity player)
    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {

        SeeInvis mod = ModuleManager.get(SeeInvis.class);

        // Se o mod estiver ligado, a gente mente pro jogo:
        // "Não, essa entidade NÃO está invisível pra esse player."
        if (mod != null && mod.isEnabled()) {
            info.setReturnValue(false);
        }
    }
}