package com.vivim.vivimminigame.events.guievents;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInvSellLootEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPlayerClickInv(InventoryClickEvent e) {
        var title = e.getView().getTitle();
        if (title.contains("Продажа лута")) e.setCancelled(false);
    }
}
