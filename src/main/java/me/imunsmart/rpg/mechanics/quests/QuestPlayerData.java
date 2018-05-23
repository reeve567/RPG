package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.mechanics.Stats;
import org.bukkit.Bukkit;

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
						activeQuest =  QuestManager.getQuest(Bukkit.getPlayer(id),s.substring(3,s.indexOf('-')));
					}
					else {
						activeQuest = QuestManager.getQuest(Bukkit.getPlayer(id),s.substring(3));
					}
				}
			}
		} else {
		
		}
	}
	
}
