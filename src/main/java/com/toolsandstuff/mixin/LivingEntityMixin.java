package com.toolsandstuff.mixin;

import com.toolsandstuff.PioletClimb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hace que el jugador cuente como trepando (igual que una escalera):
 * sube al empujar contra el muro, Shift lo mantiene quieto, se anula el dano
 * por caida y el servidor lo acepta igual que el cliente.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
    private void tas$pioletClimb(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && PioletClimb.canCling(player)) {
            cir.setReturnValue(true);
        }
    }
}
