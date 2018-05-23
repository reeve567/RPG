package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.inventory.ItemStack;

public class SingleStageQuest extends Quest {
	
	public SingleStageQuest(String name, int start, int end, ItemStack[] rewards) {
		super(name, rewards, start, end);
	}
	
}
