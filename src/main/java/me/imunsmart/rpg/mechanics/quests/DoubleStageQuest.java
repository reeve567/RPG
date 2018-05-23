package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DoubleStageQuest extends Quest {
	
	private ArrayList<String> middleDialog;
	
	public DoubleStageQuest(String name, int start, int mid, int end, ItemStack[] rewards) {
		super(player, name, rewards, start, end);
		middleDialog = getList("mid", mid);
	}
}
