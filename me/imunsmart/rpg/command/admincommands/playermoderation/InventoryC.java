package me.imunsmart.rpg.command.admincommands.playermoderation;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.util.StringUtility;
import net.md_5.bungee.api.ChatColor;

public class InventoryC {

	public static void run(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "Invalid usage.");
			return;
		}
		Player target = Bukkit.getPlayer(args[0]);
		if (args.length == 1) {
			if (target != null && target.isOnline()) {
				p.openInventory(target.getInventory());
			} else {
				p.sendMessage(ChatColor.RED + "That player is not online!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /inventory <player>");
		}
	}

}
