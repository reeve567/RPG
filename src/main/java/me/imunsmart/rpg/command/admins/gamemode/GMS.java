package me.imunsmart.rpg.command.admins.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GMS {
	public static void run(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(ChatColor.GRAY + "Set gamemode to " + ChatColor.AQUA + "Survival" + ChatColor.GRAY + ".");
	}
}
