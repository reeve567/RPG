package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mobs.Mob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Nametags {
	
	public static Mob addName(Mob mob) {
		
		return mob;
	}
	
	public static void init(Monster p) {
	
	}
	
	public static void setupDevTeam() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getMainScoreboard();
		try {
			scoreboard.getTeams().remove(scoreboard.getTeam("devs"));
			Team team = scoreboard.registerNewTeam("devs");
			team.setPrefix("§b§lDEV §f");
			team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			team.addPlayer(Bukkit.getOfflinePlayer("Xwy"));
			setScoreboard(scoreboard);
		} catch (Exception ignored) {
		
		}
	}
	
	private static void setScoreboard(Scoreboard scoreboard) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.setScoreboard(scoreboard);
		}
	}
	
	public static void init(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getMainScoreboard();
		Objective objective;
		if (scoreboard.getObjective("health") == null) {
			objective = scoreboard.registerNewObjective("health", "dummy");
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		} else {
			objective = scoreboard.getObjective("health");
		}
		Score score = objective.getScore(p.getName());
		score.setScore(30);
		objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lHP"));
		p.setScoreboard(scoreboard);
	}
	
	static void setScore(Player p, int i) {
		Scoreboard scoreboard = p.getScoreboard();
		Objective objective = scoreboard.getObjective("health");
		Score score = objective.getScore(p.getName());
		score.setScore(i);
		p.setScoreboard(scoreboard);
	}
	
}
