package com.vivim.vivimminigame.events.guievents;

import com.vivim.vivimminigame.gui.SwordUpGui;
import com.vivim.vivimminigame.utils.UpgradeSwordUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static com.vivim.vivimminigame.utils.Utils.getEnchantLevel;

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
            }
        }

        //гуи выбора зачарование для меча
        else if (title.contains("Выбери зачарование") && material != Material.BLACK_STAINED_GLASS_PANE) {
            if (name.contains("Острота")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SHARPNESS,
                    getEnchantLevel(p,Utils.ENCHANTS.SHARPNESS)+1);
            else if (name.contains("Небесная кара")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SMITE,
                    getEnchantLevel(p,Utils.ENCHANTS.SMITE)+1);
            else if (name.contains("Разящий клинок")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.SWEEP_EDGE,
                    getEnchantLevel(p,Utils.ENCHANTS.SWEEP_EDGE)+1);
            else if (name.contains("Добыча")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.LOOTING,
                    getEnchantLevel(p,Utils.ENCHANTS.LOOTING)+1);
            else if (name.contains("Фильтр")) UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.FILTER,
                    getEnchantLevel(p,Utils.ENCHANTS.FILTER)+1);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1f);
            p.closeInventory();
        }
    }
}
