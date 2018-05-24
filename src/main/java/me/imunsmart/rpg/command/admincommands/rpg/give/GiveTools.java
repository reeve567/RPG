package me.imunsmart.rpg.command.admincommands.rpg.give;

import me.imunsmart.rpg.mechanics.AdminTools;
import org.bukkit.entity.Player;

public class GiveTools {
	
	public static void run(Player p) {
		p.getInventory().setItem(0, AdminTools.delete);
		p.getInventory().setItem(1, AdminTools.lootChest);
		p.getInventory().setItem(2, AdminTools.gemSpawner);
		p.getInventory().setItem(3, AdminTools.mobSpawner);
		p.getInventory().setItem(4, AdminTools.spawnerAmountTool);
		p.getInventory().setItem(5, AdminTools.spawnerTypeTool[0]);
	}
}
