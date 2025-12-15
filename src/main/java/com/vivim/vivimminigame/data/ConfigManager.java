package com.vivim.vivimminigame.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ConfigManager {
    private static ConfigManager instance;
    private final FileConfiguration config;
    private final File file;
    JavaPlugin plugin;

    private ConfigManager(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        config = YamlConfiguration.loadConfiguration(file);
        instance = this;
        this.plugin = plugin;
    }

    public static synchronized ConfigManager getInstance(JavaPlugin plugin) {
        return instance==null ? new ConfigManager(plugin) : instance;
    }
    public static ConfigManager getInstance() {
        return instance;
    }

    public void save() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,()-> {
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //public String getPlayers() { return config.getString("players."); }
    public void addPlayerActive(UUID uuid) { config.set("players." + uuid, true); save(); }
    public boolean isPlayerActive(UUID uuid) { return config.contains("players."+uuid); }

    public void setPlayerSpawner(UUID uuid, int i, String type) {
        config.set("players."+uuid+".spawners."+i+".mob", type); save();
        SboardManager.updateScoreboard(Bukkit.getPlayer(uuid),i,type);
    }
    public EntityType getPlayerSpawner(UUID uuid, int i) {
        String entityTypeStr = config.getString("players." + uuid + ".spawners." + i + ".mob","pig");
        return switch (entityTypeStr) {
            case "zombie" -> EntityType.ZOMBIE;
            case "skeleton" -> EntityType.SKELETON;
            case "blaze" -> EntityType.BLAZE;
            case "piglin_brute" -> EntityType.PIGLIN_BRUTE;
            case "wither_skeleton" -> EntityType.WITHER_SKELETON;
            case "vindicator" -> EntityType.VINDICATOR;
            case "evoker" -> EntityType.EVOKER;
            case "creeper" -> EntityType.CREEPER;
            case "witch" -> EntityType.WITCH;
            default -> EntityType.PIG;
        };
    }
    public String getPlayerStrSpawner(UUID uuid, int i) {
        return config.getString("players." + uuid + ".spawners." + i + ".mob","pig");
    }

    public void setPlayerSpawnerUpLvl(UUID uuid, int i, int upLevel) {
        config.set("players."+uuid+".spawners."+i+".level", upLevel); save();
    }
    public int getPlayerSpawnerUpLvl(UUID uuid, int i) {
        return config.getInt("players."+uuid+".spawners."+i+".level", 1);
    }

    public void setPlayerMoney(UUID uuid, int value) {
        config.set("players."+uuid+".money", value); save();
        SboardManager.updateScoreboard(Objects.requireNonNull(Bukkit.getPlayer(uuid)),value);
    }
    public int getPlayerMoney(UUID uuid) {
        return config.getInt("players."+uuid+".money", 0);
    }
    public void addPlayerMoney(UUID uuid, int value) {
        int finalMoney = getPlayerMoney(uuid)+value;
        config.set("players."+uuid+".money", finalMoney); save();
        SboardManager.updateScoreboard(Objects.requireNonNull(Bukkit.getPlayer(uuid)),finalMoney);
    }
}