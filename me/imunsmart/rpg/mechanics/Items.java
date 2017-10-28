package me.imunsmart.rpg.mechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Items {
	
	public static final ItemStack gem = createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gem");

	public static ItemStack createItem(Material m, int amount, int durability, String name, String... lore) {
		ItemStack i = new ItemStack(m, amount, (short) durability);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> s = new ArrayList<String>();
		for (String l : lore) {
			s.add(ChatColor.GRAY + l);
		}
		im.setLore(s);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack createItem(Material m, int amount, int durability, String name, List<String> lore) {
		ItemStack i = new ItemStack(m, amount, (short) durability);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> s = new ArrayList<String>();
		for (String l : lore) {
			s.add(ChatColor.RED + l);
		}
		im.setLore(s);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.setItemMeta(im);
		return i;
	}

	private static String[] weapons = { "WOOD", "STONE", "IRON", "DIAMOND", "GOLD" };
	private static String[] swords = { "Shortsword", "Longsword", "Greatsword", "Magical Sword", "Godly Sword" };
	private static String[] axes = { "Hatchet", "Tomohawk", "Great Axe", "Magical Axe", "Godly Axe" };

	public static ItemStack createWeapon(String type, int tier, int min, int max, String flags) {
		Material m = Material.getMaterial(weapons[tier - 1] + "_" + type.toUpperCase());
		String[] names = type.equals("sword") ? swords : axes;
		String[] flag = flags.split(",");
		List<String> lore = new ArrayList<String>();
		lore.add("Damage: " + min + "-" + max);
		if (flags.length() > 0) {
			for (String s : flag) {
				if (s.contains(":"))
					lore.add(s.split(":")[0] + ": " + s.split(":")[1]);
				else
					lore.add(s);
			}
		}
		return createItem(m, 1, 0, ChatColor.WHITE + names[tier - 1], lore);
	}

	public static void convertToScraps(Player p) {
		ItemStack i = p.getInventory().getItemInMainHand();
		String name = i.getType().name();
		int amount = 2;
		if (name.contains("CHESTPLATE")) {
			amount = 4;
		}
		if (name.contains("LEGGINGS") || name.contains("AXE") || name.contains("SWORD")) {
			amount = 3;
		}
		int data = 14;
		if(name.contains("CHAINMAIL") || name.contains("STONE"))
			data = 8;
		else if(name.contains("IRON"))
			data = 7;
		else if(name.contains("DIAMOND"))
			data = 12;
		else if(name.contains("GOLD"))
			data = 11;
		p.getInventory().addItem(createItem(Material.INK_SACK, amount, data, ChatColor.WHITE + "Armor Scrap", "Drag a stack onto armor", "to repair it."));
		p.getInventory().setItemInMainHand(null);
		p.updateInventory();
	}

	private static String[] armor = { "LEATHER", "CHAINMAIL", "IRON", "DIAMOND", "GOLD" };
	private static String[] armors = { "Old", "Rusted", "Great", "Magical", "Godly" };

	public static ItemStack createArmor(String type, int tier, int maxhp, String flags) {
		Material m = Material.getMaterial(armor[tier - 1] + "_" + type.toUpperCase());
		String[] flag = flags.split(",");
		List<String> lore = new ArrayList<String>();
		if (maxhp < 1)
			maxhp++;
		lore.add("Health: " + maxhp);
		if (flags.length() > 0) {
			for (String s : flag) {
				if (s.contains(":"))
					lore.add(s.split(":")[0] + ": " + s.split(":")[1]);
				else
					lore.add(s);
			}
		}
		return createItem(m, 1, 0, ChatColor.WHITE + armors[tier - 1] + " " + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase(), lore);
	}

	public static ItemStack randomEnchant(ItemStack i) {
		for (Enchantment e : Enchantment.values()) {
			if (e.canEnchantItem(i)) {
				if (Math.random() < 0.25) {
					i.addEnchantment(e, (int) (1 + Math.random() * (e.getMaxLevel() - 1)));
				}
			}
		}
		return i;
	}

	public static ItemStack randomDurability(ItemStack i) {
		int max = i.getType().getMaxDurability() - 1;
		i.setDurability((short) ((max / 4) + Math.random() * (max - (max / 4))));
		return i;
	}

	public static void useItem(Player p) {
		PlayerInventory pi = p.getInventory();
		if (p.getGameMode() != GameMode.CREATIVE) {
			if (pi.getItemInMainHand().getAmount() > 1) {
				pi.getItemInMainHand().setAmount(pi.getItemInMainHand().getAmount() - 1);
			} else {
				pi.setItemInMainHand(null);
			}
			p.updateInventory();
		}
	}
	
	public static int getTier(ItemStack i) {
		String name = i.getType().name();
		if(name.contains("CHAINMAIL") || name.contains("STONE"))
			return 2;
		else if(name.contains("IRON"))
			return 3;
		else if(name.contains("DIAMOND"))
			return 4;
		else if(name.contains("GOLD"))
			return 5;
		return 1;
	}
	
	public static ItemStack createGems(int amount) {
		return createItem(Material.DIAMOND, amount, 0, ChatColor.AQUA + "Gem");
	}
}
