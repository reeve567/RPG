package me.imunsmart.rpg.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	//	public static Object getStat(Player p, String id) {
	//		File f = new File(dir, p.getUniqueId() + ".yml");
	//		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
	//		if (!fc.contains(id))
	//			return null;
	//		return fc.get(id);
	//	}

	public static int getInt(Player p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0;
		return fc.getInt(id);
	}

	public static int getInt(Player p, String id, int df) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return df;
		return fc.getInt(id);
	}

	public static double getDouble(Player p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0.0;
		return fc.getDouble(id);
	}

	public static int getString(Player p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0;
		return fc.getInt(id);
	}

	public static List<String> getList(Player p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return new ArrayList<String>();
		return fc.getStringList(id);
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
