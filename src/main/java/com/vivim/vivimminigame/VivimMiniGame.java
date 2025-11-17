package com.vivim.vivimminigame;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.events.*;
import com.vivim.vivimminigame.events.guievents.ClickInvSellLootEvent;
import com.vivim.vivimminigame.events.guievents.ClickInvSpawnersBuyEvent;
import com.vivim.vivimminigame.events.guievents.ClickInvSwordUpEvent;
import com.vivim.vivimminigame.events.guievents.CloseInvSellLootEvent;
import com.vivim.vivimminigame.events.mobevents.CreeperExplodeEvent;
import com.vivim.vivimminigame.events.mobevents.EvokerSpawningEvent;
import com.vivim.vivimminigame.events.mobevents.PiglinTransformEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VivimMiniGame extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("MobFarm started");
        ConfigManager.getInstance(this);

        Bukkit.getPluginManager().registerEvents(new EntityDamageEntityEvent(),this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeEvent(),this);
        Bukkit.getPluginManager().registerEvents(new GetLootFromKillEntityEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractNpc(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerTakesDamageEvent(),this);

        Bukkit.getPluginManager().registerEvents(new ClickInvSwordUpEvent(),this);
        Bukkit.getPluginManager().registerEvents(new ClickInvSpawnersBuyEvent(),this);
        Bukkit.getPluginManager().registerEvents(new ClickInvSellLootEvent(),this);
        Bukkit.getPluginManager().registerEvents(new CloseInvSellLootEvent(),this);

        Bukkit.getPluginManager().registerEvents(new CreeperExplodeEvent(),this);
        Bukkit.getPluginManager().registerEvents(new EvokerSpawningEvent(),this);
        Bukkit.getPluginManager().registerEvents(new PiglinTransformEvent(),this);

        this.getCommand("trash").setExecutor(this);
        this.getCommand("sell").setExecutor(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("trash")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(Bukkit.createInventory(player, 54, "Мусорка"));
            return true;
        }

        else if (command.getName().equalsIgnoreCase("sell")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(Bukkit.createInventory(null, 54, "Продажа лута"));
            return true;
        }
        return false;
    }
}
