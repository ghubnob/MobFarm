package com.vivim.vivimminigame.events.guievents;

import com.vivim.vivimminigame.gui.SpawnersUpGui;
import com.vivim.vivimminigame.gui.SwordUpGui;
import com.vivim.vivimminigame.utils.SpawnerUtils;
import com.vivim.vivimminigame.utils.UpgradeSwordUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static com.vivim.vivimminigame.utils.Utils.SWORDS;
import static com.vivim.vivimminigame.utils.Utils.getPlayerEnchantLevel;

public class ClickInvSwordUpEvent implements Listener {
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
        if (title.contains("Upgrade sword") && material != Material.BLACK_STAINED_GLASS_PANE) {
            if (material == Material.ENCHANTED_BOOK) {
                SwordUpGui.openEnchSelectionGui(p);
            } else if (material == Material.ANVIL) {
                UpgradeSwordUtils.upgradeSword(p);
            }
        }

        //гуи выбора зачарование для меча
        else if (title.contains("Select enchantment") && material != Material.BLACK_STAINED_GLASS_PANE) {
            if (name.contains("Sharpness")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SHARPNESS,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SHARPNESS)+1);
            else if (name.contains("Smite")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SMITE,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SMITE)+1);
            else if (name.contains("Sweeping edge")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SWEEP_EDGE,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.SWEEP_EDGE)+1);
            else if (name.contains("Looting")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.LOOTING,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.LOOTING)+1);
            else if (name.contains("Filter")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.FILTER,
                    getPlayerEnchantLevel(p,Utils.ENCHANTS.FILTER)+1);
        }
    }
}
