package com.vivim.vivimminigame.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FoodLevelChangeEvent implements Listener {
    @EventHandler
    public void onFoodLevelChanged(org.bukkit.event.entity.FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
