package me.imunsmart.rpg.command.admincommands.rpg;

import me.imunsmart.rpg.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {
	
	public static void run(Player p, String[] args) {
	
		if (args.length == 1) {
			
			switch (args[0]) {
				case "spawn":
					p.teleport(Util.spawn);
					break;
				case "credits":
					p.teleport(new Location(Util.w,10.5,12,182.5));
					break;
			}
		}
		else {
		
		}
		
	}
	
}
