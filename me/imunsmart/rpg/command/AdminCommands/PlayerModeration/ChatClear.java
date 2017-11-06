package me.imunsmart.rpg.command.AdminCommands.PlayerModeration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatClear {
	
	public static void run(Player p) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (!pl.hasPermission("rpg.admin")) {
				for (int i = 0; i < 450; i++) {
					pl.sendMessage(" ");
				}
			}
			pl.sendMessage(ChatColor.GRAY + "Chat has been " + ChatColor.AQUA + "cleared " + ChatColor.GRAY + "by" + ChatColor.AQUA + p.getName());
		}
	}
	
}
