package com.vivim.vivimminigame.commands;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Objects;

public class SellLootCommand implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPlayerClickInv(InventoryClickEvent e) {
        var title = e.getView().getTitle();
        if (!title.contains("Продажа лута")) return;
        if (e.getCurrentItem()==null) return;
        if (e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
        else if (e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) { e.getWhoClicked().closeInventory(); return; }
        e.setCancelled(false);
    }
    @EventHandler
    public static void onCloseInventory(InventoryCloseEvent e) {
        var title = e.getView().getTitle();
        var p = e.getPlayer();
        ConfigManager cfgMng = ConfigManager.getInstance();

        if (!title.contains("Продажа лута")) return;
        for (ItemStack i : e.getInventory().getContents()) {
            if (i==null) continue;
            int iCost = Utils.getMoneyByItem(i.getType())*i.getAmount();
            cfgMng.addPlayerMoney(p.getUniqueId(),iCost);
        }
    }

    public static boolean callCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 54, "Продажа лута");
        ItemStack nullGlass = Utils.createItemWithMeta(Material.BLACK_STAINED_GLASS_PANE, ChatColor.GRAY+"...");
        ItemStack sellButton = Utils.createItemWithMeta(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN+"Продать лут");
        for (int i=45;i<54;i++) {
            if (i==49) { inv.setItem(i,sellButton); continue; }
            inv.setItem(i,nullGlass);
        }
        player.openInventory(inv);
        return true;
    }
}
