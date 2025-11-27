package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.enchants.EnchantmentManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Utils {
    public static final List<Material> SWORDS = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    //enum enchantments (need to add farmer)
    public enum ENCHANTS {SHARPNESS, SMITE, LOOTING, SWEEP_EDGE, FILTER, EXPERIENCE}
    public static final List<Material> GOOD_MOB_DROP = Arrays.asList(Material.TOTEM_OF_UNDYING,
            Material.ROTTEN_FLESH, Material.BONE, Material.ARROW, Material.EMERALD, Material.SUGAR,
            Material.CARROT, Material.COAL, Material.POTATO, Material.GOLD_INGOT, Material.IRON_INGOT,
            Material.WITHER_SKELETON_SKULL, Material.BLAZE_ROD, Material.GUNPOWDER, Material.GOLDEN_AXE);
    public enum COL_ENUM {LIGHT_RED, SOFT_RED, LIGHT_BLUE, LIGHT_GREEN}
    private static final Map<COL_ENUM,String> COLORS = Map.of(COL_ENUM.LIGHT_RED,"#FFC4C4", COL_ENUM.SOFT_RED,"#C67B7B",
            COL_ENUM.LIGHT_BLUE,"#AFFFFF", COL_ENUM.LIGHT_GREEN, "#99FFB3");

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

    @Deprecated
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

    public static int getEnchantLevel(Player p, ENCHANTS e) {
        if (p == null) { return 0; }
        var mainHandSword = getMainHandSword(p);
        if (mainHandSword == null) { return 0; }
        if (!mainHandSword.containsEnchantment(getEnchFromEnum(e))) { return 0; }
        return mainHandSword.getEnchantmentLevel(getEnchFromEnum(e));
    }

    public static String createNiceMobName(String mobType) {
        mobType = mobType.toLowerCase();
        if(mobType.contains("zombie")) return ChatColor.DARK_GREEN+"Зомби";
        if(mobType.contains("wither_skeleton")) return ChatColor.DARK_GRAY+"Визер скелет";
        else if(mobType.contains("skeleton")) return ChatColor.GRAY+"Скелет";
        if(mobType.contains("piglin_brute")) return ChatColor.GOLD+"Брутальный пиглин";
        if(mobType.contains("vindicator")) return ChatColor.WHITE+"Поборник";
        if(mobType.contains("evoker")) return ChatColor.LIGHT_PURPLE+"Призыватель";
        if(mobType.contains("creeper")) return ChatColor.GREEN+"Крипер";
        if(mobType.contains("witch")) return ChatColor.DARK_PURPLE+"Ведьма";
        if(mobType.contains("blaze")) return ChatColor.YELLOW+"Ифрит";
        else return ChatColor.GRAY+"Отсутствует";
    }
    public static String createNiceMobName(Material egg) {
        return switch (egg) {
            case ZOMBIE_SPAWN_EGG -> ChatColor.DARK_GREEN+"Зомби";
            case SKELETON_SPAWN_EGG -> ChatColor.GRAY+"Скелет";
            case WITHER_SKELETON_SPAWN_EGG -> ChatColor.DARK_GRAY+"Визер скелет";
            case PIGLIN_BRUTE_SPAWN_EGG -> ChatColor.GOLD+"Брутальный пиглин";
            case VINDICATOR_SPAWN_EGG -> ChatColor.WHITE+"Поборник";
            case EVOKER_SPAWN_EGG -> ChatColor.LIGHT_PURPLE+"Призыватель";
            case CREEPER_SPAWN_EGG -> ChatColor.GREEN+"Крипер";
            case WITCH_SPAWN_EGG -> ChatColor.DARK_PURPLE+"Ведьма";
            case BLAZE_SPAWN_EGG -> ChatColor.YELLOW+"Ифрит";
            default -> ChatColor.GRAY+"Отсутствует";
        };
    }

    public static int getMoneyByItem(Material mt) {
        return switch (mt) {
            //zombie
            case ROTTEN_FLESH -> 10;
            case CARROT -> 25;
            case POTATO -> 28;
            case IRON_INGOT -> 300;
            //skeleton
            case BONE -> 10;
            case ARROW -> 10;
            //blaze
            case BLAZE_ROD -> 20;
            //wither skeleton
            case COAL -> 10;
            case WITHER_SKELETON_SKULL -> 500;
            //witch
            case SUGAR -> 60;
            //creeper
            case GUNPOWDER -> 25;
            //evoker
            case TOTEM_OF_UNDYING -> 130;
            case EMERALD -> 30;
            //piglin brute
            case GOLD_INGOT -> 45;
            case GOLDEN_AXE -> 80;
            default -> 0;
        };
    }

    public static int getNeedLevelForSpawner(Player p) {
        SpawnerUtils sp = new SpawnerUtils(p);
        return switch (sp.getSpawnersAmount()) {
            case 1 -> 10;  case 2 -> 20;  case 3 -> 30;  case 4 -> 45;  case 5 -> 70;

            case 6 -> 90;  case 7 -> 120;  case 8 -> 140;  case 9 -> 170;  case 10 -> 200;

            case 11 -> 230;  case 12 -> 270;  default -> 300;
        };
    }

    public static int needMoneyForExperienced(int level) {
        return switch (level) {
            case 0 -> 10_000;
            case 1 -> 30_000;
            case 2 -> 70_000;
            case 3 -> 150_000;
            case 4 -> 250_000;
            default -> 100_000*(level*level/2);
        };
    }

    public static String makeGradient(String text, String color1, String color2) {
        if (text == null || text.isEmpty()) return "";

        char[] textArr = text.toCharArray();
        StringBuilder gradientText = new StringBuilder();

        java.awt.Color startColor = java.awt.Color.decode(color1.startsWith("#") ? color1 : "#" + color1);
        java.awt.Color endColor = java.awt.Color.decode(color2.startsWith("#") ? color2 : "#" + color2);

        int length = textArr.length;

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            int red = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * ratio);
            int green = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * ratio);
            int blue = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * ratio);

            red = Math.max(0, Math.min(255, red));
            green = Math.max(0, Math.min(255, green));
            blue = Math.max(0, Math.min(255, blue));

            java.awt.Color interpolatedColor = new java.awt.Color(red, green, blue);
            gradientText.append(net.md_5.bungee.api.ChatColor.of(interpolatedColor))
                    .append(textArr[i]);
        }

        return gradientText.toString();
    }

    public static String getColoredText(String text, COL_ENUM col) {
        return net.md_5.bungee.api.ChatColor.of(Utils.COLORS.get(col))+text;
    }

    public static int countMoneyFromInv(Inventory inv) {
        int iCost = 0;
        for (int i=0;i<inv.getSize();i++) {
            ItemStack item = inv.getItem(i);
            if (item==null) continue;
            if (!Utils.GOOD_MOB_DROP.contains(item.getType())) continue;
            iCost += Utils.getMoneyByItem(item.getType())*item.getAmount();
        }
        return iCost;
    }
}
