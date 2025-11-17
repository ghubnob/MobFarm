package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class GetLootFromKillEntityEvent implements Listener {
    @EventHandler
    public static void onPlayerKillEntity(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) {e.getDrops().clear(); return;}

        //if player have filter
        var playerSword = Utils.getMainHandSword(killer);
        if (playerSword==null) {e.getDrops().clear(); return;}
        if (true/*playerSword.containsEnchantment(Utils.getEnchFromEnum(Utils.ENCHANTS.FILTER))*/) {
            for (ItemStack i : e.getDrops()) {
                Material m = i.getType();
                if (Utils.GOOD_MOB_DROP.contains(m))
                    killer.getInventory().addItem(i);
            }
            e.getDrops().clear();
            return;
        }

        //player don't have filter
        for (ItemStack i : e.getDrops()) {
            killer.getInventory().addItem(i);
        }
        e.getDrops().clear();
    }
}
