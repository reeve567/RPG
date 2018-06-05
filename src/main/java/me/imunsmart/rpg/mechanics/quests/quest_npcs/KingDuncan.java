package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mechanics.quests.QuestPlayerData;
import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

public class KingDuncan extends NPCS.QuestGiver {
	
	private static final String[] strings = {
			"Don't die!",
			"Have fun on your adventures.",
			"Watch out for powerful monsters."
	};
	
	public KingDuncan(Location location) {
		super(location, Villager.Profession.PRIEST, "§bKing Duncan",
				strings
		);
		quests.add("KingDuncanFirstTask");
	}
	
	@Override
	protected String setOther() {
		return "king_duncan";
	}
}
