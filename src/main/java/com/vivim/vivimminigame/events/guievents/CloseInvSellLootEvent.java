package com.vivim.vivimminigame.events.guievents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class CloseInvSellLootEvent implements Listener {
    @EventHandler
    public static void onCloseSellInv(InventoryCloseEvent e) {
        var title = e.getView().getTitle();
        var p = e.getPlayer();
        if (!title.contains("Продажа лута")) return;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i==null) continue;
            p.sendMessage(i.toString());
        }
    }
}
