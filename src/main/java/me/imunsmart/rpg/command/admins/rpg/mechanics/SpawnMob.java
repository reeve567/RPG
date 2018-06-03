package me.imunsmart.rpg.command.admins.rpg.mechanics;

import me.imunsmart.rpg.mobs.EntityManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class SpawnMob {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			String type = args[0];
			int tier = Integer.parseInt(args[1]);
			EntityManager.spawn(p.getLocation(), type, tier);
		} else if (args.length == 3) {
			String type = args[0];
			int tier = Integer.parseInt(args[1]);
			int amount = 0;
			try {
				amount = Integer.valueOf(args[2]);
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Usage: /spawnmob <type> <tier> [amount]");
				return;
			}
			for (int i = 0; i < amount; i++)
				EntityManager.spawn(p.getLocation(), type, tier);
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /spawnmob <type> <tier> [amount]");
		}
	}
}
