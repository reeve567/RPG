package me.imunsmart.rpg.mechanics.test;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.GlobalMarket;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;

public class NPCS implements Listener {
	
	private static ArrayList<NPC> npcs = new ArrayList<>();
	
	public NPCS(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		new Banker(new Location(Util.w, 21.5, 65, -4.5));
		new Marketer(new Location(Util.w, 21.5, 65, -2.5));
		new Talker(new Location(Util.w, 19.5, 66, 0.5), Villager.Profession.PRIEST, "§aKing Duncan", "Have fun on your adventures!", "Don't die!");
		
	}
	
	public static void disable() {
		for (NPC npc : npcs) {
			npc.entity.remove();
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Entity entity = e.getRightClicked();
		if (entity.getScoreboardTags().contains("npc")) {
			e.setCancelled(true);
			if (entity.getScoreboardTags().contains("banker")) {
				Banker.onClick(e.getPlayer());
			} else if (entity.getScoreboardTags().contains("marketer")) {
				Marketer.onClick(e.getPlayer());
			} else if (entity.getScoreboardTags().contains("talker")) {
				for (NPC npc : npcs) {
					if (npc instanceof Talker) {
						Talker talker = (Talker) npc;
						if (talker.entity.equals(entity)) {
							talker.onClick(e.getPlayer());
						}
					}
				}
			}
		} else {
			System.out.println("unhandled npc");
		}
	}
	
	private enum Direction {
		NORTH(180), NORTHWEST(135), WEST(90), SOUTHWEST(45), SOUTH(0), EAST(270);
		
		int yaw;
		
		Direction(int yaw) {
			this.yaw = yaw;
		}
		
		public int getYaw() {
			return yaw;
		}
	}
	
	public abstract static class NPC {
		
		public LivingEntity entity;
		public String name;
		private Location location;
		
		private NPC(EntityType type, Location location, String name) {
			this.entity = (LivingEntity) Util.w.spawnEntity(location, type);
			this.location = location;
			set();
			npcs.add(this);
			this.name = name;
		}
		
		private void set() {
			entity.addScoreboardTag("npc");
			entity.addScoreboardTag(setOther());
			
			entity.setRemoveWhenFarAway(false);
			entity.setGravity(false);
			entity.setAI(false);
			entity.setInvulnerable(true);
			entity.setSilent(true);
			if (name != null && !name.equals("")) {
				entity.setCustomName(name);
				entity.setCustomNameVisible(true);
			}
		}
		
		protected abstract String setOther();
		
		private NPC(Villager.Profession profession, Location location, String name) {
			this.entity = (LivingEntity) Util.w.spawnEntity(location, EntityType.VILLAGER);
			((Villager) entity).setProfession(profession);
			this.location = location;
			set();
			npcs.add(this);
			this.name = name;
		}
		
		public Location getLocation() {
			return location;
		}
	}
	
	public static class Banker extends NPC {
		
		Banker(Location location) {
			super(Villager.Profession.LIBRARIAN, location, "§aBanker");
		}
		
		static void onClick(Player player) {
			Bank.open(player);
		}
		
		@Override
		protected String setOther() {
			return "banker";
		}
	}
	
	public static class Marketer extends NPC {
		Marketer(Location location) {
			super(EntityType.VILLAGER, location, "§2Marketer");
		}
		
		static void onClick(Player player) {
			GlobalMarket.open(player);
		}
		
		@Override
		protected String setOther() {
			return "marketer";
		}
	}
	
	public static class Talker extends NPC {
		
		private String[] strings;
		
		public Talker(Location location, EntityType type, String name, String... strings) {
			super(type, location, name);
			this.strings = strings;
		}
		
		Talker(Location location, Villager.Profession profession, String name, String... strings) {
			super(profession, location, name);
			this.strings = strings;
		}
		
		@Override
		protected String setOther() {
			return "talker";
		}
		
		void onClick(Player player) {
			player.sendMessage(name + "§f:§a " + strings[(int) (strings.length * Math.random())]);
			
		}
	}
	
	public abstract static class Custom extends NPC {
		
		private Custom(EntityType type, Location location, String name) {
			super(type, location, name);
		}
		
		private Custom(Villager.Profession type, Location location, String name) {
			super(type, location, name);
		}
		
	}
}

