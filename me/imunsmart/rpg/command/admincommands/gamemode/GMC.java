package me.imunsmart.rpg.command.admincommands.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GMC {
	public static void run(Player p) {
		p.setGameMode(GameMode.CREATIVE);
		p.sendMessage(ChatColor.GRAY + "Set gamemode to " + ChatColor.AQUA + "Creative" + ChatColor.GRAY + ".");
	}
}
