package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.mobs.Mob;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spawners {
	public static List<Spawner> spawns = new ArrayList<Spawner>();
	private static File spawn;
	private static Spawners spawners;
	private static int total = 0;
	Main pl;
	
	public Spawners(Main pl) {
		this.pl = pl;
		spawn = new File(pl.getDataFolder(), "spawners.yml");
		spawners = this;
		reloadSpawners();
	}
	
	public static void die(Mob m) {
		for (Spawner s : spawns) {
			if (s.getSpawned().contains(m)) {
				s.getSpawned().remove(m);
				break;
			}
		}
	}
	
	public static void disable() {
		for (LivingEntity le : Util.w.getLivingEntities()) {
			if (le.getCustomName() != null)
				le.remove();
		}
	}
	
	public static void reloadSpawners() {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
		total = fc.getInt("total", 0);
		for (String name : fc.getKeys(false)) {
			if(name.equalsIgnoreCase("total")) continue;
			spawns.add(new Spawner(spawners, name, fc.getString(name + ".type"), Bukkit.getWorld(fc.getString(name + ".world")), fc.getInt(name + ".x"), fc.getInt(name + ".y"), fc.getInt(name + ".z"), fc.getInt(name + ".tier"), fc.getInt(name + ".max")).spawn());
		}
	}
	
	public static void remove(String id) {
		for (Spawner ss : spawns) {
			if (ss.getName().equals(id)) {
				spawns.remove(ss);
				FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
				fc.set(ss.getName(), null);
				try {
					fc.save(spawn);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void setSpawn(Location l, int tier, int max, String type) {
		String w = l.getWorld().getName();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		String name = String.valueOf(total++);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
		fc.set("total", total);
		fc.set(name + ".world", w);
		fc.set(name + ".x", x);
		fc.set(name + ".y", y);
		fc.set(name + ".z", z);
		fc.set(name + ".tier", tier);
		fc.set(name + ".max", max);
		fc.set(name + ".type", type);
		try {
			fc.save(spawn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Spawner {
	private List<Mob> spawned = new ArrayList<>();
	private String name, type;
	private int tier, max;
	private World w;
	private Spawners s;
	private Location l;
	
	public Spawner(Spawners s, String name, String type, World w, int x, int y, int z, int tier, int max) {
		this.s = s;
		this.name = name;
		this.type = type;
		this.l = new Location(w, x, y, z);
		this.tier = tier;
		this.max = max;
	}
	
	public Spawner spawn() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(s.pl, () -> {
			if (spawned.size() < max) {
				spawned.add(EntityManager.spawn(l, type, tier));
			}
		}, 0, (1200 * tier));
		return this;
	}

	public Location getLocation() {
		return l;
	}

	public String getName() {
		return name;
	}

	public int getTier() {
		return tier;
	}

	public int getMax() {
		return max;
	}

	public List<Mob> getSpawned() {
		return spawned;
	}
}