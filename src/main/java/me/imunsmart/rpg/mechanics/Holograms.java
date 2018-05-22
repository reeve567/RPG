package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class Holograms {
	
	private static ArrayList<TextHologram> textHolograms = new ArrayList<>();
	
	public Holograms() {
		addTextHologram(new Location(Util.w, 6.5, 15, 186.5), "§c§lTeddyBe is gay");
		addTextHologram(new Location(Util.w, 6.5, 14.75, 186.5), "§c§lTeddyBe is gay 2");
		addTextHologram(new Location(Util.w, 6.5, 14.5, 186.5), "§c§lTeddyBe is gay 3");
		
		addMultilineTextHologram(new Location(Util.w,6.5,14,186.5),"1","2","3","4","5");
		
	}
	
	private static TextHologram addTextHologram(Location location, String content) {
		TextHologram textHologram = new TextHologram(location, content);
		textHolograms.add(textHologram);
		return textHologram;
	}
	
	private static ArrayList<TextHologram> addMultilineTextHologram(Location location, String... content) {
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
	
	private static class TextHologram {
		
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
