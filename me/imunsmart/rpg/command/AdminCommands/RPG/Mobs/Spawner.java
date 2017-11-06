package me.imunsmart.rpg.command.admincommands.rpg.mobs;

import me.imunsmart.rpg.events.Spawners;
import org.bukkit.entity.Player;

public class Spawner {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			String name = args[0];
			int tier = Integer.parseInt(args[1]);
			Spawners.setSpawn(p.getLocation(), tier, name);
		}
	}
}
