package me.imunsmart.rpg.command.admins.rpg.mechanics;

import me.imunsmart.rpg.events.Spawners;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class Spawner {
	public static void run(Player p, String[] args) {
		if (args.length == 3) {
			String type = args[0];
			int tier = Integer.parseInt(args[1]);
			int max = Integer.parseInt(args[2]);
			Spawners.setSpawn(p.getLocation(), tier, max, type);
			p.sendMessage(ChatColor.GREEN + "Created spawner.");
		} else {
			p.sendMessage(ChatColor.RED + "/spawner <delete> <name> or /spawner <name> <tier> <maxMobs> <type>");
		}
	}
}
