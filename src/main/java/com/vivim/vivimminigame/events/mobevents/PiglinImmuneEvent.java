package com.vivim.vivimminigame.events.mobevents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class PiglinImmuneEvent implements Listener {
    @EventHandler
    public static void onPiglinSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof PiglinBrute) {
            ((PiglinBrute) e.getEntity()).setImmuneToZombification(true);
        }
    }
    @EventHandler
    public static void onEntityTargetEntity(EntityTargetEvent e) {
        if (e.getEntity().getType() == EntityType.EXPERIENCE_ORB) return;
        e.setCancelled(true);
    }
}
