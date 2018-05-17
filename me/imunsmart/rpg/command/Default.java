package me.imunsmart.rpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

public class Default implements CommandExecutor {
	private Main pl;

	public Default(Main pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		Player p = (Player) sender;
		return false;
	}
}
