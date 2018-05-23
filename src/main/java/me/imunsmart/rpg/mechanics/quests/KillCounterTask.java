package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class KillCounterTask extends Quest implements Listener {

	protected int kills = 0;
	
	protected KillCounterTask(Player player, String name, ItemStack[] rewards, int start, int end) {
		super(player, name, rewards, start, end);
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			if (e.getEntity().getKiller() instanceof Player) {
				if (e.getEntity().getScoreboardTags().contains("monster")) {
					kills++;
				}
			}
		}
	}

}
