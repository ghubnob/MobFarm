package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.enchants.EnchantmentManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpgradeSwordUtils {

    //replace sword in main hand
    public static void upgradeSword(Player p) {
        int mainHandSlot = p.getInventory().getHeldItemSlot();
        ItemStack currentItem = p.getInventory().getItemInMainHand();

        if (!Utils.SWORDS.contains(currentItem.getType())) return;

        ItemStack newSword = currentItem.clone();
        newSword.setType(switch (newSword.getType()){
            case WOODEN_SWORD -> Material.STONE_SWORD;
            case STONE_SWORD -> Material.IRON_SWORD;
            case IRON_SWORD -> Material.DIAMOND_SWORD;
            case DIAMOND_SWORD -> Material.NETHERITE_SWORD;
            default -> newSword.getType();
        });

        p.getInventory().setItem(mainHandSlot, newSword);

        p.updateInventory();
    }

    public static void enchantSword(Player p, Utils.ENCHANTS enchEnum, int level) {
        Enchantment ench = Utils.getEnchFromEnum(enchEnum);

        int slot = p.getInventory().getHeldItemSlot();
        ItemStack sword = p.getInventory().getItem(slot);

        int needExp = getExpEnchantCost(enchEnum,Utils.getPlayerEnchantLevel(p,enchEnum));
        if (p.getLevel() < needExp)
        { p.sendMessage(ChatColor.RED+"Недостаточно опыта! "+p.getLevel()+"/"+ needExp); return; }

        if (sword == null || !Utils.SWORDS.contains(sword.getType()))
            { p.sendMessage(ChatColor.RED+"Возьмите меч в руку"); return; }

        sword.addUnsafeEnchantment(ench,level);

        // lore если кастомный зачар
        if (enchEnum == Utils.ENCHANTS.EXPERIENCE || enchEnum == Utils.ENCHANTS.FILTER)
        { sword.setItemMeta(getCustomEnchMeta(sword,enchEnum,level)); sword.getItemMeta().addEnchant(ench,level,true);}

        p.setLevel(p.getLevel()-needExp);

        p.getInventory().setItem(slot, sword);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1f);
    }

    public static int getExpEnchantCost(Utils.ENCHANTS e, int level) {
        if (e == Utils.ENCHANTS.SHARPNESS)
            return switch (level) {
                case 0 -> 10; case 1 -> 30; case 2 -> 35; case 3 -> 50; case 4 -> 65;
                case 5 -> 80; case 6 -> 100;case 7 -> 120; case 8 -> 135; case 9 -> 150;
                default -> Integer.MAX_VALUE;
            };
        else if (e == Utils.ENCHANTS.SMITE)
            return switch (level) {
                case 0 -> 10; case 1 -> 30; case 2 -> 35; case 3 -> 50; case 4 -> 65;
                default -> Integer.MAX_VALUE;
            };
        else if (e == Utils.ENCHANTS.SWEEP_EDGE)
            return switch (level) {
                case 0 -> 30; case 1 -> 50; case 2 -> 75; case 3 -> 100; case 4 -> 120;
                default -> Integer.MAX_VALUE;
            };
        else if (e == Utils.ENCHANTS.LOOTING)
            return switch (level) {
                case 0 -> 50; case 1 -> 80; case 2 -> 100; case 3 -> 150; case 4 -> 250; case 5 -> 600;
                default -> Integer.MAX_VALUE;
            };
        else if (e == Utils.ENCHANTS.EXPERIENCE)
            return switch (level) {
                case 0 -> 1; case 9 -> 2;
                default -> Integer.MAX_VALUE;
            };
        else if (e == Utils.ENCHANTS.FILTER)
            return level==0 ?  1 : Integer.MAX_VALUE;
        else return Integer.MAX_VALUE;
    }

    private static ItemMeta getCustomEnchMeta(ItemStack sword, Utils.ENCHANTS ench, int level) {
        ItemMeta meta = sword.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) lore = new ArrayList<>();

        String name;
        if (ench == Utils.ENCHANTS.EXPERIENCE)
            name = ChatColor.DARK_PURPLE + "Опытность ";
        else
            name = ChatColor.WHITE + "Фильтр ";

        String newLine = name + level;

        lore.removeIf(line -> line.startsWith(name));

        lore.add(newLine);

        meta.setLore(lore);
        return meta;
    }

}
