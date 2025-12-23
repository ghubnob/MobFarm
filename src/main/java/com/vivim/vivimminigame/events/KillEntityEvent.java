package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.enchants.EnchantmentManager;
import com.vivim.vivimminigame.utils.FarmingUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KillEntityEvent implements Listener {
    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) {e.getDrops().clear(); return;}

        var playerSword = Utils.getMainHandSword(killer);
        if (playerSword==null) {e.getDrops().clear(); return;}

        //если у игрока есть опытность
        if (playerSword.containsEnchantment(EnchantmentManager.EXPERIENCE)) {
            int farmerLvl = killer.getInventory().getItemInMainHand().getEnchantmentLevel(EnchantmentManager.EXPERIENCE);
            e.setDroppedExp((int) (e.getDroppedExp()*Math.pow(2,farmerLvl)));
        }

        FarmingUtils.giveLootKilledMob(killer, e.getDrops());
        e.getDrops().clear();

        //дополнительный шанс на wither skull с добычей
        if (!(e.getEntity() instanceof WitherSkeleton)) return;
        if (!playerSword.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) return;
        int lootLvl = playerSword.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        int chance = switch (lootLvl) {
            case 1 -> 3; case 2 -> 5; case 3 -> 7; case 4 -> 10; case 5 -> 15; case 6 -> 20; default -> 0;
        };
        if (Math.random()*100<=chance) killer.getInventory().addItem(new ItemStack(Material.WITHER_SKELETON_SKULL));
    }
}
