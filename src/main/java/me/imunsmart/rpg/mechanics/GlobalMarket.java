package me.imunsmart.rpg.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

public class GlobalMarket implements Listener {
	private Main pl;
	private static File f;
	private static FileConfiguration fc;
	private ArrayList<String> selling = new ArrayList<String>();
	private HashMap<String, ItemStack> sell = new HashMap<String, ItemStack>();

	public GlobalMarket(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		f = new File(pl.getDataFolder(), "market.yml");
		fc = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			try {
				f.createNewFile();
				fc.set("items", Arrays.asList("@iEMERALD_BLOCK@a1@d0@n&aBlock@lTest;ImUnsmart;100"));
				fc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void disable() {
		f = null;
		fc = null;
	}

	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Global Exchange");
		inv.setItem(12, Items.createItem(Material.EMERALD_BLOCK, 1, 0, ChatColor.GREEN + "Buy Items", "Click to purchase items", "on the market."));
		inv.setItem(14, Items.createItem(Material.DIAMOND_BLOCK, 1, 0, ChatColor.AQUA + "Sell Items", "Click to sell items", "on the market."));
		p.openInventory(inv);
	}

	public static void openBuy(Player p, int page) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Global Exchange - Buy Page:" + (page + 1) + "");
		inv.setItem(45, Items.createItem(Material.CARPET, 1, 14, ChatColor.RED + "<- Prev"));
		inv.setItem(53, Items.createItem(Material.CARPET, 1, 13, ChatColor.DARK_GREEN + "Next ->"));
		List<String> items = new ArrayList<>();
		for (int x = (page * 52); x < (page * 52) + 52; x++) {
			if(x >= items.size())
				break;
			String s = items.get(x);
			System.out.println(Arrays.toString(s.split("^")));
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
			inv.addItem(i);
		}
		p.openInventory(inv);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		Player p = (Player) e.getWhoClicked();
		if (!e.getCurrentItem().hasItemMeta())
			return;
		if (e.getInventory().getTitle().equals(ChatColor.DARK_GREEN + "Global Exchange")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Items")) {
				p.closeInventory();
				Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
				openBuy(p, 0);
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Items")) {
				selling.add(p.getName());
				p.sendMessage(ChatColor.GREEN + "Click an item in your inventory to sell it.");
				p.closeInventory();
			}
		} else if (e.getInventory().getTitle().contains("Global Exchange - Buy")) {
			e.setCancelled(true);
			int page = Integer.valueOf(e.getInventory().getTitle().split(":")[1]);
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("<- Prev")) {
				p.closeInventory();
				Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
				int pg = page - 2;
				if(pg < 0)
					pg = 0;
				openBuy(p, pg);
				return;
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Next ->")) {
				p.closeInventory();
				Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
				int pg = page;
				if(pg > 100)
					pg = 100;
				openBuy(p, pg);
				return;
			}
			int index = e.getSlot() + (54 * (page - 1));
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
				try {
					fc.save(f);
				} catch (IOException e1) {
					e1.printStackTrace();
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
				try {
					fc.save(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
					if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Gem")) {
						p.sendMessage(ChatColor.RED + "You cannot sell a Gem!");
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
			if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling sell.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				selling.remove(p.getName());
			}
		}
		if (sell.containsKey(p.getName())) {
			if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
				p.sendMessage(ChatColor.RED + "You moved, cancelling sell.");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				sell.remove(p.getName());
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (sell.containsKey(p.getName())) {
			e.setCancelled(true);
			int cost = -1;
			try {
				cost =  Integer.parseInt(e.getMessage());
			} catch (Exception e2) {
			}
			if(cost == -1) {
				p.sendMessage(ChatColor.GRAY + "Cost not entered. Cancelling...");
				Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
				return;
			}
			ItemStack i = sell.remove(p.getName());
			List<String> items = fc.getStringList("items");
			String s = Items.serialize(i) + ";" + p.getName() + ";" + cost;
			items.add(s);
			fc.set("items", items);
			try {
				fc.save(f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			p.getInventory().remove(i);
			p.sendMessage(ChatColor.GRAY + "Successfully placed item on market for " + ChatColor.AQUA + cost + " gems.");
			Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
		}
	}
}
