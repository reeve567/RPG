package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import java.io.File;

import java.util.ArrayList;

public class Holograms {
	
	private static ArrayList<TextHologram> textHolograms = new ArrayList<>();
	private Main pl;
	
	public Holograms(Main pl) {
		addTextHologram(new Location(Util.w,-14.5, 66, -18.5), ChatColor.GREEN.toString() + ChatColor.BOLD + "The Mine");
		this.pl = pl;
	}
	
	public static TextHologram addTextHologram(Location location, String content) {
		TextHologram textHologram = new TextHologram(location, content);
		textHolograms.add(textHologram);
		return textHologram;
	}
	
	public static ArrayList<TextHologram> addMultilineTextHologram(Location location, String... content) {
		ArrayList<TextHologram> holos = new ArrayList<>();
		int amount = 0;
		for (String s : content) {
			Location location1 = new Location(Util.w, location.getX(), location.getY() - (amount * 0.25), location.getZ());
			TextHologram textHologram = new TextHologram(location1, s);
			holos.add(textHologram);
			textHolograms.add(textHologram);
			amount++;
		}
		return holos;
	}
	
	public static void disable() {
		for (TextHologram hologram : textHolograms) {
			hologram.disable();
		}
	}
	
	public static class TextHologram {
		
		private Location location;
		private String content;
		private ArmorStand stand;
		
		TextHologram(Location location, String content) {
			location.setY(location.getY());
			this.location = location;
			this.content = content;
			stand = (ArmorStand) location.getWorld().spawnEntity(this.location, EntityType.ARMOR_STAND);
			stand.addScoreboardTag("hologram-text");
			stand.setMarker(true);
			stand.setVisible(false);
			stand.setInvulnerable(true);
			stand.setGravity(false);
			stand.setCustomName(content);
			stand.setCustomNameVisible(true);
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		public void disable() {
			stand.remove();
		}
	}
	
}
