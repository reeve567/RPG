package me.imunsmart.rpg.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtility {
	
	public static Inventory setBackground(Inventory inventory, ItemStack stack) {
		for (int i = 0; i < inventory.getSize(); i++)
			inventory.setItem(i, stack);
		return inventory;
	}
	
}
