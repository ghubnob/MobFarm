package com.vivim.vivimminigame.data;

import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class SboardManager {
    public static void updateScoreboard(Player p, int spawnerNum, String mobType) {
        if (mobType.contains("pig")) return;
        String name = Utils.getNiceMobName(mobType);
        String scoreText = ChatColor.GRAY + "Спавнер " + (spawnerNum+1) + ": " + name;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard sb = p.getScoreboard();

        if (manager == null) return;
        if (sb == null) {
            sb = manager.getNewScoreboard();
        }

        Objective obj = sb.getObjective("stats");
        if (obj == null) {
            obj = sb.registerNewObjective("stats", "dummy", ChatColor.WHITE+""+ChatColor.BOLD+p.getDisplayName());
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (String entry : sb.getEntries()) {
            if (entry.contains("Спавнер " + (spawnerNum+1) + ":")) {
                sb.resetScores(entry);
                break;
            }
        }

        Score sc = obj.getScore(scoreText);
        sc.setScore(14 - spawnerNum);

        p.setScoreboard(sb);
    }
}
