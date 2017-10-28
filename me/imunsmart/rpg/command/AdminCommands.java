package me.imunsmart.rpg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mobs.MobManager;
import net.md_5.bungee.api.ChatColor;

public class AdminCommands implements CommandExecutor {
	private Main pl;

	public AdminCommands(Main pl) {
		this.pl = pl;
		pl.getCommand("giveweapon").setExecutor(this);
		pl.getCommand("givearmor").setExecutor(this);
		pl.getCommand("givegems").setExecutor(this);
		pl.getCommand("spawnmob").setExecutor(this);
		pl.getCommand("spawner").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		if (!sender.hasPermission("rpg.admin")) {
			sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("giveweapon")) {
			if (args.length == 3) {
				String type = args[0].toLowerCase();
				int tier = Integer.parseInt(args[1]);
				int min = Integer.valueOf(args[2].split("-")[0]);
				int max = Integer.valueOf(args[2].split("-")[1]);
				p.getInventory().addItem(Items.createWeapon(type, tier, min, max, ""));
				p.updateInventory();
				p.sendMessage(ChatColor.GREEN + "Item created.");
			} else if (args.length == 4) {
				Player tp = Bukkit.getPlayer(args[0]);
				String type = args[1].toLowerCase();
				int tier = Integer.parseInt(args[2]);
				int min = Integer.valueOf(args[3].split("-")[0]);
				int max = Integer.valueOf(args[3].split("-")[1]);
				tp.getInventory().addItem(Items.createWeapon(type, tier, min, max, ""));
				tp.updateInventory();
				p.sendMessage(ChatColor.GREEN + "Item created.");
				tp.sendMessage(ChatColor.GREEN + "Item created.");
			} else if (args.length == 5) {
				Player tp = Bukkit.getPlayer(args[0]);
				String type = args[1].toLowerCase();
				int tier = Integer.parseInt(args[2]);
				int min = Integer.valueOf(args[3].split("-")[0]);
				int max = Integer.valueOf(args[3].split("-")[1]);
				tp.getInventory().addItem(Items.createWeapon(type, tier, min, max, args[4]));
				tp.updateInventory();
				p.sendMessage(ChatColor.GREEN + "Item created.");
				tp.sendMessage(ChatColor.GREEN + "Item created.");
			} else {
				p.sendMessage(ChatColor.RED + "Invalid usage.");
			}
		}
		if (label.equalsIgnoreCase("givearmor")) {
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
		if (label.equalsIgnoreCase("spawnmob")) {
			if(args.length == 2) {
				String type = args[0];
				int tier = Integer.parseInt(args[1]);
				MobManager.spawn(p.getLocation(), type, tier);
			}
		}
		if (label.equalsIgnoreCase("spawner")) {
			if (args.length == 2) {
				String name = args[0];
				int tier = Integer.parseInt(args[1]);
				Spawners.setSpawn(p.getLocation(), tier, name);
			}
		}
		if (label.equalsIgnoreCase("givegems")) {
			if (args.length == 1) {
				Player tp = p;
				int amount = Integer.parseInt(args[0]);
				p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
				int stacks = amount / 64;
				amount -= stacks * 64;
				tp.getInventory().addItem(Items.createGems(amount));
				for (int i = 0; i < stacks; i++) {
					tp.getInventory().addItem(Items.createGems(64));
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
			}
		}
		return false;
	}
}
