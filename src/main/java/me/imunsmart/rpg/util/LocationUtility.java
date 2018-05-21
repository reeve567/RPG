package me.imunsmart.rpg.util;

import org.bukkit.Location;

public class LocationUtility {
	
	public static Location centerBlock(Location location) {
		return new Location(location.getWorld(),location.getX()+0.5,location.getY()+0.5,location.getZ()+0.5);
	}
	
}
