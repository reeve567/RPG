package me.imunsmart.rpg.command.AdminCommands.RPG.Mobs;

import me.imunsmart.rpg.mobs.MobManager;
import org.bukkit.entity.Player;

public class SpawnMob {
	public static void run(Player p, String[] args) {
		if (args.length == 2) {
			String type = args[0];
			int tier = Integer.parseInt(args[1]);
			MobManager.spawn(p.getLocation(), type, tier);
		}
	}
}
