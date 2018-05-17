package me.imunsmart.rpg.util;

import org.bukkit.ChatColor;

public class StringUtility {
	
	public static String colorConv(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String join(String[] s, int i) {
		StringBuilder sb = new StringBuilder();
		for (int x = i; x < s.length; x++) {
			sb.append(s[x]).append(" ");
		}
		return sb.toString().trim();
	}
	
}
