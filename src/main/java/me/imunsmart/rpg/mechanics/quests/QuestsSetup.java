package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class QuestsSetup {
	
	public QuestsSetup(Main main) {
		register(main
				//King Duncan >
				, new KillTracker()
				
				
				);
	}
	
	private void register(Main main, Listener... listeners) {
		for (Listener l : listeners) {
			Bukkit.getPluginManager().registerEvents(l, main);
		}
	}
	
}
