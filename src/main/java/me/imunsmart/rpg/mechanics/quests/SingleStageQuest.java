package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class SingleStageQuest extends Quest {
	
	public SingleStageQuest(Player player, String name, int start, int end, ItemStack[] rewards) {
		super(player,name, rewards, start, end);
	}
	
}
