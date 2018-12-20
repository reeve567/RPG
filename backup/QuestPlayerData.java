package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KingDuncanFirstTask;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestPlayerData {
	
	private UUID id;
	private ArrayList<String> quests = new ArrayList<>();
	private Quest activeQuest = null;
	
	public QuestPlayerData(UUID id) {
		this.id = id;
		List<String> strings = Stats.getQuestData(id);
		if (strings.size() > 1) {
			for (String s : strings) {
				if (s.startsWith("Q:")) {
					quests.add(s.substring(2));
				} else if (s.startsWith("CQ:")) {
					if (s.contains("-")) {
						activeQuest = QuestManager.getQuest(Bukkit.getPlayer(id), s.substring(3, s.indexOf('-')), s.substring(s.indexOf('-') + 1));
					} else {
						activeQuest = QuestManager.getQuest(Bukkit.getPlayer(id), s.substring(3));
					}
				}
			}
		} else {
			if (strings.size() == 1)
				activeQuest = new KingDuncanFirstTask(Bukkit.getPlayer(id), strings.get(0).substring(strings.get(0).indexOf('-') + 1));
			else
				activeQuest = new KingDuncanFirstTask(Bukkit.getPlayer(id));
		}
		save();
	}
	
	private void save() {
		Stats.setStat(Bukkit.getOfflinePlayer(id), "json", toStringList());
	}
	
	private ArrayList<String> toStringList() {
		ArrayList<String> result = new ArrayList<>();
		if (activeQuest == null) {
			result.add("CQ:null");
		}
		else {
			result.add("CQ:" + activeQuest.toString());
		}
		for (String s : quests) {
			result.add("Q:" + s);
		}
		return result;
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
		save();
	}
	
	public void finishQuest() {
		quests.add(activeQuest.getName());
		activeQuest = null;
		save();
	}
}
