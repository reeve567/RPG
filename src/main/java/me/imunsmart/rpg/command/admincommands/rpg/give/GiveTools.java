package me.imunsmart.rpg.command.admincommands.rpg.give;

import me.imunsmart.rpg.mechanics.AdminTools;
import org.bukkit.entity.Player;

public class GiveTools {
	
	public static void run(Player p) {
		p.getInventory().addItem(AdminTools.lootChest);
		p.getInventory().addItem(AdminTools.gemSpawner);
		p.getInventory().addItem(AdminTools.mobSpawner);
		p.getInventory().addItem(AdminTools.spawnerAmountTool);
		p.getInventory().addItem(AdminTools.spawnerTypeTool[0]);
	}
}
