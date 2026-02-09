package net.jellysquid.sodium.compat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

// Alvo: A classe que gerencia os metadados dos mods para o servidor
@Mixin(targets = "net.fabricmc.fabric.impl.networking.payload.ResolvableCombinedPayload", remap = false)
public class NetworkIntegrityMixin {

    @Inject(method = "getModIdVersionMap", at = @At("RETURN"), cancellable = true)
    private static void onGetModList(CallbackInfoReturnable<Map<String, String>> cir) {
        Map<String, String> modMap = cir.getReturnValue();

        // Remove o seu mod da lista que será enviada ao servidor
        if (modMap.containsKey("sodium-compatibility-layer")) {
            modMap.remove("sodium-compatibility-layer");
            cir.setReturnValue(modMap);
        }
    }
}