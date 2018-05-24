package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestList;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDFirstTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class KingDuncan extends NPCS.QuestGiver {
	
	private static ArrayList<Class<? extends Quest>> quests = new ArrayList<>();
	
	public KingDuncan(Location location) {
		super(location, Villager.Profession.PRIEST, "§bKing Duncan",
				"Don't die!",
				"Have fun on your adventures.",
				"Watch out for powerful monsters."
		);
		entity.addScoreboardTag("king_duncan");
		setQuests();
	}
	
	public void onClick(Player player) {
		super.onClick(player);
		String s = Stats.getQuest(player);
		if (s == null) {
			boolean found = false;
			for (Class c : quests) {
				if (!Stats.finishedQuest(player,c.getName())) {
					found = true;
				}
			}
			if (!found) {
				
				//completed all quests for KingDuncan
			}
		}
		else {
			boolean found = false;
			for (Class c : quests) {
				if (!found && c.getName().equalsIgnoreCase(s)) {
					found = true;
					player.sendMessage("§bKing Duncan§f:§7" + ResourceBundle.getBundle(c.getName()).getString("notDone"));
				}
			}
		}
	}
	
	public static void setQuests() {
		quests.addAll(QuestList.getQuests("KD"));
	}
}
