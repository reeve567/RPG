package me.imunsmart.rpg.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.imunsmart.rpg.Main;

public class AutoBroadcaster {
	private static List<String> messages;

	public AutoBroadcaster(Main pl) {
		initMessages(pl);
		pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
			int id = 0;

			@Override
			public void run() {
				Bukkit.broadcastMessage(Util.logo + ChatColor.GRAY + " > " + ChatColor.translateAlternateColorCodes('&', messages.get(id)));
				id++;
				if (id >= messages.size())
					id = 0;
			}
		}, 0, 1200L);
	}

	public static void initMessages(Main pl) {
		messages = new ArrayList<String>();
		File f = new File(pl.getDataFolder(), "broadcasts.yml");
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for (String s : fc.getStringList("broadcasts"))
			messages.add(s);
	}
}
