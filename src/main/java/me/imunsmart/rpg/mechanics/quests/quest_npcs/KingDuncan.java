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
	private static ArrayList<String> quests = new ArrayList<>();
	private static int index = -1;
	
	public KingDuncan(Location location) {
		super(location, Villager.Profession.PRIEST, "§bKing Duncan",
				strings
		);
		setQuests();
	}
	
	public static void setQuests() {
		quests.add("KDFT");
	}
	
	@Override
	protected String setOther() {
		return "king_duncan";
	}
	
	public void onClick(Player player) {
		if (QuestManager.playerData.get(player.getUniqueId()).isInQuest()) {
			QuestPlayerData playerData = QuestManager.playerData.get(player.getUniqueId());
			Quest quest = playerData.getActiveQuest();
			if (quests.contains(quest.getName())) {
				if (quest.canFinish()) {
					quest.finish();
				}
				else {
					player.sendMessage("§bKing Duncan§f:§7 " + quest.getNotDone());
				}
			}
		} else {
			QuestPlayerData playerData = QuestManager.playerData.get(player.getUniqueId());
			
			boolean found = false;
			for (String s : quests) {
				if (!found && playerData.hasFinished(s)) {
					found = true;
					playerData.setActiveQuest(QuestManager.getQuest(player, s));
				}
			}
			if (found) {
				player.sendMessage(MessagesUtil.questStarted(playerData.getActiveQuest().getName()));
			} else {
				//no available quests
				index++;
				if (index >= strings.length)
					index = 0;
				player.sendMessage("§bKing Duncan§f:§7 " + strings[index]);
				
			}
		}
		QuestManager.updateBook(player);
	}
}
