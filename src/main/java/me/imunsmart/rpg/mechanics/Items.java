package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.util.StringUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Items {

	public static final ItemStack gem = createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gem");
	public static ChatColor[] nameColor = { ChatColor.GOLD, ChatColor.GRAY, ChatColor.WHITE, ChatColor.AQUA, ChatColor.YELLOW };
	private static String[] weapons = { "WOOD", "STONE", "IRON", "DIAMOND", "GOLD" };
	private static String[] swords = { "Shortsword", "Longsword", "Greatsword", "Mystic Sword", "Godly Sword" };
	private static String[] axes = { "Hatchet", "Tomohawk", "Great Axe", "Mystic Axe", "Godly Axe" };
	private static String[] armor = { "LEATHER", "CHAINMAIL", "IRON", "DIAMOND", "GOLD" };
	private static String[] armors = { "Old", "Rusted", "Great", "Mystic", "Godly" };

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
		int tier = 1;
		if (name.contains("CHAINMAIL") || name.contains("STONE"))
			tier = 2;
		else if (name.contains("IRON"))
			tier = 3;
		else if (name.contains("DIAMOND"))
			tier = 4;
		else if (name.contains("GOLD"))
			tier = 5;
		p.getInventory().addItem(createScraps(amount, tier));
		p.getInventory().setItemInMainHand(null);
		p.updateInventory();
	}

	public static ItemStack createArmor(String type, int tier, int maxhp, String flags) {
		Material m = Material.getMaterial(armor[tier - 1] + "_" + type.toUpperCase());
		String[] flag = flags.split(",");
		String name = ChatColor.WHITE + armors[tier - 1] + " " + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
		List<String> lore = new ArrayList<String>();
		if (maxhp < 1)
			maxhp++;
		lore.add("Health: +" + maxhp);
		name = getString(flags, name, flag, lore);
		return createItem(m, 1, 0, name, lore);
	}

	public static ItemStack createGemNote(int amount) {
		return Items.createItem(Material.EMPTY_MAP, 1, 0, ChatColor.AQUA + "Bank Note", "Value: " + amount);
	}

	public static ItemStack createGems(int amount) {
		return createItem(Material.DIAMOND, amount, 0, ChatColor.AQUA + "Gem");
	}

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

	public static ItemStack createPotion(int tier) {
		ItemStack i = Items.createItem(Material.POTION, 1, 0, Items.nameColor[tier - 1] + Potions.names[tier - 1] + " Potion of Healing", Arrays.asList("Restores: " + Potions.amounts[tier - 1]));
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setColor(Potions.colors[tier - 1]);
		i.setItemMeta(pm);
		return i;
	}

	public static ItemStack createScraps(int amount, int tier) {
		int data = 14;
		if (tier == 2)
			data = 8;
		else if (tier == 3)
			data = 7;
		else if (tier == 4)
			data = 12;
		else if (tier == 5)
			data = 11;
		return createItem(Material.INK_SACK, amount, data, nameColor[tier - 1] + "Scrap", "Drag a stack onto armor", "to repair it.");
	}

	public static ItemStack createWeapon(String type, int tier, int min, int max, String flags) {
		Material m = Material.getMaterial(weapons[tier - 1] + "_" + type.toUpperCase());
		String[] names = type.equals("sword") ? swords : axes;
		String name = ChatColor.WHITE + names[tier - 1];
		String[] flag = flags.split(",");
		List<String> lore = new ArrayList<String>();
		lore.add("Damage: " + min + "-" + max);
		name = getString(flags, name, flag, lore);
		return createItem(m, 1, 0, name, lore);
	}

	private static String getString(String flags, String name, String[] flag, List<String> lore) {
		if (flags.length() > 0) {
			for (String s : flag) {
				if (s.contains(":")) {
					if (s.contains("name")) {
						name = StringUtility.colorConv(s.split(":")[1]);
					} else
						lore.add(s.split(":")[0] + ": " + s.split(":")[1]);
				} else {
					if (s.contains("uncommon")) {
						lore.add(" ");
						lore.add(ChatColor.YELLOW + "Uncommon");
					} else if (s.contains("rare")) {
						lore.add(" ");
						lore.add(ChatColor.DARK_PURPLE + "Rare");
					} else if (s.contains("exclusive")) {
						lore.add(" ");
						lore.add(ChatColor.AQUA.toString() + ChatColor.BOLD + "Exclusive");
					} else
						lore.add(s);
				}
			}
		}
		return name;
	}

	public static ItemStack deserialize(String s) {
		String[] tokens = s.split("@");
		Material m = Material.valueOf(tokens[1].substring(1));
		int amt = Integer.valueOf(tokens[2].substring(1));
		int dur = Integer.valueOf(tokens[3].substring(1));
		if (tokens.length > 4) {
			String name = ChatColor.translateAlternateColorCodes('&', tokens[4].substring(1));
			List<String> lore = new ArrayList<String>();
			if (tokens.length > 5)
				Collections.addAll(lore, tokens[5].substring(1).split(","));
			return Items.createItem(m, amt, dur, name, lore);
		} else
			return new ItemStack(m, amt, (short) dur);
	}

	public static int getTier(ItemStack i) {
		String name = i.getType().name();
		if (name.contains("CHAINMAIL") || name.contains("STONE"))
			return 2;
		else if (name.contains("IRON"))
			return 3;
		else if (name.contains("DIAMOND"))
			return 4;
		else if (name.contains("GOLD"))
			return 5;
		return 1;
	}

	public static ItemStack randomDurability(ItemStack i) {
		int max = i.getType().getMaxDurability() - 1;
		i.setDurability((short) ((max / 4) + Math.random() * (max - (max / 4))));
		return i;
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

	public static String serialize(ItemStack i) {
		if (i == null)
			return "@iAIR@a0@d-1";
		if (!i.hasItemMeta())
			return "@i" + i.getType().name() + "@a" + i.getAmount() + "@d" + i.getDurability();
		String lore = "";
		int x = 0;
		if (i.getItemMeta().hasLore()) {
			for (String s : i.getItemMeta().getLore()) {
				if (x != i.getItemMeta().getLore().size() - 1)
					lore += s + ",";
				else
					lore += s;
				x++;
			}
		}
		return "@i" + i.getType().name() + "@a" + i.getAmount() + "@d" + i.getDurability() + "@n" + i.getItemMeta().getDisplayName() + "@l" + lore;
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
}