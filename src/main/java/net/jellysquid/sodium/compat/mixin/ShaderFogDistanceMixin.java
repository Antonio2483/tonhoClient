package net.jellysquid.sodium.compat.mixin;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.FogDisabler;
import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class ShaderFogDistanceMixin {

    @Shadow @Final private GpuBuffer emptyBuffer;
    @Shadow @Final public static int FOG_UBO_SIZE;

    /**
     * Forçamos o jogo a usar o 'emptyBuffer' (neblina desativada)
     * sempre que o módulo NoFog estiver ligado.
     */
    @Inject(method = "getFogBuffer", at = @At("HEAD"), cancellable = true)
    private void onGetFogBuffer(FogRenderer.FogType fogType, CallbackInfoReturnable<GpuBufferSlice> cir) {
        FogDisabler mod = ModuleManager.get(FogDisabler.class);

        if (mod != null && mod.isEnabled()) {
            // Retorna o slice do buffer vazio que a própria Mojang inicializou no construtor
            cir.setReturnValue(this.emptyBuffer.slice(0, FOG_UBO_SIZE));
        }
    }
}