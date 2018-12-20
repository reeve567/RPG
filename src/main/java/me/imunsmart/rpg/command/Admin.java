package me.imunsmart.rpg.command;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admins.gamemode.GMA;
import me.imunsmart.rpg.command.admins.gamemode.GMC;
import me.imunsmart.rpg.command.admins.gamemode.GMS;
import me.imunsmart.rpg.command.admins.gamemode.GMSP;
import me.imunsmart.rpg.command.admins.playermoderation.Kick;
import me.imunsmart.rpg.command.admins.Speed;
import me.imunsmart.rpg.command.admins.rpg.RemoveEntities;
import me.imunsmart.rpg.command.admins.rpg.Warp;
import me.imunsmart.rpg.command.admins.rpg.give.*;
import me.imunsmart.rpg.core.command.admins.rpg.give.*;
import me.imunsmart.rpg.command.admins.rpg.mechanics.SpawnMob;
import me.imunsmart.rpg.command.admins.rpg.mechanics.Spawner;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Admin extends ICommand {

	public Admin(Main pl) {
		super(pl,
				"giveweapon","givearmor","givegems","givescraps","giveitem","givetool","spawnmob","spawner","gmc",
				"gms","gmsp","gma","stats","speed","givetools","warp","re"
				);
//		pl.getCommand("lootchest").setExecutor(this);
//		pl.getCommand("lc").setExecutor(this);
//      pl.getCommand("gemspawner").setExecutor(this);
//      pl.getCommand("gs").setExecutor(this);
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
		if (playerOnlyMessage(sender)) {
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
		}else if (label.equalsIgnoreCase("re")) {
			RemoveEntities.run(p);
		}
		return false;
	}
}
