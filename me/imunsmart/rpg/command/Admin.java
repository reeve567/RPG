package me.imunsmart.rpg.command;

import me.imunsmart.rpg.command.AdminCommands.Broadcast;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMA;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMC;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMS;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMSP;
import me.imunsmart.rpg.command.AdminCommands.InventoryManagement.ClearInventory;
import me.imunsmart.rpg.command.AdminCommands.PlayerModeration.ChatClear;
import me.imunsmart.rpg.command.AdminCommands.PlayerModeration.Kick;
import me.imunsmart.rpg.command.AdminCommands.InventoryManagement.GiveArmor;
import me.imunsmart.rpg.command.AdminCommands.InventoryManagement.GiveGems;
import me.imunsmart.rpg.command.AdminCommands.InventoryManagement.GiveWeapon;
import me.imunsmart.rpg.command.AdminCommands.PlayerModeration.Suicide;
import me.imunsmart.rpg.command.AdminCommands.RPG.Mobs.SpawnMob;
import me.imunsmart.rpg.command.AdminCommands.RPG.Mobs.Spawner;
import me.imunsmart.rpg.mechanics.Health;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.List;

public class Admin implements CommandExecutor {
	private Main pl;
	
	public Admin(Main pl) {
		this.pl = pl;
		
		register(Arrays.asList("giveweapon", "givearmor", "givegems", "spawnmob", "spawner", "kick", "gmc", "gms", "gma", "gmsp",
				"ci", "clearinv", "clearInventory", "cc", "chatclear","suicide","bc","broadcast"));
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
		} else if (label.equalsIgnoreCase("gmc")) {
			GMC.run(p);
		} else if (label.equalsIgnoreCase("gms")) {
			GMS.run(p);
		} else if (label.equalsIgnoreCase("gmsp")) {
			GMSP.run(p);
		} else if (label.equalsIgnoreCase("gma")) {
			GMA.run(p);
		} else if (label.equalsIgnoreCase("ci") ||
				label.equalsIgnoreCase("clearinv") ||
				label.equalsIgnoreCase("clearinventory")) {
			ClearInventory.run(p);
		} else if (label.equalsIgnoreCase("chatclear") ||
				label.equalsIgnoreCase("cc")) {
			ChatClear.run(p);
		}
		else if (label.equalsIgnoreCase("suicide")) {
			Suicide.run(p,args);
		}
		else if (label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("bc")) {
			Broadcast.run(p,pl,args);
		}
		return true;
	}
	
	private void register(List<String> s) {
		for (String st : s) {
			pl.getCommand(st).setExecutor(this);
		}
	}
	
}
