package me.imunsmart.rpg.command.admincommands;

import org.bukkit.entity.Player;

public class Speed {
	
	public static void run(Player player, String[] args) {
	
		if (args.length == 1) {
			
			try {
				int speed = Integer.parseInt(args[0]);
				
				player.setFlySpeed(speed);
				
			} catch (Exception ignored) {
				player.sendMessage("probelm child with arg");
			}
		}
	
	
	}
	
}
