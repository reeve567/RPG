package me.imunsmart.rpg.command;

import me.imunsmart.rpg.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ICommand implements CommandExecutor {

	public ICommand(Main pl, String... list) {
		for (String s : list) {
			pl.getCommand(s).setExecutor(this);
		}
	}

	public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

	protected boolean playerOnlyMessage(CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		return false;
	}


}