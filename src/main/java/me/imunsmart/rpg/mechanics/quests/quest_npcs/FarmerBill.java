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

public class FarmerBill extends NPCS.QuestGiver {
	
	private static final String[] strings = {
			"Eat your fruits!",
			"I'd love to go on your adventures.",
			"What all is out there?"
	};
	private static ArrayList<String> quests = new ArrayList<>();
	private static int index = -1;
	
	public FarmerBill(Location location) {
		super(location, Villager.Profession.FARMER, "§aFarmer Bill", strings);
	}
	
	@Override
	protected String setOther() {
		return "farmer_bill";
	}
	
	
	@Override
	public void onClick(Player player) {
		if (QuestManager.playerData.get(player.getUniqueId()).isInQuest()) {
			QuestPlayerData playerData = QuestManager.playerData.get(player.getUniqueId());
			Quest quest = playerData.getActiveQuest();
			player.sendMessage(quest.getName());
			if (quests.contains(quest.getName())) {
				if (!quest.isStarted()) {
					String s = quest.getNextDialog();
					if (s != null) {
						player.sendMessage(MessagesUtil.npcMessage(name, s));
					}
				} else {
					if (quest.canFinish()) {
						String s = quest.getNextDialog();
						if (s == null) {
							quest.finish();
							QuestManager.updateBook(player);
						} else {
							player.sendMessage(MessagesUtil.npcMessage(name, s));
						}
					} else {
						player.sendMessage(MessagesUtil.npcMessage(name, quest.getNotDone()));
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
				if (index >= strings.length) index = 0;
				player.sendMessage(MessagesUtil.npcMessage(name, strings[index]));
			}
		}
	}
}
