package me.imunsmart.rpg.command;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admins.Fly;
import me.imunsmart.rpg.command.admins.Speed;
import me.imunsmart.rpg.command.admins.gamemode.GMA;
import me.imunsmart.rpg.command.admins.gamemode.GMC;
import me.imunsmart.rpg.command.admins.gamemode.GMS;
import me.imunsmart.rpg.command.admins.gamemode.GMSP;
import me.imunsmart.rpg.command.admins.playermoderation.Kick;
import me.imunsmart.rpg.command.admins.rpg.RemoveEntities;
import me.imunsmart.rpg.command.admins.rpg.Warp;
import me.imunsmart.rpg.command.admins.rpg.give.*;
import me.imunsmart.rpg.command.admins.rpg.mechanics.SpawnMob;
import me.imunsmart.rpg.command.admins.rpg.mechanics.Spawner;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Admin implements CommandExecutor {
	private Main pl;
	
	public Admin(Main pl) {
		this.pl = pl;
		pl.getCommand("giveweapon").setExecutor(this);
		pl.getCommand("givearmor").setExecutor(this);
		pl.getCommand("givegems").setExecutor(this);
		pl.getCommand("givescraps").setExecutor(this);
		pl.getCommand("giveitem").setExecutor(this);
		pl.getCommand("givetool").setExecutor(this);
		pl.getCommand("spawnmob").setExecutor(this);
		pl.getCommand("spawner").setExecutor(this);
		pl.getCommand("gmc").setExecutor(this);
		pl.getCommand("gms").setExecutor(this);
		pl.getCommand("gmsp").setExecutor(this);
		pl.getCommand("gma").setExecutor(this);
		pl.getCommand("stats").setExecutor(this);
//		pl.getCommand("lootchest").setExecutor(this);
//		pl.getCommand("lc").setExecutor(this);
//        pl.getCommand("gemspawner").setExecutor(this);
//        pl.getCommand("gs").setExecutor(this);
		pl.getCommand("speed").setExecutor(this);
		pl.getCommand("givetools").setExecutor(this);
		pl.getCommand("warp").setExecutor(this);
		pl.getCommand("fly").setExecutor(this);
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
		} else if (label.equalsIgnoreCase("givetool")) {
			GiveTool.run(p, args);
		} else if (label.equalsIgnoreCase("gmc")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			if (tp != null)
				GMC.run(tp);
		} else if (label.equalsIgnoreCase("gms")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			if (tp != null)
				GMS.run(tp);
		} else if (label.equalsIgnoreCase("gmsp")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			if (tp != null)
				GMSP.run(tp);
		} else if (label.equalsIgnoreCase("gma")) {
			Player tp = p;
			if (args.length == 1) {
				tp = Bukkit.getPlayer(args[0]);
				if (tp == null) {
					p.sendMessage(ChatColor.RED + "Player not online.");
				}
			}
			if (tp != null)
				GMA.run(tp);
		} else if (label.equalsIgnoreCase("stats")) {
			CStats.run(p, args);
		} else if (label.equalsIgnoreCase("lootchest") || label.equalsIgnoreCase("lc")) {
			LootChestC.run(p, args);
		} else if (label.equalsIgnoreCase("gemspawner") || label.equalsIgnoreCase("gs")) {
			GemSpawnerC.run(p, args);
		} else if (label.equalsIgnoreCase("givetools")) {
			GiveTools.run(p);
		} else if (label.equalsIgnoreCase("warp")) {
			Warp.run(p, args);
		} else if (label.equalsIgnoreCase("speed")) {
			Speed.run(p,args);
		} else if (label.equalsIgnoreCase("fly")) {
			Fly.run(p);
		} else if (label.equalsIgnoreCase("removeEntities")) {
			RemoveEntities.run(p);
		}
		return false;
	}
}
