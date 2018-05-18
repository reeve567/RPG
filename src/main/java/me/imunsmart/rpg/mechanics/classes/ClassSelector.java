package me.imunsmart.rpg.mechanics.classes;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.util.Glow;
import me.imunsmart.rpg.util.ItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClassSelector implements Listener {
	
	private Main main;
	
	public ClassSelector(Main main) {
		this.main = main;
	}
	
	public static Inventory get(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36);
		
		for (int i = 0; i < 36; i++) {
			inv.setItem(i, ItemUtility.addGlow(ItemUtility.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7), "&8&lChoose a class", true)));
		}
		
		Glow glow = new Glow(999);
		
		inv.setItem(11, ItemUtility.addLore(ItemUtility.addLore(ItemUtility.setName(ItemUtility.addEnchant(new ItemStack(Material.DIAMOND_SWORD), glow, 1), "&4&lWarrior", true), "&2The warriors are skilled in warface in warfare.", true), "&2They are great in athletics and have great melee strength.", true));
		
		inv.setItem(13, ItemUtility.addLore(ItemUtility.addLore(ItemUtility.setName(ItemUtility.addEnchant(new ItemStack(Material.FIREBALL), glow, 1), "&1&lWarrior", true), "&1The wizard hols the amazing skill called Magic.  Magic is their weapon, way of life, and way of survival.", true), "&1They are very skilled in the Art of magic.", true));
		
		inv.setItem(15, ItemUtility.addLore(ItemUtility.addLore(ItemUtility.addEnchant(ItemUtility.setName(new ItemStack(Material.BOW), "&f&lArcher", true), glow, 1), "&fArcher is an Art, the Art of range.", true), "&fThe archers are skilled in the art of archery and ranged weapons.", true));
		
		if (!p.hasPermission("classes.donator")) {
			for (int i = 19; i < 26; i++) {
				inv.setItem(i, ItemUtility.addLore(ItemUtility.addEnchant(ItemUtility.setName(new ItemStack(Material.BEACON), "&aVIP Classes", true), glow, 1), "&ePurchase a rank on the store to have access to the &aVIP &eClasses!", true));
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
					Stats.setClassType((Player) e.getWhoClicked(), Class.ARCHER);
					return;
				}
				if (e.getSlot() == 13) {
					e.getWhoClicked().closeInventory();
					Stats.setClassType((Player) e.getWhoClicked(), Class.WIZARD);
					return;
				}
			}
		}
	}
	
}
