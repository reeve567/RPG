package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.Main;
import org.bukkit.event.Listener;

public class ClassManager implements Listener {
	
	private Main main;
	
	public ClassManager(Main main) {
		this.main = main;
	}
	
//	public static Class getClass(Player p) {
//		return hasClass(p) ? (p.getScoreboardTags().contains("archer") ? Class.ARCHER : (p.getScoreboardTags().contains("warrior") ? Class.WARRIOR : Class.WIZARD)) : Class.NONE;
//	}
//	
//	public static boolean hasClass(Player p) {
//		return p.getScoreboardTags().contains("selected");
//	}
//	
//	@EventHandler
//	public void onClose(InventoryCloseEvent e) {
//		if (!ClassManager.hasClass((Player) e.getPlayer())) {
//			new BukkitRunnable() {
//				@Override
//				public void run() {
//					e.getPlayer().openInventory(ClassSelector.get((Player) e.getPlayer()));
//				}
//			}.runTaskLater(main, 30);
//		}
//	}
//	
//	@EventHandler
//	public void onJoin(PlayerJoinEvent e) {
//		Player player = e.getPlayer();
//		Class cl = Stats.getClass(player);
//		if (cl != null) {
//			player.addScoreboardTag("selected");
//			player.addScoreboardTag(cl.toString());
//		} else {
//			new BukkitRunnable() {
//				@Override
//				public void run() {
//					player.openInventory(ClassSelector.get(player));
//				}
//			}.runTaskLater(main, 20);
//		}
//	}
}
