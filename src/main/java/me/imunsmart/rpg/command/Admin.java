package me.imunsmart.rpg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admincommands.gamemode.GMA;
import me.imunsmart.rpg.command.admincommands.gamemode.GMC;
import me.imunsmart.rpg.command.admincommands.gamemode.GMS;
import me.imunsmart.rpg.command.admincommands.gamemode.GMSP;
import me.imunsmart.rpg.command.admincommands.playermoderation.InventoryC;
import me.imunsmart.rpg.command.admincommands.playermoderation.Kick;
import me.imunsmart.rpg.command.admincommands.playermoderation.Suicide;
import me.imunsmart.rpg.command.admincommands.rpg.give.CStats;
import me.imunsmart.rpg.command.admincommands.rpg.give.GiveArmor;
import me.imunsmart.rpg.command.admincommands.rpg.give.GiveGems;
import me.imunsmart.rpg.command.admincommands.rpg.give.GiveItem;
import me.imunsmart.rpg.command.admincommands.rpg.give.GiveScraps;
import me.imunsmart.rpg.command.admincommands.rpg.give.GiveWeapon;
import me.imunsmart.rpg.command.admincommands.rpg.give.LootChest;
import me.imunsmart.rpg.command.admincommands.rpg.mechanics.SpawnMob;
import me.imunsmart.rpg.command.admincommands.rpg.mechanics.Spawner;
import net.md_5.bungee.api.ChatColor;

public class Admin implements CommandExecutor {
	private Main pl;

	public Admin(Main pl) {
		this.pl = pl;
		pl.getCommand("giveweapon").setExecutor(this);
		pl.getCommand("givearmor").setExecutor(this);
		pl.getCommand("givegems").setExecutor(this);
		pl.getCommand("givescraps").setExecutor(this);
		pl.getCommand("giveitem").setExecutor(this);
		pl.getCommand("spawnmob").setExecutor(this);
		pl.getCommand("spawner").setExecutor(this);
		pl.getCommand("kick").setExecutor(this);
		pl.getCommand("gmc").setExecutor(this);
		pl.getCommand("gms").setExecutor(this);
		pl.getCommand("gmsp").setExecutor(this);
		pl.getCommand("gma").setExecutor(this);
		pl.getCommand("suicide").setExecutor(this);
		pl.getCommand("stats").setExecutor(this);
		pl.getCommand("lootchest").setExecutor(this);
		pl.getCommand("lc").setExecutor(this);
		pl.getCommand("inventory").setExecutor(this);
		pl.getCommand("inv").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("rpg.admin")) {
			sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
			return true;
		}
		if (label.equalsIgnoreCase("kick")) {
			Kick.run(sender, args);
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("giveweapon")) {
			GiveWeapon.run(p, args);
		} else if (label.equalsIgnoreCase("givearmor")) {
			GiveArmor.run(p, args);
		} else if (label.equalsIgnoreCase("spawnmob")) {
			SpawnMob.run(p, args);
		} else if (label.equalsIgnoreCase("spawner")) {
			Spawner.run(p, args);
		} else if (label.equalsIgnoreCase("givegems")) {
			GiveGems.run(p, args);
		} else if (label.equalsIgnoreCase("givescraps")) {
			GiveScraps.run(p, args);
		} else if (label.equalsIgnoreCase("giveitem")) {
			GiveItem.run(p, args);
		} else if (label.equalsIgnoreCase("gmc")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			GMC.run(tp);
		} else if (label.equalsIgnoreCase("gms")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			GMS.run(tp);
		} else if (label.equalsIgnoreCase("gmsp")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			GMSP.run(tp);
		} else if (label.equalsIgnoreCase("gma")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			GMA.run(tp);
		} else if (label.equalsIgnoreCase("suicide")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			Suicide.run(tp);
		} else if (label.equalsIgnoreCase("stats")) {
			CStats.run(p, args);
		} else if (label.equalsIgnoreCase("lootchest") || label.equalsIgnoreCase("lc")) {
			LootChest.run(p, args);
		} else if (label.equalsIgnoreCase("inventory") || label.equalsIgnoreCase("inv")) {
			InventoryC.run(p, args);
		}
		return false;
	}
}