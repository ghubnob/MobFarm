package com.vivim.vivimminigame.commands;

import com.vivim.vivimminigame.VivimMiniGame;
import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.utils.FarmingUtils;
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

public class SellLootCommand implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPlayerClickInv(InventoryClickEvent e) {
        var title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();

        if (!title.contains("Продажа лута")) return;
        if (e.getCurrentItem()!=null && e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) return;

        Inventory inv = e.getInventory();
        if (e.getCurrentItem()!=null && e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE)
            { FarmingUtils.sellInventory(p,inv); return; }

        e.setCancelled(false);
        Bukkit.getScheduler().runTaskLater(VivimMiniGame.getInstance(), () -> {
            var item1 = inv.getItem(49);
            if (item1 == null) return;
            var meta = item1.getItemMeta();
            meta.setLore(Collections.singletonList(Utils.getColoredText("Вы получите: " + Utils.countMoneyFromInv(inv) + " монет",
                    Utils.COL_ENUM.LIGHT_GREEN)));
            item1.setItemMeta(meta);
        },1);
    }

    @EventHandler
    public static void onCloseInventory(InventoryCloseEvent e) {
        var title = e.getView().getTitle();
        var p = e.getPlayer();

        if (!title.contains("Продажа лута")) return;
        Inventory inv = e.getInventory();
        for (int i=0;i<e.getInventory().getSize();i++) {
            ItemStack item = inv.getItem(i);
            if (item==null) continue;
            if (item.getType()==Material.GRAY_STAINED_GLASS_PANE || item.getType()==Material.GREEN_STAINED_GLASS_PANE) continue;
            p.getInventory().addItem(item);
        }
    }

    public static boolean callCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 54, "Продажа лута");
        ItemStack nullGlass = Utils.createItemWithMeta(Material.GRAY_STAINED_GLASS_PANE, ChatColor.GRAY+"...");
        ItemStack sellButton = Utils.createItemWithMeta(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN+"Продать лут",
                Collections.singletonList(Utils.getColoredText("Вы получите: 0 монет", Utils.COL_ENUM.LIGHT_GREEN)));
        for (int i=45;i<54;i++) {
            if (i==49) { inv.setItem(i,sellButton); continue; }
            inv.setItem(i,nullGlass);
        }
        player.openInventory(inv);
        return true;
    }
}
