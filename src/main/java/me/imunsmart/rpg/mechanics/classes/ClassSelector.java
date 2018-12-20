package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.Main;
import org.bukkit.event.Listener;

public class ClassSelector implements Listener {
	
//	public static Inventory get(Player p) {
//		Inventory inv = Bukkit.createInventory(null, 36, MessagesUtil.classMenuTitle);
//		inv = InventoryUtility.setBackground(inv, new CustomItem(Material.STAINED_GLASS_PANE).setDurability(7).setName("&8&lChoose a class").addGlow());
//		inv.setItem(11, new CustomItem(Material.DIAMOND_SWORD).hideAttributes().addGlow().setName("&4&lWarrior").setLore("&4The warriors are skilled in warface in warfare.", "&4They are great in athletics and have great melee strength."));
//		inv.setItem(13, new CustomItem(Material.FIREBALL).addGlow().setName("&1Wizard").setLore("&1The wizard has an amazing skill called Magic.",  "&1Magic is their weapon, way of life, and way of survival.", "&1They are very skilled in the Art of magic."));
//		inv.setItem(15, new CustomItem(Material.BOW).addGlow().setName("&f&lArcher").setLore("&fArcher is an Art, the Art of range.", "&fThe archers are skilled in the art of archery and ranged weapons."));
//		if (!p.hasPermission("classes.donator"))
//			for (int i = 19; i < 26; i++) inv.setItem(i, new CustomItem(Material.BEACON).setName("&aVIP Classes").addGlow().setLore("&ePurchase a rank on the store to have access to the &aVIP &eClasses!"));
//		
//		return inv;
//	}
//	
//	@EventHandler
//	public void onClick(InventoryClickEvent e) {
//		if (e.getAction() != InventoryAction.NOTHING && e.getInventory().getName().equals(MessagesUtil.classMenuTitle)) {
//			e.setCancelled(true);
//			if (e.getSlot() == 15) {
//				new TitleMessage("Archer Class").sendToAll();
//				e.getWhoClicked().closeInventory();
//				Stats.setClassType((OfflinePlayer) e.getWhoClicked(), Class.ARCHER);
//				e.getWhoClicked().addScoreboardTag("selected");
//				e.getWhoClicked().addScoreboardTag(Class.ARCHER.toString());
//				return;
//			}
//			if (e.getSlot() == 13) {
//				new TitleMessage("Wizard Class").sendToAll();
//				e.getWhoClicked().closeInventory();
//				Stats.setClassType((OfflinePlayer) e.getWhoClicked(), Class.WIZARD);
//				e.getWhoClicked().addScoreboardTag("selected");
//				e.getWhoClicked().addScoreboardTag(Class.WIZARD.toString());
//				return;
//			}
//			if (e.getSlot() == 11) {
//				new TitleMessage("Warrior Class").sendToAll();
//				e.getWhoClicked().closeInventory();
//				Stats.setClassType((OfflinePlayer) e.getWhoClicked(), Class.WARRIOR);
//				e.getWhoClicked().addScoreboardTag("selected");
//				e.getWhoClicked().addScoreboardTag(Class.WARRIOR.toString());
//			}
//		}
//	}
	
}
