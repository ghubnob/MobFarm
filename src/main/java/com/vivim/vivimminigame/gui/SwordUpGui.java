package com.vivim.vivimminigame.gui;

import com.vivim.vivimminigame.utils.UpgradeSwordUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

import static com.vivim.vivimminigame.utils.UpgradeSwordUtils.getExpEnchantCost;

public class SwordUpGui {
    public static void openSwordUpgradeGui(Player p) {
        Inventory swordGui = Bukkit.createInventory(null, 9, ChatColor.DARK_RED+"Upgrade sword");

        for (int i=0;i<9;i++) {

            if (i==4) {
                ItemStack sword = Utils.getMainHandSword(p);
                if (sword==null) p.sendMessage(net.md_5.bungee.api.ChatColor.of("#FFABAB")+"You need to hold sword in your Main Hand");
                swordGui.setItem(i,sword);
            }

            else if (i==2) {
                ItemStack enchBookItem = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta bookMeta = enchBookItem.getItemMeta();
                bookMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Наложить зачарование на меч");
                bookMeta.setLore(Arrays.asList(ChatColor.GRAY+"Accessible enchants:",
                        "Sharpness (I-X)",
                        "Sweeping Edge (I-V)",
                        "Smite (I-V)",
                        "Looting (I-VI)",
                        ChatColor.LIGHT_PURPLE+"Farmer (I-X)",
                        ChatColor.WHITE+"Filter I"));
                enchBookItem.setItemMeta(bookMeta);
                swordGui.setItem(i,enchBookItem);
            }

            else if (i==6) {
                ItemStack upAnvilItem = new ItemStack(Material.ANVIL);
                ItemMeta anvilMeta = upAnvilItem.getItemMeta();
                anvilMeta.setDisplayName("Upgrade your sword to better");
                anvilMeta.setLore(Arrays.asList(ChatColor.GRAY+"Upgrades:",
                        "Wooden -> Stone",
                        "Stone -> Iron",
                        "Iron -> Diamond",
                        "Diamond -> Netherite"));
                upAnvilItem.setItemMeta(anvilMeta);
                swordGui.setItem(i,upAnvilItem);
            }

            else {
                ItemStack glassItem = Utils.createItemWithMeta(Material.BLACK_STAINED_GLASS_PANE,ChatColor.GRAY+"...");
                swordGui.setItem(i,glassItem);
            }
        }

        p.openInventory(swordGui);
    }

    public static void openEnchSelectionGui(Player p) {
        Inventory enchantsGui = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA+"Select enchantment");
        for (int i=0; i<9; i++) {
            var item = switch (i) {
                case 2 -> {int needExp = getExpEnchantCost(Utils.ENCHANTS.SHARPNESS,Utils.getPlayerEnchantLevel(p, Utils.ENCHANTS.SHARPNESS));
                    yield Utils.createItemWithMeta(Material.ENCHANTED_BOOK, ChatColor.AQUA+"Sharpness",
                        Collections.singletonList(needExp==0 ? ChatColor.RED+"У вас максимальный уровень данного зачарования" :
                        ChatColor.DARK_AQUA+"Стоимость: "+ needExp +" опыта."));
                }
                case 3 ->  {int needExp = getExpEnchantCost(Utils.ENCHANTS.SMITE,Utils.getPlayerEnchantLevel(p, Utils.ENCHANTS.SMITE));
                    yield Utils.createItemWithMeta(Material.ENCHANTED_BOOK, ChatColor.AQUA+"Smite",
                            Collections.singletonList(ChatColor.DARK_AQUA+"Стоимость: "+ needExp +" опыта."));
                }
                case 4 -> { int needExp = getExpEnchantCost(Utils.ENCHANTS.SWEEP_EDGE,Utils.getPlayerEnchantLevel(p, Utils.ENCHANTS.SWEEP_EDGE));
                    yield Utils.createItemWithMeta(Material.ENCHANTED_BOOK, ChatColor.AQUA+"Sweeping edge",
                            Collections.singletonList(ChatColor.DARK_AQUA+"Стоимость: "+ needExp +" опыта."));
                }
                case 5 -> { int needExp = getExpEnchantCost(Utils.ENCHANTS.LOOTING,Utils.getPlayerEnchantLevel(p, Utils.ENCHANTS.LOOTING));
                    yield Utils.createItemWithMeta(Material.ENCHANTED_BOOK, ChatColor.AQUA+"Looting",
                            Collections.singletonList(ChatColor.DARK_AQUA+"Стоимость: "+ needExp +" опыта."));
                }

                case 6 -> Utils.createItemWithMeta(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE+"Filter");
                default -> Utils.createItemWithMeta(Material.BLACK_STAINED_GLASS_PANE, ChatColor.GRAY+"...");
            };

            enchantsGui.setItem(i, item);
            p.openInventory(enchantsGui);
        }
    }
}
