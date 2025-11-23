package com.vivim.vivimminigame.events.guievents;

import com.vivim.vivimminigame.gui.SpawnersUpGui;
import com.vivim.vivimminigame.utils.SpawnerUtils;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickInvSpawnersBuyEvent implements Listener {
    @EventHandler
    public static void onPlayerClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();
        //return; if default inventory
        if (e.getInventory().getHolder()!=null) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) e.getCurrentItem().getItemMeta().setDisplayName("");
        String name = e.getCurrentItem().getItemMeta().getDisplayName();
        Material material = e.getCurrentItem().getType();
        SpawnerUtils sp = new SpawnerUtils(p);

        //гуи покупки спавнеров
        if (title.contains("Покупка спавнеров") && name.contains((sp.getSpawnersAmount()+1)+" "))
            SpawnersUpGui.openMobSelectionGui(p);

        //гуи выбора моба
        else if (title.contains("Выбери моба") && material != Material.BLACK_STAINED_GLASS_PANE)
            SpawnersUpGui.openSpawnerUpGui(p, material);

        //покупа спавнера
        else if (title.contains("Купить спавнер") && material == Material.GREEN_STAINED_GLASS_PANE) {
            Material mtEgg = e.getInventory().getItem(13).getType();
            int needLevel = Utils.getNeedLevelForSpawner(p);
            if (p.getLevel()<needLevel) {p.sendMessage(ChatColor.RED+"Не хватает опыта! Нужно "+needLevel+" exp");return;}
            p.setLevel(p.getLevel()-needLevel);
            e.getInventory().setItem(0,new ItemStack(Material.CLAY));
            sp.setSpawnerType(sp.getSpawnersAmount(), selectMobFromMaterial(mtEgg));
            p.closeInventory();
            p.sendMessage(ChatColor.GREEN+"Куплен "+sp.getSpawnersAmount()+"-й спавнер: "+Utils.createNiceMobName(mtEgg));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1f);
        }
    }

    private static EntityType selectMobFromMaterial(Material m) {
        return switch (m) {
            case ZOMBIE_SPAWN_EGG -> EntityType.ZOMBIE;
            case BLAZE_SPAWN_EGG -> EntityType.BLAZE;
            case SKELETON_SPAWN_EGG -> EntityType.SKELETON;
            case WITHER_SKELETON_SPAWN_EGG -> EntityType.WITHER_SKELETON;
            case PIGLIN_BRUTE_SPAWN_EGG -> EntityType.PIGLIN_BRUTE;
            case VINDICATOR_SPAWN_EGG -> EntityType.VINDICATOR;
            case EVOKER_SPAWN_EGG -> EntityType.EVOKER;
            case CREEPER_SPAWN_EGG -> EntityType.CREEPER;
            case WITCH_SPAWN_EGG -> EntityType.WITCH;
            default -> EntityType.PIG;
        };
    }
}
