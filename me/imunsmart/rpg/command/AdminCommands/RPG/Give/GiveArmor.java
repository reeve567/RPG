package me.imunsmart.rpg.command.AdminCommands.RPG.Give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveArmor {
	
	public static void run(Player p,String[] args) {
		if (args.length == 4) {
			Player tp = Bukkit.getPlayer(args[0]);
			String type = args[1].toLowerCase();
			int tier = Integer.parseInt(args[2]);
			int maxhp = Integer.valueOf(args[3]);
			tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, ""));
			tp.updateInventory();
			p.sendMessage(ChatColor.GREEN + "Item created.");
			tp.sendMessage(ChatColor.GREEN + "Item created.");
		} else if (args.length == 5) {
			Player tp = Bukkit.getPlayer(args[0]);
			String type = args[1].toLowerCase();
			int tier = Integer.parseInt(args[2]);
			int maxhp = Integer.valueOf(args[3]);
			tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, args[4]));
			tp.updateInventory();
			p.sendMessage(ChatColor.GREEN + "Item created.");
			tp.sendMessage(ChatColor.GREEN + "Item created.");
		} else {
			p.sendMessage(ChatColor.RED + "Invalid usage.");
		}
	}
}
