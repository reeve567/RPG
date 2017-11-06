package me.imunsmart.rpg.mechanics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GREEN + p.getName() + "'s Bank");
		int gems = Stats.getInt(p, "gems");
		inv.setItem(4, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gems: " + gems, "Left click to withdraw.", "Shift-left to withdraw a note.", "Shift-click gems in your inventory", "to deposit them."));
		inv.setItem(8, Items.createItem(Material.CHEST, 1, 0, ChatColor.GOLD + "Storage", "Left click to open."));
		p.openInventory(inv);
	}

	public static void openBank(Player p) {
		int size = Stats.getInt(p, "bank.size", 1);
		List<String> storage = Stats.getList(p, "bank.storage");
		int op = size * 9 + 9;
		if(size == 6)
			op = 54;
		Inventory inv = Bukkit.createInventory(null, op, ChatColor.GREEN + p.getName() + "'s Bank Storage");
		for (int i = 0; i < storage.size(); i++) {
			System.out.println(i);
			inv.setItem(i, Items.deserialize(storage.get(i)));
		}
		if (size < 6) {
			for (int i = 9; i > 0; i--) {
				inv.setItem(inv.getSize() - i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 13, ChatColor.GREEN + "Upgrade", "Click to upgrade your bank."));
			}
		}
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
				open(p);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank Storage")) {
			List<String> items = new ArrayList<String>();
			for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
				items.add(Items.serialize(e.getInventory().getItem(i)));
			}
			Stats.setStat(p, "bank.storage", items);
		}
	}

	HashMap<String, Boolean> withdraw = new HashMap<String, Boolean>();

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (p.getOpenInventory().getTopInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank")) {
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
					open(p);
				}
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Storage")) {
				if (e.getClick() == ClickType.LEFT) {
					openBank(p);
				}
			}
		}
		if (p.getOpenInventory().getTopInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank Storage")) {
			if (!e.getCurrentItem().hasItemMeta())
				return;
			if (e.getClick() == ClickType.SHIFT_LEFT) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gem") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Bank Note")) {
					e.setCancelled(true);
					depositGems(p);
					p.closeInventory();
					open(p);
				}
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Upgrade")) {
				e.setCancelled(true);
				p.closeInventory();
				int gems = Stats.getInt(p, "gems");
				int size = Stats.getInt(p, "bank.size", 1);
				System.out.println(size);
				int cost = upgradeCosts[size - 1];
				if (gems >= cost) {
					upgrade.add(p.getName());
					p.sendMessage(ChatColor.GRAY + "A bank upgrade will cost " + ChatColor.AQUA + cost + ChatColor.GRAY + " gems. Type 'confirm' to upgrade.");
				} else {
					p.sendMessage(ChatColor.RED + "Insufficient gem balance. You need " + ChatColor.BOLD + cost + ChatColor.RED + " for that. Try depositing more gems.");
				}
			}
		}
	}

	List<String> upgrade = new ArrayList<String>();

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
			int pg = Stats.getInt(p, "gems");
			if (gems > pg) {
				wd = false;
			}
			if (wd) {
				if (withdraw.get(p.getName())) {
					ItemStack i = Items.createGemNote(gems);
					p.getInventory().addItem(i);
					p.sendMessage(ChatColor.GRAY + "Successfully withdrew bank note with worth of " + ChatColor.AQUA + gems + " gems.");
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
					Stats.setStat(p, "gems", pg - gems);
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
					Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
					Stats.setStat(p, "gems", pg - gems);
				}
				withdraw.remove(p.getName());
			} else {
				p.sendMessage(ChatColor.RED + "Failed to withdraw, enter a valid gem amount or you don't have enough gems.");
				withdraw.remove(p.getName());
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
			}
		}
		if (upgrade.contains(p.getName())) {
			e.setCancelled(true);
			upgrade.remove(p.getName());
			if (e.getMessage().contains("confirm")) {
				int gems = Stats.getInt(p, "gems");
				int size = Stats.getInt(p, "bank.size", 1);
				int cost = upgradeCosts[size - 1];
				Stats.setStat(p, "gems", gems - cost);
				Stats.setStat(p, "bank.size", size + 1);
				p.sendMessage(ChatColor.GRAY + "Successfully upgrade bank by " + ChatColor.AQUA + "9" + ChatColor.GRAY + " slots.");
				Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else {
				p.sendMessage(ChatColor.RED + "Upgrade cancelled.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getTo().distanceSquared(e.getFrom()) > 1) {
			if (withdraw.containsKey(p.getName())) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling withdrawl.");
				withdraw.remove(p.getName());
			}
			if (upgrade.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling upgrade.");
				upgrade.remove(p.getName());
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

	private int[] upgradeCosts = { 200, 500, 1000, 2500, 5000 };
}
