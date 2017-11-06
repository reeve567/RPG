package me.imunsmart.rpg.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mobs.Mob;
import me.imunsmart.rpg.mobs.MobManager;
import me.imunsmart.rpg.util.Util;

public class Spawners {
	Main pl;
	private static File spawn;

	public static List<Spawner> spawns = new ArrayList<Spawner>();
	private static Spawners spawners;

	public Spawners(Main pl) {
		this.pl = pl;
		spawn = new File(pl.getDataFolder(), "spawners.yml");
		spawners = this;
		reloadSpawners();
	}

	public static void setSpawn(Location l, int tier, String name) {
		String w = l.getWorld().getName();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
		fc.set(name + ".world", w);
		fc.set(name + ".x", x);
		fc.set(name + ".y", y);
		fc.set(name + ".z", z);
		fc.set(name + ".tier", tier);
		try {
			fc.save(spawn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void die(Mob m) {
		for (Spawner s : spawns) {
			if (s.spawned.contains(m)) {
				s.spawned.remove(m);
				break;
			}
		}
	}
	
	public static void disable() {
		for(LivingEntity le : Util.w.getLivingEntities()) {
			if(le.getCustomName().length() != 0)
				le.remove();
		}
	}
	
	public static void reloadSpawners() {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
		for (String name : fc.getKeys(false)) {
			spawns.add(new Spawner(spawners, Bukkit.getWorld(fc.getString(name + ".world")), fc.getInt(name + ".x"), fc.getInt(name + ".y"), fc.getInt(name + ".z"), fc.getInt(name + ".tier")).spawn());
		}
	}
}

class Spawner {
	List<Mob> spawned = new ArrayList<>();
	int x, y, z, tier;
	World w;
	Spawners s;

	public Spawner(Spawners s, World w, int x, int y, int z, int tier) {
		this.s = s;
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tier = tier;
	}

	public Spawner spawn() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(s.pl, () -> {
			if (spawned.size() < 5) {
				spawned.add(MobManager.spawn(new Location(w, x, y, z), Math.random() < 0.5 ? "zombie" : "skeleton", tier));
			}
		}, 0, (1200 * tier));
		return this;
	}
}