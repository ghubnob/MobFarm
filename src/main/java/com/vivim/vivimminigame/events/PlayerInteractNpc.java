package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.gui.SpawnersUpGui;
import com.vivim.vivimminigame.gui.SwordUpGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractNpc implements Listener {
    @EventHandler
    public static void playerInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getRightClicked().getName().contains("Upgr")) SwordUpGui.openSwordUpgradeGui(p);
        else if (e.getRightClicked().getName().contains("Spaw")) SpawnersUpGui.openSpawnersGui(p);
    }
}
