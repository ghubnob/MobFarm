package com.vivim.vivimminigame.events.mobevents;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;


public class EvokerSpawningEvent implements Listener {
    @EventHandler
    public static void onVexSpawn(EntitySpawnEvent e) {
        if (e.getEntity().getType() == EntityType.VEX || e.getEntity().getType() == EntityType.EVOKER_FANGS) e.setCancelled(true);
    }
}
