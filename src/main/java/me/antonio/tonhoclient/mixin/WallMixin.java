package me.antonio.tonhoclient.mixin;

import me.antonio.tonhoclient.module.render.Wall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class WallMixin {

    // Shadow para acessar o método getFlag da classe Entity
    @Shadow protected abstract boolean getFlag(int index);

    @Inject(method = "getFlag", at = @At("HEAD"), cancellable = true)
    private void onGetFlag(int index, CallbackInfoReturnable<Boolean> info) {
        // O índice 6 é o GLOWING_FLAG_INDEX (conforme a classe Entity que você mandou)
        if (index == 6) {
            if (Wall.currentMode == Wall.Mode.OFF) {
                // Se o mod tá OFF, não forçamos nada, deixa o Minecraft usar o original
                return;
            }

            Object entity = (Object) this;
            boolean filtroPassou = false;

            switch (Wall.currentMode) {
                case PLAYERS -> filtroPassou = entity instanceof net.minecraft.entity.player.PlayerEntity;
                case MOBS -> filtroPassou = entity instanceof net.minecraft.entity.mob.HostileEntity;
                case TUDO -> filtroPassou = true;
            }

            // Força o resultado do bit gráfico
            info.setReturnValue(filtroPassou);
        }
    }
}