package com.vivim.vivimminigame.events.guievents;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.utils.UpgradeSwordUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ExperiencedGuiClickInv implements Listener {
    @EventHandler
    public static void onPlayerClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();
        ConfigManager cfgMng = ConfigManager.getInstance();
        //return; if default inventory
        if (e.getInventory().getHolder() != null) return;
        if (e.getCurrentItem()==null) return;
        if (!title.contains("Опытность") || e.getCurrentItem().getType()==Material.GRAY_STAINED_GLASS_PANE) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) e.getCurrentItem().getItemMeta().setDisplayName("");

        //покупка зачара "опытный"
        int currentEnchLevel = Utils.getEnchantLevel(p, Utils.ENCHANTS.EXPERIENCE);
        int needMoney = Utils.needMoneyForExperienced(currentEnchLevel);
        int plMoney = cfgMng.getPlayerMoney(p.getUniqueId());
        ItemStack swordHold = Utils.getMainHandSword(p);

        if(swordHold==null) {p.sendMessage(net.md_5.bungee.api.ChatColor.of("#FFACAC")+"Возьми меч в руку!"); return;}
        if (swordHold.getType()!=Material.NETHERITE_SWORD)
            {p.sendMessage(ChatColor.DARK_RED+"Твой меч должен быть незеритовым!");return;}

        if (plMoney >= needMoney) {
            if (UpgradeSwordUtils.enchantSword(p, Utils.ENCHANTS.EXPERIENCE, currentEnchLevel+1))
                cfgMng.setPlayerMoney(p.getUniqueId(),plMoney-needMoney);
        }
        else p.sendMessage(ChatColor.GOLD+"Не хватает монет! "+plMoney+"/"+needMoney);
    }
}
