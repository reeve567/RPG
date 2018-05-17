package me.imunsmart.rpg.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.imunsmart.rpg.mechanics.Stats;
import net.md_5.bungee.api.ChatColor;

public class Util {
	public static final String logo = ChatColor.AQUA.toString() + ChatColor.BOLD + "InfernoRealms";

	public static final World w = Bukkit.getWorld("world");
	public static final Location spawn = new Location(w, 0, 74, 0);

	public static final int[] radi = { 25, };
	public static final Location[] safeZones = { spawn };

	public static float baseXP = 100;
	public static float mult = 1.3f;

	public static float neededXP(OfflinePlayer p) {
		return (baseXP * (mult * Stats.getLevel(p)));
	}

	public static boolean inSafeZone(Player p) {
		for (int i = 0; i < safeZones.length; i++) {
			Location l = safeZones[i];
			int r = radi[i] * radi[i];
			if (p.getLocation().distanceSquared(l) <= r)
				return true;
		}
		return false;
	}
	
	public static boolean validClick(PlayerInteractEvent e) {
		return e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK);
	}

}
