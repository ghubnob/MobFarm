package com.vivim.vivimminigame.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageEntityEvent implements Listener {
    @EventHandler
    public static void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) e.setCancelled(true);
    }
}
