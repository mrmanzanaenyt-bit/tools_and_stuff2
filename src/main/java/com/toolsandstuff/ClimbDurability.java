package com.toolsandstuff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

/** Cada bloque escalado hacia arriba gasta 1 de durabilidad en AMBOS piolets (900 en total). */
public final class ClimbDurability {
    private static final class Track { double lastY; double acc; }
    private static final Map<UUID, Track> TRACKS = new HashMap<>();

    private ClimbDurability() {}

    public static void tick(ServerPlayer p) {
        Track t = TRACKS.computeIfAbsent(p.getUUID(), id -> {
            Track n = new Track();
            n.lastY = p.getY();
            return n;
        });
        double dy = p.getY() - t.lastY;
        t.lastY = p.getY();

        if (!PioletClimb.isClimbing(p)) {
            t.acc = 0;
            return;
        }
        if (dy > 0) {
            t.acc += dy;
            while (t.acc >= 1.0 && PioletClimb.holdsTwo(p)) {
                t.acc -= 1.0;
                p.getMainHandItem().hurtAndBreak(1, p, EquipmentSlot.MAINHAND);
                p.getOffhandItem().hurtAndBreak(1, p, EquipmentSlot.OFFHAND);
            }
        }
    }

    public static void forget(UUID id) {
        TRACKS.remove(id);
    }
}
