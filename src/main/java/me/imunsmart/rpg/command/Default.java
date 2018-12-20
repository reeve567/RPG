package me.imunsmart.rpg.command;

import me.imunsmart.rpg.command.defaults.QuestLog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;

public class Default extends ICommand {

	public Default(Main pl) {
		super(pl, "quests","q");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (playerOnlyMessage(sender)) {
			return true;
		}
		Player p = (Player) sender;
		QuestLog.run(p);
		return false;
	}
}