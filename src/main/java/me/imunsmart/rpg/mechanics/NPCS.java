package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.RPGNPCClickEvent;
import me.imunsmart.rpg.mechanics.gui.BuyMenu;
import me.imunsmart.rpg.mechanics.gui.GlobalMarket;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.FarmerBill;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.PyroTechnic;
import me.imunsmart.rpg.util.Util;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.KingDuncan;
import org.apache.logging.log4j.Marker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class NPCS implements Listener {
	private static ArrayList<NPC> npcs = new ArrayList<>();
	private static int questGivers = 1;
	private static int marketers = 1;
	private static int bankers = 1;
	private static int merchants = 1;
	
	public NPCS() {
		new Marketer(new Location(Util.w, 0.5, 75.5, -1.5, 180, 0));
		new Marketer(new Location(Util.w, 2.5, 75.5, 0.5, -90, 0));
		new Marketer(new Location(Util.w, 0.5, 75.5, 2.5, 0, 0));
		new Marketer(new Location(Util.w, -1.5, 75.5, 0.5, 90, 0));
		new Merchant(new Location(Util.w, 6.5, 63.5, -11.5, 90, 0));
		//new Talker(new Location(Util.w, 19.5, 66, 0.5, 90, 0), Villager.Profession.PRIEST, "§bKing Duncan", "Have fun on your adventures!", "Don't die!");
		
		//QUEST NPCS
		new KingDuncan(new Location(Util.w, 300, 13.5, 631.5));
		new FarmerBill(new Location(Util.w, -13, 63, -16));
		new PyroTechnic(new Location(Util.w, 320.5, 81.5, 656.5));
	}
	
	public static void disable() {
		for (NPC npc : npcs) {
			LivingEntity entity = npc.getEntity();
			if (entity == null)
				continue;
			npc.getEntity().remove();
		}
		npcs.clear();
	}
	
//	public static void createMarker(String setName, String displayName, String labelName, Location location, String iconName, int id) {
//		if (Main.main.markerAPI != null) {
//			MarkerSet set = Main.main.markerAPI.getMarkerSet(setName);
//			if (set != null) {
//				boolean found = false;
//				for (Marker m : set.getMarkers()) {
//					if (!found) if (m.getMarkerID().equalsIgnoreCase(labelName + id)) found = true;
//				}
//				if (!found)
//					set.createMarker(labelName + id, displayName, "world", location.getX(), location.getY(), location.getZ(), Main.main.markerAPI.getMarkerIcon(iconName), false);
//
//			} else {
//				set = Main.main.markerAPI.createMarkerSet(setName, setName, Main.main.markerAPI.getMarkerIcons(), false);
//				set.createMarker(labelName + id, displayName, "world", location.getX(), location.getY(), location.getZ(), Main.main.markerAPI.getMarkerIcon(iconName), false);
//			}
//		} else {
//			System.out.println("error 73");
//		}
//	}
	
	@EventHandler
	public void onHit(EntityDamageEvent e) {
		if (e.getEntity().getScoreboardTags().contains("npc")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Entity entity = e.getRightClicked();
		if (entity.getScoreboardTags().contains("npc")) {
			e.setCancelled(true);
			for (NPC npc : npcs) {
				if (npc.getEntity() != null && npc.getEntity() == entity) {
					RPGNPCClickEvent ev = new RPGNPCClickEvent(e.getPlayer(), npc);
					Bukkit.getPluginManager().callEvent(ev);
				}
			}
		} else {
			System.out.println("unhandled npc");
		}
	}
	
	@EventHandler
	public void onClick(RPGNPCClickEvent e) {
		e.getNPC().onClick(e.getPlayer());
	}
	
	public static abstract class NPC {
		
		public UUID uuid;
		public String name;
		private Location location;
		
		private NPC(EntityType type, Location location, String name) {
			this.uuid = location.getWorld().spawn(location, type.getEntityClass()).getUniqueId();
			this.location = location;
			npcs.add(this);
			this.name = name;
			set();
		}
		
		private void set() {
			LivingEntity entity = getEntity();
			if (entity == null)
				return;
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
		
		public LivingEntity getEntity() {
			return (LivingEntity) Bukkit.getEntity(uuid);
		}
		
		protected abstract String setOther();
		
		private NPC(Villager.Profession profession, Location location, String name) {
			LivingEntity entity = location.getWorld().spawn(location, Villager.class);
			this.uuid = entity.getUniqueId();
			((Villager) entity).setProfession(profession);
			this.location = location;
			npcs.add(this);
			this.name = name;
			set();
		}
		
		public Location getLocation() {
			return location;
		}
		
		public void onClick(Player player) {
		
		}
	}
	
	public static class Banker extends NPC {
		
		Banker(Location location) {
			super(Villager.Profession.LIBRARIAN, location, "§a§lBanker");
		}
		
		@Override
		protected String setOther() {
//			createMarker("Bankers", "Banker", "banker_", getLocation(), "diamond", bankers);
			bankers++;
			return "banker";
		}
		
		public void onClick(Player player) {
			Bank.open(player);
		}
	}
	
	public static class Merchant extends NPC {
		
		Merchant(Location location) {
			super(Villager.Profession.LIBRARIAN, location, "§a§lMerchant");
		}
		
		@Override
		protected String setOther() {
//			createMarker("Merchants", "Merchant", "merchant_", getLocation(), "pickaxe", merchants);
			merchants++;
			return "merchant";
		}
		
		public void onClick(Player player) {
			BuyMenu.showMenu(player);
		}
	}
	
	public static class Marketer extends NPC {
		Marketer(Location location) {
			super(EntityType.VILLAGER, location, "§2§lMarketer");
		}
		
		@Override
		protected String setOther() {
//			createMarker("Marketers", "Marketer", "marketer_", getLocation(), "sword", marketers);
			marketers++;
			return "marketer";
		}
		
		public void onClick(Player player) {
			GlobalMarket.open(player);
		}
	}
	
	public abstract static class QuestGiver extends NPC {
		
		protected String[] strings;
		protected int index;
		protected ArrayList<String> quests = new ArrayList<>();
		
		public QuestGiver(Location location, EntityType type, String name, String... strings) {
			super(type, location, name);
			this.strings = strings;
			init(name, location);
		}
		
		private void init(String name, Location location) {
//			createMarker("Quests", ChatColor.stripColor(name), "questGiver_", location, "book", questGivers);
			questGivers++;
		}
		
		public QuestGiver(Location location, Villager.Profession profession, String name, String... strings) {
			super(profession, location, name);
			this.strings = strings;
			init(name, location);
		}
		
		public void onClick(Player player) {
		
		}

		public void say(Player p, String s) {
			p.sendMessage(name + ChatColor.WHITE + ": " + s);
		}

		public void speak(Player player) {
			index++;
			if (index >= strings.length)
				index = 0;
			player.sendMessage(name + ChatColor.WHITE + ": " + strings[index]);
		}
	}
	
	public static class Talker extends NPC {
		
		private String[] strings;
		private int index = -1;
		
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
		
		@Override
		public void onClick(Player player) {
			index++;
			if (index >= strings.length)
				index = 0;
			player.sendMessage(name + "§f:§7 " + strings[index]);
		}
	}
	
}

