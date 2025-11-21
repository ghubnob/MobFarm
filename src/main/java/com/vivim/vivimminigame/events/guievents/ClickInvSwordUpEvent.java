package com.vivim.vivimminigame.events.guievents;

import com.vivim.vivimminigame.enchants.EnchantmentManager;
import com.vivim.vivimminigame.gui.SpawnersUpGui;
import com.vivim.vivimminigame.gui.SwordUpGui;
import com.vivim.vivimminigame.utils.SpawnerUtils;
import com.vivim.vivimminigame.utils.UpgradeSwordUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static com.vivim.vivimminigame.utils.Utils.SWORDS;
import static com.vivim.vivimminigame.utils.Utils.getPlayerEnchantLevel;

public class ClickInvSwordUpEvent implements Listener {
    private static JavaPlugin plugin;
    public ClickInvSwordUpEvent(JavaPlugin pl) {plugin = pl;}
    @EventHandler
    public static void onPlayerClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();
        //return if default inventory
        if (e.getInventory().getHolder()!=null) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) e.getCurrentItem().getItemMeta().setDisplayName("");
        String name = e.getCurrentItem().getItemMeta().getDisplayName();
        Material material = e.getCurrentItem().getType();

        //гуи открывшееся после пкм по нпс
        if (title.contains("Прокачай меч") && material != Material.BLACK_STAINED_GLASS_PANE) {
            if (material == Material.ENCHANTED_BOOK) {
                SwordUpGui.openEnchSelectionGui(p);
            } else if (material == Material.ANVIL) {
                UpgradeSwordUtils.upgradeSword(p);
            } else if (material == Material.NETHERITE_SWORD) {
                UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.EXPERIENCE,9);
            }
        }

        //гуи выбора зачарование для меча
        else if (title.contains("Выбери зачарование") && material != Material.BLACK_STAINED_GLASS_PANE) {
            if (name.contains("Острота")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SHARPNESS,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SHARPNESS)+1);
            else if (name.contains("Небесная кара")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SMITE,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SMITE)+1);
            else if (name.contains("Разящий клинок")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SWEEP_EDGE,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SWEEP_EDGE)+1);
            else if (name.contains("Добыча")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.LOOTING,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.LOOTING)+1);
            else if (name.contains("Фильтр")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.FILTER,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.FILTER)+1);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1f);
            p.closeInventory();
        }
    }
}
