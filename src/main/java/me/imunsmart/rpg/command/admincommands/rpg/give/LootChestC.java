package me.imunsmart.rpg.command.admincommands.rpg.give;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class LootChestC {
	public static HashMap<String, Integer> lc = new HashMap<>();

	public static void run(Player p, String[] args) {
		if (args.length == 0) {
			lc.put(p.getName(), 0);
			p.sendMessage(ChatColor.GRAY + "Right click a chest to remove it. Left click to cancel.");
		} else if (args.length == 1) {
			int i = 0;
			try {
				i = Integer.valueOf(args[0]);
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Usage: /lootchest [tier]");
			}
			lc.put(p.getName(), i);
			p.sendMessage(ChatColor.GRAY + "Click the chest.");
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /lootchest [tier]");
		}
	}
}
