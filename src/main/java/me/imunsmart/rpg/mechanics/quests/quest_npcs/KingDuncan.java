package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mechanics.quests.QuestPlayerData;
import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import sun.plugin2.message.Message;

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
	
	private static void setQuests() {
		quests.add("KingDuncanFirstTask");
	}
	
	@Override
	protected String setOther() {
		return "king_duncan";
	}
	
	public void onClick(Player player) {
		if (QuestManager.playerData.get(player.getUniqueId()).isInQuest()) {
			QuestPlayerData playerData = QuestManager.playerData.get(player.getUniqueId());
			Quest quest = playerData.getActiveQuest();
			player.sendMessage(quest.getName());
			if (quests.contains(quest.getName())) {
				if (!quest.isStarted()) {
					String s = quest.getNextDialog();
					if (s != null) {
						player.sendMessage(MessagesUtil.npcMessage(name,s));
					}
				} else {
					if (quest.canFinish()) {
						String s = quest.getNextDialog();
						if (s == null) {
							quest.finish();
							QuestManager.updateBook(player);
						} else {
							player.sendMessage("§bKing Duncan§f:§7 " + s);
						}
					} else {
						player.sendMessage("§bKing Duncan§f:§7 " + quest.getNotDone());
						QuestManager.updateBook(player);
					}
				}
			}
		} else {
			QuestPlayerData playerData = QuestManager.playerData.get(player.getUniqueId());
			
			boolean found = false;
			for (String s : quests) {
				if (!found && !playerData.hasFinished(s)) {
					found = true;
					playerData.setActiveQuest(QuestManager.getQuest(player, s));
					QuestManager.updateBook(player);
				}
			}
			if (found) {
				QuestManager.updateBook(player);
			} else {
				index++;
				if (index >= strings.length)
					index = 0;
				player.sendMessage("§bKing Duncan§f:§7 " + strings[index]);
			}
		}
	}
}
