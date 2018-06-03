package me.imunsmart.rpg.command.admins.rpg;

import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {

	public static void run(Player p, String[] args) {
		if (args.length == 1) {
			String name = args[0];
			Location l = Util.getWarp(name);
			if(l == null) {
				p.sendMessage(ChatColor.RED + "Warp does not exist.");
				return;
			}
			p.teleport(l);
			p.sendMessage(ChatColor.GRAY + "You warped to: " + ChatColor.AQUA + name + ".");
		} else {
			String all = "";
			for (String s : Util.warpNames) {
				all += s + ChatColor.GRAY + ", ";
			}
			p.sendMessage(ChatColor.GRAY + "Warps: " + ChatColor.AQUA + all.substring(0, all.length() - 2).toLowerCase());
		}
	}

}
