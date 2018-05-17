package me.imunsmart.rpg.command.admincommands.playermoderation;

import org.bukkit.entity.Player;

import me.imunsmart.rpg.mechanics.Health;

public class Suicide {
	public static void run(Player p) {
		Health.health.put(p.getName(), 0);
		p.setHealth(0);
	}
}
