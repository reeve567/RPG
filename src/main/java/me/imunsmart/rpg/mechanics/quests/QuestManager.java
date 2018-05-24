package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDFT;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class QuestManager {
	
	public static HashMap<UUID, QuestPlayerData> playerData = new HashMap<>();
	
	public static Quest getQuest(Player player, String s) {
		
		if (s.endsWith("FT")) {
			return new FirstTaskFinder(player, s).getTask();
		}
		
		return null;
	}
	
	public static Quest getQuest(Player player, String s, String progress) {
		
		if (s.endsWith("FT")) {
			return new FirstTaskFinder(player, s, progress).getTask();
		}
		
		return null;
	}
	
	
	private static class FirstTaskFinder {
		
		Player player;
		String s;
		String progress = null;
		
		public FirstTaskFinder(Player player, String s, String progress) {
			this(player, s);
			this.progress = progress;
		}
		
		public FirstTaskFinder(Player player, String s) {
			this.s = s;
			this.player = player;
		}
		
		private Quest getTask() {
			
			boolean useProgress = progress != null;
			switch (s) {
				
				case "KDFT":
					if (!useProgress)
						return new KDFT(player);
					else return new KDFT(player, progress);
				case "":
					break;
			}
			
			return null;
		}
		
	}
	
}
