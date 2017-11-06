package me.imunsmart.rpg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mobs.MobManager;
import net.md_5.bungee.api.ChatColor;

public class DefaultCommands implements CommandExecutor {
	private Main pl;

	public DefaultCommands(Main pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("repair")) {
			
		}
		return false;
	}
}
