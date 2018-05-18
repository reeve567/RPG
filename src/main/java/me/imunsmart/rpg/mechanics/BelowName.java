package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mobs.Mob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class BelowName {
	
	public static Mob addName(Mob mob) {
		ArmorStand stand = (ArmorStand) mob.mob.getWorld().spawnEntity(mob.mob.getLocation(), EntityType.ARMOR_STAND);
		ArmorStand stand2 = (ArmorStand) stand.getWorld().spawnEntity(stand.getLocation(),EntityType.ARMOR_STAND);
		stand.setSmall(true);
		stand.setCustomName("§atest");
		stand.setCustomNameVisible(true);
		stand.setVisible(false);
		stand2.setSmall(true);
		stand2.setCustomName("test2");
		stand2.setCustomNameVisible(true);
		stand2.setVisible(false);
		mob.mob.addPassenger(stand);
		stand.addPassenger(stand2);
		return mob;
	}
	
	public static void init(Monster p) {
	
	}
	
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
	
	static void setScore(Player p, int i) {
		Scoreboard scoreboard = p.getScoreboard();
		Objective objective = scoreboard.getObjective("health");
		Score score = objective.getScore("hp");
		score.setScore(i);
		p.setScoreboard(scoreboard);
	}
	
}
