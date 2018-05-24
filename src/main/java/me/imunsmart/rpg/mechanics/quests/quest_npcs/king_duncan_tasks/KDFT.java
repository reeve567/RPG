package me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.FirstTask;
import me.imunsmart.rpg.mechanics.quests.KillCounterTask;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KDFT extends KillCounterTask implements FirstTask {
	
	public KDFT(Player player) {
		super(player, "KDFT", new ItemStack[]{Items.createGems(30)}, 7, 2);
	}
	
	public KDFT(Player player, String progress) {
		this(player);
		kills = Integer.parseInt(progress);
	}
}
