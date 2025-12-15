package com.vivim.vivimminigame.events;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.npc.UpgradeNpcUtils;
import com.vivim.vivimminigame.utils.SpawnerUtilsMng;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener {
    private final JavaPlugin plugin;
    public PlayerJoin(JavaPlugin plugin) {this.plugin = plugin;}
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        final ConfigManager cfgMng = ConfigManager.getInstance();

        if (cfgMng==null) {Bukkit.getLogger().info(ChatColor.RED+"ConfigManager null");return;}

        if(!cfgMng.isPlayerActive(p.getUniqueId())) {
            SpawnerUtilsMng spwnUtils = new SpawnerUtilsMng(p,true);

            Bukkit.getLogger().info(ChatColor.AQUA+e.getPlayer().getDisplayName()+" joined first time");
            ItemStack startSword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta swordMeta = startSword.getItemMeta();
            swordMeta.setUnbreakable(true);
            swordMeta.setDisplayName(ChatColor.of("#FF6B6B")+"Ле"+ChatColor.of("#FFABAB")+"ген"+ChatColor.of("#FFDBDB")+"дар"+ChatColor.of("#FFEEEE")+"ный");
            swordMeta.setAttributeModifiers(null);
            startSword.setItemMeta(swordMeta);
            p.getInventory().addItem(startSword);

            p.sendTitle(ChatColor.of("#ac0e61")+"MobFarm", "Добро пожаловать!",10,40,20);

            cfgMng.addPlayerActive(p.getUniqueId());
            cfgMng.save();

            spwnUtils.setFirstSpawners();
            spwnUtils.addSpawnerLevel(0);

            UpgradeNpcUtils.spawnUpgraderNpc(p.getWorld());
            UpgradeNpcUtils.spawnSpawnerNpc(p.getWorld());
            UpgradeNpcUtils.spawnExperiencedNpc(p.getWorld());

            if (cfgMng.getPlayerMoney(p.getUniqueId())==0) cfgMng.setPlayerMoney(p.getUniqueId(),0);
            p.setGameMode(GameMode.ADVENTURE);
        }
        else {
            SpawnerUtilsMng spawnUtils = new SpawnerUtilsMng(p);
            p.sendTitle(ChatColor.of("#ac0e61")+"MobFarm", "Рады видеть тебя снова!",10,40,20);
            p.setFoodLevel(20);
        }
    }
}
