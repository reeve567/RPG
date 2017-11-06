package me.imunsmart.rpg.command.AdminCommands.Gamemode;

import me.imunsmart.rpg.utility.StringUtility;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GMS {
	public static void run(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(StringUtility.colorConv("&6Gamemode set to &csurvival&6."));
	}
}
