package com.vivim.vivimminigame.gui;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static com.vivim.vivimminigame.utils.UpgradeSwordUtils.getExpEnchantCost;

public class ExperiencedGui {
    public static void openExperiencedGui(Player p) {
        ConfigManager cfgMng = ConfigManager.getInstance();
        int currentEnchLevel = Utils.getEnchantLevel(p, Utils.ENCHANTS.EXPERIENCE);
        int needMoney = Utils.needMoneyForExperienced(currentEnchLevel);
        int plMoney = cfgMng.getPlayerMoney(p.getUniqueId());
        int needExp = getExpEnchantCost(Utils.ENCHANTS.EXPERIENCE,Utils.getEnchantLevel(p, Utils.ENCHANTS.EXPERIENCE));

        Inventory mobGui = Bukkit.createInventory(null, 27, "Наложить \"Опытность\"");
        for (int i=0; i<27; i++) {
            var item = switch (i) {
                case 13 -> Utils.createItemWithMeta(Material.NETHERITE_SWORD,"Меч мечты",
                        Arrays.asList(ChatColor.DARK_PURPLE + "Опытность " +
                                (Utils.getEnchantLevel(p, Utils.ENCHANTS.EXPERIENCE)+1), "",
                                ChatColor.GRAY+""+plMoney+"/"+needMoney+" монет",
                                ChatColor.GRAY+""+p.getLevel()+"/"+needExp+" опыта"));
                default -> Utils.createItemWithMeta(Material.GRAY_STAINED_GLASS_PANE, ChatColor.GRAY+"...");
            };

            mobGui.setItem(i, item);
            p.openInventory(mobGui);
        }
    }
}
