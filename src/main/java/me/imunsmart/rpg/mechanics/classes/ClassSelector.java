package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.InventoryUtility;
import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClassSelector implements Listener {
	
	private Main main;
	
	public ClassSelector(Main main) {
		this.main = main;
	}
	
	public static Inventory get(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36,MessagesUtil.classMenuTitle);
		
		inv = InventoryUtility.setBackground(inv, new CustomItem(Material.STAINED_GLASS_PANE).setDurability(7).setName("&8&lChoose a class").addGlow());
		
		inv.setItem(11, new CustomItem(Material.DIAMOND_SWORD).addGlow().setName("&4&lWarrior").setLore("&2The warriors are skilled in warface in warfare.", "&2They are great in athletics and have great melee strength."));
		
		inv.setItem(13, new CustomItem(Material.FIREBALL).addGlow().setName("&Wizard").setLore("&1The wizard has an amazing skill called Magic.  Magic is their weapon, way of life, and way of survival.", "&1They are very skilled in the Art of magic."));
		
		inv.setItem(15, new CustomItem(Material.BOW).addGlow().setName("&f&lArcher").setLore("&fArcher is an Art, the Art of range.", "&fThe archers are skilled in the art of archery and ranged weapons."));
		if (!p.hasPermission("classes.donator")) {
			for (int i = 19; i < 26; i++) {
				inv.setItem(i, new CustomItem(Material.BEACON).setName("&aVIP Classes").addGlow().setLore("&ePurchase a rank on the store to have access to the &aVIP &eClasses!"));
			}
		}
		
		return inv;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getAction() != InventoryAction.NOTHING) {
			if (e.getInventory().getName().equals(get((Player) e.getWhoClicked()).getName())) {
				e.setCancelled(true);
				
				if (e.getSlot() == 15) {
					e.getWhoClicked().closeInventory();
					Stats.setClassType((OfflinePlayer) e.getWhoClicked(), Class.ARCHER);
					return;
				}
				if (e.getSlot() == 13) {
					e.getWhoClicked().closeInventory();
					Stats.setClassType((OfflinePlayer) e.getWhoClicked(), Class.WIZARD);
					return;
				}
				if (e.getSlot() == 11) {
					e.getWhoClicked().closeInventory();
					Stats.setClassType((OfflinePlayer) e.getWhoClicked(),Class.WARRIOR);
				}
			}
		}
	}
	
}
