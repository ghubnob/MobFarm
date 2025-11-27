package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSwordUtils {

    //replace sword in main hand
    public static void upgradeSword(Player p) {
        int mainHandSlot = p.getInventory().getHeldItemSlot();
        ItemStack currentItem = p.getInventory().getItemInMainHand();
        ConfigManager cfgMng = ConfigManager.getInstance();

        if (!Utils.SWORDS.contains(currentItem.getType())) return;

        int price;
        ItemStack newSword = currentItem.clone();
        Material swordType = switch (newSword.getType()){
            case WOODEN_SWORD -> {price = 2000; yield Material.STONE_SWORD;}
            case STONE_SWORD -> {price = 10000; yield Material.IRON_SWORD;}
            case IRON_SWORD -> {price = 30000; yield Material.DIAMOND_SWORD;}
            case DIAMOND_SWORD -> {price = 85000; yield Material.NETHERITE_SWORD;}
            default -> {price=Integer.MAX_VALUE; yield newSword.getType();}
        };

        int pMoney = cfgMng.getPlayerMoney(p.getUniqueId());
        if (price==Integer.MAX_VALUE) {p.sendMessage("У вас уже максимальный уровень меча!");return;}
        if (pMoney<price) {p.sendMessage("Недостаточно денег! "+pMoney+"/"+price);return;}
        cfgMng.addPlayerMoney(p.getUniqueId(), -price);
        newSword.setType(swordType);
        p.getInventory().setItem(mainHandSlot, newSword);

        p.updateInventory();
    }

    public static boolean enchantSword(Player p, Utils.ENCHANTS enchEnum, int level) {
        Enchantment ench = Utils.getEnchFromEnum(enchEnum);

        int slot = p.getInventory().getHeldItemSlot();
        ItemStack sword = p.getInventory().getItem(slot);

        int needExp = getExpEnchantCost(enchEnum,Utils.getEnchantLevel(p,enchEnum));
        if (p.getLevel() < needExp)
        { p.sendMessage(ChatColor.RED+"Недостаточно опыта! "+p.getLevel()+"/"+ needExp); return false; }

        if (sword == null || !Utils.SWORDS.contains(sword.getType()))
            { p.sendMessage(ChatColor.RED+"Возьмите меч в руку"); return false; }

        sword.addUnsafeEnchantment(ench,level);

        // lore если кастомный зачар
        if (enchEnum == Utils.ENCHANTS.EXPERIENCE || enchEnum == Utils.ENCHANTS.FILTER)
        { sword.setItemMeta(getCustomEnchMeta(sword,enchEnum,level)); sword.getItemMeta().addEnchant(ench,level,true);}

        p.setLevel(p.getLevel()-needExp);

        p.getInventory().setItem(slot, sword);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1f);
        p.closeInventory();
        return true;
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
                case 0 -> 30; case 1 -> 50; case 2 -> 80; case 3 -> 120; case 4 -> 230; case 5 -> 500;
                default -> Integer.MAX_VALUE;
            };

        else if (e == Utils.ENCHANTS.EXPERIENCE)
            return switch(level) {
                case 0 -> 80;
                case 1 -> 120;
                case 2 -> 200;
                default -> (int) (Math.pow((level*3),2.5)/100)*100;
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

        String strLevel = switch (level) {
            case 1 -> "I"; case 2 -> "II"; case 3 -> "III"; case 4 -> "IV"; case 5 -> "V";
            case 6 -> "VI"; case 7 -> "VII"; case 8 -> "VIII"; case 9 -> "IX"; case 10 -> "X";
            default -> Integer.toString(level);
        };
        String newLine = name + strLevel;

        lore.removeIf(line -> line.startsWith(name));

        lore.add(newLine);

        meta.setLore(lore);
        return meta;
    }

}
