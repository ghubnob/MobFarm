package com.vivim.vivimminigame.npc;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class UpgradeNpcUtils {
    private final Player p;
    private final World w;

    public UpgradeNpcUtils(Player p) {
        this.p = p;
        this.w = p.getWorld();
    }

    public static void spawnUpgraderNpc(World w) {
        Villager villager = (Villager) w.spawnEntity(new Location(w,-27.5,64,27.5), EntityType.VILLAGER);
        villager.setCustomName(ChatColor.of("#079ab2")+"Upgr"+ChatColor.of("#0492a9")+"ader");
        villager.setCustomNameVisible(true);
        villager.setCanPickupItems(false);
        villager.setInvulnerable(true);
        villager.setAI(false);
        villager.setCollidable(false);
        villager.setGlowing(true);
        villager.setSilent(true);
        villager.setPersistent(true);
        villager.setRemoveWhenFarAway(false);
        villager.setRotation(40,0);
    }

    public static void spawnSpawnerNpc(World w) {
        Villager villager = (Villager) w.spawnEntity(new Location(w,-27.5,64,36.5), EntityType.VILLAGER);
        villager.setCustomName(ChatColor.of("#079ab2")+"Spaw"+ChatColor.of("#0492a9")+"ner");
        villager.setCustomNameVisible(true);
        villager.setCanPickupItems(false);
        villager.setInvulnerable(true);
        villager.setAI(false);
        villager.setCollidable(false);
        villager.setGlowing(true);
        villager.setSilent(true);
        villager.setPersistent(true);
        villager.setRemoveWhenFarAway(false);
        villager.setRotation(140,0);
    }
}
