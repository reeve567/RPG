package me.imunsmart.rpg.command;

import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMA;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMC;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMS;
import me.imunsmart.rpg.command.AdminCommands.Gamemode.GMSP;
import me.imunsmart.rpg.command.AdminCommands.PlayerModeration.Kick;
import me.imunsmart.rpg.command.AdminCommands.RPG.Give.GiveArmor;
import me.imunsmart.rpg.command.AdminCommands.RPG.Give.GiveGems;
import me.imunsmart.rpg.command.AdminCommands.RPG.Give.GiveWeapon;
import me.imunsmart.rpg.command.AdminCommands.RPG.Mobs.SpawnMob;
import me.imunsmart.rpg.command.AdminCommands.RPG.Mobs.Spawner;
import me.imunsmart.rpg.utility.StringUtility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mobs.MobManager;
import net.md_5.bungee.api.ChatColor;

public class Admin implements CommandExecutor {
	private Main pl;

	public Admin(Main pl) {
		this.pl = pl;
		pl.getCommand("giveweapon").setExecutor(this);
		pl.getCommand("givearmor").setExecutor(this);
		pl.getCommand("givegems").setExecutor(this);
		pl.getCommand("spawnmob").setExecutor(this);
		pl.getCommand("spawner").setExecutor(this);
		pl.getCommand("kick").setExecutor(this);
		pl.getCommand("gmc").setExecutor(this);
		pl.getCommand("gms").setExecutor(this);
		pl.getCommand("gmsp").setExecutor(this);
		pl.getCommand("gma").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("rpg.admin")) {
			sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
			return true;
		}
		if (label.equalsIgnoreCase("kick")) {
			Kick.run(sender,args);
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("giveweapon")) {
			GiveWeapon.run(p,args);
		}
		else if (label.equalsIgnoreCase("givearmor")) {
			GiveArmor.run(p,args);
		}
		else if (label.equalsIgnoreCase("spawnmob")) {
			SpawnMob.run(p,args);
		}
		else if (label.equalsIgnoreCase("spawner")) {
			Spawner.run(p,args);
		}
		else if (label.equalsIgnoreCase("givegems")) {
			GiveGems.run(p,args);
		}
		else if (label.equalsIgnoreCase("gmc")) {
			GMC.run(p);
		}
		else if (label.equalsIgnoreCase("gms")) {
			GMS.run(p);
		}
		else if (label.equalsIgnoreCase("gmsp")) {
			GMSP.run(p);
		}
		else if (label.equalsIgnoreCase("gma")) {
			GMA.run(p);
		}
		return false;
	}
}
