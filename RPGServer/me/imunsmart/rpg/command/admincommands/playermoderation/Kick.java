package me.imunsmart.rpg.command.admincommands.playermoderation;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.util.StringUtility;
import net.md_5.bungee.api.ChatColor;

public class Kick {

	public static void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Invalid usage.");
			return;
		}
		Player target = Bukkit.getPlayer(args[0]);
		if (args.length == 1) {
			if (target != null && target.isOnline()) {
				target.kickPlayer("Kicked from server.");
				sender.sendMessage(ChatColor.GREEN + "Target has been kicked.");
			} else {
				sender.sendMessage(ChatColor.RED + "That player is not online!");
			}
		} else if (args.length > 1) {
			String reason = StringUtility.colorConv(StringUtility.join(args, 1));
			target.kickPlayer(ChatColor.RED + "Kicked for: " + ChatColor.WHITE + reason);
		}
	}

}
