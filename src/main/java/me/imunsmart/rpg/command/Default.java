package me.imunsmart.rpg.command;

import me.imunsmart.rpg.command.defaults.QuestLog;
import me.imunsmart.rpg.mechanics.quests.QuestGUI;
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
		pl.getCommand("quests").setExecutor(this);
		pl.getCommand("q").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("quests") || label.equalsIgnoreCase("q")) {
			QuestLog.run(p);
		}
		return false;
	}
}