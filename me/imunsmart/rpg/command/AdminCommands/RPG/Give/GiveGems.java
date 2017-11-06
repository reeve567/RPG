package me.imunsmart.rpg.command.AdminCommands.RPG.Give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveGems {
	public static void run(Player p, String[] args) {
		if (args.length == 1) {
			int amount = Integer.parseInt(args[0]);
			p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + p.getName());
			int stacks = amount / 64;
			amount -= stacks * 64;
			p.getInventory().addItem(Items.createGems(amount));
			for (int i = 0; i < stacks; i++) {
				p.getInventory().addItem(Items.createGems(64));
			}
		} else if (args.length == 2) {
			Player tp = Bukkit.getPlayer(args[0]);
			int amount = Integer.parseInt(args[1]);
			p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
			int stacks = amount / 64;
			amount -= stacks * 64;
			tp.getInventory().addItem(Items.createGems(amount));
			for (int i = 0; i < stacks; i++) {
				tp.getInventory().addItem(Items.createGems(64));
			}
		} else if (args.length == 3) {
			Player tp = Bukkit.getPlayer(args[0]);
			int amount = Integer.parseInt(args[1]);
			if (args[2].equalsIgnoreCase("noted")) {
				p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
				tp.getInventory().addItem(Items.createGemNote(amount));
			}
		}
	}
}
