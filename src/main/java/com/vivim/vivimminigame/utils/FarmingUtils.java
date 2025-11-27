package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.enchants.EnchantmentManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class FarmingUtils {
    public static void giveLootKilledMob(Player killer, List<ItemStack> drops) {
        var playerSword = Utils.getMainHandSword(killer);
        if (playerSword==null) return;
        //если у игрока есть фильтр
        if (playerSword.containsEnchantment(EnchantmentManager.FILTER)) {
            for (ItemStack i : drops) {
                Material m = i.getType();
                if (Utils.GOOD_MOB_DROP.contains(m))
                    killer.getInventory().addItem(i);
            }
        }
        //нет фильтра
        else for (ItemStack i : drops) {
            killer.getInventory().addItem(i);
        }
    }

    public static void sellInventory(Player p, Inventory inv) {
        ConfigManager cfgMng = ConfigManager.getInstance();
        int iCost = 0;
        for (int i=0;i<inv.getSize();i++) {
            ItemStack item = inv.getItem(i);
            if (item==null) continue;
            if (!Utils.GOOD_MOB_DROP.contains(item.getType())) continue;
            iCost += Utils.getMoneyByItem(item.getType())*item.getAmount();
            inv.clear(i);
        }

        var item1 = inv.getItem(49);
        if (item1 == null) return;
        var meta = item1.getItemMeta();
        meta.setLore(Collections.singletonList(Utils.getColoredText("Вы получите: 0 монет", Utils.COL_ENUM.LIGHT_GREEN)));
        item1.setItemMeta(meta);

        cfgMng.addPlayerMoney(p.getUniqueId(),iCost);
        p.sendMessage(ChatColor.GOLD+"Получено: "+ChatColor.WHITE+iCost+ChatColor.GOLD+" монет");
    }
    public static void sellInventory(Player p) {
        Inventory inv = p.getInventory();
        ConfigManager cfgMng = ConfigManager.getInstance();
        int iCost = 0;
        for (int i=0;i<inv.getSize();i++) {
            ItemStack item = inv.getItem(i);
            if (item==null) continue;
            if (!Utils.GOOD_MOB_DROP.contains(item.getType())) continue;
            iCost += Utils.getMoneyByItem(item.getType())*item.getAmount();
            inv.clear(i);
        }

        if (iCost<1) return;
        cfgMng.addPlayerMoney(p.getUniqueId(),iCost);
        p.sendMessage(ChatColor.GOLD+"Получено: "+ChatColor.WHITE+iCost+ChatColor.GOLD+" монет");
    }
}
