package me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.SingleStageQuest;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KDFirstTask extends SingleStageQuest {
	
	public KDFirstTask(Player player) {
		super(player, "KDFirstTask", 7, 2, new ItemStack[]{Items.createGems(30)});
	}
	
	public KDFirstTask(Player player, int amountKilled) {
		this(player);
		KillTracker.killMap.put(player.getUniqueId(), amountKilled);
	}
	
}
