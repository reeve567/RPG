package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ClassManager implements Listener {
	
	private Main main;
	
	public ClassManager(Main main) {
		this.main = main;
	}
	
	public static Class getClass(Player p) {
		return hasClass(p) ? (p.getScoreboardTags().contains("archer") ? Class.ARCHER : (p.getScoreboardTags().contains("warrior") ? Class.WARRIOR : Class.WIZARD)) : Class.NONE;
	}
	
	public static boolean hasClass(Player p) {
		return p.getScoreboardTags().contains("selected");
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (!ClassManager.hasClass((Player) e.getPlayer())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					e.getPlayer().openInventory(ClassSelector.get((Player) e.getPlayer()));
				}
			}.runTaskLater(main, 10);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Class cl = Stats.getClass(player);
		if (cl != null) {
			player.addScoreboardTag("selected");
			player.addScoreboardTag(cl.toString());
		} else {
			new BukkitRunnable() {
				@Override
				public void run() {
					System.out.println("asdf");
					player.openInventory(ClassSelector.get(player));
				}
			}.runTaskLater(main, 10);
		}
	}
}
