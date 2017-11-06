package me.imunsmart.rpg.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Util {
	public static final String logo = ChatColor.AQUA.toString() + ChatColor.BOLD + "InfernoRealms";

	public static final World w = Bukkit.getWorld("world");
	public static final Location spawn = new Location(w, 0, 74, 0);

}
