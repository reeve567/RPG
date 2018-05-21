package me.imunsmart.rpg.command.admincommands.rpg.give;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GemSpawnerC {
	public static HashMap<String, Integer> gsp = new HashMap<>();
	
	public static void run(Player p, String[] args) {
		if (args.length == 0) {
			gsp.put(p.getName(), 0);
			p.sendMessage(ChatColor.GRAY + "Right click a block to remove it. Left click to cancel.");
		} else if (args.length == 1) {
			int i = 0;
			try {
				i = Integer.valueOf(args[0]);
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Usage: /gemspawner [tier]");
			}
			gsp.put(p.getName(), i);
			p.sendMessage(ChatColor.GRAY + "Click the block.");
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /gemspawner [tier]");
		}
	}
}