package me.imunsmart.rpg.command.admincommands.rpg.give;

import me.imunsmart.rpg.mechanics.AdminTools;
import org.bukkit.entity.Player;

public class GiveTools {
	
	public static void run(Player p) {
		p.getInventory().addItem(AdminTools.lootChestTools[0]);
		p.getInventory().addItem(AdminTools.gemSpawnerTools[0]);
		p.getInventory().addItem(AdminTools.mobSpawnerTools[0]);
		p.getInventory().addItem(AdminTools.spawnerAmountTool);
	}
}
