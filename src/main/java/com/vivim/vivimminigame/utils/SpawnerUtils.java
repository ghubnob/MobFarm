package com.vivim.vivimminigame.utils;

import com.vivim.vivimminigame.data.ConfigManager;
import com.vivim.vivimminigame.data.SboardManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.block.CreatureSpawner;
import java.util.*;

public class SpawnerUtils {
    private final Player p;
    private final World w;
    private final List<Location> spawnerLocations;
    private final Map<Integer, EntityType> spawnerConfig;
    ConfigManager cfgMng = ConfigManager.getInstance();

    public SpawnerUtils(Player p) {
        this.p = p;
        this.w = p.getWorld();
        this.spawnerLocations = initializeSpawnerLocations();
        this.spawnerConfig = initializeSpawnerConfig();
    }

    public SpawnerUtils(Player p, boolean first) {
        this.p = p;
        this.w = p.getWorld();
        this.spawnerLocations = initializeSpawnerLocations();
        this.spawnerConfig = initializeSpawnerConfig();
        setFirstSpawners();
    }

    private List<Location> initializeSpawnerLocations() {
        return Arrays.asList(
                new Location(w, -29, 73, 33),
                new Location(w, -29, 74, 33),
                new Location(w, -29, 73, 30),
                new Location(w, -29, 74, 30),
                new Location(w, -31, 73, 28),
                new Location(w, -31, 74, 28),
                new Location(w, -31, 73, 35),
                new Location(w, -31, 74, 35),
                new Location(w, -34, 73, 35),
                new Location(w, -34, 74, 35),
                new Location(w, -34, 73, 28),
                new Location(w, -34, 74, 28),
                new Location(w, -36, 73, 30),
                new Location(w, -36, 74, 30),
                new Location(w, -36, 73, 33),
                new Location(w, -36, 74, 33)
        );
    }

    private Map<Integer, EntityType> initializeSpawnerConfig() {
        Map<Integer, EntityType> config = new HashMap<>();
        for (int i = 0; i < spawnerLocations.size(); i++) {
            config.put(i, cfgMng.getPlayerSpawner(p.getUniqueId(),i));
        }
        return config;
    }

    public void setFirstSpawners() {
        setSpawnerType(0,EntityType.ZOMBIE);
        for (int i = 1; i < spawnerLocations.size(); i++) {
            setSpawnerType(i, EntityType.PIG);
        }
    }

    public void setSpawnerType(int spawnerIndex, EntityType entityType) {
        if (spawnerIndex < 0 || spawnerIndex >= spawnerLocations.size()) return;

        CreatureSpawner spawner = (CreatureSpawner) w.getBlockAt(spawnerLocations.get(spawnerIndex)).getState();
        spawner.setSpawnedType(entityType);
        spawner.update();

        spawnerConfig.put(spawnerIndex, entityType);

        String entityTypeStr = switch (entityType) {
            case ZOMBIE -> "zombie";
            case SKELETON -> "skeleton";
            case BLAZE -> "blaze";
            case PIGLIN_BRUTE -> "piglin_brute";
            case WITHER_SKELETON -> "wither_skeleton";
            case VINDICATOR -> "vindicator";
            case EVOKER -> "evoker";
            case CREEPER -> "creeper";
            case WITCH -> "witch";
            default -> "pig";
        };
        cfgMng.setPlayerSpawner(p.getUniqueId(),spawnerIndex,entityTypeStr);
    }

    public EntityType getSpawnerType(int spawnerIndex) {
        if (spawnerIndex < 0 || spawnerIndex >= spawnerLocations.size()) return null;
        return spawnerConfig.getOrDefault(spawnerIndex, EntityType.PIG);
    }

    public int getSpawnersAmount() {
        return (int) spawnerConfig.values().stream().filter(a->a!=EntityType.PIG).count();
    }
}