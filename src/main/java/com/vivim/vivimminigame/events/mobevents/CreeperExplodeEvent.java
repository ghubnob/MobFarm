package com.vivim.vivimminigame.events.mobevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplodeEvent implements Listener {
    @EventHandler
    public static void onCreeperExplode(EntityExplodeEvent e){
        e.setYield(0);
        e.blockList().clear();
        e.setCancelled(true);
    }
}
