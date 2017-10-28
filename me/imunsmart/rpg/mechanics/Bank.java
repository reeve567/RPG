package me.imunsmart.rpg.mechanics;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

public class Bank implements Listener {
	private Main pl;

	public Bank(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	public static void open(Player p) {
	}

	public static void openBank(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GREEN + p.getName() + "'s Bank");
		int gems = Stats.getStat(p, "gems");
		inv.setItem(4, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gems: " + gems, "Left click to withdraw.", "Shift-left to withdraw a note."));
		p.openInventory(inv);
	}

	public static void saveBank() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.EMPTY_MAP) {
					if (e.getItem().hasItemMeta())
						e.setCancelled(true);
				}
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				e.setCancelled(true);
				openBank(p);
			}
		}
	}

	HashMap<String, Boolean> withdraw = new HashMap<String, Boolean>();

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (p.getOpenInventory().getTopInventory().getTitle().contains("Bank")) {
			e.setCancelled(true);
			if (!e.getCurrentItem().hasItemMeta())
				return;
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Gems: ")) {
				p.sendMessage(ChatColor.GRAY + "Enter the amount you'd like to withdraw or move to cancel.");
				withdraw.put(p.getName(), (e.getClick() == ClickType.SHIFT_LEFT) ? true : false);
				p.closeInventory();
			}
			if (e.getClick() == ClickType.SHIFT_LEFT) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gem") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Bank Note")) {
					depositGems(p);
					p.closeInventory();
					openBank(p);
				}
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (withdraw.containsKey(p.getName())) {
			e.setCancelled(true);
			boolean wd = true;
			int gems = 0;
			try {
				gems = Integer.valueOf(e.getMessage());
			} catch (Exception e2) {
				wd = false;
			}
			int pg = Stats.getStat(p, "gems");
			if (gems > pg) {
				wd = false;
			}
			if (wd) {
				Stats.setStat(p, "gems", pg - gems);
				if (withdraw.get(p.getName())) {
					ItemStack i = Items.createItem(Material.EMPTY_MAP, 1, 0, ChatColor.AQUA + "Bank Note", "Value: " + gems);
					p.getInventory().addItem(i);
					p.sendMessage(ChatColor.GRAY + "Successfully withdrew bank note with worth of " + ChatColor.AQUA + gems + " gems.");
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
				} else {
					int stacks = gems / 64;
					gems -= stacks * 64;
					int space = stacks;
					if (gems > 0)
						space++;
					if (!hasSpace(p, space)) {
						p.sendMessage(ChatColor.RED + "You don't have space for that. Try emptying space or using a gem note.");
						return;
					}
					for (int i = 0; i < stacks; i++) {
						p.getInventory().addItem(Items.createGems(64));
					}
					p.getInventory().addItem(Items.createGems(gems));
					p.sendMessage(ChatColor.GRAY + "Withdrew a total of " + ChatColor.AQUA + (stacks * 64 + gems) + " gems.");
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
				}
				withdraw.remove(p.getName());
			} else {
				p.sendMessage(ChatColor.RED + "Failed to withdraw, enter a valid gem amount or you don't have enough gems.");
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (withdraw.containsKey(p.getName())) {
			if (e.getTo() != e.getFrom()) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling withdrawl.");
				withdraw.remove(p.getName());
			}
		}
	}

	public static int getGems(Player p) {
		int gems = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) {
				ItemStack it = p.getInventory().getItem(i);
				if (it.getType() == Material.DIAMOND) {
					gems += p.getInventory().getItem(i).getAmount();
				} else if (it.getType() == Material.EMPTY_MAP) {
					if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
						gems += Integer.valueOf(ChatColor.stripColor(it.getItemMeta().getLore().get(0).split(" ")[1].trim()));
					}
				}
			}
		}
		return gems;
	}

	public static void pay(Player p, int gems) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (gems == 0)
				break;
			if (p.getInventory().getItem(i) != null) {
				ItemStack it = p.getInventory().getItem(i);
				if (it.getType() == Material.DIAMOND) {
					if (gems > it.getAmount()) {
						gems -= it.getAmount();
						p.getInventory().remove(it);
					} else {
						it.setAmount(it.getAmount() - gems);
						gems = 0;
					}
				} else if (it.getType() == Material.EMPTY_MAP) {
					if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
						int value = Integer.valueOf(ChatColor.stripColor(it.getItemMeta().getLore().get(0).split(" ")[1].trim()));
						if (value > gems) {
							ItemMeta im = it.getItemMeta();
							im.setLore(Arrays.asList(ChatColor.GRAY + "Value: " + (value - gems)));
							it.setItemMeta(im);
							p.updateInventory();
							gems = 0;
						} else {
							gems -= value;
							p.getInventory().remove(it);
						}
					}
				}
			}
		}
		p.updateInventory();
	}

	public static void depositGems(Player p) {
		int gems = getGems(p);
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) {
				ItemStack it = p.getInventory().getItem(i);
				if (it.getType() == Material.DIAMOND) {
					p.getInventory().remove(it);
				} else if (it.getType() == Material.EMPTY_MAP) {
					if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
						p.getInventory().remove(it);
					}
				}
			}
		}
		p.sendMessage(ChatColor.GRAY + "Deposited " + ChatColor.AQUA + gems + ChatColor.GRAY + " gems.");
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
		p.updateInventory();
		Stats.addStat(p, "gems", gems);
	}

	public static boolean hasSpace(Player p, int slots) {
		int free = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) == null)
				free++;
		}
		return free >= slots;
	}
}
