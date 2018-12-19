package me.imunsmart.rpg.mechanics.gui;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.PlayerSkull;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GlobalMarket implements Listener {
	private static Main pl;
	private static File f;
	private static FileConfiguration fc;
	private ArrayList<String> selling = new ArrayList<String>();
	private HashMap<String, ItemStack> sell = new HashMap<String, ItemStack>();
	private static HashMap<String, Integer> page = new HashMap<String, Integer>();
	private static int maxPages = 1;

	public GlobalMarket(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		f = new File(pl.getDataFolder(), "market.yml");
		fc = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			try {
				f.createNewFile();
				fc.set("items", null);
				fc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		maxPages = (int) Math.floor(fc.getStringList("items").size() / 45.0d) + 1;
	}

	public void disable() {
		f = null;
		fc = null;
	}

	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Global Exchange");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, Items.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, " "));
		}
		inv.setItem(4, new CustomItem(new PlayerSkull("0qt")).setName(ChatColor.DARK_GREEN + "Global Exchange"));
		inv.setItem(12, Items.createItem(Material.EMERALD_BLOCK, 1, 0, ChatColor.GREEN + "Buy Items", "Click to purchase items", "on the market."));
		inv.setItem(14, Items.createItem(Material.DIAMOND_BLOCK, 1, 0, ChatColor.AQUA + "Sell Items", "Click to sell items", "on the market."));
		p.openInventory(inv);
		if (page.containsKey(p.getName()))
			page.remove(p.getName());
	}

	public static void openBuy(Player p, Inventory inv) {
		if (inv == null) {
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Global Exchange - Buy");
			GlobalMarket.page.put(p.getName(), 0);
		} else {
			inv.clear();
		}
		int page = GlobalMarket.page.get(p.getName());
		for (int i = 0; i < 9; i++) {
			inv.setItem(i + 45, Items.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, " "));
		}
		inv.setItem(45, Items.createItem(Material.RED_CARPET, 1, 0, ChatColor.RED + "<- Prev"));
		inv.setItem(49, Items.createItem(Material.COMPASS, 1, 0, ChatColor.GRAY + "Page: " + (page + 1) + " / " + maxPages, "Click to return to page 1."));
		inv.setItem(53, Items.createItem(Material.GREEN_CARPET, 1, 0, ChatColor.DARK_GREEN + "Next ->"));
		File f = new File(pl.getDataFolder(), "market.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		List<String> items = fc.getStringList("items");
		for (int x = (page * 45); x < (page * 45) + 45; x++) {
			if (x >= items.size())
				break;
			String s = items.get(x);
			ItemStack i = Items.deserialize(s.split(";")[0]);
			String seller = s.split(";")[1];
			int cost = Integer.valueOf(s.split(";")[2]);
			ItemMeta im = i.getItemMeta();
			List<String> lore = im.getLore() != null ? im.getLore() : new ArrayList<>();
			lore.add(" ");
			lore.add(ChatColor.GRAY + "Seller: " + ChatColor.AQUA + seller);
			lore.add(ChatColor.GRAY + "Cost: " + ChatColor.AQUA + cost);
			im.setLore(lore);
			i.setItemMeta(im);
			inv.setItem(x - (page * 45), i);
		}
		p.openInventory(inv);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equals(ChatColor.DARK_GREEN + "Global Exchange")) {
			if (!e.getCurrentItem().hasItemMeta())
				return;
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Items")) {
				p.closeInventory();
				Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
				openBuy(p, null);
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Items")) {
				selling.add(p.getName());
				p.sendMessage(ChatColor.GREEN + "Click an item in your inventory to sell it.");
				p.closeInventory();
			}
		} else if (e.getInventory().getTitle().contains("Global Exchange - Buy")) {
			e.setCancelled(true);
			int pg = page.containsKey(p.getName()) ? page.get(p.getName()) : 0;
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("<- Prev")) {
					Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
					pg--;
					if (pg <= 0)
						pg = 0;
					page.put(p.getName(), pg);
					openBuy(p, e.getInventory());
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Page: " + (pg + 1))) {
					Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
					page.put(p.getName(), 0);
					openBuy(p, e.getInventory());
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Next ->")) {
					Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
					pg++;
					if (pg > maxPages - 1)
						pg = maxPages - 1;
					page.put(p.getName(), pg);
					openBuy(p, e.getInventory());
					return;
				}
			}
			if (!e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasLore())
				return;
			int index = e.getSlot() + (45 * pg);
			ItemStack i = e.getCurrentItem();
			ItemMeta im = i.getItemMeta();
			List<String> lore = im.getLore();
			int cost = Integer.valueOf(ChatColor.stripColor(lore.get(lore.size() - 1).split(" ")[1]));
			String seller = ChatColor.stripColor(lore.get(lore.size() - 2).split(" ")[1]);
			List<String> items = fc.getStringList("items");
			if (!Bank.hasSpace(p, 1)) {
				p.sendMessage(ChatColor.RED + "You don't have space in your inventory to buy that.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				return;
			}
			if (p.getName().equals(seller)) {
				Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
				e.getInventory().setItem(e.getSlot(), null);
				lore.remove(lore.size() - 3);
				lore.remove(lore.size() - 2);
				lore.remove(lore.size() - 1);
				im.setLore(lore);
				i.setItemMeta(im);
				p.getInventory().addItem(i);
				p.sendMessage(ChatColor.GREEN + "Removed item from the market.");
				items.remove(index);
				fc.set("items", items);
				updateMarket();
				for (String s : page.keySet()) {
					Player pp = Bukkit.getPlayer(s);
					if (page.get(s) == pg)
						openBuy(pp, pp.getOpenInventory().getTopInventory());
				}
				return;
			}
			if (Bank.pay(p, cost)) {
				e.getInventory().setItem(e.getSlot(), null);
				lore.remove(lore.size() - 3);
				lore.remove(lore.size() - 2);
				lore.remove(lore.size() - 1);
				im.setLore(lore);
				i.setItemMeta(im);
				p.getInventory().addItem(i);
				Stats.addStat(Bukkit.getOfflinePlayer(seller), "gems", cost);
				items.remove(index);
				fc.set("items", items);
				updateMarket();
				p.sendMessage(ChatColor.GRAY + "You made a purchase for " + ChatColor.AQUA + cost + " gems.");
				Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
				for (String s : page.keySet()) {
					Player pp = Bukkit.getPlayer(s);
					if (page.get(s) == pg)
						openBuy(pp, pp.getOpenInventory().getTopInventory());
				}
				return;
			} else {
				p.sendMessage(ChatColor.RED + "You cannot afford that.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
			}
		}
	}

	@EventHandler
	public void onClickPlayerInv(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (selling.contains(p.getName())) {
			if (e.getClickedInventory() == p.getInventory()) {
				e.setCancelled(true);
				selling.remove(p.getName());
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Gem") || e.getCurrentItem().getItemMeta().getDisplayName().contains("Bank Note")) {
						p.sendMessage(ChatColor.RED + "You cannot sell Gems!");
						Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
						p.closeInventory();
						return;
					}
					if (e.getCurrentItem().containsEnchantment(Enchantment.VANISHING_CURSE)) {
						p.sendMessage(ChatColor.RED + "You cannot sell cursed items!");
						Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
						p.closeInventory();
						return;
					}
				}
				sell.put(p.getName(), e.getCurrentItem());
				p.sendMessage(ChatColor.GRAY + "Enter a cost for this item in chat or type 'cancel' to cancel.");
				p.closeInventory();
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (selling.contains(p.getName())) {
			if (Util.moved(e.getFrom(), e.getTo())) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling sell.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				selling.remove(p.getName());
			}
		}
		if (sell.containsKey(p.getName())) {
			if (Util.moved(e.getFrom(), e.getTo())) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling sell.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				sell.remove(p.getName());
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		new BukkitRunnable() {
			@Override
			public void run() {
				if (p.getOpenInventory() == null || p.getOpenInventory().getTopInventory() == null || !p.getOpenInventory().getTopInventory().getTitle().contains("Global Exchange - Buy")) {
					if (page.containsKey(p.getName()))
						page.remove(p.getName());
				}
			}
		}.runTaskLater(pl, 2);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (sell.containsKey(p.getName())) {
			e.setCancelled(true);
			int cost = -1;
			try {
				cost = Integer.parseInt(e.getMessage());
			} catch (Exception e2) {
			}
			if (cost == -1) {
				p.sendMessage(ChatColor.GRAY + "Cost not entered. Cancelling...");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				return;
			}
			ItemStack i = sell.remove(p.getName());
			List<String> items = fc.getStringList("items");
			String s = Items.serialize(i) + ";" + p.getName() + ";" + cost;
			items.add(s);
			fc.set("items", items);
			updateMarket();
			p.getInventory().remove(i);
			p.sendMessage(ChatColor.GRAY + "Successfully placed item on market for " + ChatColor.AQUA + cost + " gems.");
			Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
		}
	}

	private void updateMarket() {
		try {
			fc.save(f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		maxPages = fc.getStringList("items").size() / 45 + 1;
	}
}
