package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDFT;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestPlayerData {
	
	private ArrayList<String> quests = new ArrayList<>();
	private Quest activeQuest = null;
	
	public QuestPlayerData(UUID id) {
		List<String> strings = Stats.getQuestData(id);
		if (strings.size() > 1) {
			for (String s : strings) {
				if (s.startsWith("Q:")) {
					quests.add(s.substring(2));
				} else if (s.startsWith("CQ:")) {
					if (s.contains("-")) {
						activeQuest = QuestManager.getQuest(Bukkit.getPlayer(id), s.substring(3, s.indexOf('-')),s.substring(s.indexOf('-') + 1));
					} else {
						activeQuest = QuestManager.getQuest(Bukkit.getPlayer(id), s.substring(3));
					}
				}
			}
		} else {
			if (strings.size() == 1)
				activeQuest = new KDFT(Bukkit.getPlayer(id), strings.get(0).substring(strings.get(0).indexOf('-') + 1));
			else
				activeQuest = new KDFT(Bukkit.getPlayer(id));
		}
	}
	
	public boolean hasFinished(String s) {
		return quests.contains(s);
	}
	
	public boolean isInQuest() {
		return activeQuest != null;
	}
	
	public Quest getActiveQuest() {
		return activeQuest;
	}
	
	public void setActiveQuest(Quest activeQuest) {
		this.activeQuest = activeQuest;
	}
	
	public void save() {
		//File f = new File();
		//FileConfiguration fc =
	}
}
