package me.imunsmart.rpg.command.admincommands.rpg.give;

import org.bukkit.entity.Player;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;

public class GiveItem {

	public static void run(Player p, String[] args) {
		Player tp = p;
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("potion")) {
				int tier = Integer.parseInt(args[1]);
				tp.getInventory().addItem(Items.createPotion(tier));
				p.sendMessage(ChatColor.GREEN + "Item created successfully.");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /giveitem <type> <data>");
		}
	}

}
