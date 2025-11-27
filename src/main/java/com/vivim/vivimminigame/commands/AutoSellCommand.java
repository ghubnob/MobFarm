package com.vivim.vivimminigame.commands;

import com.vivim.vivimminigame.utils.FarmingUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoSellCommand implements Listener {
    private static final Map<UUID, BukkitTask> tasks = new HashMap<>();

    private static boolean isRunning(Player p) {
        return tasks.containsKey(p.getUniqueId());
    }

    private static void start(Player p, JavaPlugin plugin) {
        if (isRunning(p)) return;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            FarmingUtils.sellInventory(p);
        }, 0, 50);

        tasks.put(p.getUniqueId(), task);
    }

    private static void stop(Player p) {
        BukkitTask task = tasks.remove(p.getUniqueId());
        if (task != null) task.cancel();
    }

    public static boolean callCommand(CommandSender sender, String[] args, JavaPlugin plugin) {
        if (!(sender instanceof Player p)) return false;
        if (!p.isOnline()) return false;
        //PERMISSION СЕЙЧАС НИКОМУ НЕ ВЫДАЁТСЯ
        if (!p.hasPermission("minigame.autosell")) {p.sendMessage(ChatColor.RED+"У вас нет автопродажи!");return false;}
        if (args.length!=1) {p.sendMessage(ChatColor.GRAY+"Использование: /autosell on | /autosell off"); return false;}

        if (args[0].equalsIgnoreCase("on")) {
            start(p,plugin);
        }
        else if (args[0].equalsIgnoreCase("off")) {
            stop(p);
        }
        else {p.sendMessage(ChatColor.GRAY+"Использование: /autosell on | /autosell off"); return false;}

        return true;
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) { stop(e.getPlayer()); }
}
