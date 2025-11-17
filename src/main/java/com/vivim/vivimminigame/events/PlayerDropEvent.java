package com.vivim.vivimminigame.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropEvent implements Listener {
    @EventHandler
    public static void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
}
