package me.imunsmart.rpg.command.admincommands.rpg.mechanics;

import me.imunsmart.rpg.mobs.EntityManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class SpawnMob {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			String type = args[0];
			int tier = Integer.parseInt(args[1]);
			EntityManager.spawn(p.getLocation(), type, tier);
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /spawnmob <type> <tier>");
		}
	}
}