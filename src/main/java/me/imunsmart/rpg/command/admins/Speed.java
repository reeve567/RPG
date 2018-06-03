package me.imunsmart.rpg.command.admins;

import org.bukkit.entity.Player;

public class Speed {
	
	public static void run(Player player, String[] args) {
		
		float speed = Float.parseFloat(args[0]);
		
		player.setFlySpeed(speed);
	
	
	}
	
}
