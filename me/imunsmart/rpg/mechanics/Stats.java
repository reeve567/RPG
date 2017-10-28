package me.imunsmart.rpg.mechanics;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;

public class Stats {
	private static File dir;

	public Stats(Main pl) {
		dir = new File(pl.getDataFolder() + "/players");
		if (!pl.getDataFolder().exists())
			pl.getDataFolder().mkdir();
		if (!dir.exists())
			dir.mkdirs();
	}

	public static int getStat(Player p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		return fc.getInt(id);
	}

	public static void setStat(Player p, String id, Object o) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set(id, o);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addStat(Player p, String id, int i) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set(id, fc.getInt(id) + i);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
