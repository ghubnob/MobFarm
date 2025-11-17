package com.vivim.vivimminigame.events.mobevents;

import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class PiglinTransformEvent implements Listener {
    @EventHandler
    public static void onPiglinTransform(EntityTransformEvent e) {
        if (e.getEntity() instanceof PiglinBrute) {
            if (e.getTransformReason() == EntityTransformEvent.TransformReason.PIGLIN_ZOMBIFIED) {
                e.setCancelled(true);
            }
        }
    }
}
