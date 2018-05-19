package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RepairMenu implements Listener {
	private Main pl;
	
	public RepairMenu(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Repair");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.GRAY + " "));
		}
		inv.setItem(10, null);
		inv.setItem(12, null);
		inv.setItem(16, null);
		inv.setItem(19, Items.createItem(Material.STAINED_GLASS_PANE, 1, 13, ChatColor.GRAY + "Item to Repair"));
		inv.setItem(21, Items.createItem(Material.STAINED_GLASS_PANE, 1, 14, ChatColor.GRAY + "Scraps"));
		inv.setItem(25, Items.createItem(Material.STAINED_GLASS_PANE, 1, 3, ChatColor.GRAY + "Result"));
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		Inventory top = p.getOpenInventory().getTopInventory();
		if (top.getName().equals(ChatColor.DARK_AQUA + "Repair")) {
			if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
				e.setCancelled(true);
			if (e.getCurrentItem().hasItemMeta()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Repair")) {
					e.setCancelled(true);
					if (top.getItem(10) != null) {
						int dur = top.getItem(10).getDurability();
						ItemStack scraps = top.getItem(12);
						double rp = 0.01;
						if (scraps != null) {
							short data = scraps.getDurability();
							if (data == 8)
								rp = 0.03;
							if (data == 7)
								rp = 0.05;
							if (data == 12)
								rp = 0.08;
							if (data == 11)
								rp = 0.14;
							rp *= scraps.getAmount();
							if (scraps.getType() != Material.INK_SACK)
								rp = 0;
						}
						if (dur == 0) {
							p.sendMessage(ChatColor.RED + "That item cannot be repaired.");
							Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
							return;
						}
						p.sendMessage(ChatColor.GREEN + "Repaired.");
						Sounds.play(p, Sound.BLOCK_ANVIL_USE, 0.67f);
						ItemStack i = top.getItem(10);
						i.setDurability((short) (dur - (int) (rp * top.getItem(10).getType().getMaxDurability())));
						p.getInventory().addItem(i);
						e.getInventory().clear();
						p.closeInventory();
					}
				}
			}
			new BukkitRunnable() {
				public void run() {
					if (top.getItem(10) != null) {
						int dur = top.getItem(10).getDurability();
						ItemStack scraps = top.getItem(12);
						double rp = 0.01;
						if (scraps != null && scraps.getType() == Material.INK_SACK) {
							short data = scraps.getDurability();
							if (data == 8)
								rp = 0.03;
							if (data == 7)
								rp = 0.05;
							if (data == 12)
								rp = 0.08;
							if (data == 11)
								rp = 0.14;
							rp *= scraps.getAmount();
						} else
							rp = 0;
						top.setItem(16, Items.createItem(top.getItem(10).getType(), 1, (short) (dur - (int) (rp * top.getItem(10).getType().getMaxDurability())), ChatColor.GREEN + "Repair", "Click to confirm."));
					} else {
						top.setItem(16, null);
					}
					p.updateInventory();
				}
			}.runTaskLater(pl, 2);
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		Inventory top = p.getOpenInventory().getTopInventory();
		if (top.getName().equals(ChatColor.DARK_AQUA + "Repair") && e.getInventory().firstEmpty() != 0) {
			p.sendMessage(ChatColor.RED + "Cancelling repair.");
			if (top.getItem(10) != null) {
				p.getInventory().addItem(top.getItem(10));
			}
			if (top.getItem(12) != null) {
				p.getInventory().addItem(top.getItem(12));
			}
		}
	}
}
