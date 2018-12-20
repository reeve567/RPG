package me.imunsmart.rpg.util;

import me.imunsmart.rpg.util.DiscordBroadcaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Nametags {
	
	public static void init() {
		setupTesterTeam();
		setupDevTeam();
	}
	
	public static void setupDevTeam() {
		setupTeam("devs", "§b§lDEV §f", "Xwy", "ImUnsmart");
	}
	
	public static void setupTeam(String name, String prefix, String... members) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getMainScoreboard();
		try {
			scoreboard.getTeams().remove(scoreboard.getTeam(name));
		} catch (Exception ignored) {
		}
		try {
			Team team = scoreboard.registerNewTeam(name);
			team.setPrefix(prefix);
			team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			for (String s : members) {
				team.addPlayer(Bukkit.getOfflinePlayer(s));
			}
			setScoreboard(scoreboard);
		} catch (Exception ignored) {
		}
		DiscordBroadcaster.messages.add("Setup team " + name);
	}
	
	private static void setScoreboard(Scoreboard scoreboard) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.setScoreboard(scoreboard);
		}
	}
	
	public static void setupTesterTeam() {
		setupTeam("testers", "§c§lTESTER §f", "Chasemantate", "flame8499", "matt2117");
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
