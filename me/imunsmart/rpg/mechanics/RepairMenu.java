package me.imunsmart.rpg.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

public class RepairMenu implements Listener {
	private Main pl;

	public RepairMenu(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Repair");
		int gems = Stats.getInt(p, "gems");
		inv.setItem(4, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gems: " + gems, "Left click to withdraw.", "Shift-left to withdraw a note."));
		inv.setItem(8, Items.createItem(Material.CHEST, 1, 0, ChatColor.GOLD + "Storage", "Left click to open."));
		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (p.getOpenInventory().getTopInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank")) {
			e.setCancelled(true);
		}
	}
}
