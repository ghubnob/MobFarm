package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.enchants.EnchantmentManager;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KillEntityEvent implements Listener {
    @EventHandler
    public static void onPlayerKillEntity(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) {e.getDrops().clear(); return;}

        var playerSword = Utils.getMainHandSword(killer);
        if (playerSword==null) {e.getDrops().clear(); return;}

        //если у игрока есть опытность
        if (playerSword.containsEnchantment(EnchantmentManager.EXPERIENCE)) {
            int farmerLvl = killer.getInventory().getItemInMainHand().getEnchantmentLevel(EnchantmentManager.EXPERIENCE);
            e.setDroppedExp((int) (e.getDroppedExp()*Math.pow(2,farmerLvl)));
        }

        //если у игрока есть фильтр
        if (playerSword.containsEnchantment(EnchantmentManager.FILTER)) {
            for (ItemStack i : e.getDrops()) {
                Material m = i.getType();
                if (Utils.GOOD_MOB_DROP.contains(m))
                    killer.getInventory().addItem(i);
            }
        }
        //нет фильтра
        else for (ItemStack i : e.getDrops()) {
            killer.getInventory().addItem(i);
        }
        e.getDrops().clear();
    }
}
