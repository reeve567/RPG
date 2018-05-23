package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDFirstTask;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDSecondTask;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class QuestList {
	
	public static HashMap<String, Class<? extends Quest>> quests = new HashMap<>();
	private static Class[] questsP = new Class[]{
			KDFirstTask.class,
			KDSecondTask.class
	};
	
	static {
		for (Class c : questsP) {
			quests.put(c.getName(), c);
		}
	}
	
	public static ArrayList<Class<? extends Quest>> getQuests(String startWith) {
		ArrayList<Class<? extends Quest>> temp = new ArrayList<>();
		
		for (String s : quests.keySet()) {
			if (s.startsWith(startWith)) {
				temp.add(quests.get(s));
			}
		}
		return temp;
	}
	
}
