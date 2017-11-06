package me.imunsmart.rpg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mobs.MobManager;
import me.imunsmart.rpg.util.AutoBroadcaster;
import net.md_5.bungee.api.ChatColor;

public class AdminCommands implements CommandExecutor {
	private Main pl;

	public AdminCommands(Main pl) {
		this.pl = pl;
		pl.getCommand("giveweapon").setExecutor(this);
		pl.getCommand("givearmor").setExecutor(this);
		pl.getCommand("givegems").setExecutor(this);
		pl.getCommand("givescraps").setExecutor(this);
		pl.getCommand("spawnmob").setExecutor(this);
		pl.getCommand("spawner").setExecutor(this);
		pl.getCommand("suicide").setExecutor(this);
		pl.getCommand("broadcast").setExecutor(this);
		pl.getCommand("bc").setExecutor(this);
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
				try {
					String type = args[0].toLowerCase();
					int tier = Integer.parseInt(args[1]);
					int min = Integer.valueOf(args[2].split("-")[0]);
					int max = Integer.valueOf(args[2].split("-")[1]);
					p.getInventory().addItem(Items.createWeapon(type, tier, min, max, ""));
					p.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /giveweapon [player] <type> <tier> <min-max> [flags]");
				}
			} else if (args.length == 4) {
				try {
					Player tp = Bukkit.getPlayer(args[0]);
					if (tp == null) {
						p.sendMessage(ChatColor.RED + "Invalid player.");
						return true;
					}
					String type = args[1].toLowerCase();
					int tier = Integer.parseInt(args[2]);
					int min = Integer.valueOf(args[3].split("-")[0]);
					int max = Integer.valueOf(args[3].split("-")[1]);
					tp.getInventory().addItem(Items.createWeapon(type, tier, min, max, ""));
					tp.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
					tp.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /giveweapon [player] <type> <tier> <min-max> [flags]");
				}
			} else if (args.length == 5) {
				try {
					Player tp = Bukkit.getPlayer(args[0]);
					if (tp == null) {
						p.sendMessage(ChatColor.RED + "Invalid player.");
						return true;
					}
					String type = args[1].toLowerCase();
					int tier = Integer.parseInt(args[2]);
					int min = Integer.valueOf(args[3].split("-")[0]);
					int max = Integer.valueOf(args[3].split("-")[1]);
					tp.getInventory().addItem(Items.createWeapon(type, tier, min, max, args[4]));
					tp.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
					tp.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /giveweapon [player] <type> <tier> <min-max> [flags]");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /giveweapon [player] <type> <tier> <min-max> [flags]");
			}
		}
		if (label.equalsIgnoreCase("givearmor")) {
			if (args.length == 3) {
				try {
					Player tp = p;
					String type = args[0].toLowerCase();
					int tier = Integer.parseInt(args[1]);
					int maxhp = Integer.valueOf(args[2]);
					tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, ""));
					tp.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
					tp.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givearmor [player] <type> <tier> <maxhp> [flags]");
				}
			} else if (args.length == 4) {
				try {
					Player tp = Bukkit.getPlayer(args[0]);
					if (tp == null) {
						p.sendMessage(ChatColor.RED + "Invalid player.");
						return true;
					}
					String type = args[1].toLowerCase();
					int tier = Integer.parseInt(args[2]);
					int maxhp = Integer.valueOf(args[3]);
					tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, ""));
					tp.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
					tp.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givearmor [player] <type> <tier> <maxhp> [flags]");
				}
			} else if (args.length == 5) {
				try {
					Player tp = Bukkit.getPlayer(args[0]);
					if (tp == null) {
						p.sendMessage(ChatColor.RED + "Invalid player.");
						return true;
					}
					String type = args[1].toLowerCase();
					int tier = Integer.parseInt(args[2]);
					int maxhp = Integer.valueOf(args[3]);
					tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, args[4]));
					tp.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Item created.");
					tp.sendMessage(ChatColor.GREEN + "Item created.");
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givearmor [player] <type> <tier> <maxhp> [flags]");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /givearmor [player] <type> <tier> <maxhp> [flags]");
			}
		}
		if (label.equalsIgnoreCase("spawnmob")) {
			if (args.length == 2) {
				String type = args[0];
				int tier = Integer.parseInt(args[1]);
				MobManager.spawn(p.getLocation(), type, tier);
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /spawn <type> <tier>");
			}
		}
		if (label.equalsIgnoreCase("spawner")) {
			if (args.length == 2) {
				String name = args[0];
				int tier = Integer.parseInt(args[1]);
				Spawners.setSpawn(p.getLocation(), tier, name);
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /spawner <name> <tier>");
			}
		}
		if (label.equalsIgnoreCase("givescraps")) {
			if (args.length == 2) {
				Player tp = p;
				try {
					int tier = Integer.parseInt(args[0]);
					int amount = Integer.parseInt(args[1]);
					p.sendMessage(ChatColor.GOLD + "Given " + amount + " scraps to " + tp.getName());
					int stacks = amount / 64;
					amount -= stacks * 64;
					tp.getInventory().addItem(Items.createScraps(amount, tier));
					for (int i = 0; i < stacks; i++) {
						tp.getInventory().addItem(Items.createScraps(amount, tier));
					}
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givescraps [player] <tier> <amount>");
				}
			} else if (args.length == 2) {
				Player tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Invalid player.");
					return true;
				}
				try {
					int tier = Integer.parseInt(args[1]);
					int amount = Integer.parseInt(args[2]);
					p.sendMessage(ChatColor.GOLD + "Given " + amount + " scraps to " + tp.getName());
					int stacks = amount / 64;
					amount -= stacks * 64;
					tp.getInventory().addItem(Items.createScraps(amount, tier));
					for (int i = 0; i < stacks; i++) {
						tp.getInventory().addItem(Items.createScraps(amount, tier));
					}
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givescraps [player] <tier> <amount>");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /givescraps [player] <tier> <amount>");
			}
		}
		if (label.equalsIgnoreCase("givegems")) {
			if (args.length == 1) {
				Player tp = p;
				try {
					int amount = Integer.parseInt(args[0]);
					p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
					int stacks = amount / 64;
					amount -= stacks * 64;
					tp.getInventory().addItem(Items.createGems(amount));
					for (int i = 0; i < stacks; i++) {
						tp.getInventory().addItem(Items.createGems(64));
					}
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givegems [player] <amount>");
				}
			} else if (args.length == 2) {
				Player tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Invalid player.");
					return true;
				}
				try {
					int amount = Integer.parseInt(args[1]);
					p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
					int stacks = amount / 64;
					amount -= stacks * 64;
					tp.getInventory().addItem(Items.createGems(amount));
					for (int i = 0; i < stacks; i++) {
						tp.getInventory().addItem(Items.createGems(64));
					}
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givegems [player] <amount>");
				}
			} else if (args.length == 3) {
				Player tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Invalid player.");
					return true;
				}
				try {
					int amount = Integer.parseInt(args[1]);
					if (args[2].equalsIgnoreCase("noted")) {
						p.sendMessage(ChatColor.AQUA + "Given " + amount + " gems to " + tp.getName());
						tp.getInventory().addItem(Items.createGemNote(amount));
					}
				} catch (Exception e) {
					p.sendMessage(ChatColor.RED + "Usage: /givegems [player] <amount>");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /givegems [player] <amount>");
			}
		}
		if (label.equalsIgnoreCase("suicide")) {
			if (args.length == 0) {
				Player tp = p;
				Health.health.put(tp.getName(), 0);
				tp.setHealth(0);
			} else if (args.length == 1) {
				Player tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Invalid player.");
					return true;
				}
				Health.health.put(tp.getName(), 0);
				tp.setHealth(0);
			} else {
				p.sendMessage(ChatColor.RED + "/suicide [player]");
			}
		}
		if (label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("bc")) {
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
		return false;
	}
}
