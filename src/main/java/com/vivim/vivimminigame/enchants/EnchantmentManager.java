package com.vivim.vivimminigame.enchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class EnchantmentManager {
    public static Enchantment EXPERIENCE;
    public static Enchantment FILTER;

    public static void registerAll(JavaPlugin plugin) {
        unlockEnchants();

        EXPERIENCE = new ExperienceEnchantment(new NamespacedKey(plugin, "exp"));
        FILTER = new FilterEnchantment(new NamespacedKey(plugin, "filter"));

        register(EXPERIENCE);
        register(FILTER);
    }

    private static void register(Enchantment enchant) {
        try {
            Enchantment.registerEnchantment(enchant);
        } catch (IllegalArgumentException ignored) {}
    }

    private static void unlockEnchants() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            Bukkit.getLogger().info(ChatColor.YELLOW+"Enchantment unlock exception: "+e);
        }
    }

    public static void unregisterAll() {
        EXPERIENCE = null;
        FILTER = null;
    }
}
