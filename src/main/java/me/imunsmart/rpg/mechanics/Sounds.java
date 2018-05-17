package me.imunsmart.rpg.mechanics;

import org.bukkit.entity.Player;

public class Sounds {
	public static void play(Player p, org.bukkit.Sound s, float pitch) {
		p.playSound(p.getLocation(), s, 1, pitch);
	}
}
