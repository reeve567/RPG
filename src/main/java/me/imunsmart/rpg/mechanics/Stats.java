package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mobs.Constants;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Stats {
	private static File dir;

	public Stats(Main pl) {
		dir = new File(pl.getDataFolder() + "/players");
		if (!pl.getDataFolder().exists())
			pl.getDataFolder().mkdir();
		if (!dir.exists())
			dir.mkdirs();
	}

	public static List<String> getCompletedQuests(OfflinePlayer p) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains("completed-quests")) return new ArrayList<String>();
		return fc.getStringList("completed-quests");
	}

	public static void completeQuest(OfflinePlayer op, String id) {
		List<String> cq = getCompletedQuests(op);
		cq.add(id);
		setStat(op, "completed-quests", cq);
		setStat(op, "current-quest", null);
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

	public static List<String> getList(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return new ArrayList<>();
		return fc.getStringList(id);
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

	public static int getInt(OfflinePlayer p, String id, int df) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id)) {
			setStat(p, id, df);
			return df;
		}
		return fc.getInt(id);
	}

	public static void levelUp(OfflinePlayer p) {
		int l = getLevel(p) + 1;
		addStat(p, "level", 1);
		setStat(p, "xp", 0);
		if (p.isOnline()) {
			Player op = (Player) p;
			op.sendMessage(ChatColor.AQUA + "You have leveled up!" + ChatColor.GRAY);
			op.sendMessage(ChatColor.GRAY + "You are now level " + ChatColor.AQUA + l + ChatColor.GRAY + "!");
			op.playSound(op.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
			new ActionBar(ChatColor.AQUA + "Level up!").sendToPlayer(op);
			for (int i = 0; i < Constants.LEVEL_REQ.length; i++) {
				int x = Constants.LEVEL_REQ[i];
				int tier = i + 1;
				if (l == x) {
					op.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You can now wield " + Items.nameColor[i].toString() + ChatColor.BOLD + "Tier " + tier + ChatColor.GREEN.toString() + ChatColor.BOLD + " items.");
					break;
				}
			}
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

	public static int getLevel(OfflinePlayer p) {
		return getInt(p, "level", 1);
	}

	public static List<String> getQuestData(UUID id) {
		return getList(id, "quests");
	}

	public static List<String> getList(UUID p, String id) {
		File f = new File(dir, p + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return new ArrayList<>();
		return fc.getStringList(id);
	}

	public static boolean canWield(Player p, int tier) {
		int level = getLevel(p);
		return level >= Constants.LEVEL_REQ[tier - 1];
	}

	public static boolean exists(OfflinePlayer p) {
		return new File(dir, p.getUniqueId() + ".yml").exists();
	}

	public static double getDouble(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0.0;
		return fc.getDouble(id);
	}

	public static int getInt(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return 0;
		return fc.getInt(id);
	}

	public static String getString(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		if (!fc.contains(id))
			return "";
		return fc.getString(id);
	}

	public static ConfigurationSection getKey(OfflinePlayer p, String id) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		return fc.getConfigurationSection(id);
	}

	public static void reset(OfflinePlayer p) {
		File f = new File(dir, p.getUniqueId() + ".yml");
		if (f.exists())
			f.delete();
	}

}
