package me.imunsmart.rpg.mechanics.quests.trackers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class KillTracker implements Listener {
	
	public static HashMap<UUID, Integer> killMap = new HashMap<>();
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			if (e.getEntity().getKiller() instanceof Player) {
				if (e.getEntity().getScoreboardTags().contains("monster")) {
					if (killMap.containsKey(e.getEntity().getKiller().getUniqueId())) {
						killMap.put(e.getEntity().getKiller().getUniqueId(),killMap.get(e.getEntity().getKiller().getUniqueId())+1);
					}
				}
			}
		}
	}
	
	public static boolean hasEnough(Player player, int i) {
		return killMap.get(player.getUniqueId()) >= i;
	}
	
	public static int getProgress(Player player) {
		return killMap.get(player.getUniqueId());
	}
	
	public static double getProgress(Player player,int goal) {
		return ((double) goal / killMap.get(player.getUniqueId()))*100;
	}
}
