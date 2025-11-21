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

        //spawners upgrade guis
        if (title.contains("Покупка спавнеров") && name.contains((sp.getSpawnersAmount()+1)+" "))
            SpawnersUpGui.openMobSelectionGui(p);

        else if (title.contains("Выбери моба") && material != Material.BLACK_STAINED_GLASS_PANE)
            SpawnersUpGui.openSpawnerUpGui(p, material);

        else if (title.contains("Купить спавнер") && material == Material.GREEN_STAINED_GLASS_PANE) {
            Material mtEgg = e.getInventory().getItem(13).getType();
            e.getInventory().setItem(0,new ItemStack(Material.CLAY));
            sp.setSpawnerType(sp.getSpawnersAmount(), selectMobFromMaterial(mtEgg));
            p.closeInventory();
            p.sendMessage(sp.getSpawnersAmount()+" spawners");
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
