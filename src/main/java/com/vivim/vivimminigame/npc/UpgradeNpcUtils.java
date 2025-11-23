package com.vivim.vivimminigame.npc;

import com.vivim.vivimminigame.utils.Utils;
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
        String gradientName = Utils.makeGradient("Прокачка меча","#5dcbff","#95ddff");
        villager.setCustomName(gradientName);
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
        villager.setProfession(Villager.Profession.BUTCHER);
    }

    public static void spawnSpawnerNpc(World w) {
        Villager villager = (Villager) w.spawnEntity(new Location(w,-27.5,64,36.5), EntityType.VILLAGER);
        String gradientName = Utils.makeGradient("Спавнера","#65ffc0","#aeffde");
        villager.setCustomName(gradientName);
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
        villager.setProfession(Villager.Profession.CARTOGRAPHER);
    }

    public static void spawnExperiencedNpc(World w) {
        Villager villager = (Villager) w.spawnEntity(new Location(w,-37,64,32), EntityType.VILLAGER);
        String gradientName = Utils.makeGradient("Опытный","#9f3ffc","#bd7efd");
        villager.setCustomName(gradientName);
        villager.setCustomNameVisible(true);
        villager.setCanPickupItems(false);
        villager.setInvulnerable(true);
        villager.setAI(false);
        villager.setCollidable(false);
        villager.setGlowing(true);
        villager.setSilent(true);
        villager.setPersistent(true);
        villager.setRemoveWhenFarAway(false);
        villager.setRotation(-90,0);
        villager.setProfession(Villager.Profession.LIBRARIAN);
    }
}
