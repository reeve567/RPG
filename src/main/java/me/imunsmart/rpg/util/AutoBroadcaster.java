package me.imunsmart.rpg.util;

import me.imunsmart.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoBroadcaster {
	private static List<String> messages;
	
	public AutoBroadcaster(Main pl) {
		File folder = pl.getDataFolder();
		folder.mkdir();
		initMessages(pl);
		pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
			int id = 0;
			
			@Override
			public void run() {
				if (messages.size() > 0) {
					Bukkit.broadcastMessage(Util.logo + ChatColor.GRAY + " > " + ChatColor.translateAlternateColorCodes('&', messages.get(id)));
					id++;
					if (id >= messages.size())
						id = 0;
				}
			}
		}, 0, 1200L);
	}
	
	private static void initMessages(Main pl) {
		messages = new ArrayList<>();
		File f = new File(pl.getDataFolder(), "broadcasts.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		messages.addAll(fc.getStringList("broadcasts"));
	}
}
