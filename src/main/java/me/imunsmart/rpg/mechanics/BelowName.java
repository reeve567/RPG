package me.imunsmart.rpg.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scoreboard.*;

public class BelowName {
	
	public static void init(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("health", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		Score score = objective.getScore("hp");
		score.setScore(5);
		objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lHP"));
		p.setScoreboard(scoreboard);
	}
	
	public static void init(Monster p) {
		p.addScoreboardTag("health");
	}
	
	
	
	
	
	
	
	static void setScore(Player p, int i) {
		Scoreboard scoreboard = p.getScoreboard();
		Objective objective = scoreboard.getObjective("health");
		Score score = objective.getScore("hp");
		score.setScore(i);
		p.setScoreboard(scoreboard);
	}
	
}
