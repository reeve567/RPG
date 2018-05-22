package me.imunsmart.rpg.mechanics.test;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.GlobalMarket;
import me.imunsmart.rpg.util.LocationUtility;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class NPCS implements Listener {
	
	private static ArrayList<NPC> npcs = new ArrayList<>();
	
	public NPCS(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		new Banker(new Location(Util.w, 21.5, 65, -4.5, 90, 0));
		new Marketer(new Location(Util.w, 21.5, 65, -2.5, 90, 0));
		new Talker(new Location(Util.w, 19.5, 66, 0.5, 90, 0), Villager.Profession.PRIEST, "§bKing Duncan", "Have fun on your adventures!", "Don't die!");
		
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
	
	@EventHandler
	public void onHit(EntityDamageEvent e) {
		if (e.getEntity().getScoreboardTags().contains("npc")) {
			e.setCancelled(true);
		}
	}
	
	public abstract static class NPC {
		
		public LivingEntity entity;
		public String name;
		private Location location;
		
		private NPC(EntityType type, Location location, String name) {
			this.entity = (LivingEntity) Util.w.spawnEntity(location, type);
			this.location = location;
			npcs.add(this);
			this.name = name;
			set();
		}
		
		private void set() {
			entity.addScoreboardTag("npc");
			entity.addScoreboardTag(setOther());
			
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true));
			entity.setInvulnerable(true);
			entity.setSilent(true);
			entity.setCollidable(false);
			if (name != null && !name.equals("")) {
				entity.setCustomName(name);
				entity.setCustomNameVisible(true);
			}
		}
		
		protected abstract String setOther();
		
		private NPC(EntityType type, Location location, String name, LocationUtility.Direction direction) {
			this.entity = (LivingEntity) Util.w.spawnEntity(LocationUtility.setYaw(location, direction.getYaw()), type);
			this.location = LocationUtility.setYaw(location, direction.getYaw());
			npcs.add(this);
			this.name = name;
			set();
		}
		
		private NPC(Villager.Profession profession, Location location, String name) {
			this.entity = (LivingEntity) Util.w.spawnEntity(location, EntityType.VILLAGER);
			((Villager) entity).setProfession(profession);
			this.location = location;
			npcs.add(this);
			this.name = name;
			set();
		}
		
		private NPC(Villager.Profession profession, Location location, String name, LocationUtility.Direction direction) {
			this.entity = (LivingEntity) Util.w.spawnEntity(LocationUtility.setYaw(location, direction.getYaw()), EntityType.VILLAGER);
			((Villager) entity).setProfession(profession);
			this.location = LocationUtility.setYaw(location, direction.getYaw());
			npcs.add(this);
			this.name = name;
			set();
		}
		
		public Location getLocation() {
			return location;
		}
	}
	
	public static class Banker extends NPC {
		
		Banker(Location location, LocationUtility.Direction direction) {
			super(Villager.Profession.LIBRARIAN, location, "§aBanker", direction);
		}
		
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
		Marketer(Location location, LocationUtility.Direction direction) {
			super(EntityType.VILLAGER, location, "§2Marketer", direction);
		}
		
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
		
		public Talker(Location location, EntityType type, String name, LocationUtility.Direction direction, String... strings) {
			super(type, location, name, direction);
			this.strings = strings;
		}
		
		Talker(Location location, Villager.Profession profession, LocationUtility.Direction direction, String name, String... strings) {
			super(profession, location, name, direction);
			this.strings = strings;
		}
		
		@Override
		protected String setOther() {
			return "talker";
		}
		
		void onClick(Player player) {
			player.sendMessage(name + "§f:§7 " + strings[(int) (strings.length * Math.random())]);
			
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

