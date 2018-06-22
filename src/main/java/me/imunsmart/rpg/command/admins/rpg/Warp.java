package me.imunsmart.rpg.command.admins.rpg;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerSet;

public class Warp {
	
	private static WarpLocation[] warps = {
			new WarpLocation(getNew(819, 15, 817), "Reeve's Lake", "ReevesLake", "pearl"),
			new WarpLocation(getNew(508, 45, 250), "Cave", "Cave", "skull"),
			new WarpLocation(getNew(24, 15, 246), "Thief Hideaway", "ThiefsHideaway", "pearl")
	};
	
	private static Location getNew(int x, int y, int z) {
		return new Location(Util.w, x, y, z);
	}
	
	public static void run(Player p, String[] args) {
		if (args.length == 1) {
			String name = args[0];
			
			WarpLocation location = null;
			
			for ( WarpLocation wl : warps) {
				if (wl.getLabelName().equalsIgnoreCase(name)) {
					location = wl;
				}
			}
			
			if (location == null) {
				p.sendMessage(ChatColor.RED + "Warp does not exist.");
				return;
			}
			location.teleport(p);
			p.sendMessage(ChatColor.GRAY + "You warped to: " + ChatColor.AQUA + location.getDisplayName() + ".");
		} else {
			String all = "";
			for (WarpLocation s : warps) {
				all += s.getDisplayName() + ChatColor.GRAY + ", ";
			}
			p.sendMessage(ChatColor.GRAY + "Warps: " + ChatColor.AQUA + all.substring(0, all.length() - 2).toLowerCase());
		}
	}
	
	public static void createMarker(String setName, String displayName, String labelName, Location location, String iconName) {
		if (Main.main.markerAPI != null) {
			MarkerSet set = Main.main.markerAPI.getMarkerSet(setName);
			if (set != null) {
				boolean found = false;
				for (Marker m : set.getMarkers()) {
					if (!found) if (m.getMarkerID().equalsIgnoreCase(labelName)) found = true;
				}
				if (!found)
					set.createMarker(labelName, displayName, "world", location.getX(), location.getY(), location.getZ(), Main.main.markerAPI.getMarkerIcon(iconName), false);
				
			} else {
				set = Main.main.markerAPI.createMarkerSet(setName, setName, Main.main.markerAPI.getMarkerIcons(), false);
				set.createMarker(labelName, displayName, "world", location.getX(), location.getY(), location.getZ(), Main.main.markerAPI.getMarkerIcon(iconName), false);
			}
		} else {
			System.out.println("error 73");
		}
	}
	
	private static class WarpLocation {
		
		private Location location;
		private String displayName;
		private String labelName;
		
		private WarpLocation(Location location, String displayName, String labelName, String icon) {
			this.location = location;
			this.displayName = displayName;
			this.labelName = labelName;
			createMarker("Areas", displayName, "area_" + labelName, location, icon);
			
			
		}
		
		public Location getLocation() {
			return location;
		}
		
		public String getDisplayName() {
			return displayName;
		}
		
		public String getLabelName() {
			return labelName;
		}
		
		public void teleport(Player player) {
			player.teleport(new Location(location.getWorld(), location.getX(), 100, location.getZ()));
		}
	}
	
}
