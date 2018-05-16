package me.imunsmart.rpg.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;

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

	public static int getInt(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0;
		return fc.getInt(id);
	}

	public static int getInt(OfflinePlayer p, String id, int df) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id)) {
			setStat(p, id, df);
			return df;
		}
		return fc.getInt(id);
	}

	public static double getDouble(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0.0;
		return fc.getDouble(id);
	}

	public static int getString(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0;
		return fc.getInt(id);
	}

	public static List<String> getList(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return new ArrayList<String>();
		return fc.getStringList(id);
	}

	public static void setStat(OfflinePlayer p, String id, Object o) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set(id, o);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addStat(OfflinePlayer p, String id, int i) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set(id, fc.getInt(id) + i);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean exists(OfflinePlayer p) {
		return new File(dir, p.getUniqueId() + ".yml").exists();
	}

	public static void reset(OfflinePlayer p) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		if (f.exists())
			f.delete();
	}

	public static int getLevel(OfflinePlayer p) {
		return getInt(p, "level", 1);
	}

	public static void addXP(OfflinePlayer p, int xp) {
		int x = getInt(p, "xp", 0);
		x += xp;
		if (x >= Util.neededXP(p)) {
			levelUp(p);
		} else {
			addStat(p, "xp", xp);
		}
		if (p.isOnline()) {
			Player op = (Player) p;
			new ActionBar(ChatColor.YELLOW + "+" + xp + " XP " + ChatColor.GRAY + "[" + ChatColor.YELLOW + x + " / " + (int) (Util.neededXP(p)) + ChatColor.GRAY + "]").sendToPlayer(op);
		}
	}

	public static void levelUp(OfflinePlayer p) {
		int l = getLevel(p);
		addStat(p, "level", 1);
		setStat(p, "xp", 0);
		if (p.isOnline()) {
			Player op = (Player) p;
			op.sendMessage(ChatColor.AQUA + "You have leveled up!" + ChatColor.GRAY);
			op.sendMessage(ChatColor.GRAY + "You are now level " + ChatColor.AQUA + (l + 1) + ChatColor.GRAY + "!");
			op.playSound(op.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
			if (l == 5) {
				op.sendMessage(ChatColor.GRAY + "You can now wear " + ChatColor.DARK_GRAY + "Tier 2" + ChatColor.GRAY + " armor.");
				op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.1f);
			} else if (l == 10) {
				op.sendMessage(ChatColor.GRAY + "You can now wear " + ChatColor.WHITE + "Tier 3" + ChatColor.GRAY + " armor.");
				op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.1f);
			} else if (l == 20) {
				op.sendMessage(ChatColor.GRAY + "You can now wear " + ChatColor.AQUA + "Tier 4" + ChatColor.GRAY + " armor.");
				op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.1f);
			} else if (l == 30) {
				op.sendMessage(ChatColor.GRAY + "You can now wear " + ChatColor.YELLOW + "Tier 5" + ChatColor.GRAY + " armor.");
				op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.1f);
			}
		}
	}

}
