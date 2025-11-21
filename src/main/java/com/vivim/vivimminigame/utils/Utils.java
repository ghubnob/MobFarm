package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.enchants.EnchantmentManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final List<Material> SWORDS = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    //enum enchantments (need to add farmer)
    public enum ENCHANTS {SHARPNESS, SMITE, LOOTING, SWEEP_EDGE, FILTER, EXPERIENCE}
    public static final List<Material> GOOD_MOB_DROP = Arrays.asList(Material.TOTEM_OF_UNDYING,
            Material.ROTTEN_FLESH, Material.BONE, Material.ARROW, Material.EMERALD, Material.SUGAR,
            Material.CARROT, Material.COAL, Material.POTATO, Material.GOLD_INGOT, Material.IRON_INGOT,
            Material.WITHER_SKELETON_SKULL, Material.BLAZE_ROD, Material.GUNPOWDER);

    //get sword in main hand
    public static ItemStack getMainHandSword(Player p) {
        var mainHandItem = p.getInventory().getItemInMainHand();
        if (SWORDS.contains(mainHandItem.getType()) && mainHandItem.getItemMeta() != null) {
            if (mainHandItem.getItemMeta().isUnbreakable()) return mainHandItem;
        }
        return null;
    }

    //create item stack
    public static ItemStack createItemWithMeta(Material m, String name, List<String> lore){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    //create item stack without lore
    public static ItemStack createItemWithMeta(Material m, String name){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static Enchantment getEnchFromEnum(ENCHANTS e){
        return switch (e) {
            case SHARPNESS -> Enchantment.DAMAGE_ALL;
            case SMITE -> Enchantment.DAMAGE_UNDEAD;
            case LOOTING -> Enchantment.LOOT_BONUS_MOBS;
            case SWEEP_EDGE -> Enchantment.SWEEPING_EDGE;
            case FILTER -> EnchantmentManager.FILTER;
            case EXPERIENCE -> EnchantmentManager.EXPERIENCE;
        };
    }

    public static String niceMobMessage(String entityTypeStr) {
        return switch (entityTypeStr) {
            case "zombie" -> ChatColor.DARK_GREEN+entityTypeStr.toUpperCase();
            case "skeleton" -> ChatColor.GRAY+entityTypeStr.toUpperCase();
            case "blaze" -> ChatColor.YELLOW+entityTypeStr.toUpperCase();
            case "piglin_brute" -> ChatColor.GOLD+entityTypeStr.toUpperCase();
            case "wither_skeleton" -> ChatColor.DARK_GRAY+entityTypeStr.toUpperCase();
            case "vindicator" -> ChatColor.WHITE+entityTypeStr.toUpperCase();
            case "evoker" -> ChatColor.LIGHT_PURPLE+entityTypeStr.toUpperCase();
            case "creeper" -> ChatColor.GREEN+entityTypeStr.toUpperCase();
            case "witch" -> ChatColor.AQUA+entityTypeStr.toUpperCase();
            default -> "pig";
        };
    }

    public static int getPlayerEnchantLevel(Player p, ENCHANTS e) {
        if (p == null) { return 0; }
        var mainHandSword = getMainHandSword(p);
        if (mainHandSword == null) { return 0; }
        if (!mainHandSword.containsEnchantment(getEnchFromEnum(e))) { return 0; }
        return mainHandSword.getEnchantmentLevel(getEnchFromEnum(e));
    }

    public static String getNiceMobName(String mobType) {
        mobType = mobType.toLowerCase();
        if(mobType.contains("zombie")) return ChatColor.DARK_GREEN+"Зомби";
        if(mobType.contains("skeleton")) return ChatColor.GRAY+"Скелет";
        if(mobType.contains("wither_skeleton")) return ChatColor.DARK_GRAY+"Визер скелет";
        if(mobType.contains("piglin_brute")) return ChatColor.GOLD+"Брутальный пиглин";
        if(mobType.contains("vindicator")) return ChatColor.WHITE+"Поборник";
        if(mobType.contains("evoker")) return ChatColor.LIGHT_PURPLE+"Призыватель";
        if(mobType.contains("creeper")) return ChatColor.GREEN+"Крипер";
        if(mobType.contains("witch")) return ChatColor.DARK_PURPLE+"Ведьма";
        if(mobType.contains("blaze")) return ChatColor.YELLOW+"Ифрит";
        else return ChatColor.GRAY+"Отсутствует";
    }

    public static int getMoneyByItem(Material mt) {
        return switch (mt) {
            //zombie
            case ROTTEN_FLESH -> 5;
            case CARROT -> 25;
            case POTATO -> 28;
            case IRON_INGOT -> 55;
            //skeleton
            case BONE -> 5;
            case ARROW -> 5;
            //blaze
            case BLAZE_ROD -> 8;
            //wither skeleton
            case COAL -> 10;
            case WITHER_SKELETON_SKULL -> 500;
            //witch
            case SUGAR -> 40;
            //creeper
            case GUNPOWDER -> 25;
            //evoker
            case TOTEM_OF_UNDYING -> 130;
            case EMERALD -> 30;
            //piglin brute
            case GOLD_INGOT -> 45;
            default -> 0;
        };
    }
}
