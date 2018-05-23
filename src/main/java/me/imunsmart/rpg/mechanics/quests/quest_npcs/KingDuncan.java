package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.quests.Quest;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

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
	
	public static void onClick(Player player) {
	
	}
	
	public static void setQuests() {
		quests.addAll(QuestList.getQuests("KD"));
	}
}
