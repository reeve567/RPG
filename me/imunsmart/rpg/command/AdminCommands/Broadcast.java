package me.imunsmart.rpg.command.AdminCommands;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcast {
	
	public static void run(Player p, Main pl,String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
				AutoBroadcaster.initMessages(pl);
				p.sendMessage(ChatColor.GREEN + "Reloaded broadcasts.");
			} else {
				StringBuilder sb = new StringBuilder();
				for (String s : args) {
					sb.append(s).append(" ");
				}
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', sb.toString().trim()));
			}
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
		}
	
	}
	
}
