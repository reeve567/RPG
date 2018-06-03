package me.imunsmart.rpg.command.admins.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GMSP {

	public static void run(Player p) {
		p.setGameMode(GameMode.SPECTATOR);
		p.sendMessage(ChatColor.GRAY + "Set gamemode to " + ChatColor.AQUA + "Spectator" + ChatColor.GRAY + ".");
	}

}
