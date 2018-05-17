package me.imunsmart.rpg.util;

import me.imunsmart.rpg.mechanics.Stats;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Util {
	public static final String logo = ChatColor.AQUA.toString() + ChatColor.BOLD + "InfernoRealms";
	
	public static final World w = Bukkit.getWorld("world");
	public static final Location spawn = new Location(w, 0, 86, 0);
	
	public static final int[] s_radi = {25,};
	public static final Location[] safeZones = {spawn};
	
	public static final int[] p_radi = {};
	public static final Location[] pvpZones = {};
	
	public static float baseXP = 100;
	public static float mult = 1.3f;
	
	public static boolean inPvPZone(Player p) {
		if (inZone(p, pvpZones, p_radi)) return true;
		return false;
	}
	
	private static boolean inZone(Player p, Location[] pvpZones, int[] p_radi) {
		for (int i = 0; i < pvpZones.length; i++) {
			Location l = pvpZones[i];
			int r = p_radi[i] * s_radi[i];
			if (p.getLocation().distanceSquared(l) <= r)
				return true;
		}
		return false;
	}
	
	public static boolean inSafeZone(Player p) {
		if (inZone(p, safeZones, s_radi)) return true;
		return false;
	}
	
	public static float neededXP(OfflinePlayer p) {
		return (baseXP * (mult * Stats.getLevel(p)));
	}
	
	public static boolean validClick(PlayerInteractEvent e) {
		return e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK);
	}
	
}
