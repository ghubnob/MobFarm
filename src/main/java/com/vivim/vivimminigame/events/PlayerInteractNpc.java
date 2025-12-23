package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.gui.ExperiencedGui;
import com.vivim.vivimminigame.gui.SpawnersUpGui;
import com.vivim.vivimminigame.gui.SwordUpGui;
import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractNpc implements Listener {
    @EventHandler
    public void playerInteract(PlayerInteractEntityEvent e) {
        Entity npc = e.getRightClicked();
        if (!(npc instanceof Villager)) return;
        Player p = e.getPlayer();
        if (((Villager) npc).getProfession() == Villager.Profession.BUTCHER)
            SwordUpGui.openSwordUpgradeGui(p);
        else if (((Villager) npc).getProfession() == Villager.Profession.CARTOGRAPHER)
            SpawnersUpGui.openSpawnersGui(p);
        else if (((Villager) npc).getProfession() == Villager.Profession.LIBRARIAN)
            ExperiencedGui.openExperiencedGui(p);
        e.setCancelled(true);
    }
}
