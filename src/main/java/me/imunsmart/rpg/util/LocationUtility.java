package me.imunsmart.rpg.util;

import org.bukkit.Location;

public class LocationUtility {
	
	public static Location centerBlock(Location location) {
		return new Location(location.getWorld(),location.getX()+0.5,location.getY()+0.5,location.getZ()+0.5);
	}
	
	public static Location setYaw(Location location, int yaw) {
		return new Location(location.getWorld(), location.getX(), location.getY(),location.getZ(),yaw,location.getPitch());
	}
	
	public enum Direction {
		NORTH(180),
		NORTHWEST(135),
		WEST(90),
		SOUTHWEST(45),
		SOUTH(0),
		SOUTHEAST(315),
		EAST(270);
		
		int yaw;
		
		Direction(int yaw) {
			this.yaw = yaw;
		}
		
		public int getYaw() {
			return yaw;
		}
	}
	
}
