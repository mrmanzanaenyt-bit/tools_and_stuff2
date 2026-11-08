package com.toolsandstuff;

import net.minecraft.world.entity.player.Player;

/** Logica compartida (cliente y servidor) del escalado con piolet. */
public final class PioletClimb {
    private PioletClimb() {}

    /** Piolet en AMBAS manos. */
    public static boolean holdsTwo(Player p) {
        return p.getMainHandItem().is(ToolsAndStuff.PIOLET)
                && p.getOffhandItem().is(ToolsAndStuff.PIOLET);
    }

    /** Pegado a un muro: dos piolets + choque horizontal contra una pared. */
    public static boolean canCling(Player p) {
        return !p.isSpectator() && !p.isPassenger() && !p.isInWater()
                && p.horizontalCollision && holdsTwo(p);
    }

    /**
     * True mientras dura la escalada: desde que se pega al muro hasta que
     * toca el suelo (o se suelta del muro). Pensado para enganchar la animacion.
     */
    public static boolean isClimbing(Player p) {
        return canCling(p) && !p.onGround();
    }
}
