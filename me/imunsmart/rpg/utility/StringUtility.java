package me.imunsmart.rpg.utility;

import org.bukkit.ChatColor;

public class StringUtility {
	
	public static String colorConv(String s) {
		return ChatColor.translateAlternateColorCodes('&',s);
	}
	
}
