package com.vivim.vivimminigame.data;

import com.vivim.vivimminigame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class SboardManager {
    public static void updateScoreboard(Player p, int spawnerNum, String mobType) {
        String name = Utils.getNiceMobName(mobType);
        String scoreText;
        scoreText = ChatColor.GRAY + "Спавнер " + (spawnerNum+1) + ": " + name;

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
    public static void updateScoreboard(Player p, int money) {
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

        String moneyText = ChatColor.GOLD + "Монеты:      " + money;

        for (String entry : sb.getEntries()) {
            if (entry.contains("Монеты:")) {
                sb.resetScores(entry);
                break;
            }
        }

        Score moneyScore = obj.getScore(moneyText);
        moneyScore.setScore(15);

        p.setScoreboard(sb);
    }
}
