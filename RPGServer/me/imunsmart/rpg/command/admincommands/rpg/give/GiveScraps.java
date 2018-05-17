package me.imunsmart.rpg.command.admincommands.rpg.give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveScraps {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			int tier = Integer.parseInt(args[0]);
			int amount = Integer.parseInt(args[1]);
			p.getInventory().addItem(Items.createScraps(amount, tier));
			p.sendMessage(ChatColor.GREEN + "Created " + amount + " tier " + tier + " scraps.");
		} else if (args.length == 3) {
			Player tp = Bukkit.getPlayer(args[0]);
			if (tp == null) {
				p.sendMessage(ChatColor.RED + "Player does not exist.");
				return;
			}
			int tier = Integer.parseInt(args[1]);
			int amount = Integer.parseInt(args[2]);
			tp.getInventory().addItem(Items.createScraps(amount, tier));
			tp.sendMessage(ChatColor.GREEN + "Recieved " + tp.getName() + amount + " tier " + tier + " scraps.");
			p.sendMessage(ChatColor.GREEN + "Gave " + tp.getName() + amount + " tier " + tier + " scraps.");
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /givescraps [name] <tier> <amount>");
		}
	}
}
