package com.vivim.vivimminigame;

import com.vivim.vivimminigame.commands.SellLootCommand;
import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.enchants.EnchantmentManager;
import com.vivim.vivimminigame.events.*;
import com.vivim.vivimminigame.events.guievents.ClickInvSpawnersBuyEvent;
import com.vivim.vivimminigame.events.guievents.ClickInvSwordUpEvent;
import com.vivim.vivimminigame.events.guievents.ExperiencedGuiClickInv;
import com.vivim.vivimminigame.events.mobevents.CreeperExplodeEvent;
import com.vivim.vivimminigame.events.mobevents.EvokerSpawningEvent;
import com.vivim.vivimminigame.events.mobevents.PiglinImmuneEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VivimMiniGame extends JavaPlugin {
    private static VivimMiniGame instance;
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info("MobFarm started");
        ConfigManager.getInstance(this);

        Bukkit.getPluginManager().registerEvents(new EntityDamageEntityEvent(),this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeEvent(),this);
        Bukkit.getPluginManager().registerEvents(new KillEntityEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractNpc(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerTakesDamageEvent(),this);

        Bukkit.getPluginManager().registerEvents(new ClickInvSwordUpEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new ClickInvSpawnersBuyEvent(),this);
        Bukkit.getPluginManager().registerEvents(new SellLootCommand(),this);
        Bukkit.getPluginManager().registerEvents(new ExperiencedGuiClickInv(),this);

        Bukkit.getPluginManager().registerEvents(new CreeperExplodeEvent(),this);
        Bukkit.getPluginManager().registerEvents(new EvokerSpawningEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PiglinImmuneEvent(),this);

        this.getCommand("trash").setExecutor(this);
        this.getCommand("sell").setExecutor(this);
        this.getCommand("observe").setExecutor(this);

        EnchantmentManager.registerAll(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("trash")) {
            if (!(sender instanceof Player player)) {
                return true;
            }
            player.openInventory(Bukkit.createInventory(player, 54, "Мусорка"));
            return true;
        }

        else if (command.getName().equalsIgnoreCase("sell")) {
            return SellLootCommand.callCommand(sender);
        }

        if (command.getName().equalsIgnoreCase("observe")) {
            if (!(sender instanceof Player player)) {
                return true;
            }
            Location lastLoc = player.getLocation();
            player.setGameMode(GameMode.SPECTATOR);
            var task = Bukkit.getScheduler().runTaskTimer(this, () -> {
                player.teleport(new Location(player.getWorld(),-32,76.5,32,90,90));
            },1,1);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                task.cancel(); player.teleport(lastLoc); player.setGameMode(GameMode.ADVENTURE);
            },101);
            return true;
        }

        return false;
    }

    public static VivimMiniGame getInstance() {
        return instance;
    }
}
