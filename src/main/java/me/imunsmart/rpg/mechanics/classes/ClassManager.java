package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.mechanics.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ClassManager implements Listener {
	
	public static Class getClass(Player p) {
		return hasClass(p) ? (p.getScoreboardTags().contains("archer") ? Class.ARCHER : (p.getScoreboardTags().contains("warrior") ? Class.WARRIOR : Class.WIZARD)) : Class.NONE;
	}
	
	public static boolean hasClass(Player p) {
		return p.getScoreboardTags().contains("selected");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Class cl = Stats.getClass(player);
		if (cl != null) {
			player.addScoreboardTag("selected");
			player.addScoreboardTag(cl.toString());
		}
	}
}
