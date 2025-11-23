package com.vivim.vivimminigame.gui;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.vivim.vivimminigame.utils.SpawnersGuiUtils;

import java.util.Collections;

public class SpawnersUpGui {
    public static void openSpawnersGui(Player p) {
        Inventory spawnersGui = Bukkit.createInventory(null, 36, "Покупка спавнеров");
        for (int i=0;i<36;i++) {
            if ((i >= 10 && i <= 16) || (i >= 19 && i <= 25)) {
                ItemStack spawner = new ItemStack(Material.SPAWNER);
                var spMeta = spawner.getItemMeta();

                int spawnerIndex;
                if (i <= 16) {
                    spawnerIndex = i - 10;
                } else {
                    spawnerIndex = i - 12;
                }

                spMeta.setDisplayName((spawnerIndex + 1) + " Спавнер");
                String mobType = ConfigManager.getInstance().getPlayerStrSpawner(p.getUniqueId(), spawnerIndex);
                String mobInSp = mobType.equals("pig") ? "нету" : Utils.createNiceMobName(mobType);

                spMeta.setLore(Collections.singletonList("Моб в спавнере: " + mobInSp));
                spawner.setItemMeta(spMeta);
                spawnersGui.setItem(i, spawner);
            } else {
                var paneItem = Utils.createItemWithMeta(Material.GRAY_STAINED_GLASS_PANE, ChatColor.GRAY+"...");
                spawnersGui.setItem(i, paneItem);
            }
        }
        p.openInventory(spawnersGui);
    }

    public static void openSpawnerUpGui(Player p, Material m) {
        Inventory spawnersGui = Bukkit.createInventory(null, 27, "Купить спавнер");
        for (int i=0;i<27;i++) {
            var agreeWindow = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            var agreeMeta = agreeWindow.getItemMeta();
            agreeMeta.setDisplayName(ChatColor.GREEN+"Buy spawner");
            int needLevel = Utils.getNeedLevelForSpawner(p);
            agreeMeta.setLore(Collections.singletonList(ChatColor.WHITE+"Тебе нужно "+ChatColor.LIGHT_PURPLE+ needLevel +ChatColor.WHITE+" уровней"));
            agreeWindow.setItemMeta(agreeMeta);
            if (i<3 || (i>=9 && i<12) || (i>=18 && i<21)) spawnersGui.setItem(i, agreeWindow);
            else if ((i>=15 && i<18) || (i>=6 && i<9) || i>=24) spawnersGui.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            else spawnersGui.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            spawnersGui.setItem(13,new ItemStack(m));
        }
        p.openInventory(spawnersGui);
    }

    public static void openMobSelectionGui(Player p) {
        Inventory mobGui = Bukkit.createInventory(null, 27, "Выбери моба");
        for (int i=0; i<27; i++) {
            var item = switch (i) {
                case 9 ->  SpawnersGuiUtils.createMobItem("zombie");
                case 10 -> SpawnersGuiUtils.createMobItem("skeleton");
                case 11 -> SpawnersGuiUtils.createMobItem("wither_skeleton");
                case 12 -> SpawnersGuiUtils.createMobItem("piglin_brute");
                case 13 -> SpawnersGuiUtils.createMobItem("vindicator");
                case 14 -> SpawnersGuiUtils.createMobItem("evoker");
                case 15 -> SpawnersGuiUtils.createMobItem("creeper");
                case 16 -> SpawnersGuiUtils.createMobItem("witch");
                case 17 -> SpawnersGuiUtils.createMobItem("blaze");
                default -> new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            };

            mobGui.setItem(i, item);
            p.openInventory(mobGui);
        }
    }
}
