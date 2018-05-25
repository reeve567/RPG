package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mobs.Constants;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.StringUtility;
import me.imunsmart.rpg.util.Util;
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
	public static ChatColor[] nameColor = {ChatColor.GOLD, ChatColor.GRAY, ChatColor.WHITE, ChatColor.AQUA, ChatColor.YELLOW};
	public static String[] tools = {"WOOD", "STONE", "IRON", "DIAMOND", "GOLD"};
	public static String[] picks = {"Beginner's", "Novice's", "Professional", "Masterful", "Godly"};
	private static String[] swords = {"Shortsword", "Longsword", "Greatsword", "Mystic Sword", "Godly Sword"};
	private static String[] axes = {"Hatchet", "Tomohawk", "Great Axe", "Mystic Axe", "Godly Axe"};
	private static String[] armor = {"LEATHER", "CHAINMAIL", "IRON", "DIAMOND", "GOLD"};
	private static String[] armors = {"Old", "Rusted", "Great", "Mystic", "Godly"};
	private static String[] types = {"axe", "sword", "helmet", "chestplate", "leggings", "boots"};
	
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
	
	public static CustomItem createQuestInfo() {
		return new CustomItem(Material.PAPER).setName("§aQuest Info").addGlow();
	}
	
	public static ItemStack createGemNote(int amount) {
		return Items.createItem(Material.EMPTY_MAP, 1, 0, ChatColor.AQUA + "Bank Note", "Value: " + amount);
	}
	
	public static ItemStack createGems(int amount) {
		return createItem(Material.DIAMOND, amount, 0, ChatColor.AQUA + "Gem");
	}
	
	public static ItemStack createPotion(int tier) {
		ItemStack i = Items.createItem(Material.POTION, 1, 0, Items.nameColor[tier - 1] + Potions.names[tier - 1] + " Potion of Healing", Arrays.asList("Restores: " + Potions.amounts[tier - 1]));
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setColor(Potions.colors[tier - 1]);
		i.setItemMeta(pm);
		return i;
	}
	
	public static ItemStack createItem(Material m, int amount, int durability, String name, List<String> lore) {
		ItemStack i = new ItemStack(m, amount, (short) durability);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> s = new ArrayList<>();
		for (String l : lore) {
			s.add(ChatColor.RED + l);
		}
		im.setLore(s);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createTeleportScroll(int amount, String location, int delay) {
		String name = ChatColor.GREEN + "Teleport: " + location;
		String lore = ChatColor.GRAY + "Teleport delay: " + ChatColor.YELLOW + delay + ChatColor.GRAY + " seconds";
		return Items.createItem(Material.BOOK, amount, 0, name, lore);
	}
	
	public static ItemStack deserialize(String s) {
		String[] tokens = s.split("@");
		Material m = Material.valueOf(tokens[1].substring(1));
		int amt = Integer.valueOf(tokens[2].substring(1));
		int dur = Integer.valueOf(tokens[3].substring(1));
		if (tokens.length > 4) {
			String name = ChatColor.translateAlternateColorCodes('&', tokens[4].substring(1));
			List<String> lore = new ArrayList<>();
			if (tokens.length > 5)
				Collections.addAll(lore, tokens[5].substring(1).split(","));
			return Items.createItem(m, amt, dur, name, lore);
		} else
			return new ItemStack(m, amt, (short) dur);
	}
	
	public static int getTier(ItemStack i) {
		String name = i.getType().name();
		if(name.contains("LEATHER") || name.contains("WOOD"))
			return 1;
		if (name.contains("CHAINMAIL") || name.contains("STONE"))
			return 2;
		else if (name.contains("IRON"))
			return 3;
		else if (name.contains("DIAMOND"))
			return 4;
		else if (name.contains("GOLD"))
			return 5;
		return 0;
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
	
	public static ItemStack getRandomItem(int tier) {
		int r = (int) (Math.random() * types.length);
		if (r <= 1) {
			return Items.getRandomWeapon(tier, types[r]);
		} else {
			return Items.getRandomArmorPiece(tier, types[r]);
		}
	}
	
	public static ItemStack getRandomWeapon(int tier, String type) {
		String flag = Constants.randomWeaponFlag(tier);
		int diff = Constants.getMaxDamage(tier) - Constants.getMinDamage(tier);
		int m = (int) (Math.random() * diff);
		int max = Constants.getMinDamage(tier) + m;
		int min = Constants.getMinDamage(tier) + (int) (Math.random() * m);
		if (max < min)
			max = min;
		if (flag.contains("uncommon")) {
			min *= Constants.SCALE_UNC;
			max *= Constants.SCALE_UNC;
		}
		if (flag.contains("rare")) {
			min *= Constants.SCALE_RARE;
			max *= Constants.SCALE_RARE;
		}
		return Items.randomDurability(Items.createWeapon(type, tier, min, max, flag));
	}
	
	public static ItemStack getRandomArmorPiece(int tier, String type) {
		String s = type.substring(0, 1).toUpperCase();
		int m = Constants.getMaxHealth(tier, s) / 2 + (int) (Math.random() * (Constants.getMaxHealth(tier, s) / 2));
		String flag = Constants.randomArmorFlag(m, tier);
		if (flag.contains("uncommon")) {
			m *= Constants.SCALE_UNC;
		}
		if (flag.contains("rare")) {
			m *= Constants.SCALE_RARE;
		}
		return Items.randomDurability(Items.createArmor(type, tier, m, flag));
	}
	
	public static ItemStack randomDurability(ItemStack i) {
		int max = i.getType().getMaxDurability() - 1;
		i.setDurability((short) ((max / 4) + Math.random() * (max - (max / 4))));
		return i;
	}
	
	public static ItemStack createWeapon(String type, int tier, int min, int max, String flags) {
		Material m = Material.getMaterial(tools[tier - 1] + "_" + type.toUpperCase());
		String[] names = type.equals("sword") ? swords : axes;
		String name = nameColor[tier - 1] + names[tier - 1];
		String[] flag = flags.split(",");
		List<String> lore = new ArrayList<>();
		lore.add("Damage: " + min + "-" + max);
		name = getName(name, flag, lore);
		return createItem(m, 1, 0, name, lore);
	}
	
	public static ItemStack createArmor(String type, int tier, int maxhp, String flags) {
		Material m = Material.getMaterial(armor[tier - 1] + "_" + type.toUpperCase());
		String[] flag = flags.split(",");
		String name = nameColor[tier - 1] + armors[tier - 1] + " " + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
		List<String> lore = new ArrayList<String>();
		if (maxhp < 1)
			maxhp++;
		lore.add("Health: +" + maxhp);
		name = getName(name, flag, lore);
		return createItem(m, 1, 0, name, lore);
	}

	public static ItemStack createPickaxe(int level, int durability, String flags) {
		int tier = level / 20;
		Material m = Material.getMaterial(tools[tier] + "_PICKAXE");
		String name = nameColor[tier] + picks[tier] + " Pickaxe";
		String[] flag = flags.split(",");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Use often to level up.");
		lore.add(" ");
		lore.add(ChatColor.GRAY + "Level: " + ChatColor.AQUA + level);
		lore.add(ChatColor.GRAY + "XP: " + ChatColor.AQUA + "0 / " + Util.pickXP(level));
		name = getName(name, flag, lore);
		return createItem(m, 1, durability, name, lore);
	}
	
	private static String getName(String name, String[] flag, List<String> lore) {
		if (flag.length > 0) {
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
}