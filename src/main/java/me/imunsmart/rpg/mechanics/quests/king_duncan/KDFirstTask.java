package me.imunsmart.rpg.mechanics.quests.king_duncan;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.SingleStageQuest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KDFirstTask extends SingleStageQuest implements Listener {
	
	private Player player;
	private int amountKilled;
	
	public KDFirstTask(Player player) {
		super("KDFirstTask", 7, 2, new ItemStack[]{Items.createGems(30)});
		this.player = player;
	}
	
	public KDFirstTask(Player player, int amountKilled) {
		this(player);
		this.amountKilled = amountKilled;
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			if (e.getEntity().getKiller() instanceof Player) {
				if (e.getEntity().getKiller().equals(player) && e.getEntity().getScoreboardTags().contains("monster")) {
					amountKilled++;
				}
			}
		}
	}
}
