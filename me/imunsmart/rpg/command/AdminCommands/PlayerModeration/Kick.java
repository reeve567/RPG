package me.imunsmart.rpg.command.AdminCommands.PlayerModeration;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Kick {
	
	public static void run(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
				Bukkit.getPlayer(args[0]).kickPlayer("Kicked from server.");
				sender.sendMessage(ChatColor.GREEN + "Target has been kicked.");
			}
			else {
				sender.sendMessage(ChatColor.RED + "That player is not online!");
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "Invalid usage.");
		}
	}
	
}
