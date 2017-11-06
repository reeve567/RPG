package me.imunsmart.rpg.command.AdminCommands.InventoryManagement;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearInventory {
	
	public static void run(Player p) {
		for (ItemStack it : p.getInventory()) {
			it.setType(Material.AIR);
		}
		p.sendMessage(ChatColor.GRAY + "Your inventory has been " + ChatColor.AQUA + "cleared" + ChatColor.GRAY + ".");
	}
	
}
