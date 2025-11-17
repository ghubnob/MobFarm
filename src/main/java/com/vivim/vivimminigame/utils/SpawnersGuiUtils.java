package com.vivim.vivimminigame.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpawnersGuiUtils {
    public static ItemStack createMobItem(String type) {
        var mob = createMobStack(type);
        mob.setItemMeta(createMobMeta(type,mob));
        return mob;
    }
    private static ItemStack createMobStack(String type) {
        return switch (type) {
            case "zombie" -> new ItemStack(Material.ZOMBIE_SPAWN_EGG);
            case "blaze" -> new ItemStack(Material.BLAZE_SPAWN_EGG);
            case "skeleton" -> new ItemStack(Material.SKELETON_SPAWN_EGG);
            case "wither_skeleton" -> new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
            case "piglin_brute" -> new ItemStack(Material.PIGLIN_BRUTE_SPAWN_EGG);
            case "vindicator" -> new ItemStack(Material.VINDICATOR_SPAWN_EGG);
            case "evoker" -> new ItemStack(Material.EVOKER_SPAWN_EGG);
            case "creeper" -> new ItemStack(Material.CREEPER_SPAWN_EGG);
            case "witch" -> new ItemStack(Material.WITCH_SPAWN_EGG);
            default -> new ItemStack(Material.CLAY);
        };
    }
    private static ItemMeta createMobMeta(String type, ItemStack item) {
        var meta = item.getItemMeta();
        assert meta != null;
        switch (type) {
            case "zombie" -> {
                meta.setDisplayName(ChatColor.DARK_GREEN + "Zombie");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "blaze" -> {
                meta.setDisplayName(ChatColor.YELLOW + "Blaze");
                meta.setLore(Collections.singletonList(ChatColor.GOLD + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GOLD + " levels and " + ChatColor.WHITE + " money."));
            }
            case "skeleton" -> {
                meta.setDisplayName(ChatColor.WHITE + "Skeleton");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "wither_skeleton" -> {
                meta.setDisplayName(ChatColor.DARK_GRAY + "Wither Skeleton");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "piglin_brute" -> {
                meta.setDisplayName(ChatColor.DARK_RED + "Piglin Brute");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "vindicator" -> {
                meta.setDisplayName(ChatColor.DARK_GRAY + "Vindicator");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "evoker" -> {
                meta.setDisplayName(ChatColor.DARK_PURPLE + "Evoker");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "creeper" -> {
                meta.setDisplayName(ChatColor.GREEN + "Creeper");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            case "witch" -> {
                meta.setDisplayName(ChatColor.DARK_PURPLE + "Witch");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "You need " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.GRAY + " levels and " + ChatColor.WHITE + " money."));
            }
            default -> {}
        }
        return meta;
    }
}
