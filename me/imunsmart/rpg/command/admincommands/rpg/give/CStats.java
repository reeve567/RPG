package me.imunsmart.rpg.command.admincommands.rpg.give;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.mechanics.Stats;
import net.md_5.bungee.api.ChatColor;

public class CStats {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			Player tp = Bukkit.getPlayer(args[0]);
			if (!Stats.exists(tp)) {
				p.sendMessage(ChatColor.RED + "There are no stats for that player.");
				return;
			}
			if (args[1].equalsIgnoreCase("reset")) {
				Stats.reset(tp);
				p.sendMessage(ChatColor.RED + "Reset the stats of: " + tp.getName());
			}
		} else if (args.length == 4) {
			Player tp = Bukkit.getPlayer(args[0]);
			if (!Stats.exists(tp)) {
				p.sendMessage(ChatColor.RED + "There are no stats for that player.");
				return;
			}
			String stat = args[2];
			if (args[1].equalsIgnoreCase("seti")) {
				int i = 0;
				try {
					i = Integer.valueOf(args[3]);
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Invalid usage, specify an integer.");
					return;
				}
				Stats.setStat(tp, stat, i);
				p.sendMessage(ChatColor.GREEN + "Set the stat of " + p.getName() + "'s " + stat + " to " + i + ".");
			} else if (args[1].equalsIgnoreCase("setd")) {
				double d = 0;
				try {
					d = Double.valueOf(args[3]);
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Invalid usage, specify an double.");
					return;
				}
				Stats.setStat(tp, stat, d);
				p.sendMessage(ChatColor.GREEN + "Set the stat of " + p.getName() + "'s " + stat + " to " + d + ".");
			} else if (args[1].equalsIgnoreCase("sets")) {
				String s = args[3];
				Stats.setStat(tp, stat, s);
				p.sendMessage(ChatColor.GREEN + "Set the stat of " + p.getName() + "'s " + stat + " to " + s + ".");
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /stats <player> <reset/set> <stat> <value>");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /stats <player> <reset/set> <stat> <value>");
		}
	}
}
